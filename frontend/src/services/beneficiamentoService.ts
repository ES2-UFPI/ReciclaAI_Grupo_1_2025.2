import { API_BASE_URL } from '@/config/api';
import { Beneficiamento, EventoBeneficiamento, PageableResponse } from '@/types/api';

interface CriarBeneficiamentoRequest {
  receptorId: number;
  dataInicio: string;
  dataFim: string;
  pontoColeta: {
    id?: number;
    logradouro: string;
    numero: string;
    bairro: string;
    cep: string;
  };
}

export async function criarBeneficiamento(data: CriarBeneficiamentoRequest): Promise<Beneficiamento> {
  const response = await fetch(`${API_BASE_URL}/beneficiamentos`, {
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

export async function listarBeneficiamentosReceptor(
  receptorId: number,
  page = 0,
  size = 10
): Promise<PageableResponse<Beneficiamento>> {
  const response = await fetch(
    `${API_BASE_URL}/beneficiamentos/receptor/${receptorId}?page=${page}&size=${size}`
  );
  
  if (!response.ok) {
    throw new Error('Erro ao buscar beneficiamentos do receptor');
  }
  
  return response.json();
}

interface AdicionarItemBeneficiamentoRequest {
  quantidadeMinima: number;
  beneficiamentoId: number;
  itemId: number;
}

export async function adicionarItemBeneficiamento(data: AdicionarItemBeneficiamentoRequest): Promise<any> {
  const response = await fetch(`${API_BASE_URL}/itens-beneficiamento`, {
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

export async function listarBeneficiamentosAgendadosColetor(coletorId: number): Promise<EventoBeneficiamento[]> {
  const response = await fetch(`${API_BASE_URL}/eventos-beneficiamento/coletor/${coletorId}`);
  
  if (!response.ok) {
    throw new Error('Erro ao carregar beneficiamentos agendados');
  }
  
  return response.json();
}

export async function buscarBeneficiamentosPorBairro(
  bairro: string, 
  page = 0, 
  size = 4
): Promise<PageableResponse<Beneficiamento>> {
  const response = await fetch(
    `${API_BASE_URL}/beneficiamentos/por-bairro?bairro=${encodeURIComponent(bairro)}&page=${page}&size=${size}`
  );
  
  if (!response.ok) {
    throw new Error('Erro ao buscar beneficiamentos por bairro');
  }
  
  return response.json();
}

export async function criarEventoBeneficiamento(
  beneficiamentoId: number,
  coletorId: number
): Promise<EventoBeneficiamento> {
  const response = await fetch(`${API_BASE_URL}/eventos-beneficiamento`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ beneficiamentoId, coletorId }),
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

export async function deletarEventoBeneficiamento(id: number): Promise<void> {
  const response = await fetch(`${API_BASE_URL}/eventos-beneficiamento/${id}`, {
    method: 'DELETE',
  });

  if (!response.ok) {
    throw new Error('Erro ao deletar evento de beneficiamento');
  }
}

export async function adicionarItemEventoBeneficiamento(
  eventoBeneficiamentoId: number,
  itemId: number,
  quantidade: number
): Promise<any> {
  const response = await fetch(`${API_BASE_URL}/itens-evento-beneficiamento`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ 
      eventoBeneficiamentoId, 
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