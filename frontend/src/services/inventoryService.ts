import { API_BASE_URL } from '@/config/api';
import { InventoryItem, TipoPessoa } from '@/types/api';

export async function listarInventario(pessoaId: number, tipoPessoa: TipoPessoa): Promise<InventoryItem[]> {
  const response = await fetch(`${API_BASE_URL}/inventario/pessoa/${pessoaId}?tipoPessoa=${tipoPessoa}`);
  
  if (!response.ok) {
    throw new Error('Erro ao buscar inventário');
  }
  
  return response.json();
}

interface ItemInventarioUpdateForm {
  quantidade: number;
}

export async function atualizarQuantidadeInventario(
  itemInventarioId: number, 
  quantidade: number
): Promise<InventoryItem> {
  const response = await fetch(`${API_BASE_URL}/inventario/${itemInventarioId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ quantidade }),
  });
  
  if (!response.ok) {
    throw new Error('Erro ao atualizar quantidade');
  }
  
  return response.json();
}

export interface SaldoMoedasVerdes {
  saldoMoedasVerdes: number;
}

export async function obterSaldoMoedasVerdes(produtorId: number): Promise<SaldoMoedasVerdes> {
  const response = await fetch(`${API_BASE_URL}/produtores/${produtorId}`);
  
  if (!response.ok) {
    throw new Error('Erro ao buscar saldo de moedas verdes');
  }
  
  return response.json();
}

export interface SaldoColetor {
  saldo: number;
}

export async function obterSaldoColetor(coletorId: number): Promise<SaldoColetor> {
  const response = await fetch(`${API_BASE_URL}/coletores/${coletorId}`);
  
  if (!response.ok) {
    throw new Error('Erro ao buscar saldo do coletor');
  }
  
  return response.json();
}