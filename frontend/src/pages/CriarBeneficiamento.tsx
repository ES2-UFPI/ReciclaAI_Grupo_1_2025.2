import { useEffect, useState } from 'react';
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Calendar, MapPin, Plus } from "lucide-react";
import { useNavigate } from 'react-router-dom';

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

interface Beneficiamento {
  id: number;
  dataInicio: string;
  dataFim: string;
  pontoBeneficiamento: PontoBeneficiamento;
  itensBeneficiamento: ItemBeneficiamento[];
}

// Mock data - será substituído pela API posteriormente
const mockBeneficiamentos: Beneficiamento[] = [
  {
    id: 1,
    dataInicio: '2025-11-15T08:00:00',
    dataFim: '2025-11-15T17:00:00',
    pontoBeneficiamento: {
      id: 1,
      logradouro: 'Av. Industrial',
      numero: '1500',
      bairro: 'Distrito Industrial',
      cidade: 'Teresina',
      estado: 'PI',
      cep: '64000-000',
    },
    itensBeneficiamento: [
      {
        id: 1,
        item: { id: 1, nome: 'Plástico PET', unidade: 'kg' },
        quantidadeMinima: 50,
      },
      {
        id: 2,
        item: { id: 2, nome: 'Alumínio', unidade: 'kg' },
        quantidadeMinima: 30,
      },
    ],
  },
  {
    id: 2,
    dataInicio: '2025-11-20T09:00:00',
    dataFim: '2025-11-20T16:00:00',
    pontoBeneficiamento: {
      id: 2,
      logradouro: 'Rua da Reciclagem',
      numero: '200',
      bairro: 'Centro',
      cidade: 'Teresina',
      estado: 'PI',
      cep: '64001-000',
    },
    itensBeneficiamento: [
      {
        id: 3,
        item: { id: 3, nome: 'Papel', unidade: 'kg' },
        quantidadeMinima: 100,
      },
      {
        id: 4,
        item: { id: 4, nome: 'Papelão', unidade: 'kg' },
        quantidadeMinima: 80,
      },
      {
        id: 5,
        item: { id: 5, nome: 'Vidro', unidade: 'kg' },
        quantidadeMinima: 40,
      },
    ],
  },
];

const CriarBeneficiamento = () => {
  const [beneficiamentos, setBeneficiamentos] = useState<Beneficiamento[]>([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    // Simula o carregamento dos dados mockados
    const fetchBeneficiamentos = async () => {
      try {
        // Simula delay de API
        await new Promise(resolve => setTimeout(resolve, 500));
        setBeneficiamentos(mockBeneficiamentos);
      } catch (err) {
        console.error('Erro ao carregar beneficiamentos:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchBeneficiamentos();
  }, []);

  const handleCriarBeneficiamento = () => {
    // TODO: Implementar navegação para formulário de beneficiamento
    navigate("/formulario-beneficiamento");
  };

  if (loading) return <div>Carregando beneficiamentos...</div>;

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

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {beneficiamentos.map((beneficiamento) => (
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
                      {beneficiamento.pontoBeneficiamento.logradouro}, {beneficiamento.pontoBeneficiamento.numero}
                    </p>
                    <p className="text-sm text-muted-foreground">
                      {beneficiamento.pontoBeneficiamento.bairro}
                    </p>
                  </div>
                </div>

                <div className="pt-3 border-t border-border">
                  <p className="text-xs text-muted-foreground mb-2">Materiais para beneficiamento:</p>
                  <div className="flex flex-wrap gap-2">
                    {beneficiamento.itensBeneficiamento.map((itemBeneficiamento) => (
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
              </div>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
};

export default CriarBeneficiamento;
