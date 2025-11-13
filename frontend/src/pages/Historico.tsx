import { useEffect, useState } from "react";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Calendar, MapPin, User, ChevronDown, ChevronUp } from "lucide-react";
import { useAuth } from "@/contexts/AuthContext";
import { listarHistorico, EventoHistorico } from "@/services/historicoService";
import { Button } from "@/components/ui/button";

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

const getTipoEventoColor = (tipo: 'COLETA' | 'BENEFICIAMENTO') => {
  return tipo === 'COLETA' ? 'bg-blue-500/10 text-blue-500' : 'bg-purple-500/10 text-purple-500';
};

const Historico = () => {
  const [eventos, setEventos] = useState<EventoHistorico[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [expandedIds, setExpandedIds] = useState<Set<number>>(new Set());
  const { user } = useAuth();

  useEffect(() => {
    const fetchHistorico = async () => {
      if (!user) {
        setError('Usuário não autenticado');
        setLoading(false);
        return;
      }

      try {
        const data = await listarHistorico(user.pessoaId, user.tipoPessoa);
        setEventos(data);
      } catch (err) {
        setError('Erro ao carregar histórico');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchHistorico();
  }, [user]);

  const toggleExpand = (eventoId: number) => {
    setExpandedIds(prev => {
      const newSet = new Set(prev);
      if (newSet.has(eventoId)) {
        newSet.delete(eventoId);
      } else {
        newSet.add(eventoId);
      }
      return newSet;
    });
  };

  if (loading) return <div>Carregando histórico...</div>;
  if (error) return <div className="text-red-500">{error}</div>;

  return (
    <div className="max-w-6xl">
      <div className="mb-6">
        <h1 className="text-3xl font-bold text-foreground mb-2">Histórico</h1>
        <p className="text-muted-foreground">
          Acompanhe o histórico de todos os seus eventos
        </p>
      </div>

      {eventos.length > 0 ? (
        <Card>
          <CardContent className="p-0">
            <div className="divide-y divide-border">
              {eventos.map((evento) => {
                const isExpanded = expandedIds.has(evento.eventoId);
                return (
                  <div
                    key={evento.eventoId}
                    className="hover:bg-accent/5 transition-colors"
                  >
                    <div
                      className="flex items-center gap-4 p-4 cursor-pointer"
                      onClick={() => toggleExpand(evento.eventoId)}
                    >
                      {/* Data */}
                      <div className="flex items-center gap-2 min-w-[140px]">
                        <Calendar className="h-4 w-4 text-muted-foreground" />
                        <div>
                          <p className="text-sm font-medium">
                            {new Date(evento.data).toLocaleDateString('pt-BR', {
                              day: '2-digit',
                              month: '2-digit',
                              year: '2-digit'
                            })}
                          </p>
                          <p className="text-xs text-muted-foreground">
                            {new Date(evento.data).toLocaleTimeString('pt-BR', {
                              hour: '2-digit',
                              minute: '2-digit'
                            })}
                          </p>
                        </div>
                      </div>

                      {/* Tipo e Status */}
                      <div className="flex gap-2 min-w-[200px]">
                        <Badge 
                          variant="secondary"
                          className={getTipoEventoColor(evento.tipoEvento)}
                        >
                          {evento.tipoEvento}
                        </Badge>
                        <Badge 
                          variant="secondary"
                          className={getStatusColor(evento.status)}
                        >
                          {evento.status}
                        </Badge>
                      </div>

                      {/* Participante */}
                      <div className="flex items-center gap-2 flex-1 min-w-0">
                        <User className="h-4 w-4 text-muted-foreground flex-shrink-0" />
                        <p className="text-sm truncate">{evento.nomeParticipante}</p>
                      </div>

                      {/* Local */}
                      <div className="flex items-center gap-2 flex-1 min-w-0">
                        <MapPin className="h-4 w-4 text-muted-foreground flex-shrink-0" />
                        <p className="text-sm truncate">{evento.nomeLocal}</p>
                      </div>

                      {/* Quantidade de itens */}
                      <div className="text-sm text-muted-foreground min-w-[80px] text-right">
                        {evento.itens.length} {evento.itens.length === 1 ? 'item' : 'itens'}
                      </div>

                      {/* Expand icon */}
                      <Button variant="ghost" size="icon" className="h-8 w-8">
                        {isExpanded ? (
                          <ChevronUp className="h-4 w-4" />
                        ) : (
                          <ChevronDown className="h-4 w-4" />
                        )}
                      </Button>
                    </div>

                    {/* Detalhes expandidos */}
                    {isExpanded && (
                      <div className="px-4 pb-4 pt-0 bg-accent/5">
                        <div className="border-t border-border pt-3">
                          <p className="text-xs text-muted-foreground mb-2">Materiais:</p>
                          <div className="flex flex-wrap gap-2">
                            {evento.itens.map((item, index) => (
                              <Badge 
                                key={index}
                                variant="secondary"
                                className="bg-background"
                              >
                                {item}
                              </Badge>
                            ))}
                          </div>
                        </div>
                      </div>
                    )}
                  </div>
                );
              })}
            </div>
          </CardContent>
        </Card>
      ) : (
        <Card className="p-8 text-center bg-card">
          <p className="text-muted-foreground">
            Nenhum evento encontrado no histórico.
          </p>
        </Card>
      )}
    </div>
  );
};

export default Historico;
