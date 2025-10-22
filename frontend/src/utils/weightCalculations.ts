import { InventoryItem } from '@/types/inventory';

export const convertToKg = (quantity: number, unit?: string): number => {
  switch (unit) {
    case 'ton':
      return quantity * 1000;
    case 'kg':
      return quantity;
    case 'unit':
    default:
      return 0; // Units don't count towards weight
  }
};

export const calculateTotalWeight = (items: InventoryItem[]): number => {
  return items.reduce((total, item) => {
    return total + convertToKg(item.quantity, item.unit);
  }, 0);
};

export const formatWeight = (weightInKg: number): string => {
  if (weightInKg >= 1000) {
    return `${(weightInKg / 1000).toFixed(2)} ton`;
  }
  return `${weightInKg.toFixed(2)} kg`;
};