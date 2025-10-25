package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.enuns.TipoPessoaEnum;
import br.ufpi.recicle_ai.domain.model.item.ItemInventario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemInventarioRepository extends JpaRepository<ItemInventario, Long> {

    Optional<ItemInventario> findByPessoaIdAndTipoPessoaAndItemId(Long pessoaId, TipoPessoaEnum tipoPessoa, Long itemId);

    List<ItemInventario> findByPessoaIdAndTipoPessoa(Long pessoaId, TipoPessoaEnum tipoPessoa);
}
