package br.ufpi.recicle_ai.domain.repository;

import br.ufpi.recicle_ai.domain.model.Itens;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ItensRepository extends JpaRepository<Itens, Long> {
    @Query("SELECT i FROM Itens i WHERE i.nomeItem = :nomeItem AND i.produtor.id = :produtorId")
    Optional<Itens> findByNomeItemAndProdutorId(@Param("nomeItem") String nomeItem, @Param("produtorId") Long produtorId);

    List<Itens> findByProdutorId(Long produtorId);
}
