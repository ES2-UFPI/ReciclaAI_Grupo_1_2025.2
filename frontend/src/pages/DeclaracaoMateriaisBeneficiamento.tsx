import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { useToast } from "@/components/ui/use-toast";

// Interfaces mockadas
interface Item {
  id: number;
  nome: string;
  unidade: string;
}

interface ItemBeneficiamento {
  id: number;
  item: Item;
  quantidadeMinima: number;
}

interface PontoBeneficiamento {
  id: number;
  logradouro: string;
  numero: string;
  bairro: string;
  cidade: string;
  estado: string;
  cep: string;
}

interface Receptor {
  id: number;
  nome: string;
  email: string;
}

interface Beneficiamento {
  id: number;
  dataInicio: string;
  dataFim: string;
  pontoBeneficiamento: PontoBeneficiamento;
  receptor: Receptor;
  itensBeneficiamento: ItemBeneficiamento[];
}

interface LocationState {
  beneficiamento: Beneficiamento;
  eventoBeneficiamentoId: number;
}

const DeclaracaoMateriaisBeneficiamento = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { toast } = useToast();
  const { beneficiamento, eventoBeneficiamentoId } = location.state as LocationState;
  const [quantidades, setQuantidades] = useState<Record<number, number>>({});
  const [submitting, setSubmitting] = useState(false);

  const handleQuantidadeChange = (itemId: number, value: string) => {
    setQuantidades((prev) => ({
      ...prev,
      [itemId]: Number(value) || 0,
    }));
  };

  const handleSubmit = async () => {
    try {
      setSubmitting(true);

      // Valida se pelo menos um item foi informado
      const itensInformados = Object.entries(quantidades).filter(([_, quantidade]) => quantidade > 0);
      
      if (itensInformados.length === 0) {
        toast({
          variant: "destructive",
          title: "Nenhum material informado",
          description: "Informe ao menos um material para continuar.",
        });
        return;
      }

      // Mock: Simula envio para API
      await new Promise(resolve => setTimeout(resolve, 1000));

      const itensAdicionados = itensInformados.map(([itemId, quantidade]) => ({
        eventoBeneficiamentoId,
        itemId: Number(itemId),
        quantidade,
      }));

      console.log("Materiais adicionados ao evento de beneficiamento (mock):", itensAdicionados);

      toast({
        title: "Materiais confirmados",
        description: "Os materiais foram registrados com sucesso.",
      });

      navigate("/agendar-beneficiamento");
    } catch (err: any) {
      toast({
        variant: "destructive",
        title: "Erro ao salvar materiais",
        description: err.message || "Erro ao salvar os materiais. Tente novamente.",
      });
      console.error(err);
    } finally {
      setSubmitting(false);
    }
  };

  const getItemIcon = (itemId: number) => {
    switch (itemId) {
      case 1: // Vidro
        return (
          <img
            src="/icone-vidro.png"
            alt="Ícone Vidro"
            className="w-6 h-6 object-contain"
          />
        );
      case 2: // Plástico PET
        return (
          <img
            src="/icone-pet.png"
            alt="Ícone PET"
            className="w-6 h-6 object-contain"
          />
        );
      case 3: // Alumínio
        return (
          <img
            src="/icone-aluminio.png"
            alt="Ícone Alumínio"
            className="w-6 h-6 object-contain"
          />
        );
      case 4: // Papelão
        return (
          <img
            src="/icone-papelao.png"
            alt="Ícone Papelão"
            className="w-6 h-6 object-contain"
          />
        );
      default:
        return null;
    }
  };

  return (
    <div className="max-w-3xl p-6">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-foreground mb-2">
          Informar Materiais para Beneficiamento
        </h1>
        <p className="text-muted-foreground">
          Indique as quantidades dos materiais que você irá entregar para
          este beneficiamento
        </p>
      </div>

      <Card className="mb-6">
        <CardContent className="p-6">
          <div className="space-y-6">
            {beneficiamento.itensBeneficiamento.map((itemBeneficiamento) => (
              <div
                key={itemBeneficiamento.id}
                className="flex items-center justify-between border-b border-border pb-4 last:border-0 last:pb-0"
              >
                <div className="flex items-start gap-3 flex-1 mr-4">
                  <div className="p-2 rounded-lg bg-primary/10">
                    {getItemIcon(itemBeneficiamento.item.id)}
                  </div>
                  <div>
                    <p className="font-medium mb-1">{itemBeneficiamento.item.nome}</p>
                    <Badge variant="secondary" className="bg-accent/10">
                      Mínimo: {itemBeneficiamento.quantidadeMinima}{" "}
                      {itemBeneficiamento.item.unidade === "unidade"
                        ? itemBeneficiamento.quantidadeMinima > 1
                          ? "unidades"
                          : "unidade"
                        : itemBeneficiamento.item.unidade}
                    </Badge>
                  </div>
                </div>
                <div className="w-32 flex-shrink-0">
                  <Input
                    type="number"
                    min="0"
                    placeholder="Quantidade"
                    value={quantidades[itemBeneficiamento.item.id] || ""}
                    onChange={(e) =>
                      handleQuantidadeChange(itemBeneficiamento.item.id, e.target.value)
                    }
                  />
                </div>
              </div>
            ))}
          </div>
        </CardContent>
      </Card>

      <div className="flex gap-4">
        <Button onClick={handleSubmit} disabled={submitting} className="flex-1">
          {submitting ? "Salvando..." : "Confirmar Materiais"}
        </Button>
        <Button
          variant="outline"
          onClick={() => navigate(-1)}
          disabled={submitting}
        >
          Voltar
        </Button>
      </div>
    </div>
  );
};

export default DeclaracaoMateriaisBeneficiamento;
