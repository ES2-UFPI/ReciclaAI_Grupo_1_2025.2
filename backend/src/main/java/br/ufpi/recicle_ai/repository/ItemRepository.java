package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.model.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import
 org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
