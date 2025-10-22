import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Calendar, Clock, User, Recycle, Package2, FileText, GlassWater } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent } from "@/components/ui/card";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { toast } from "sonner";

interface Material {
  id: string;
  nome: string;
  icon: any;
  minimoKg: number;
  quantidade: number;
}

const DeclaracaoMateriais = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const evento = location.state?.evento;

  const [showConfirmacao, setShowConfirmacao] = useState(false);
  const [materiais, setMateriais] = useState<Material[]>([
    { id: "1", nome: "Plástico PET", icon: Recycle, minimoKg: 5, quantidade: 0 },
    { id: "2", nome: "Papelão", icon: Package2, minimoKg: 10, quantidade: 0 },
    { id: "3", nome: "Papel", icon: FileText, minimoKg: 3, quantidade: 0 },
    { id: "4", nome: "Vidro", icon: GlassWater, minimoKg: 8, quantidade: 0 },
  ]);

  if (!evento) {
    navigate("/agendar-coleta");
    return null;
  }

  const atualizarQuantidade = (id: string, valor: string) => {
    const quantidade = parseFloat(valor) || 0;
    setMateriais((prev) =>
      prev.map((m) => (m.id === id ? { ...m, quantidade } : m))
    );
  };

  const confirmarAgendamento = () => {
    const materiaisPreenchidos = materiais.filter((m) => m.quantidade > 0);
    
    if (materiaisPreenchidos.length === 0) {
      toast.error("Por favor, informe a quantidade de pelo menos um material.");
      return;
    }

    const materiaisInsuficientes = materiaisPreenchidos.filter(
      (m) => m.quantidade < m.minimoKg
    );

    if (materiaisInsuficientes.length > 0) {
      toast.error(
        "Alguns materiais não atingem a quantidade mínima exigida."
      );
      return;
    }

    // montar objeto de agendamento e salvar no localStorage
    try {
      const novoAgendamento = {
        id: crypto.randomUUID(),
        coletor: evento.coletor,
        coletorIniciais: evento.coletorIniciais,
        data: evento.data,
        horario: evento.horario,
        bairro: evento.bairro ?? "",
        endereco: evento.endereco ?? "",
        pontoReferencia: evento.pontoReferencia ?? "",
        materiais: materiaisPreenchidos.map((m) => m.nome),
        status: "confirmada" as const,
      };

      const key = "coletasAgendadas";
      const raw = localStorage.getItem(key);
      const lista = raw ? JSON.parse(raw) : [];
      lista.unshift(novoAgendamento); // adicionar no topo
      localStorage.setItem(key, JSON.stringify(lista));
    } catch (err) {
      // não bloquear fluxo por erro de storage
      console.error("Erro ao salvar agendamento:", err);
    }

    setShowConfirmacao(true);
  };

  const fecharModal = () => {
    setShowConfirmacao(false);
    navigate("/agendar-coleta");
  };

  return (
    <div className="max-w-4xl">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-foreground mb-2">
          Informe seus Materiais
        </h1>
        <p className="text-muted-foreground">
          Declare a quantidade de materiais que você possui para coleta.
        </p>
      </div>

      <Card className="mb-8 bg-primary-light border-primary/20">
        <CardContent className="p-6">
          <h2 className="text-sm font-semibold text-primary mb-4">
            Detalhes do Evento Selecionado
          </h2>
          <div className="flex items-start gap-4">
            <Avatar className="h-12 w-12 border-2 border-primary/30">
              <AvatarFallback className="bg-primary text-primary-foreground font-medium">
                {evento.coletorIniciais}
              </AvatarFallback>
            </Avatar>
            <div className="flex-1 space-y-2">
              <div className="flex items-center gap-2">
                <User className="h-4 w-4 text-primary" />
                <p className="text-sm">
                  <span className="text-muted-foreground">Coletor:</span>{" "}
                  <span className="font-medium text-foreground">{evento.coletor}</span>
                </p>
              </div>
              <div className="flex items-center gap-2">
                <Calendar className="h-4 w-4 text-primary" />
                <p className="text-sm">
                  <span className="text-muted-foreground">Data:</span>{" "}
                  <span className="font-medium text-foreground">{evento.data}</span>
                </p>
              </div>
              <div className="flex items-center gap-2">
                <Clock className="h-4 w-4 text-primary" />
                <p className="text-sm">
                  <span className="text-muted-foreground">Horário:</span>{" "}
                  <span className="font-medium text-foreground">{evento.horario}</span>
                </p>
              </div>
            </div>
          </div>
        </CardContent>
      </Card>

      <div className="space-y-4 mb-8">
        {materiais.map((material) => (
          <Card key={material.id} className="bg-card hover:border-primary/30 transition-colors">
            <CardContent className="p-6">
              <div className="flex items-center gap-4">
                <div className="flex-shrink-0 w-12 h-12 rounded-full bg-primary-light flex items-center justify-center">
                  <material.icon className="h-6 w-6 text-primary" />
                </div>
                <div className="flex-1">
                  <h3 className="font-semibold text-foreground mb-1">
                    {material.nome}
                  </h3>
                  <p className="text-xs text-muted-foreground">
                    Quantidade mínima exigida: {material.minimoKg} kg
                  </p>
                </div>
                <div className="flex items-center gap-2">
                  <Input
                    type="number"
                    min="0"
                    step="0.1"
                    placeholder="0"
                    value={material.quantidade || ""}
                    onChange={(e) => atualizarQuantidade(material.id, e.target.value)}
                    className="w-24 text-center bg-background"
                  />
                  <span className="text-sm text-muted-foreground font-medium">kg</span>
                </div>
              </div>
            </CardContent>
          </Card>
        ))}
      </div>

      <Button
        onClick={confirmarAgendamento}
        size="lg"
        className="w-full bg-accent hover:bg-accent-hover text-accent-foreground font-semibold text-base h-12"
      >
        Confirmar Agendamento
      </Button>

      <Dialog open={showConfirmacao} onOpenChange={setShowConfirmacao}>
        <DialogContent className="sm:max-w-md">
          <div className="flex flex-col items-center text-center p-6">
            <div className="w-16 h-16 rounded-full bg-primary/10 flex items-center justify-center mb-4">
              <div className="w-12 h-12 rounded-full bg-primary flex items-center justify-center">
                <svg
                  className="w-8 h-8 text-primary-foreground"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    strokeLinecap="round"
                    strokeLinejoin="round"
                    strokeWidth={3}
                    d="M5 13l4 4L19 7"
                  />
                </svg>
              </div>
            </div>
            
            <DialogHeader>
              <DialogTitle className="text-2xl font-bold text-foreground mb-2">
                Agendamento Confirmado!
              </DialogTitle>
              <DialogDescription className="text-base text-muted-foreground">
                Sua coleta com o coletor <strong className="text-foreground">{evento.coletor}</strong> foi
                agendada com sucesso para o dia{" "}
                <strong className="text-foreground">{evento.data}</strong>. Você será notificado quando ele
                estiver a caminho.
              </DialogDescription>
            </DialogHeader>

            <Button
              onClick={fecharModal}
              className="mt-6 w-full bg-primary hover:bg-primary-dark"
              size="lg"
            >
              Entendido
            </Button>
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
};

export default DeclaracaoMateriais;
