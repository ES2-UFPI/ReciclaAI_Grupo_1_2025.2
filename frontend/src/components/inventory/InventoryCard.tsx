import { useState } from 'react';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { TrendingUp, Pencil } from 'lucide-react';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { atualizarQuantidadeInventario } from '@/services/inventoryService';
import { InventoryItem } from '@/types/api';

interface InventoryCardProps {
  item: InventoryItem;
  onUpdate?: (updatedItem: InventoryItem) => void;
}

export const InventoryCard = ({ item, onUpdate }: InventoryCardProps) => {
  const [isEditing, setIsEditing] = useState(false);
  const [quantidade, setQuantidade] = useState(item.quantidade);
  const [isUpdating, setIsUpdating] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleUpdate = async () => {
    try {
      setIsUpdating(true);
      setError(null);
      const updatedItem = await atualizarQuantidadeInventario(item.id, quantidade);
      onUpdate?.(updatedItem);
      setIsEditing(false);
    } catch (err) {
      setError('Erro ao atualizar quantidade');
      console.error(err);
    } finally {
      setIsUpdating(false);
    }
  };

  const getItemIcon = (itemId: number) => {
    switch (itemId) {
      case 1: // Garrafas de Vidro
        return (
          <img 
            src="/icone-vidro.png" 
            alt="Ícone Vidro" 
            className="w-5 h-5 object-contain"
          />
        );
      case 2: // Garrafas PET
        return (
          <img 
            src="/icone-pet.png" 
            alt="Ícone PET" 
            className="w-5 h-5 object-contain"
          />
        );
      case 3: // Alumínio
        return (
          <img 
            src="/icone-aluminio.png" 
            alt="Ícone Alumínio" 
            className="w-5 h-5 object-contain"
          />
        );
      case 4: // Papelão
        return (
          <img 
            src="/icone-papelao.png" 
            alt="Ícone Papelão" 
            className="w-5 h-5 object-contain"
          />
        );
      default:
        return <TrendingUp className="w-5 h-5 text-primary" />;
    }
  };

  return (
    <Card className="hover:shadow-soft transition-all duration-300 hover:scale-[1.02] group relative">
      <CardHeader className="pb-3">
        <div className="flex items-start justify-between">
          <div className="flex items-center gap-2">
            <div className="p-2 rounded-lg bg-primary/10">
              {getItemIcon(item.item.id)}
            </div>
            <div>
              <CardTitle className="text-lg">{item.item.nome}</CardTitle>
            </div>
          </div>
          <button 
            onClick={() => setIsEditing(true)}
            className="opacity-0 group-hover:opacity-100 transition-opacity p-2 hover:bg-accent/10 rounded-full"
          >
            <Pencil className="w-4 h-4 text-muted-foreground" />
          </button>
        </div>
      </CardHeader>
      <CardContent className="space-y-3">
        <div className="grid grid-cols-1 gap-3 text-sm">
          <div className="flex items-center gap-2 text-muted-foreground">
            <TrendingUp className="w-4 h-4" />
            {isEditing ? (
              <div className="flex items-center gap-2">
                <Input
                  type="number"
                  value={quantidade}
                  onChange={(e) => setQuantidade(Number(e.target.value))}
                  className="w-24"
                  min="0"
                  step="1"
                />
                <div className="flex gap-2">
                  <Button 
                    size="sm" 
                    onClick={handleUpdate}
                    disabled={isUpdating}
                  >
                    Salvar
                  </Button>
                  <Button 
                    size="sm" 
                    variant="ghost"
                    onClick={() => {
                      setIsEditing(false);
                      setQuantidade(item.quantidade);
                      setError(null);
                    }}
                  >
                    Cancelar
                  </Button>
                </div>
              </div>
            ) : (
              <span className="font-semibold text-foreground">
                {item.quantidade} {item.item.unidade === 'unidade' 
                  ? (item.quantidade > 1 ? 'unidades' : 'unidade')
                  : item.item.unidade}
              </span>
            )}
          </div>
          {error && (
            <p className="text-sm text-red-500">{error}</p>
          )}
        </div>
      </CardContent>
    </Card>
  );
};