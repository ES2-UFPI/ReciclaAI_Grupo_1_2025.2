import { useEffect, useState } from 'react';
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Calendar, MapPin, Plus, DollarSign } from "lucide-react";
import { useNavigate } from 'react-router-dom';
import { listarBeneficiamentosReceptor } from '@/services/beneficiamentoService';
import { Beneficiamento } from '@/types/api';
import { useAuth } from '@/contexts/AuthContext';

const CriarBeneficiamento = () => {
  const [beneficiamentos, setBeneficiamentos] = useState<Beneficiamento[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();
  const { user } = useAuth();

  useEffect(() => {
    const fetchBeneficiamentos = async () => {
      if (!user) {
        setError('Usuário não autenticado');
        setLoading(false);
        return;
      }

      try {
        const response = await listarBeneficiamentosReceptor(user.pessoaId);
        setBeneficiamentos(response.content);
      } catch (err) {
        setError('Erro ao carregar beneficiamentos');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchBeneficiamentos();
  }, [user]);

  const handleCriarBeneficiamento = () => {
    navigate("/formulario-beneficiamento");
  };

  const calcularPrecoMinimo = (beneficiamento: Beneficiamento): number => {
    return beneficiamento.itensBeneficiamento.reduce((total, itemBenef) => {
      const valor = itemBenef.valor || 0;
      return total + (valor * itemBenef.quantidadeMinima);
    }, 0);
  };

  if (loading) return <div>Carregando beneficiamentos...</div>;
  if (error) return <div className="text-red-500">{error}</div>;

  return (
    <div className="max-w-5xl">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-foreground mb-2">
          Criar Novo Beneficiamento
        </h1>
        <p className="text-muted-foreground">
          Defina os detalhes do seu beneficiamento de materiais
        </p>
      </div>

      <div className="mb-12">
        <Button
          onClick={handleCriarBeneficiamento}
          size="lg"
          className="w-full md:w-auto bg-primary hover:bg-primary-dark"
        >
          <Plus className="mr-2 h-5 w-5" />
          Criar Beneficiamento
        </Button>
      </div>

      <div className="mb-6">
        <h2 className="text-2xl font-bold text-foreground mb-2">
          Meus Beneficiamentos Criados
        </h2>
        <p className="text-muted-foreground">
          Gerencie seus beneficiamentos disponíveis
        </p>
      </div>

      {beneficiamentos && beneficiamentos.length > 0 ? (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {beneficiamentos.map((beneficiamento) => {
            const precoMinimo = calcularPrecoMinimo(beneficiamento);
            
            return (
              <Card
                key={beneficiamento.id}
                className="bg-card hover:shadow-lg transition-shadow duration-200 border-primary/20"
              >
                <CardContent className="p-6 relative">
                  <img 
                    src="/icone-coleta-2.png" 
                    alt="Ícone Beneficiamento" 
                    className="absolute top-4 right-4 w-8 h-8 object-contain opacity-90"
                  />

                  <div className="space-y-4">
                    <div className="flex items-start gap-3">
                      <Calendar className="h-5 w-5 text-primary mt-0.5 flex-shrink-0" />
                      <div>
                        <p className="text-xs text-muted-foreground mb-0.5">Data e Horário</p>
                        <p className="font-medium text-foreground">
                          {new Date(beneficiamento.dataInicio).toLocaleDateString('pt-BR')}
                        </p>
                        <p className="text-sm text-muted-foreground">
                          {new Date(beneficiamento.dataInicio).toLocaleTimeString('pt-BR', {
                            hour: '2-digit',
                            minute: '2-digit'
                          })} às {new Date(beneficiamento.dataFim).toLocaleTimeString('pt-BR', {
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
                          {beneficiamento.pontoColeta.logradouro}, {beneficiamento.pontoColeta.numero}
                        </p>
                        <p className="text-sm text-muted-foreground">
                          {beneficiamento.pontoColeta.bairro}
                        </p>
                      </div>
                    </div>

                    <div className="pt-3 border-t border-border">
                      <p className="text-xs text-muted-foreground mb-2">Materiais para beneficiamento:</p>
                      <div className="flex flex-wrap gap-2">
                        {beneficiamento.itensBeneficiamento && beneficiamento.itensBeneficiamento.map((itemBeneficiamento) => (
                          <Badge 
                            key={itemBeneficiamento.id} 
                            variant="secondary"
                            className="bg-accent/10 text-foreground"
                          >
                            {itemBeneficiamento.item.nome} (min: {itemBeneficiamento.quantidadeMinima} {
                              itemBeneficiamento.item.unidade === 'unidade' 
                                ? (itemBeneficiamento.quantidadeMinima > 1 ? 'unidades' : 'unidade')
                                : itemBeneficiamento.item.unidade
                            })
                          </Badge>
                        ))}
                      </div>
                    </div>

                    <div className="pt-3 border-t border-border">
                      <div className="flex items-center gap-2 bg-green-50 p-3 rounded-lg">
                        <DollarSign className="h-5 w-5 text-green-600" />
                        <div>
                          <p className="text-xs text-green-600 font-medium">Preço Mínimo</p>
                          <p className="text-lg font-bold text-green-700">
                            R$ {precoMinimo.toFixed(2)}
                          </p>
                        </div>
                      </div>
                    </div>
                  </div>
                </CardContent>
              </Card>
            );
          })}
        </div>
      ) : (
        <div className="text-center py-12 text-muted-foreground">
          <p>Nenhum beneficiamento criado ainda.</p>
        </div>
      )}
    </div>
  );
};

export default CriarBeneficiamento;
