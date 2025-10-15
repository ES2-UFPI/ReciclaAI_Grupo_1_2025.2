import { useState } from "react";
import { Search, Calendar, Clock, User } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent } from "@/components/ui/card";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { useNavigate } from "react-router-dom";

interface Evento {
  id: string;
  coletor: string;
  coletorIniciais: string;
  data: string;
  horario: string;
  bairro: string;
}

const eventosDisponiveis: Evento[] = [
  {
    id: "1",
    coletor: "Carlos Andrade",
    coletorIniciais: "CA",
    data: "20 de Outubro de 2025",
    horario: "Das 09:00 às 12:00",
    bairro: "Centro",
  },
  {
    id: "2",
    coletor: "Ana Santos",
    coletorIniciais: "AS",
    data: "22 de Outubro de 2025",
    horario: "Das 14:00 às 17:00",
    bairro: "Centro",
  },
  {
    id: "3",
    coletor: "Roberto Lima",
    coletorIniciais: "RL",
    data: "25 de Outubro de 2025",
    horario: "Das 08:00 às 11:00",
    bairro: "Centro",
  },
];

const AgendarColeta = () => {
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
