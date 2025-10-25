import { API_BASE_URL } from '@/config/api';
import { Coleta, EventoColeta, PageableResponse } from '@/types/api';

export async function listarColetas(page = 0, size = 5): Promise<PageableResponse<Coleta>> {
  const response = await fetch(`${API_BASE_URL}/coletas?page=${page}&size=${size}`);
  
  if (!response.ok) {
    throw new Error('Erro ao buscar coletas');
  }
  
  return response.json();
}

export async function listarColetasAgendadas(produtorId: number): Promise<EventoColeta[]> {
  const response = await fetch(`${API_BASE_URL}/eventos-coleta/produtor/${produtorId}`);
  
  if (!response.ok) {
    throw new Error('Erro ao buscar coletas agendadas');
  }
  
  return response.json();
}

export async function buscarColetasPorBairro(
  bairro: string, 
  page = 0, 
  size = 4
): Promise<PageableResponse<Coleta>> {
  const response = await fetch(
    `${API_BASE_URL}/coletas/por-bairro?bairro=${encodeURIComponent(bairro)}&page=${page}&size=${size}`
  );
  
  if (!response.ok) {
    throw new Error('Erro ao buscar coletas por bairro');
  }
  
  return response.json();
}

export async function criarEventoColeta(
  coletaId: number,
  produtorId: number
): Promise<EventoColeta> {
  const response = await fetch(`${API_BASE_URL}/eventos-coleta`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ coletaId, produtorId }),
  });

  const data = await response.json();

  if (!response.ok) {
    throw {
      status: response.status,
      message: data.message,
      error: data.error
    };
  }

  return data;
}

export async function adicionarItemEventoColeta(
  eventoColetaId: number,
  itemId: number,
  quantidade: number
): Promise<any> {
  const response = await fetch(`${API_BASE_URL}/itens-evento-coleta`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ 
      eventoColetaId, 
      itemId, 
      quantidade 
    }),
  });

  const data = await response.json();

  if (!response.ok) {
    throw {
      status: response.status,
      message: data.message,
      error: data.error
    };
  }

  return data;
}

export async function deletarEventoColeta(id: number): Promise<void> {
  const response = await fetch(`${API_BASE_URL}/eventos-coleta/${id}`, {
    method: 'DELETE',
  });

  if (!response.ok) {
    throw new Error('Erro ao deletar evento de coleta');
  }
}