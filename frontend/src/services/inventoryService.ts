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