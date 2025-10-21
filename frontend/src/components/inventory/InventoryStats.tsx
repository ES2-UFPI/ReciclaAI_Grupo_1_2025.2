import { InventoryItem } from '@/types/inventory';
import { Card, CardContent } from '@/components/ui/card';
import { Package, TrendingUp, DollarSign, Layers } from 'lucide-react';

interface InventoryStatsProps {
  items: InventoryItem[];
}

export const InventoryStats = ({ items }: InventoryStatsProps) => {
  const totalItems = items.length;
  const totalWeight = items.reduce((acc, item) => {
    if (item.unit === 'kg') return acc + item.quantity;
    if (item.unit === 'ton') return acc + item.quantity * 1000;
    return acc;
  }, 0);
  const totalValue = items.reduce((acc, item) => acc + (item.estimatedValue || 0), 0);
  const categories = new Set(items.map(item => item.category)).size;

  const stats = [
    {
      label: 'Total de Itens',
      value: totalItems,
      icon: Package,
      color: 'text-primary',
    },
    {
      label: 'Peso Total',
      value: `${(totalWeight / 1000).toFixed(1)} ton`,
      icon: TrendingUp,
      color: 'text-info',
    },
    {
      label: 'Valor Estimado',
      value: `R$ ${totalValue.toFixed(2)}`,
      icon: DollarSign,
      color: 'text-success',
    },
    {
      label: 'Categorias',
      value: categories,
      icon: Layers,
      color: 'text-warning',
    },
  ];

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
      {stats.map((stat) => {
        const Icon = stat.icon;
        return (
          <Card key={stat.label} className="overflow-hidden">
            <CardContent className="p-6">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-sm text-muted-foreground mb-1">{stat.label}</p>
                  <p className="text-2xl font-bold">{stat.value}</p>
                </div>
                <div className={`p-3 rounded-lg bg-secondary ${stat.color}`}>
                  <Icon className="w-6 h-6" />
                </div>
              </div>
            </CardContent>
          </Card>
        );
      })}
    </div>
  );
};