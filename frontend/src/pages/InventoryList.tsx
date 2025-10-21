import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { InventoryCard } from '@/components/inventory/InventoryCard';
import { InventoryStats } from '@/components/inventory/InventoryStats';
import { mockInventoryItems } from '@/data/mockInventory';
import { Plus, Search, Filter } from 'lucide-react';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';

const InventoryList = () => {
  const navigate = useNavigate();
  const [searchTerm, setSearchTerm] = useState('');
  const [categoryFilter, setCategoryFilter] = useState<string>('all');

  const filteredItems = mockInventoryItems.filter((item) => {
    const matchesSearch = item.name.toLowerCase().includes(searchTerm.toLowerCase()) ||
      item.description?.toLowerCase().includes(searchTerm.toLowerCase());
    const matchesCategory = categoryFilter === 'all' || item.category === categoryFilter;
    return matchesSearch && matchesCategory;
  });

  return (
    <div className="min-h-screen bg-background">
      {/* Header */}
      <header className="bg-gradient-eco text-primary-foreground py-8 px-4 shadow-soft">
        <div className="container mx-auto">
          <div className="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
            <div>
              <h1 className="text-3xl font-bold mb-2">Inventário de Reciclagem</h1>
              <p className="text-primary-foreground/90">
                Gerencie seus materiais recicláveis
              </p>
            </div>
            <Button
              onClick={() => navigate('/inventory/add')}
              size="lg"
              className="bg-white text-primary hover:bg-white/90 shadow-md"
            >
              <Plus className="mr-2 h-5 w-5" />
              Adicionar Item
            </Button>
          </div>
        </div>
      </header>

      <main className="container mx-auto px-4 py-8">
        {/* Stats */}
        <div className="mb-8">
          <InventoryStats items={filteredItems} />
        </div>

        {/* Filters */}
        <div className="mb-6 flex flex-col md:flex-row gap-4">
          <div className="relative flex-1">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 text-muted-foreground w-5 h-5" />
            <Input
              placeholder="Buscar itens..."
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="pl-10"
            />
          </div>
          <Select value={categoryFilter} onValueChange={setCategoryFilter}>
            <SelectTrigger className="w-full md:w-[200px]">
              <Filter className="mr-2 h-4 w-4" />
              <SelectValue placeholder="Categoria" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="all">Todas</SelectItem>
              <SelectItem value="plastic">Plástico</SelectItem>
              <SelectItem value="paper">Papel</SelectItem>
              <SelectItem value="glass">Vidro</SelectItem>
              <SelectItem value="metal">Metal</SelectItem>
              <SelectItem value="electronic">Eletrônico</SelectItem>
              <SelectItem value="organic">Orgânico</SelectItem>
            </SelectContent>
          </Select>
        </div>

        {/* Inventory Grid */}
        {filteredItems.length > 0 ? (
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {filteredItems.map((item) => (
              <InventoryCard key={item.id} item={item} />
            ))}
          </div>
        ) : (
          <div className="text-center py-12">
            <div className="inline-flex items-center justify-center w-16 h-16 rounded-full bg-muted mb-4">
              <Search className="w-8 h-8 text-muted-foreground" />
            </div>
            <h3 className="text-lg font-semibold mb-2">Nenhum item encontrado</h3>
            <p className="text-muted-foreground mb-4">
              Tente ajustar seus filtros de busca
            </p>
          </div>
        )}
      </main>
    </div>
  );
};

export default InventoryList;