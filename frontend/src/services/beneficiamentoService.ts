import { API_BASE_URL } from '@/config/api';
import { Beneficiamento, PageableResponse } from '@/types/api';

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