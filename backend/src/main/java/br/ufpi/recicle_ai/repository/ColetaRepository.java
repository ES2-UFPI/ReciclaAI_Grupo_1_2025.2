package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.model.coleta.Coleta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ColetaRepository extends JpaRepository<Coleta, Long> {
    Page<Coleta> findAllByPontoColetaBairroContainingIgnoreCase(String bairro, Pageable pageable);
}
