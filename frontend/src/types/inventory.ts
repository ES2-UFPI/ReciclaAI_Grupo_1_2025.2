export type InventoryItem = {
  id: string;
  name: string;
  category: 'plastic' | 'paper' | 'glass' | 'metal' | 'electronic' | 'organic';
  quantity: number;
  unit?: 'kg' | 'ton' | 'unit';
  condition?: 'excellent' | 'good' | 'fair' | 'poor';
  description?: string;
  location?: string;
  estimatedValue?: number;
  status: 'ready' | 'pending';
  createdAt: string;
};

export const categoryLabels: Record<InventoryItem['category'], string> = {
  plastic: 'Plástico',
  paper: 'Papel',
  glass: 'Vidro',
  metal: 'Metal',
  electronic: 'Eletrônico',
  organic: 'Orgânico',
};

export const conditionLabels: Record<InventoryItem['condition'], string> = {
  excellent: 'Excelente',
  good: 'Bom',
  fair: 'Regular',
  poor: 'Ruim',
};