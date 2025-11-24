import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent } from "@/components/ui/card";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { useNavigate } from "react-router-dom";
import { Badge } from "@/components/ui/badge";
import { Search, Calendar, MapPin, Trash2, DollarSign } from "lucide-react";
import { useToast } from "@/components/ui/use-toast";
import { useAuth } from "@/contexts/AuthContext";
import { 
  listarBeneficiamentosAgendadosColetor,
  buscarBeneficiamentosPorBairro,
  criarEventoBeneficiamento,
  deletarEventoBeneficiamento
} from "@/services/beneficiamentoService";
import { EventoBeneficiamento, Beneficiamento } from "@/types/api";

const AgendarBeneficiamento = () => {
  const [beneficiamentosAgendados, setBeneficiamentosAgendados] = useState<EventoBeneficiamento[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [bairro, setBairro] = useState("");
  const [resultados, setResultados] = useState<Beneficiamento[]>([]);
  const [buscaRealizada, setBuscaRealizada] = useState(false);
  const [loadingBusca, setLoadingBusca] = useState(false);
  const navigate = useNavigate();
  const { toast } = useToast();
  const { user } = useAuth();

  useEffect(() => {
    const fetchBeneficiamentos = async () => {
      if (!user) {
        setError('Usuário não autenticado');
        setLoading(false);
        return;
      }

      try {
        const response = await listarBeneficiamentosAgendadosColetor(user.pessoaId);
        setBeneficiamentosAgendados(response);
      } catch (err) {
        setError('Erro ao carregar beneficiamentos agendados');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchBeneficiamentos();
  }, [user]);

  const buscarEventos = async () => {
    if (!bairro.trim()) return;
    
    setLoadingBusca(true);
    setBuscaRealizada(true);
    
    try {
      const response = await buscarBeneficiamentosPorBairro(bairro.trim());
      setResultados(response.content);
    } catch (err) {
      console.error(err);
      setResultados([]);
    } finally {
      setLoadingBusca(false);
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      buscarEventos();
    }
  };

  const selecionarEvento = async (beneficiamento: Beneficiamento) => {
    if (!user) {
      toast({
        variant: "destructive",
        title: "Erro",
        description: "Usuário não autenticado.",
      });
      return;
    }

    try {
      const eventoBeneficiamento = await criarEventoBeneficiamento(beneficiamento.id, user.pessoaId);
      navigate("/declaracao-materiais-beneficiamento", { 
        state: { 
          beneficiamento,
          eventoBeneficiamentoId: eventoBeneficiamento.id
        } 
      });
    } catch (error: any) {
      if (error.status === 400 && error.message) {
        toast({
          variant: "destructive",
          title: "Erro ao agendar beneficiamento",
          description: error.message,
        });
      } else {
        toast({
          variant: "destructive",
          title: "Erro ao agendar beneficiamento",
          description: "Ocorreu um erro ao tentar agendar o beneficiamento. Tente novamente.",
        });
      }
      console.error('Erro ao criar evento de beneficiamento:', error);
    }
  };

  const handleDelete = async (eventoId: number) => {
    try {
      await deletarEventoBeneficiamento(eventoId);
      setBeneficiamentosAgendados(prev => prev.filter(evento => evento.id !== eventoId));
      toast({
        title: "Beneficiamento removido",
        description: "O beneficiamento foi removido com sucesso.",
      });
    } catch (error) {
      toast({
        variant: "destructive",
        title: "Erro ao remover beneficiamento",
        description: "Ocorreu um erro ao tentar remover o beneficiamento. Tente novamente.",
      });
      console.error('Erro ao deletar evento de beneficiamento:', error);
    }
  };

  const calcularPrecoMinimo = (beneficiamento: Beneficiamento): number => {
    return beneficiamento.itensBeneficiamento.reduce((total, itemBenef) => {
      const valor = itemBenef.valor || 0;
      return total + (valor * itemBenef.quantidadeMinima);
    }, 0);
  };

  return (
    <div className="max-w-5xl">
      {/* Beneficiamentos Agendados */}
      {loading ? (
        <div>Carregando beneficiamentos...</div>
      ) : error ? (
        <div className="text-red-500">{error}</div>
      ) : beneficiamentosAgendados.length > 0 && (
        <div className="mb-12">
          <div className="mb-6">
            <h2 className="text-3xl font-bold text-foreground mb-2">Meus Beneficiamentos Agendados</h2>
            <p className="text-muted-foreground">
              Acompanhe suas entregas confirmadas
            </p>
          </div>

          <div className="grid gap-4 md:grid-cols-2">
            {beneficiamentosAgendados.map((evento) => {
              const precoMinimo = calcularPrecoMinimo(evento.beneficiamento);
              
              return (
                <Card
                  key={evento.id}
                  className="bg-card hover:shadow-lg transition-shadow duration-200 border-primary/20"
                >
                  <CardContent className="p-6 relative">
                    <div className="absolute top-4 right-4 flex items-center gap-2">
                      <Button
                        variant="ghost"
                        size="icon"
                        className="h-8 w-8 text-destructive hover:text-destructive hover:bg-destructive/10"
                        onClick={() => handleDelete(evento.id)}
                      >
                        <Trash2 className="h-4 w-4" />
                      </Button>
                      <img 
                        src="/icone-coleta-1.png" 
                        alt="Ícone Beneficiamento Agendado" 
                        className="w-8 h-8 object-contain opacity-90"
                      />
                    </div>
                    <div className="flex items-start justify-between mb-4 pb-4 border-b border-border">
                      <div className="flex items-center gap-3">
                        <Avatar className="h-10 w-10 border-2 border-primary/20">
                          <AvatarFallback className="bg-primary-light text-primary font-medium">
                            {evento.beneficiamento.receptor.nome.split(' ').map(n => n[0]).join('')}
                          </AvatarFallback>
                        </Avatar>
                        <div className="flex-1 min-w-0">
                          <p className="text-xs text-muted-foreground mb-0.5">Receptor</p>
                          <p className="font-medium text-foreground truncate">
                            {evento.beneficiamento.receptor.nome}
                          </p>
                        </div>
                      </div>
                    </div>

                    <div className="space-y-3 mb-4">
                      <div className="flex items-start gap-3">
                        <Calendar className="h-5 w-5 text-primary mt-0.5 flex-shrink-0" />
                        <div>
                          <p className="text-xs text-muted-foreground mb-0.5">Data e Horário</p>
                          <p className="font-medium text-foreground">
                            {new Date(evento.beneficiamento.dataInicio).toLocaleDateString('pt-BR')}
                          </p>
                          <p className="text-sm text-muted-foreground">
                            {new Date(evento.beneficiamento.dataInicio).toLocaleTimeString('pt-BR', {
                              hour: '2-digit',
                              minute: '2-digit'
                            })} às {new Date(evento.beneficiamento.dataFim).toLocaleTimeString('pt-BR', {
                              hour: '2-digit',
                              minute: '2-digit'
                            })}
                          </p>
                        </div>
                      </div>

                      <div className="flex items-start gap-3">
                        <MapPin className="h-5 w-5 text-primary mt-0.5 flex-shrink-0" />
                        <div>
                          <p className="text-xs text-muted-foreground mb-0.5">Local</p>
                          <p className="text-sm font-medium text-foreground">
                            {evento.beneficiamento.pontoColeta.logradouro}, {evento.beneficiamento.pontoColeta.numero}
                          </p>
                          <p className="text-sm text-muted-foreground">
                            {evento.beneficiamento.pontoColeta.bairro}
                          </p>
                        </div>
                      </div>
                    </div>

                    <div className="pt-3 border-t border-border">
                      <p className="text-xs text-muted-foreground mb-2">Materiais informados:</p>
                      <div className="flex flex-wrap gap-2">
                        {evento.itens.map((item) => (
                          <Badge 
                            key={item.id} 
                            variant="secondary"
                            className="bg-accent/10 text-foreground"
                          >
                            {item.item.nome} ({item.quantidade} {item.item.unidade === 'unidade' 
                              ? (item.quantidade > 1 ? 'unidades' : 'unidade')
                              : item.item.unidade})
                          </Badge>
                        ))}
                      </div>
                    </div>

                    <div className="pt-3 border-t border-border mt-3">
                      <div className="flex items-center gap-2 bg-green-50 p-3 rounded-lg">
                        <DollarSign className="h-5 w-5 text-green-600" />
                        <div>
                          <p className="text-xs text-green-600 font-medium">Preço Mínimo</p>
                          <p className="text-lg font-bold text-green-700">
                            R$ {precoMinimo.toFixed(2)}
                          </p>
                        </div>
                      </div>
                    </div>
                  </CardContent>
                </Card>
              );
            })}
          </div>
        </div>
      )}

      {/* Agendar Novo Beneficiamento */}
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-foreground mb-2">Agendar um Beneficiamento</h1>
        <p className="text-muted-foreground">
          Encontre receptores com beneficiamentos disponíveis em sua região.
        </p>
      </div>

      <div className="mb-8">
        <div className="flex gap-3">
          <Input
            placeholder="Digite o nome do bairro..."
            value={bairro}
            onChange={(e) => setBairro(e.target.value)}
            onKeyPress={handleKeyPress}
            className="flex-1 h-12 text-base bg-background"
          />
          <Button
            onClick={buscarEventos}
            className="h-12 px-6 bg-primary hover:bg-primary-dark"
            size="lg"
          >
            <Search className="h-5 w-5" />
          </Button>
        </div>
      </div>

      {buscaRealizada && (
        <div>
          <h2 className="text-xl font-semibold mb-6 text-foreground">
            Beneficiamentos encontrados em <span className="text-primary">{bairro}</span>
          </h2>

          {loadingBusca ? (
            <div>Buscando beneficiamentos...</div>
          ) : resultados.length > 0 ? (
            <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
              {resultados.map((beneficiamento) => {
                const precoMinimo = calcularPrecoMinimo(beneficiamento);
                
                return (
                  <Card
                    key={beneficiamento.id}
                    className="bg-card hover:shadow-lg transition-shadow duration-200 border-border/50 flex flex-col"
                  >
                    <CardContent className="p-6 relative flex flex-col flex-1">
                      <img 
                        src="/icone-coleta-2.png" 
                        alt="Ícone Beneficiamento Disponível" 
                        className="absolute top-4 right-4 w-8 h-8 object-contain opacity-90"
                      />
                      
                      {/* Card Content */}
                      <div className="flex-1">
                        <div className="flex items-center gap-3 mb-4 pb-4 border-b border-border">
                          <Avatar className="h-10 w-10 border-2 border-primary/20">
                            <AvatarFallback className="bg-primary-light text-primary font-medium">
                              {beneficiamento.receptor.nome.split(' ').map(n => n[0]).join('')}
                            </AvatarFallback>
                          </Avatar>
                          <div className="flex-1 min-w-0">
                            <p className="text-xs text-muted-foreground mb-0.5">Receptor</p>
                            <p className="font-medium text-foreground truncate">
                              {beneficiamento.receptor.nome}
                            </p>
                          </div>
                        </div>

                        <div className="space-y-3 mb-6">
                          <div className="flex items-start gap-3">
                            <Calendar className="h-5 w-5 text-primary mt-0.5 flex-shrink-0" />
                            <div>
                              <p className="text-xs text-muted-foreground mb-0.5">Data e Horário</p>
                              <p className="text-sm font-medium text-foreground">
                                {new Date(beneficiamento.dataInicio).toLocaleDateString('pt-BR')}
                              </p>
                              <p className="text-sm text-muted-foreground">
                                {new Date(beneficiamento.dataInicio).toLocaleTimeString('pt-BR', {
                                  hour: '2-digit',
                                  minute: '2-digit'
                                })} às {new Date(beneficiamento.dataFim).toLocaleTimeString('pt-BR', {
                                  hour: '2-digit',
                                  minute: '2-digit'
                                })}
                              </p>
                            </div>
                          </div>

                          <div className="flex items-start gap-3">
                            <MapPin className="h-5 w-5 text-primary mt-0.5 flex-shrink-0" />
                            <div>
                              <p className="text-xs text-muted-foreground mb-0.5">Endereço</p>
                              <p className="text-sm font-medium text-foreground">
                                {beneficiamento.pontoColeta.logradouro}, {beneficiamento.pontoColeta.numero}
                              </p>
                              <p className="text-sm text-muted-foreground">
                                {beneficiamento.pontoColeta.bairro}
                              </p>
                            </div>
                          </div>
                        </div>

                        <div className="pt-3 border-t border-border mb-4">
                          <p className="text-xs text-muted-foreground mb-2">Materiais aceitos:</p>
                          <div className="flex flex-wrap gap-2">
                            {beneficiamento.itensBeneficiamento.map((itemBeneficiamento) => (
                              <Badge 
                                key={itemBeneficiamento.id} 
                                variant="secondary"
                                className="bg-accent/10 text-foreground"
                              >
                                {itemBeneficiamento.item.nome} (min: {itemBeneficiamento.quantidadeMinima} {itemBeneficiamento.item.unidade === 'unidade' 
                                  ? (itemBeneficiamento.quantidadeMinima > 1 ? 'unidades' : 'unidade')
                                  : itemBeneficiamento.item.unidade})
                              </Badge>
                            ))}
                          </div>
                        </div>

                        <div className="pt-3 border-t border-border mb-4">
                          <div className="flex items-center gap-2 bg-green-50 p-3 rounded-lg">
                            <DollarSign className="h-5 w-5 text-green-600" />
                            <div>
                              <p className="text-xs text-green-600 font-medium">Preço Mínimo</p>
                              <p className="text-lg font-bold text-green-700">
                                R$ {precoMinimo.toFixed(2)}
                              </p>
                            </div>
                          </div>
                        </div>
                      </div>

                      {/* Button at bottom */}
                      <div className="mt-auto pt-4">
                        <Button
                          onClick={() => selecionarEvento(beneficiamento)}
                          className="w-full bg-accent hover:bg-accent-hover text-accent-foreground font-medium"
                        >
                          Selecionar e Informar Materiais
                        </Button>
                      </div>
                    </CardContent>
                  </Card>
                );
              })}
            </div>
          ) : (
            <Card className="p-8 text-center bg-card">
              <p className="text-muted-foreground">
                Nenhum beneficiamento encontrado para este bairro.
              </p>
            </Card>
          )}
        </div>
      )}
    </div>
  );
};

export default AgendarBeneficiamento;
