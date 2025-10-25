package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.model.Receptor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceptorRepository extends JpaRepository<Receptor, Long> {
}
