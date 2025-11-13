// src/services/historicoService.ts
import { API_BASE_URL } from '@/config/api';
import { TipoPessoa } from '@/types/api';

export interface EventoHistorico {
  eventoId: number;
  tipoEvento: 'COLETA' | 'BENEFICIAMENTO';
  status: 'AGENDADA' | 'CANCELADA' | 'CONCLUIDA';
  data: string;
  nomeParticipante: string;
  nomeLocal: string;
  itens: string[];
}

export async function listarHistorico(pessoaId: number, tipoPessoa: TipoPessoa): Promise<EventoHistorico[]> {
  const response = await fetch(
    `${API_BASE_URL}/historico?pessoaId=${pessoaId}&tipoPessoa=${tipoPessoa}`
  );
  
  if (!response.ok) {
    throw new Error('Erro ao buscar histórico');
  }
  
  return response.json();
}