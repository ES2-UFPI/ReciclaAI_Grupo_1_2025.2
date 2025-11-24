import { API_BASE_URL } from '@/config/api';
import { Coleta, EventoColeta, Item, PageableResponse } from '@/types/api';

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

export async function listarColetasAgendadasColetor(coletorId: number): Promise<EventoColeta[]> {
  const response = await fetch(`${API_BASE_URL}/eventos-coleta/coletor/${coletorId}`);
  
  if (!response.ok) {
    throw new Error('Erro ao carregar coletas agendadas');
  }
  
  return response.json();
}

export async function confirmarEventoColeta(id: number): Promise<void> {
  const response = await fetch(`${API_BASE_URL}/eventos-coleta/${id}/confirmar`, {
    method: 'PUT',
  });

  const data = await response.json();

  if (!response.ok) {
    throw {
      status: response.status,
      message: data.message,
      error: data.error
    };
  }
}

export async function listarColetasColetor(
  coletorId: number,
  page = 0,
  size = 10
): Promise<PageableResponse<Coleta>> {
  const response = await fetch(
    `${API_BASE_URL}/coletas/coletor/${coletorId}?page=${page}&size=${size}`
  );
  
  if (!response.ok) {
    throw new Error('Erro ao buscar coletas do coletor');
  }
  
  return response.json();
}

interface CriarColetaRequest {
  coletorId: number;
  dataInicio: string;
  dataFim: string;
  pontoColeta: {
    logradouro: string;
    numero: string;
    bairro: string;
    cep: string;
  };
}

export async function criarColeta(data: CriarColetaRequest): Promise<Coleta> {
  const response = await fetch(`${API_BASE_URL}/coletas`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  });

  const result = await response.json();

  if (!response.ok) {
    throw {
      status: response.status,
      message: result.message,
      error: result.error
    };
  }

  return result;
}

interface AdicionarItemColetaRequest {
  quantidadeMinima: number;
  coletaId: number;
  itemId: number;
}

export async function adicionarItemColeta(data: AdicionarItemColetaRequest): Promise<any> {
  const response = await fetch(`${API_BASE_URL}/itens-coleta`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(data),
  });

  const result = await response.json();

  if (!response.ok) {
    throw {
      status: response.status,
      message: result.message,
      error: result.error
    };
  }

  return result;
}

export async function listarTodosItens(): Promise<Item[]> {
  // Mock data - no API endpoint available
  return Promise.resolve([
    {
      id: 1,
      nome: "Garrafas de Vidro",
      unidade: "unidade"
    },
    {
      id: 2,
      nome: "Garrafas PET",
      unidade: "unidade"
    },
    {
      id: 3,
      nome: "Alumínio",
      unidade: "kg"
    },
    {
      id: 4,
      nome: "Papelão",
      unidade: "kg"
    }
  ]);
}