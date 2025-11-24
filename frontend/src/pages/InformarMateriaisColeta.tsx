import { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { adicionarItemColeta, listarTodosItens } from "@/services/coletaService";
import { useToast } from "@/components/ui/use-toast";
import { Item } from "@/types/api";

interface LocationState {
  coletaId: number;
}

const InformarMateriaisColeta = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { toast } = useToast();
  const { coletaId } = location.state as LocationState;
  const [items, setItems] = useState<Item[]>([]);
  const [quantidades, setQuantidades] = useState<Record<number, number>>({});
  const [submitting, setSubmitting] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchItems = async () => {
      try {
        const data = await listarTodosItens();
        setItems(data);
      } catch (error) {
        toast({
          variant: "destructive",
          title: "Erro ao carregar itens",
          description: "Não foi possível carregar os itens disponíveis.",
        });
      } finally {
        setLoading(false);
      }
    };

    fetchItems();
  }, []);

  const getItemIcon = (itemId: number) => {
    switch (itemId) {
      case 1:
        return <img src="/icone-vidro.png" alt="Vidro" className="w-6 h-6 object-contain" />;
      case 2:
        return <img src="/icone-pet.png" alt="PET" className="w-6 h-6 object-contain" />;
      case 3:
        return <img src="/icone-aluminio.png" alt="Alumínio" className="w-6 h-6 object-contain" />;
      case 4:
        return <img src="/icone-papelao.png" alt="Papelão" className="w-6 h-6 object-contain" />;
      default:
        return null;
    }
  };

  const handleQuantidadeChange = (itemId: number, value: string) => {
    const quantidade = Number(value) || 0;
    setQuantidades((prev) => ({
      ...prev,
      [itemId]: quantidade,
    }));
  };

  const handleSubmit = async () => {
    try {
      setSubmitting(true);

      // Filter only items with quantity > 0
      const itemsToSubmit = Object.entries(quantidades).filter(
        ([_, quantidade]) => quantidade > 0
      );

      if (itemsToSubmit.length === 0) {
        toast({
          variant: "destructive",
          title: "Nenhum material informado",
          description: "Informe ao menos um material para continuar.",
        });
        return;
      }

      // Submit each item
      await Promise.all(
        itemsToSubmit.map(([itemId, quantidadeMinima]) =>
          adicionarItemColeta({
            coletaId,
            itemId: Number(itemId),
            quantidadeMinima,
          })
        )
      );

      toast({
        title: "Coleta criada com sucesso",
        description: "Sua coleta foi criada e está disponível para agendamento.",
      });

      navigate("/criar-coleta");
    } catch (error: any) {
      toast({
        variant: "destructive",
        title: "Erro ao salvar materiais",
        description: error.message || "Ocorreu um erro. Tente novamente.",
      });
    } finally {
      setSubmitting(false);
    }
  };

  if (loading) return <div>Carregando itens...</div>;

  return (
    <div className="max-w-3xl">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-foreground mb-2">
          Informar Materiais
        </h1>
        <p className="text-muted-foreground">
          Indique as quantidades mínimas dos materiais que você aceita nesta coleta
        </p>
      </div>

      <Card className="mb-6">
        <CardContent className="p-6">
          <div className="space-y-6">
            {items.map((item) => (
              <div
                key={item.id}
                className="flex items-center justify-between border-b border-border pb-4 last:border-0 last:pb-0"
              >
                <div className="flex items-start gap-3 flex-1 mr-4">
                  <div className="p-2 rounded-lg bg-primary/10">
                    {getItemIcon(item.id)}
                  </div>
                  <div>
                    <p className="font-medium mb-1">{item.nome}</p>
                    <Badge variant="secondary" className="bg-accent/10">
                      Unidade: {item.unidade}
                    </Badge>
                  </div>
                </div>
                <div className="w-32 flex-shrink-0">
                  <Input
                    type="number"
                    min="0"
                    placeholder="Quantidade"
                    value={quantidades[item.id] || ""}
                    onChange={(e) => handleQuantidadeChange(item.id, e.target.value)}
                  />
                </div>
              </div>
            ))}
          </div>
        </CardContent>
      </Card>

      <div className="flex gap-4">
        <Button
          onClick={handleSubmit}
          disabled={submitting}
          className="flex-1 !bg-green-600 !text-white"
        >
          {submitting ? "Salvando..." : "Finalizar Criação"}
        </Button>
        <Button
          variant="outline"
          onClick={() => navigate("/criar-coleta")}
          disabled={submitting}
        >
          Cancelar
        </Button>
      </div>
    </div>
  );
};

export default InformarMateriaisColeta;