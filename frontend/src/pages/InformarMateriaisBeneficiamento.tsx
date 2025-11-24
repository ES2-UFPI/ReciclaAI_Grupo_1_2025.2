import { useState, useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { useToast } from "@/components/ui/use-toast";
import { listarTodosItens } from "@/services/coletaService";
import { adicionarItemBeneficiamento } from "@/services/beneficiamentoService";
import { Item } from "@/types/api";

interface LocationState {
  beneficiamentoId: number;
}

interface ItemData {
  quantidade: number;
  valor: number;
}

const InformarMateriaisBeneficiamento = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { toast } = useToast();
  const { beneficiamentoId } = location.state as LocationState;
  const [items, setItems] = useState<Item[]>([]);
  const [itemsData, setItemsData] = useState<Record<number, ItemData>>({});
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
    setItemsData((prev) => ({
      ...prev,
      [itemId]: {
        ...prev[itemId],
        quantidade,
      },
    }));
  };

  const handleValorChange = (itemId: number, value: string) => {
    const valor = Number(value) || 0;
    setItemsData((prev) => ({
      ...prev,
      [itemId]: {
        ...prev[itemId],
        valor,
      },
    }));
  };

  const handleSubmit = async () => {
    try {
      setSubmitting(true);

      // Filter only items with quantity > 0
      const itemsToSubmit = Object.entries(itemsData).filter(
        ([_, data]) => data?.quantidade > 0
      );

      if (itemsToSubmit.length === 0) {
        toast({
          variant: "destructive",
          title: "Nenhum material informado",
          description: "Informe ao menos um material para continuar.",
        });
        return;
      }

      // Validate that all items with quantity also have a value
      const invalidItems = itemsToSubmit.filter(
        ([_, data]) => !data?.valor || data.valor <= 0
      );

      if (invalidItems.length > 0) {
        toast({
          variant: "destructive",
          title: "Valor obrigatório",
          description: "Informe o valor unitário para todos os materiais selecionados.",
        });
        return;
      }

      // Submit each item
      await Promise.all(
        itemsToSubmit.map(([itemId, data]) =>
          adicionarItemBeneficiamento({
            beneficiamentoId,
            itemId: Number(itemId),
            quantidadeMinima: data.quantidade,
            valor: data.valor,
          })
        )
      );

      toast({
        title: "Beneficiamento criado com sucesso",
        description: "Seu beneficiamento foi criado e está disponível para receber materiais.",
      });

      navigate("/criar-beneficiamento");
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
          Informar Materiais para Beneficiamento
        </h1>
        <p className="text-muted-foreground">
          Indique as quantidades mínimas e o valor unitário dos materiais que você aceita para beneficiamento
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
                <div className="flex gap-3 flex-shrink-0">
                  <div className="w-32">
                    <label className="text-xs text-muted-foreground mb-1 block">
                      Quantidade
                    </label>
                    <Input
                      type="number"
                      min="0"
                      placeholder="Qtd"
                      value={itemsData[item.id]?.quantidade || ""}
                      onChange={(e) => handleQuantidadeChange(item.id, e.target.value)}
                    />
                  </div>
                  <div className="w-32">
                    <label className="text-xs text-muted-foreground mb-1 block">
                      Valor (R$)
                    </label>
                    <Input
                      type="number"
                      min="0"
                      step="0.01"
                      placeholder="0,00"
                      value={itemsData[item.id]?.valor || ""}
                      onChange={(e) => handleValorChange(item.id, e.target.value)}
                    />
                  </div>
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
          onClick={() => navigate("/criar-beneficiamento")}
          disabled={submitting}
        >
          Cancelar
        </Button>
      </div>
    </div>
  );
};

export default InformarMateriaisBeneficiamento;
