import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';
import { TrendingUp } from 'lucide-react';

interface InventoryItemData {
  id: number;
  quantidade: number;
  item: {
    id: number;
    nome: string;
    unidade: string;
  };
}

interface InventoryCardProps {
  item: InventoryItemData;
}

export const InventoryCard = ({ item }: InventoryCardProps) => {
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
    <Card className="hover:shadow-soft transition-all duration-300 hover:scale-[1.02]">
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
        </div>
      </CardHeader>
      <CardContent className="space-y-3">
        <div className="grid grid-cols-1 gap-3 text-sm">
          <div className="flex items-center gap-2 text-muted-foreground">
            <TrendingUp className="w-4 h-4" />
            <span className="font-semibold text-foreground">
              {item.quantidade} {item.item.unidade === 'unidade' 
                ? (item.quantidade > 1 ? 'unidades' : 'unidade')
                : item.item.unidade}
            </span>
          </div>
        </div>
      </CardContent>
    </Card>
  );
};