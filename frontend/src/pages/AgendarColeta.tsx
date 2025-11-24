import { useEffect, useState } from "react";
import { EventoColeta, Coleta } from "@/types/api";
import { 
  listarColetasAgendadas, 
  buscarColetasPorBairro,
  criarEventoColeta,
  deletarEventoColeta
} from "@/services/coletaService";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent } from "@/components/ui/card";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { useNavigate } from "react-router-dom";
import { Badge } from "@/components/ui/badge";
import { Search, Calendar, MapPin, Trash2, ChevronLeft, ChevronRight, Coins } from "lucide-react";
import { useToast } from "@/components/ui/use-toast";
import { useAuth } from "@/contexts/AuthContext";

const AgendarColeta = () => {
  const [coletasAgendadas, setColetasAgendadas] = useState<EventoColeta[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [bairro, setBairro] = useState("");
  const [resultados, setResultados] = useState<Coleta[]>([]);
  const [buscaRealizada, setBuscaRealizada] = useState(false);
  const [loadingBusca, setLoadingBusca] = useState(false);
  const [currentPage, setCurrentPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [totalElements, setTotalElements] = useState(0);
  const navigate = useNavigate();
  const { toast } = useToast();
  const { user } = useAuth();

  useEffect(() => {
    const fetchColetas = async () => {
      if (!user) {
        setError('Usuário não autenticado');
        setLoading(false);
        return;
      }

      try {
        const response = await listarColetasAgendadas(user.pessoaId);
        setColetasAgendadas(response);
      } catch (err) {
        setError('Erro ao carregar coletas agendadas');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchColetas();
  }, [user]);

  const buscarEventos = async (page: number = 0) => {
    if (!bairro.trim()) return;
    
    setLoadingBusca(true);
    setBuscaRealizada(true);
    
    try {
      const response = await buscarColetasPorBairro(bairro.trim(), page, 4);
      setResultados(response.content);
      setCurrentPage(response.number);
      setTotalPages(response.totalPages);
      setTotalElements(response.totalElements);
    } catch (err) {
      console.error(err);
      setResultados([]);
      setTotalPages(0);
      setTotalElements(0);
    } finally {
      setLoadingBusca(false);
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      buscarEventos(0);
    }
  };

  const handlePreviousPage = () => {
    if (currentPage > 0) {
      buscarEventos(currentPage - 1);
    }
  };

  const handleNextPage = () => {
    if (currentPage < totalPages - 1) {
      buscarEventos(currentPage + 1);
    }
  };

  const calcularTotalMoedasVerdes = (coleta: Coleta): number => {
    return coleta.itensColeta.reduce((total, itemColeta) => {
      const valorMoedas = itemColeta.item.valorMoedas || 0;
      return total + (valorMoedas * itemColeta.quantidadeMinima);
    }, 0);
  };

  const selecionarEvento = async (coleta: Coleta) => {
    if (!user) {
      toast({
        variant: "destructive",
        title: "Erro",
        description: "Usuário não autenticado.",
      });
      return;
    }

    try {
      const eventoColeta = await criarEventoColeta(coleta.id, user.pessoaId);
      navigate("/declaracao-materiais", { 
        state: { 
          coleta,
          eventoColetaId: eventoColeta.id
        } 
      });
    } catch (error: any) {
      if (error.status === 400 && error.message) {
        toast({
          variant: "destructive",
          title: "Erro ao agendar coleta",
          description: error.message,
        });
      } else {
        toast({
          variant: "destructive",
          title: "Erro ao agendar coleta",
          description: "Ocorreu um erro ao tentar agendar a coleta. Tente novamente.",
        });
      }
      console.error('Erro ao criar evento de coleta:', error);
    }
  };

  const handleDelete = async (eventoId: number) => {
    try {
      await deletarEventoColeta(eventoId);
      setColetasAgendadas(prev => prev.filter(evento => evento.id !== eventoId));
      toast({
        title: "Coleta removida",
        description: "A coleta foi removida com sucesso.",
      });
    } catch (error) {
      toast({
        variant: "destructive",
        title: "Erro ao remover coleta",
        description: "Ocorreu um erro ao tentar remover a coleta. Tente novamente.",
      });
      console.error('Erro ao deletar evento de coleta:', error);
    }
  };

  return (
    <div className="max-w-5xl">
      {/* Coletas Agendadas */}
      {loading ? (
        <div>Carregando coletas...</div>
      ) : error ? (
        <div className="text-red-500">{error}</div>
      ) : coletasAgendadas.length > 0 && (
        <div className="mb-12">
          <div className="mb-6">
            <h2 className="text-3xl font-bold text-foreground mb-2">Minhas Coletas Agendadas</h2>
            <p className="text-muted-foreground">
              Acompanhe suas coletas confirmadas
            </p>
          </div>

          <div className="grid gap-4 md:grid-cols-2">
            {coletasAgendadas.map((evento) => (
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
                      alt="Ícone Coleta Agendada" 
                      className="w-8 h-8 object-contain opacity-90"
                    />
                  </div>
                  <div className="flex items-start justify-between mb-4 pb-4 border-b border-border">
                    <div className="flex items-center gap-3">
                      <Avatar className="h-10 w-10 border-2 border-primary/20">
                        <AvatarFallback className="bg-primary-light text-primary font-medium">
                          {evento.coleta.coletor.nome.split(' ').map(n => n[0]).join('')}
                        </AvatarFallback>
                      </Avatar>
                      <div className="flex-1 min-w-0">
                        <p className="text-xs text-muted-foreground mb-0.5">Coletor</p>
                        <p className="font-medium text-foreground truncate">
                          {evento.coleta.coletor.nome}
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
                          {new Date(evento.coleta.dataInicio).toLocaleDateString('pt-BR')}
                        </p>
                        <p className="text-sm text-muted-foreground">
                          {new Date(evento.coleta.dataInicio).toLocaleTimeString('pt-BR', {
                            hour: '2-digit',
                            minute: '2-digit'
                          })} às {new Date(evento.coleta.dataFim).toLocaleTimeString('pt-BR', {
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
                          {evento.coleta.pontoColeta.logradouro}, {evento.coleta.pontoColeta.numero}
                        </p>
                        <p className="text-sm text-muted-foreground">
                          {evento.coleta.pontoColeta.bairro}
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
                </CardContent>
              </Card>
            ))}
          </div>
        </div>
      )}

      {/* Agendar Nova Coleta */}
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-foreground mb-2">Agendar uma Coleta</h1>
        <p className="text-muted-foreground">
          Encontre coletores com rotas agendadas em sua região.
        </p>
      </div>

      <div className="mb-8">
        <div className="flex gap-3">
          <Input
            placeholder="Digite o nome do seu bairro..."
            value={bairro}
            onChange={(e) => setBairro(e.target.value)}
            onKeyPress={handleKeyPress}
            className="flex-1 h-12 text-base bg-background"
          />
          <Button
            onClick={() => buscarEventos(0)}
            className="h-12 px-6 bg-primary hover:bg-primary-dark"
            size="lg"
          >
            <Search className="h-5 w-5" />
          </Button>
        </div>
      </div>

      {buscaRealizada && (
        <div>
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-xl font-semibold text-foreground">
              Coletas encontradas em <span className="text-primary">{bairro}</span>
            </h2>
            {totalElements > 0 && (
              <p className="text-sm text-muted-foreground">
                {totalElements} {totalElements === 1 ? 'resultado' : 'resultados'}
              </p>
            )}
          </div>

          {loadingBusca ? (
            <div>Buscando coletas...</div>
          ) : resultados.length > 0 ? (
            <>
              <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
                {resultados.map((coleta) => {
                  const totalMoedasVerdes = calcularTotalMoedasVerdes(coleta);
                  
                  return (
                    <Card
                      key={coleta.id}
                      className="bg-card hover:shadow-lg transition-shadow duration-200 border-border/50 flex flex-col"
                    >
                      <CardContent className="p-6 relative flex flex-col flex-1">
                        <img 
                          src="/icone-coleta-2.png" 
                          alt="Ícone Coleta Disponível" 
                          className="absolute top-4 right-4 w-8 h-8 object-contain opacity-90"
                        />
                        
                        {/* Card Content */}
                        <div className="flex-1">
                          <div className="flex items-center gap-3 mb-4 pb-4 border-b border-border">
                            <Avatar className="h-10 w-10 border-2 border-primary/20">
                              <AvatarFallback className="bg-primary-light text-primary font-medium">
                                {coleta.coletor.nome.split(' ').map(n => n[0]).join('')}
                              </AvatarFallback>
                            </Avatar>
                            <div className="flex-1 min-w-0">
                              <p className="text-xs text-muted-foreground mb-0.5">Coletor</p>
                              <p className="font-medium text-foreground truncate">
                                {coleta.coletor.nome}
                              </p>
                            </div>
                          </div>

                          {/* Cotação de Moedas Verdes */}
                          {totalMoedasVerdes > 0 && (
                            <div className="mb-4 p-3 bg-green-50 border border-green-200 rounded-lg">
                              <div className="flex items-center gap-2">
                                <Coins className="h-4 w-4 text-green-600" />
                                <div>
                                  <p className="text-xs text-green-700 font-medium">Moedas Verdes</p>
                                  <p className="text-sm font-bold text-green-700">
                                    Mínimo de {totalMoedasVerdes.toLocaleString('pt-BR')} créditos
                                  </p>
                                </div>
                              </div>
                            </div>
                          )}

                          <div className="space-y-3 mb-6">
                            <div className="flex items-start gap-3">
                              <Calendar className="h-5 w-5 text-primary mt-0.5 flex-shrink-0" />
                              <div>
                                <p className="text-xs text-muted-foreground mb-0.5">Data e Horário</p>
                                <p className="text-sm font-medium text-foreground">
                                  {new Date(coleta.dataInicio).toLocaleDateString('pt-BR')}
                                </p>
                                <p className="text-sm text-muted-foreground">
                                  {new Date(coleta.dataInicio).toLocaleTimeString('pt-BR', {
                                    hour: '2-digit',
                                    minute: '2-digit'
                                  })} às {new Date(coleta.dataFim).toLocaleTimeString('pt-BR', {
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
                                  {coleta.pontoColeta.logradouro}, {coleta.pontoColeta.numero}
                                </p>
                                <p className="text-sm text-muted-foreground">
                                  {coleta.pontoColeta.bairro}
                                </p>
                              </div>
                            </div>
                          </div>

                          <div className="pt-3 border-t border-border mb-4">
                            <p className="text-xs text-muted-foreground mb-2">Materiais aceitos:</p>
                            <div className="flex flex-wrap gap-2">
                              {coleta.itensColeta.map((itemColeta) => (
                                <Badge 
                                  key={itemColeta.id} 
                                  variant="secondary"
                                  className="bg-accent/10 text-foreground"
                                >
                                  {itemColeta.item.nome} (min: {itemColeta.quantidadeMinima} {itemColeta.item.unidade === 'unidade' 
                                    ? (itemColeta.quantidadeMinima > 1 ? 'unidades' : 'unidade')
                                    : itemColeta.item.unidade})
                                </Badge>
                              ))}
                            </div>
                          </div>
                        </div>

                        {/* Button at bottom */}
                        <div className="mt-auto pt-4">
                          <Button
                            onClick={() => selecionarEvento(coleta)}
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

              {/* Paginação */}
              {totalPages > 1 && (
                <div className="flex items-center justify-center gap-2 mt-8">
                  <Button
                    variant="outline"
                    size="icon"
                    onClick={handlePreviousPage}
                    disabled={currentPage === 0 || loadingBusca}
                  >
                    <ChevronLeft className="h-4 w-4" />
                  </Button>
                  
                  <div className="flex items-center gap-2">
                    <span className="text-sm text-muted-foreground">
                      Página {currentPage + 1} de {totalPages}
                    </span>
                  </div>

                  <Button
                    variant="outline"
                    size="icon"
                    onClick={handleNextPage}
                    disabled={currentPage >= totalPages - 1 || loadingBusca}
                  >
                    <ChevronRight className="h-4 w-4" />
                  </Button>
                </div>
              )}
            </>
          ) : (
            <Card className="p-8 text-center bg-card">
              <p className="text-muted-foreground">
                Nenhuma coleta encontrada para este bairro.
              </p>
            </Card>
          )}
        </div>
      )}
    </div>
  );
};

export default AgendarColeta;
