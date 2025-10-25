import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { Coleta } from "@/types/api";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { adicionarItemEventoColeta } from "@/services/coletaService";

interface LocationState {
  coleta: Coleta;
  eventoColetaId: number;
}

const DeclaracaoMateriais = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { coleta, eventoColetaId } = location.state as LocationState;
  const [quantidades, setQuantidades] = useState<Record<number, number>>({});
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleQuantidadeChange = (itemId: number, value: string) => {
    setQuantidades((prev) => ({
      ...prev,
      [itemId]: Number(value) || 0,
    }));
  };

  const handleSubmit = async () => {
    try {
      setSubmitting(true);
      setError(null);

      // Submit each item
      await Promise.all(
        Object.entries(quantidades).map(([itemId, quantidade]) => {
          if (quantidade > 0) {
            return adicionarItemEventoColeta(
              eventoColetaId,
              Number(itemId),
              quantidade
            );
          }
        })
      );

      navigate("/agendar-coleta");
    } catch (err: any) {
      // Use the error message from the API response if available
      setError(err.message || "Erro ao salvar os materiais. Tente novamente.");
      console.error(err);
    } finally {
      setSubmitting(false);
    }
  };

  const getItemIcon = (itemId: number) => {
    switch (itemId) {
      case 1: // Garrafas de Vidro
        return (
          <img
            src="/icone-vidro.png"
            alt="Ícone Vidro"
            className="w-6 h-6 object-contain"
          />
        );
      case 2: // Garrafas PET
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
    }
  };

  return (
    <div className="max-w-3xl p-6">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-foreground mb-2">
          Informar Materiais para Coleta
        </h1>
        <p className="text-muted-foreground">
          Indique as quantidades dos materiais que você irá disponibilizar para
          esta coleta
        </p>
      </div>

      <Card className="mb-6">
        <CardContent className="p-6">
          <div className="space-y-6">
            {coleta.itensColeta.map((itemColeta) => (
              <div
                key={itemColeta.id}
                className="flex items-center justify-between border-b border-border pb-4 last:border-0 last:pb-0"
              >
                <div className="flex items-start gap-3 flex-1 mr-4">
                  <div className="p-2 rounded-lg bg-primary/10">
                    {getItemIcon(itemColeta.item.id)}
                  </div>
                  <div>
                    <p className="font-medium mb-1">{itemColeta.item.nome}</p>
                    <Badge variant="secondary" className="bg-accent/10">
                      Mínimo: {itemColeta.quantidadeMinima}{" "}
                      {itemColeta.item.unidade === "unidade"
                        ? itemColeta.quantidadeMinima > 1
                          ? "unidades"
                          : "unidade"
                        : itemColeta.item.unidade}
                    </Badge>
                  </div>
                </div>
                <div className="w-32 flex-shrink-0">
                  <Input
                    type="number"
                    min="0"
                    placeholder="Quantidade"
                    value={quantidades[itemColeta.item.id] || ""}
                    onChange={(e) =>
                      handleQuantidadeChange(itemColeta.item.id, e.target.value)
                    }
                  />
                </div>
              </div>
            ))}
          </div>
        </CardContent>
      </Card>

      {error && (
        <div className="bg-destructive/10 text-destructive p-3 rounded-md mb-6">
          {error}
        </div>
      )}

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

export default DeclaracaoMateriais;
