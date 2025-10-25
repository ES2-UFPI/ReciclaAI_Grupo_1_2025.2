package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.model.coleta.ItemColeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemColetaRepository extends JpaRepository<ItemColeta, Long> {

}
