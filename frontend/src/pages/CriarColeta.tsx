import { useEffect, useState } from 'react';
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { Calendar, MapPin, Plus } from "lucide-react";
import { listarColetasColetor } from '@/services/coletaService';
import { Coleta, PageableResponse } from '@/types/api';
import { useNavigate } from 'react-router-dom';

const CriarColeta = () => {
  const [coletas, setColetas] = useState<Coleta[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchColetas = async () => {
      try {
        // TODO: Get real coletor ID from auth context
        const coletorId = 1;
        const response = await listarColetasColetor(coletorId);
        setColetas(response.content);
      } catch (err) {
        setError('Erro ao carregar coletas');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchColetas();
  }, []);

  const handleCriarColeta = () => {
    navigate("/formulario-coleta");
  };

  if (loading) return <div>Carregando coletas...</div>;
  if (error) return <div className="text-red-500">{error}</div>;

  return (
    <div className="max-w-5xl">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-foreground mb-2">
          Criar Nova Coleta
        </h1>
        <p className="text-muted-foreground">
          Defina os detalhes da sua coleta
        </p>
      </div>

      <div className="mb-12">
        <Button
          onClick={handleCriarColeta}
          size="lg"
          className="w-full md:w-auto bg-primary hover:bg-primary-dark"
        >
          <Plus className="mr-2 h-5 w-5" />
          Criar Coleta
        </Button>
      </div>

      <div className="mb-6">
        <h2 className="text-2xl font-bold text-foreground mb-2">
          Minhas Coletas Criadas
        </h2>
        <p className="text-muted-foreground">
          Gerencie suas coletas disponíveis
        </p>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
        {coletas.map((coleta) => (
          <Card
            key={coleta.id}
            className="bg-card hover:shadow-lg transition-shadow duration-200 border-primary/20"
          >
            <CardContent className="p-6 relative">
              <img 
                src="/icone-coleta-2.png" 
                alt="Ícone Coleta" 
                className="absolute top-4 right-4 w-8 h-8 object-contain opacity-90"
              />

              <div className="space-y-4">
                <div className="flex items-start gap-3">
                  <Calendar className="h-5 w-5 text-primary mt-0.5 flex-shrink-0" />
                  <div>
                    <p className="text-xs text-muted-foreground mb-0.5">Data e Horário</p>
                    <p className="font-medium text-foreground">
                      {new Date(coleta.dataInicio).toLocaleDateString('pt-BR')}
                    </p>
                    <p className="text-sm text-muted-foreground">
                      {new Date(coleta.dataInicio).toLocaleTimeString('pt-BR', {
                        hour: '2-digit',
                        minute: '2-digit'
                      })} às {new Date(coleta.dataFim).toLocaleTimeString('pt-BR', {
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
                      {coleta.pontoColeta.logradouro}, {coleta.pontoColeta.numero}
                    </p>
                    <p className="text-sm text-muted-foreground">
                      {coleta.pontoColeta.bairro}
                    </p>
                  </div>
                </div>

                <div className="pt-3 border-t border-border">
                  <p className="text-xs text-muted-foreground mb-2">Materiais aceitos:</p>
                  <div className="flex flex-wrap gap-2">
                    {coleta.itensColeta.map((itemColeta) => (
                      <Badge 
                        key={itemColeta.id} 
                        variant="secondary"
                        className="bg-accent/10 text-foreground"
                      >
                        {itemColeta.item.nome} (min: {itemColeta.quantidadeMinima} {
                          itemColeta.item.unidade === 'unidade' 
                            ? (itemColeta.quantidadeMinima > 1 ? 'unidades' : 'unidade')
                            : itemColeta.item.unidade
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

export default CriarColeta;