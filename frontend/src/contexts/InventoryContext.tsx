import { createContext, useContext, useState, ReactNode } from 'react';
import { InventoryItem } from '@/types/inventory';
import { mockInventoryItems } from '@/data/mockInventory';

interface InventoryContextType {
  items: InventoryItem[];
  addItem: (item: Partial<InventoryItem>) => void;
  removeItem: (id: string) => void;
}

const InventoryContext = createContext<InventoryContextType | undefined>(undefined);

export function InventoryProvider({ children }: { children: ReactNode }) {
  const [items, setItems] = useState<InventoryItem[]>(mockInventoryItems);

  const addItem = (newItem: Partial<InventoryItem>) => {
    const item: InventoryItem = {
      id: crypto.randomUUID(),
      status: 'ready',
      createdAt: new Date().toISOString(),
      ...newItem,
    } as InventoryItem;
    
    setItems((prevItems) => [...prevItems, item]);
  };

  const removeItem = (id: string) => {
    setItems((prevItems) => prevItems.filter((item) => item.id !== id));
  };

  return (
    <InventoryContext.Provider value={{ items, addItem, removeItem }}>
      {children}
    </InventoryContext.Provider>
  );
}

export function useInventory() {
  const context = useContext(InventoryContext);
  if (context === undefined) {
    throw new Error('useInventory must be used within an InventoryProvider');
  }
  return context;
}