import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useToast } from "@/components/ui/use-toast";
import { ArrowLeft } from "lucide-react";

const FormularioBeneficiamento = () => {
  const navigate = useNavigate();
  const { toast } = useToast();
  const [submitting, setSubmitting] = useState(false);
  const [formData, setFormData] = useState({
    dataInicio: "",
    horaInicio: "",
    dataFim: "",
    horaFim: "",
    logradouro: "",
    numero: "",
    bairro: "",
    cep: "",
  });

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setSubmitting(true);

    try {
      // Simula delay de API
      await new Promise(resolve => setTimeout(resolve, 1000));

      // Mock: Simula criação do beneficiamento
      const beneficiamentoMock = {
        id: Math.floor(Math.random() * 1000),
        dataInicio: `${formData.dataInicio}T${formData.horaInicio}:00`,
        dataFim: `${formData.dataFim}T${formData.horaFim}:00`,
        pontoBeneficiamento: {
          logradouro: formData.logradouro,
          numero: formData.numero,
          bairro: formData.bairro,
          cep: formData.cep,
        },
      };

      console.log("Beneficiamento criado (mock):", beneficiamentoMock);

      toast({
        title: "Beneficiamento criado",
        description: "Agora informe os materiais aceitos.",
      });

      // TODO: Criar página de informar materiais do beneficiamento
      navigate("/informar-materiais-beneficiamento", {
        state: { beneficiamentoId: beneficiamentoMock.id },
      });
    } catch (error: any) {
      toast({
        variant: "destructive",
        title: "Erro ao criar beneficiamento",
        description: error.message || "Ocorreu um erro. Tente novamente.",
      });
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="max-w-5xl">
      <div className="mb-8">
        <Button
          variant="ghost"
          onClick={() => navigate("/criar-beneficiamento")}
          className="mb-4"
        >
          <ArrowLeft className="mr-2 h-4 w-4" />
          Voltar
        </Button>
        <h1 className="text-3xl font-bold text-foreground mb-2">
          Novo Beneficiamento
        </h1>
        <p className="text-muted-foreground">
          Preencha os dados do beneficiamento
        </p>
      </div>

      <Card>
        <CardHeader>
          <CardTitle>Informações do Beneficiamento</CardTitle>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit} className="space-y-6">
            {/* Data e Hora */}
            <div className="space-y-4">
              <h3 className="text-lg font-semibold">Data e Horário</h3>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div>
                  <Label htmlFor="dataInicio">Data de Início *</Label>
                  <Input
                    id="dataInicio"
                    type="date"
                    value={formData.dataInicio}
                    onChange={(e) =>
                      setFormData({ ...formData, dataInicio: e.target.value })
                    }
                    required
                  />
                </div>
                <div>
                  <Label htmlFor="horaInicio">Hora de Início *</Label>
                  <Input
                    id="horaInicio"
                    type="time"
                    value={formData.horaInicio}
                    onChange={(e) =>
                      setFormData({ ...formData, horaInicio: e.target.value })
                    }
                    required
                  />
                </div>
                <div>
                  <Label htmlFor="dataFim">Data de Término *</Label>
                  <Input
                    id="dataFim"
                    type="date"
                    value={formData.dataFim}
                    onChange={(e) =>
                      setFormData({ ...formData, dataFim: e.target.value })
                    }
                    required
                  />
                </div>
                <div>
                  <Label htmlFor="horaFim">Hora de Término *</Label>
                  <Input
                    id="horaFim"
                    type="time"
                    value={formData.horaFim}
                    onChange={(e) =>
                      setFormData({ ...formData, horaFim: e.target.value })
                    }
                    required
                  />
                </div>
              </div>
            </div>

            {/* Endereço */}
            <div className="space-y-4">
              <h3 className="text-lg font-semibold">Ponto de Beneficiamento</h3>
              <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                <div className="md:col-span-2">
                  <Label htmlFor="logradouro">Logradouro *</Label>
                  <Input
                    id="logradouro"
                    placeholder="Ex: Av. Industrial"
                    value={formData.logradouro}
                    onChange={(e) =>
                      setFormData({ ...formData, logradouro: e.target.value })
                    }
                    required
                  />
                </div>
                <div>
                  <Label htmlFor="numero">Número *</Label>
                  <Input
                    id="numero"
                    placeholder="Ex: 1500"
                    value={formData.numero}
                    onChange={(e) =>
                      setFormData({ ...formData, numero: e.target.value })
                    }
                    required
                  />
                </div>
                <div>
                  <Label htmlFor="bairro">Bairro *</Label>
                  <Input
                    id="bairro"
                    placeholder="Ex: Distrito Industrial"
                    value={formData.bairro}
                    onChange={(e) =>
                      setFormData({ ...formData, bairro: e.target.value })
                    }
                    required
                  />
                </div>
                <div>
                  <Label htmlFor="cep">CEP *</Label>
                  <Input
                    id="cep"
                    placeholder="Ex: 64000-000"
                    value={formData.cep}
                    onChange={(e) =>
                      setFormData({ ...formData, cep: e.target.value })
                    }
                    required
                  />
                </div>
              </div>
            </div>

            {/* Actions */}
            <div className="flex gap-4 pt-4 border-t">
              <Button
                type="button"
                variant="outline"
                onClick={() => navigate("/criar-beneficiamento")}
                className="flex-1"
                disabled={submitting}
              >
                Cancelar
              </Button>
              <Button
                type="submit"
                className="flex-1 !bg-green-600 !text-white"
                disabled={submitting}
              >
                {submitting ? "Salvando..." : "Próximo: Informar Materiais"}
              </Button>
            </div>
          </form>
        </CardContent>
      </Card>
    </div>
  );
};

export default FormularioBeneficiamento;
