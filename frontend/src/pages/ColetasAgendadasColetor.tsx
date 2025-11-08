import { useEffect, useState } from "react";
import { EventoColeta } from "@/types/api";
import { listarColetasAgendadasColetor, confirmarEventoColeta } from "@/services/coletaService";
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

const ColetasAgendadasColetor = () => {
  const [coletasAgendadas, setColetasAgendadas] = useState<EventoColeta[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [eventoSelecionado, setEventoSelecionado] = useState<number | null>(null);
  const { toast } = useToast();

  useEffect(() => {
    const fetchColetas = async () => {
      try {
        // TODO: Get real coletor ID from auth context
        const coletorId = 1;
        const response = await listarColetasAgendadasColetor(coletorId);
        setColetasAgendadas(response);
      } catch (err) {
        setError('Erro ao carregar coletas agendadas');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchColetas();
  }, []);

  const handleConfirmarColeta = async (eventoId: number) => {
    setEventoSelecionado(eventoId);
  };

  const handleConfirmacao = async () => {
    if (!eventoSelecionado) return;

    try {
      await confirmarEventoColeta(eventoSelecionado);
      
      toast({
        title: "Coleta confirmada",
        description: "A coleta foi confirmada com sucesso.",
      });

      // Update local state to reflect the change
      setColetasAgendadas(prev => 
        prev.map(evento => 
          evento.id === eventoSelecionado 
            ? { ...evento, status: 'CONCLUIDA' } 
            : evento
        )
      );
    } catch (error) {
      toast({
        variant: "destructive",
        title: "Erro ao confirmar coleta",
        description: "Ocorreu um erro ao confirmar a coleta. Tente novamente.",
      });
    } finally {
      setEventoSelecionado(null);
    }
  };

  if (loading) return <div>Carregando coletas...</div>;
  if (error) return <div className="text-red-500">{error}</div>;

  return (
    <div className="max-w-5xl">
      <div className="mb-12">
        <div className="mb-6">
          <h2 className="text-3xl font-bold text-foreground mb-2">
            Minhas Coletas Agendadas
          </h2>
          <p className="text-muted-foreground">
            Acompanhe as coletas confirmadas pelos produtores
          </p>
        </div>

        <div className="grid gap-4 md:grid-cols-2">
          {coletasAgendadas.map((evento) => (
            <Card
              key={evento.id}
              className="bg-card hover:shadow-lg transition-shadow duration-200 border-primary/20"
            >
              <CardContent className="p-6 relative">
                <img 
                  src="/icone-coleta-1.png" 
                  alt="Ícone Coleta Agendada" 
                  className="absolute top-4 right-4 w-8 h-8 object-contain opacity-90"
                />
                
                <div className="flex items-start justify-between mb-4 pb-4 border-b border-border">
                  <div className="flex items-center gap-3">
                    <Avatar className="h-10 w-10 border-2 border-primary/20">
                      <AvatarFallback className="bg-primary-light text-primary font-medium">
                        {evento.produtor.nome.split(' ').map(n => n[0]).join('')}
                      </AvatarFallback>
                    </Avatar>
                    <div className="flex-1 min-w-0">
                      <p className="text-xs text-muted-foreground mb-0.5">Produtor</p>
                      <p className="font-medium text-foreground truncate">
                        {evento.produtor.nome}
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
                    onClick={() => handleConfirmarColeta(evento.id)}
                    className="w-full bg-primary hover:bg-primary-dark text-primary-foreground"
                  >
                    <CheckCircle className="w-4 h-4 mr-2" />
                    Confirmar Coleta
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
            <AlertDialogTitle>Confirmar Coleta</AlertDialogTitle>
            <AlertDialogDescription>
              Você quer mesmo confirmar essa Coleta?
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

export default ColetasAgendadasColetor;