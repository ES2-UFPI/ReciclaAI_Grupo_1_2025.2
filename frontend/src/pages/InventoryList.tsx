import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { InventoryCard } from '@/components/inventory/InventoryCard';
import { Search } from 'lucide-react';
import { listarInventario } from '@/services/inventoryService';
import { InventoryItem } from '@/types/api';

const InventoryList = () => {
  const navigate = useNavigate();
  const [items, setItems] = useState<InventoryItem[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchInventory = async () => {
      try {
        const mockPessoaId = 1;
        const mockTipoPessoa = 'PRODUTOR' as const;
        
        const data = await listarInventario(mockPessoaId, mockTipoPessoa);
        setItems(data);
      } catch (err) {
        setError('Erro ao carregar inventário');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchInventory();
  }, []);

  if (loading) return <div>Carregando inventário...</div>;
  if (error) return <div className="text-red-500">{error}</div>;

  return (
    <div className="max-w-5xl">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-foreground mb-2">
          Inventário de Reciclagem
        </h1>
        <p className="text-muted-foreground">
          Gerencie seus materiais recicláveis
        </p>
      </div>

      {items.length > 0 ? (
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {items.map((inventoryItem) => (
            <InventoryCard
              key={inventoryItem.id}
              item={inventoryItem}
              onUpdate={(updatedItem) => {
                setItems(items.map(item => 
                  item.id === updatedItem.id ? updatedItem : item
                ));
              }}
            />
          ))}
        </div>
      ) : (
        <div className="text-center py-12">
          <div className="inline-flex items-center justify-center w-16 h-16 rounded-full bg-muted mb-4">
            <Search className="w-8 h-8 text-muted-foreground" />
          </div>
          <h3 className="text-lg font-semibold mb-2">Nenhum item encontrado</h3>
          <p className="text-muted-foreground mb-4">
            Seu inventário está vazio
          </p>
        </div>
      )}
    </div>
  );
};

export default InventoryList;