import { InventoryItem } from '@/types/inventory';
import { Scale, Package, Clock } from 'lucide-react';
import { calculateTotalWeight, formatWeight } from '@/utils/weightCalculations';

interface InventoryStatsProps {
  items: InventoryItem[];
}

export function InventoryStats({ items }: InventoryStatsProps) {
  const totalWeight = calculateTotalWeight(items);
  const totalItems = items.length;
  const pendingItems = items.filter((item) => item.status === 'pending').length;

  return (
    <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
      <div className="bg-card rounded-lg p-4 shadow-sm">
        <div className="flex items-center gap-2">
          <Scale className="h-5 w-5 text-primary" />
          <h3 className="font-semibold">Peso Total</h3>
        </div>
        <p className="text-2xl font-bold mt-2">{formatWeight(totalWeight)}</p>
      </div>

      <div className="bg-card rounded-lg p-4 shadow-sm">
        <div className="flex items-center gap-2">
          <Package className="h-5 w-5 text-primary" />
          <h3 className="font-semibold">Total de Itens</h3>
        </div>
        <p className="text-2xl font-bold mt-2">{totalItems}</p>
      </div>

      <div className="bg-card rounded-lg p-4 shadow-sm">
        <div className="flex items-center gap-2">
          <Clock className="h-5 w-5 text-primary" />
          <h3 className="font-semibold">Itens Pendentes</h3>
        </div>
        <p className="text-2xl font-bold mt-2">{pendingItems}</p>
      </div>
    </div>
  );
}