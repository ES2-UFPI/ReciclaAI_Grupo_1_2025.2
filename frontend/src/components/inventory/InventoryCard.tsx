import { InventoryItem, categoryLabels, conditionLabels } from '@/types/inventory';
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from '@/components/ui/card';
import { Badge } from '@/components/ui/badge';
import { Recycle, MapPin, Calendar, TrendingUp } from 'lucide-react';
import { cn } from '@/lib/utils';

interface InventoryCardProps {
  item: InventoryItem;
}

const categoryColors = {
  plastic: 'bg-blue-100 text-blue-800 border-blue-200',
  paper: 'bg-amber-100 text-amber-800 border-amber-200',
  glass: 'bg-cyan-100 text-cyan-800 border-cyan-200',
  metal: 'bg-slate-100 text-slate-800 border-slate-200',
  electronic: 'bg-purple-100 text-purple-800 border-purple-200',
  organic: 'bg-green-100 text-green-800 border-green-200',
};

const conditionColors = {
  excellent: 'bg-success/10 text-success border-success/20',
  good: 'bg-info/10 text-info border-info/20',
  fair: 'bg-warning/10 text-warning border-warning/20',
  poor: 'bg-destructive/10 text-destructive border-destructive/20',
};

export const InventoryCard = ({ item }: InventoryCardProps) => {
  return (
    <Card className="hover:shadow-soft transition-all duration-300 hover:scale-[1.02]">
      <CardHeader className="pb-3">
        <div className="flex items-start justify-between">
          <div className="flex items-center gap-2">
            <div className="p-2 rounded-lg bg-primary/10">
              <Recycle className="w-5 h-5 text-primary" />
            </div>
            <div>
              <CardTitle className="text-lg">{item.name}</CardTitle>
            </div>
          </div>
        </div>
      </CardHeader>
      <CardContent className="space-y-3">
        <div className="flex gap-2 flex-wrap">
          <Badge variant="outline" className={cn(categoryColors[item.category])}>
            {categoryLabels[item.category]}
          </Badge>
          <Badge variant="outline" className={cn(conditionColors[item.condition])}>
            {conditionLabels[item.condition]}
          </Badge>
        </div>

        <div className="grid grid-cols-2 gap-3 text-sm">
          <div className="flex items-center gap-2 text-muted-foreground">
            <TrendingUp className="w-4 h-4" />
            <span className="font-semibold text-foreground">
              {item.quantity}{' '}
              {item.unit === 'unit'
                ? (item.quantity > 1 ? 'unidades' : 'unidade')
                : item.unit}
            </span>
          </div>
          <div className="flex items-center gap-2 text-muted-foreground">
            <Calendar className="w-4 h-4" />
            <span>
              {new Date(item.createdAt).toLocaleDateString('pt-BR')}
            </span>
          </div>
        </div>

        {item.estimatedValue && (
          <div className="pt-2 border-t">
            <p className="text-sm text-muted-foreground">
              Valor estimado:{' '}
              <span className="font-semibold text-primary">
                R$ {item.estimatedValue.toFixed(2)}
              </span>
            </p>
          </div>
        )}
      </CardContent>
    </Card>
  );
};