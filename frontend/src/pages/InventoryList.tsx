import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { InventoryCard } from '@/components/inventory/InventoryCard';
import { Search, Coins, Wallet } from 'lucide-react';
import { listarInventario, obterSaldoMoedasVerdes, obterSaldoColetor } from '@/services/inventoryService';
import { InventoryItem } from '@/types/api';
import { useAuth } from '@/contexts/AuthContext';
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/card';

const InventoryList = () => {
  const navigate = useNavigate();
  const { user } = useAuth();
  const [items, setItems] = useState<InventoryItem[]>([]);
  const [saldoMoedas, setSaldoMoedas] = useState<number | null>(null);
  const [saldoColetor, setSaldoColetor] = useState<number | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchInventory = async () => {
      if (!user) {
        setError('Usuário não autenticado');
        setLoading(false);
        return;
      }

      try {
        const data = await listarInventario(user.pessoaId, user.tipoPessoa);
        setItems(data);

        // Buscar saldo de moedas verdes apenas para PRODUTOR
        if (user.tipoPessoa === 'PRODUTOR') {
          try {
            const moedasData = await obterSaldoMoedasVerdes(user.pessoaId);
            console.log('Saldo Moedas Verdes:', moedasData);
            setSaldoMoedas(moedasData.saldoMoedasVerdes);
          } catch (err) {
            console.error('Erro ao carregar saldo de moedas verdes:', err);
          }
        }

        // Buscar saldo financeiro apenas para COLETOR
        if (user.tipoPessoa === 'COLETOR') {
          try {
            console.log('Buscando saldo do coletor:', user.pessoaId);
            const coletorData = await obterSaldoColetor(user.pessoaId);
            console.log('Dados do coletor:', coletorData);
            setSaldoColetor(coletorData.saldo);
          } catch (err) {
            console.error('Erro ao carregar saldo do coletor:', err);
          }
        }
      } catch (err) {
        setError('Erro ao carregar inventário');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchInventory();
  }, [user]);

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

      {/* Card de Moedas Verdes - apenas para PRODUTOR */}
      {user?.tipoPessoa === 'PRODUTOR' && saldoMoedas !== null && (
        <Card className="mb-6 bg-gradient-to-r from-green-50 to-emerald-50 border-green-200">
          <CardHeader className="pb-3">
            <CardTitle className="flex items-center gap-2 text-green-800">
              <div className="p-2 bg-green-600 rounded-lg">
                <Coins className="w-5 h-5 text-white" />
              </div>
              Moedas Verdes
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="flex items-baseline gap-2">
              <span className="text-4xl font-bold text-green-700">
                {saldoMoedas.toLocaleString('pt-BR')}
              </span>
              <span className="text-lg text-green-600">créditos</span>
            </div>
            <p className="text-sm text-green-600 mt-2">
              Continue reciclando para acumular mais créditos!
            </p>
          </CardContent>
        </Card>
      )}

      {/* Card de Saldo Financeiro - apenas para COLETOR */}
      {user?.tipoPessoa === 'COLETOR' && saldoColetor !== null && (
        <Card className="mb-6 bg-gradient-to-r from-blue-50 to-cyan-50 border-blue-200">
          <CardHeader className="pb-3">
            <CardTitle className="flex items-center gap-2 text-blue-800">
              <div className="p-2 bg-blue-600 rounded-lg">
                <Wallet className="w-5 h-5 text-white" />
              </div>
              Saldo Disponível
            </CardTitle>
          </CardHeader>
          <CardContent>
            <div className="flex items-baseline gap-2">
              <span className="text-4xl font-bold text-blue-700">
                R$ {saldoColetor.toFixed(2)}
              </span>
            </div>
            <p className="text-sm text-blue-600 mt-2">
              Receita acumulada dos beneficiamentos realizados
            </p>
          </CardContent>
        </Card>
      )}

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