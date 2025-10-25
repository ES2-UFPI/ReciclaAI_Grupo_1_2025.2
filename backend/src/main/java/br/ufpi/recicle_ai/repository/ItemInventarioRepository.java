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
    @Query("SELECT inven FROM ItemInventario inven " +
            "JOIN inven.item item " +
            "WHERE item.id = :itemId " +
            "AND inven.pessoaId = :pessoaId " +
            "AND inven.tipoPessoa = :tipoPessoa")
    Optional<ItemInventario> findByItemIdAndProdutorId(@Param("itemId") Long itemId, @Param("pessoaId")Long pessoaId, @Param("tipoPessoa")TipoPessoaEnum tipoPessoa);

    @Query("SELECT inven FROM ItemInventario inven " +
            "WHERE inven.pessoaId = :pessoaId " +
            "AND inven.tipoPessoa = :tipoPessoa")
    List<ItemInventario> findByPessoaIdAndTipoPessoa(@Param("pessoaId")Long pessoaId, @Param("tipoPessoa")TipoPessoaEnum tipoPessoa);
}
