package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.model.coleta.PontoColeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontoColetaRepository extends JpaRepository<PontoColeta, Long>{
    
}
