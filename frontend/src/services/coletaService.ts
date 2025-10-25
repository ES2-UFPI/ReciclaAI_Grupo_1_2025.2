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