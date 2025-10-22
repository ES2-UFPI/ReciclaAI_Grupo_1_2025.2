import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { ArrowLeft, Save } from 'lucide-react';
import { toast } from 'sonner';
import { InventoryItem, categoryLabels } from '@/types/inventory';
import { useInventory } from '@/contexts/InventoryContext';

const AddInventoryItem = () => {
  const navigate = useNavigate();
  const { addItem } = useInventory();
  const [formData, setFormData] = useState<Partial<InventoryItem>>({
    category: undefined,
    quantity: 0,
    unit: 'kg',
    condition: 'good',
    estimatedValue: 0,
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    // Validation
    if (!formData.category || !formData.quantity) {
      toast.error('Por favor, preencha todos os campos obrigatórios');
      return;
    }

    // Add the new item using the context
    const name = formData.category ? categoryLabels[formData.category] ?? formData.category : 'Item';
    addItem({ ...formData, name });
    
    toast.success('Item adicionado ao inventário com sucesso!');
    navigate('/inventory');
  };

  return (
    <div className="max-w-5xl">
      {/* Top bar + texts (layout copied from AgendarColeta) */}
      <div className="bg-gradient-eco">
        <div className="max-w-5xl mx-auto px-4 py-6">
          <div className="flex items-center justify-between">
            <Button
              onClick={() => navigate('/inventory')}
              className="!bg-green-600 !text-white !border-transparent shadow-md hover:!bg-green-700 focus:!ring-2 focus:!ring-green-300"
            >
              <ArrowLeft className="mr-2 h-4 w-4" />
              Voltar ao Inventário
            </Button>
          </div>

          <div className="mt-4 mb-8">
            <h1 className="text-3xl font-bold text-foreground mb-2">Adicionar Novo Item</h1>
            <p className="text-muted-foreground">
              Cadastre um novo material reciclável no inventário
            </p>
          </div>
        </div>
      </div>

      {/* Content area (form + info) */}
      <div className="px-4 py-4 max-w-3xl ml-0 -mt-6">
        <Card>
          <CardHeader>
            <CardTitle>Informações do Item</CardTitle>
            <CardDescription>
              Preencha os dados do material reciclável que será adicionado ao inventário
            </CardDescription>
          </CardHeader>
          <CardContent>
            <form onSubmit={handleSubmit} className="space-y-6">
              {/* Basic Info */}
              <div className="space-y-4">
                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <Label htmlFor="category">Categoria *</Label>
                    <Select
                      value={formData.category}
                      onValueChange={(value: InventoryItem['category']) =>
                        setFormData({ ...formData, category: value })
                      }
                    >
                      <SelectTrigger id="category">
                        <SelectValue placeholder="Selecione..." />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="plastic">Plástico</SelectItem>
                        <SelectItem value="paper">Papel</SelectItem>
                        <SelectItem value="glass">Vidro</SelectItem>
                        <SelectItem value="metal">Metal</SelectItem>
                        <SelectItem value="electronic">Eletrônico</SelectItem>
                        <SelectItem value="organic">Orgânico</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>

                  <div>
                    <Label htmlFor="condition">Condição</Label>
                    <Select
                      value={formData.condition}
                      onValueChange={(value: InventoryItem['condition']) =>
                        setFormData({ ...formData, condition: value })
                      }
                    >
                      <SelectTrigger id="condition">
                        <SelectValue />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="excellent">Excelente</SelectItem>
                        <SelectItem value="good">Bom</SelectItem>
                        <SelectItem value="fair">Regular</SelectItem>
                        <SelectItem value="poor">Ruim</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                </div>

                <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
                  <div>
                    <Label htmlFor="quantity">Quantidade *</Label>
                    <Input
                      id="quantity"
                      type="number"
                      min="0"
                      step="0.01"
                      placeholder="0"
                      value={formData.quantity}
                      onChange={(e) =>
                        setFormData({ ...formData, quantity: parseFloat(e.target.value) || 0 })
                      }
                      required
                    />
                  </div>

                  <div>
                    <Label htmlFor="unit">Unidade</Label>
                    <Select
                      value={formData.unit}
                      onValueChange={(value: InventoryItem['unit']) =>
                        setFormData({ ...formData, unit: value })
                      }
                    >
                      <SelectTrigger id="unit">
                        <SelectValue />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="kg">Quilogramas (kg)</SelectItem>
                        <SelectItem value="ton">Toneladas (ton)</SelectItem>
                        <SelectItem value="unit">Unidades</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                </div>

                <div>
                  <Label htmlFor="estimatedValue">Valor Estimado (R$)</Label>
                  <Input
                    id="estimatedValue"
                    type="number"
                    min="0"
                    step="0.01"
                    placeholder="0.00"
                    value={formData.estimatedValue}
                    onChange={(e) =>
                      setFormData({
                        ...formData,
                        estimatedValue: parseFloat(e.target.value) || 0,
                      })
                    }
                  />
                </div>
              </div>

              {/* Actions */}
              <div className="flex gap-4 pt-4 border-t">
                <Button
                  type="button"
                  variant="outline"
                  onClick={() => navigate('/inventory')}
                  className="flex-1"
                >
                  Cancelar
                </Button>
                <Button
                  type="submit"
                  className="flex-1 !bg-gradient-eco !text-white shadow-md hover:opacity-95"
                >
                  <Save className="mr-2 h-4 w-4" />
                  Salvar Item
                </Button>
              </div>
            </form>
          </CardContent>
        </Card>

        {/* Info Card */}
        <Card className="mt-6 bg-accent/50 border-accent">
          <CardContent className="pt-6">
            <p className="text-sm text-muted-foreground">
              💡 <strong>Dica:</strong> Após adicionar o item ao inventário, você pode agendar
              uma coleta para este material através da funcionalidade de agendamento.
            </p>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default AddInventoryItem;