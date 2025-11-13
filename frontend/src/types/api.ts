export interface PageableResponse<T> {
  content: T[];
  pageable: {
    pageNumber: number;
    pageSize: number;
    sort: Sort;
    offset: number;
    paged: boolean;
    unpaged: boolean;
  };
  last: boolean;
  totalPages: number;
  totalElements: number;
  first: boolean;
  size: number;
  number: number;
  sort: Sort;
  numberOfElements: number;
  empty: boolean;
}

interface Sort {
  empty: boolean;
  sorted: boolean;
  unsorted: boolean;
}

export interface Agente {
  id: number;
  nome: string;
  tipoAgente: 'PESSOA_FISICA' | 'PESSOA_JURIDICA';
  cpf: string | null;
  cnpj: string | null;
}

export interface PontoColeta {
  id: number;
  logradouro: string;
  numero: string;
  bairro: string;
  cep: string;
}

export interface Item {
  id: number;
  nome: string;
  unidade: string;
}

export interface ItemColeta {
  id: number;
  quantidadeMinima: number;
  item: Item;
}

export interface Coleta {
  id: number;
  coletor: Agente;
  dataInicio: string;
  dataFim: string;
  pontoColeta: PontoColeta;
  itensColeta: ItemColeta[];
}

export interface ItemEvento {
  id: number;
  quantidade: number;
  item: Item;
}

export interface EventoColeta {
  id: number;
  coleta: Coleta;
  produtor: Agente;
  status: 'AGENDADA' | 'CANCELADA' | 'CONCLUIDA';
  itens: ItemEvento[];
}

export type TipoPessoa = 'PRODUTOR' | 'COLETOR' | 'RECEPTOR';

export interface InventoryItem {
  id: number;
  quantidade: number;
  item: {
    id: number;
    nome: string;
    unidade: string;
  };
  pessoaId: number;
  tipoPessoa: TipoPessoa;
}

export interface PontoBeneficiamento {
  id: number;
  logradouro: string;
  numero: string;
  bairro: string;
  cep: string;
}

export interface ItemBeneficiamento {
  id: number;
  quantidadeMinima: number;
  item: Item;
}

export interface Beneficiamento {
  id: number;
  receptor: Agente;
  dataInicio: string;
  dataFim: string;
  pontoColeta: PontoBeneficiamento;
  itensBeneficiamento: ItemBeneficiamento[];
}

export interface EventoBeneficiamento {
  id: number;
  beneficiamento: Beneficiamento;
  coletor: Agente;
  status: 'AGENDADA' | 'CANCELADA' | 'CONCLUIDA';
  itens: ItemEventoBeneficiamento[];
}

export interface ItemEventoBeneficiamento {
  id: number;
  quantidade: number;
  item: Item;
}