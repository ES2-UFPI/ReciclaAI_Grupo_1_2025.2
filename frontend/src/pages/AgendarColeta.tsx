import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent } from "@/components/ui/card";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { useNavigate } from "react-router-dom";
import { Badge } from "@/components/ui/badge";
import { Search, Calendar, Clock, MapPin, CheckCircle2 } from "lucide-react";

interface Evento {
  id: string;
  coletor: string;
  coletorIniciais: string;
  data: string;
  horario: string;
  bairro: string;
  endereco: string;
  pontoReferencia: string;
}

interface ColetaAgendada extends Evento {
  materiais: string[];
  status: "confirmada" | "pendente";
}

const defaultColetasAgendadas: ColetaAgendada[] = [
  {
    id: "agenda-1",
    coletor: "Maria Silva",
    coletorIniciais: "MS",
    data: "23 de Outubro de 2025",
    horario: "Das 10:00 às 13:00",
    bairro: "Centro",
    materiais: ["Tomates", "Alface", "Cenouras"],
    status: "confirmada",
    endereco: "Rua Álvaro Mendes, 1342, Centro",
    pontoReferencia: "Próximo à Praça Rio Branco"
  },
];

const eventosDisponiveis: Evento[] = [
  {
    id: "1",
    coletor: "Carlos Andrade",
    coletorIniciais: "CA",
    data: "20 de Outubro de 2025",
    horario: "Das 09:00 às 12:00",
    bairro: "Centro",
    endereco: "Rua Álvaro Mendes, 1342, Centro",
    pontoReferencia: "Próximo à Praça Rio Branco"
  },
  {
    id: "2",
    coletor: "Ana Santos",
    coletorIniciais: "AS",
    data: "22 de Outubro de 2025",
    horario: "Das 14:00 às 17:00",
    bairro: "Centro",
    endereco: "Rua Coelho Rodrigues, 875, Centro",
    pontoReferencia: "Ao lado do Theatro 4 de Setembro"
  },
  {
    id: "3",
    coletor: "Roberto Lima",
    coletorIniciais: "RL",
    data: "25 de Outubro de 2025",
    horario: "Das 08:00 às 11:00",
    bairro: "Centro",
    endereco: "Rua Simplício Mendes, 234, Centro",
    pontoReferencia: "Próximo ao Mercado Central"
  }
];

const AgendarColeta = () => {
  // carregar agendamentos do localStorage (se houver) ou usar default
  const [coletasAgendadas, setColetasAgendadas] = useState<ColetaAgendada[]>(() => {
    try {
      const raw = localStorage.getItem("coletasAgendadas");
      return raw ? JSON.parse(raw) : defaultColetasAgendadas;
    } catch {
      return defaultColetasAgendadas;
    }
  });
  const [bairro, setBairro] = useState("");
  const [resultados, setResultados] = useState<Evento[]>([]);
  const [buscaRealizada, setBuscaRealizada] = useState(false);
  const navigate = useNavigate();

  const buscarEventos = () => {
    if (bairro.trim()) {
      setResultados(eventosDisponiveis);
      setBuscaRealizada(true);
    }
  };

  const handleKeyPress = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      buscarEventos();
    }
  };

  const selecionarEvento = (evento: Evento) => {
    navigate("/declaracao-materiais", { state: { evento } });
  };

  return (
    <div className="max-w-5xl">
            {/* Coletas Agendadas */}
      {coletasAgendadas.length > 0 && (
        <div className="mb-12">
          <div className="mb-6">
            <h2 className="text-2xl font-bold text-foreground mb-2">Minhas Coletas Agendadas</h2>
            <p className="text-muted-foreground">
              Acompanhe suas coletas confirmadas
            </p>
          </div>

          <div className="grid gap-4 md:grid-cols-2">
            {coletasAgendadas.map((coleta) => (
              <Card
                key={coleta.id}
                className="bg-card hover:shadow-lg transition-shadow duration-200 border-primary/20"
              >
                <CardContent className="p-6">
                  <div className="flex items-start justify-between mb-4 pb-4 border-b border-border">
                    <div className="flex items-center gap-3">
                      <Avatar className="h-10 w-10 border-2 border-primary/20">
                        <AvatarFallback className="bg-primary-light text-primary font-medium">
                          {coleta.coletorIniciais}
                        </AvatarFallback>
                      </Avatar>
                      <div className="flex-1 min-w-0">
                        <p className="text-xs text-muted-foreground mb-0.5">Coletor</p>
                        <p className="font-medium text-foreground truncate">
                          {coleta.coletor}
                        </p>
                      </div>
                    </div>
                    <Badge 
                      variant="outline" 
                      className="bg-primary/10 text-primary border-primary/20"
                    >
                      <CheckCircle2 className="h-3 w-3 mr-1" />
                      {coleta.status === "confirmada" ? "Confirmada" : "Pendente"}
                    </Badge>
                  </div>

                  <div className="space-y-3 mb-4">
                    <div className="flex items-start gap-3">
                      <Calendar className="h-5 w-5 text-primary mt-0.5 flex-shrink-0" />
                      <div>
                        <p className="text-xs text-muted-foreground mb-0.5">Data</p>
                        <p className="text-sm font-medium text-foreground">
                          {coleta.data}
                        </p>
                      </div>
                    </div>

                    <div className="flex items-start gap-3">
                      <Clock className="h-5 w-5 text-primary mt-0.5 flex-shrink-0" />
                      <div>
                        <p className="text-xs text-muted-foreground mb-0.5">Horário</p>
                        <p className="text-sm font-medium text-foreground">
                          {coleta.horario}
                        </p>
                      </div>
                    </div>

                    <div className="flex items-start gap-3">
                      <MapPin className="h-5 w-5 text-primary mt-0.5 flex-shrink-0" />
                      <div>
                        <p className="text-xs text-muted-foreground mb-0.5">Local</p>
                        <p className="text-sm font-medium text-foreground">
                          {coleta.bairro}
                        </p>
                      </div>
                    </div>
                  </div>

                  <div className="pt-3 border-t border-border">
                    <p className="text-xs text-muted-foreground mb-2">Materiais informados:</p>
                    <div className="flex flex-wrap gap-2">
                      {coleta.materiais.map((material, index) => (
                        <Badge 
                          key={index} 
                          variant="secondary"
                          className="bg-accent/10 text-foreground"
                        >
                          {material}
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
            Eventos encontrados em <span className="text-primary">{bairro}</span>
          </h2>

          {resultados.length > 0 ? (
            <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
              {resultados.map((evento) => (
                <Card
                  key={evento.id}
                  className="bg-card hover:shadow-lg transition-shadow duration-200 border-border/50"
                >
                  <CardContent className="p-6">
                    <div className="flex items-center gap-3 mb-4 pb-4 border-b border-border">
                      <Avatar className="h-10 w-10 border-2 border-primary/20">
                        <AvatarFallback className="bg-primary-light text-primary font-medium">
                          {evento.coletorIniciais}
                        </AvatarFallback>
                      </Avatar>
                      <div className="flex-1 min-w-0">
                        <p className="text-xs text-muted-foreground mb-0.5">Coletor</p>
                        <p className="font-medium text-foreground truncate">
                          {evento.coletor}
                        </p>
                      </div>
                    </div>

                    <div className="space-y-3 mb-6">
                      <div className="flex items-start gap-3">
                        <Calendar className="h-5 w-5 text-primary mt-0.5 flex-shrink-0" />
                        <div>
                          <p className="text-xs text-muted-foreground mb-0.5">Data</p>
                          <p className="text-sm font-medium text-foreground">
                            {evento.data}
                          </p>
                        </div>
                      </div>

                      <div className="flex items-start gap-3">
                        <Clock className="h-5 w-5 text-primary mt-0.5 flex-shrink-0" />
                        <div>
                          <p className="text-xs text-muted-foreground mb-0.5">Horário</p>
                          <p className="text-sm font-medium text-foreground">
                            {evento.horario}
                          </p>
                        </div>
                      </div>

                      <div className="flex items-start gap-3">
                        <MapPin className="h-5 w-5 text-primary mt-0.5 flex-shrink-0" />
                        <div>
                          <p className="text-xs text-muted-foreground mb-0.5">Endereço</p>
                          <p className="text-sm font-medium text-foreground">
                            {evento.endereco}
                          </p>
                          <p className="text-xs text-muted-foreground">
                            {evento.pontoReferencia}
                          </p>
                        </div>
                      </div>
                    </div>

                    <Button
                      onClick={() => selecionarEvento(evento)}
                      className="w-full bg-accent hover:bg-accent-hover text-accent-foreground font-medium"
                    >
                      Selecionar e Informar Materiais
                    </Button>
                  </CardContent>
                </Card>
              ))}
            </div>
          ) : (
            <Card className="p-8 text-center bg-card">
              <p className="text-muted-foreground">
                Nenhum evento encontrado para este bairro.
              </p>
            </Card>
          )}
        </div>
      )}
    </div>
  );
};

export default AgendarColeta;
