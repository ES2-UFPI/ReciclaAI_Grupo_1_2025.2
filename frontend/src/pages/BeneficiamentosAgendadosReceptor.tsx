import { useEffect, useState } from "react";
import { EventoBeneficiamento } from "@/types/api";
import { listarBeneficiamentosAgendadosReceptor, confirmarEventoBeneficiamento } from "@/services/beneficiamentoService";
import { Card, CardContent } from "@/components/ui/card";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Badge } from "@/components/ui/badge";
import { Calendar, MapPin } from "lucide-react";
import { Button } from "@/components/ui/button";
import { CheckCircle } from "lucide-react";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import { useToast } from "@/components/ui/use-toast";
import { useAuth } from "@/contexts/AuthContext";

const getStatusColor = (status: 'AGENDADA' | 'CANCELADA' | 'CONCLUIDA') => {
  switch (status) {
    case 'AGENDADA':
      return 'bg-yellow-500/10 text-yellow-500';
    case 'CANCELADA':
      return 'bg-destructive/10 text-destructive';
    case 'CONCLUIDA':
      return 'bg-green-500/10 text-green-500';
    default:
      return 'bg-accent/10 text-accent';
  }
};

const BeneficiamentosAgendadosReceptor = () => {
  const [beneficiamentosAgendados, setBeneficiamentosAgendados] = useState<EventoBeneficiamento[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [eventoSelecionado, setEventoSelecionado] = useState<number | null>(null);
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
        const response = await listarBeneficiamentosAgendadosReceptor(user.pessoaId);
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

  const handleConfirmarBeneficiamento = async (eventoId: number) => {
    setEventoSelecionado(eventoId);
  };

  const handleConfirmacao = async () => {
    if (!eventoSelecionado) return;

    try {
      await confirmarEventoBeneficiamento(eventoSelecionado);
      
      toast({
        title: "Beneficiamento confirmado",
        description: "O beneficiamento foi confirmado com sucesso.",
      });

      setBeneficiamentosAgendados(prev => 
        prev.map(evento => 
          evento.id === eventoSelecionado 
            ? { ...evento, status: 'CONCLUIDA' } 
            : evento
        )
      );
    } catch (error: any) {
      toast({
        variant: "destructive",
        title: "Erro ao confirmar beneficiamento",
        description: error.message || "Ocorreu um erro ao confirmar o beneficiamento. Tente novamente.",
      });
    } finally {
      setEventoSelecionado(null);
    }
  };

  if (loading) return <div>Carregando beneficiamentos...</div>;
  if (error) return <div className="text-red-500">{error}</div>;

  return (
    <div className="max-w-5xl">
      <div className="mb-12">
        <div className="mb-6">
          <h2 className="text-3xl font-bold text-foreground mb-2">
            Meus Beneficiamentos Agendados
          </h2>
          <p className="text-muted-foreground">
            Acompanhe os beneficiamentos confirmados pelos coletores
          </p>
        </div>

        <div className="grid gap-4 md:grid-cols-2">
          {beneficiamentosAgendados.map((evento) => (
            <Card
              key={evento.id}
              className="bg-card hover:shadow-lg transition-shadow duration-200 border-primary/20"
            >
              <CardContent className="p-6 relative">
                <div className="flex items-start justify-between mb-4 pb-4 border-b border-border">
                  <div className="flex items-center gap-3">
                    <Avatar className="h-10 w-10 border-2 border-primary/20">
                      <AvatarFallback className="bg-primary-light text-primary font-medium">
                        {evento.coletor.nome.split(' ').map(n => n[0]).join('')}
                      </AvatarFallback>
                    </Avatar>
                    <div className="flex-1 min-w-0">
                      <p className="text-xs text-muted-foreground mb-0.5">Coletor</p>
                      <p className="font-medium text-foreground truncate">
                        {evento.coletor.nome}
                      </p>
                    </div>
                  </div>
                  <div className="flex items-center gap-2">
                    <Badge 
                      variant="secondary"
                      className={getStatusColor(evento.status)}
                    >
                      {evento.status}
                    </Badge>
                    <img 
                      src="/icone-coleta-1.png" 
                      alt="Ícone Beneficiamento Agendado" 
                      className="w-8 h-8 object-contain opacity-90"
                    />
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
                  <div className="flex flex-wrap gap-2 mb-4">
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

                  <Button
                    onClick={() => handleConfirmarBeneficiamento(evento.id)}
                    className="w-full bg-primary hover:bg-primary-dark text-primary-foreground"
                    disabled={evento.status === 'CONCLUIDA'}
                  >
                    <CheckCircle className="w-4 h-4 mr-2" />
                    Confirmar Beneficiamento
                  </Button>
                </div>
              </CardContent>
            </Card>
          ))}
        </div>
      </div>

      <AlertDialog open={eventoSelecionado !== null} onOpenChange={() => setEventoSelecionado(null)}>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>Confirmar Beneficiamento</AlertDialogTitle>
            <AlertDialogDescription>
              Você quer mesmo confirmar esse Beneficiamento?
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>Não</AlertDialogCancel>
            <AlertDialogAction onClick={handleConfirmacao}>
              Sim
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  );
};

export default BeneficiamentosAgendadosReceptor;