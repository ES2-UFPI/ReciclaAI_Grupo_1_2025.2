package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import br.ufpi.recicle_ai.domain.model.Receptor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BeneficiamentoRepositoryTest {

    @Autowired
    private BeneficiamentoRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Deve salvar e buscar Beneficiamento por ID do receptor")
    void deveBuscarBeneficiamentosPorReceptorId() {
        // Arrange
        Receptor receptor1 = new Receptor();
        receptor1.setNome("Centro de Reciclagem XYZ");
        entityManager.persist(receptor1);

        Receptor receptor2 = new Receptor();
        receptor2.setNome("Cooperativa Verde");
        entityManager.persist(receptor2);

        Beneficiamento b1 = new Beneficiamento();
        b1.setReceptor(receptor1);
        entityManager.persist(b1);

        Beneficiamento b2 = new Beneficiamento();
        b2.setReceptor(receptor1);
        entityManager.persist(b2);

        Beneficiamento b3 = new Beneficiamento();
        b3.setReceptor(receptor2);
        entityManager.persist(b3);

        entityManager.flush();
        entityManager.clear();

        // Act
        List<Beneficiamento> resultado = repository.findAllByReceptor_id(receptor1.getId());

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(b -> b.getReceptor().getId().equals(receptor1.getId())));
    }

    @Test
    @DisplayName("Deve retornar lista vazia se receptor não tiver beneficiamentos")
    void deveRetornarListaVaziaQuandoNaoExistirBeneficiamentosParaReceptor() {
        // Arrange
        Receptor receptor = new Receptor();
        receptor.setNome("Receptor Inativo");
        entityManager.persist(receptor);

        entityManager.flush();

        // Act
        List<Beneficiamento> resultado = repository.findAllByReceptor_id(receptor.getId());

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve salvar e recuperar Beneficiamento pelo método padrão do JPA")
    void deveSalvarEBuscarBeneficiamentoPorId() {
        // Arrange
        Receptor receptor = new Receptor();
        receptor.setNome("Centro Ambiental");
        entityManager.persist(receptor);

        Beneficiamento beneficiamento = new Beneficiamento();
        beneficiamento.setReceptor(receptor);
        Beneficiamento salvo = repository.save(beneficiamento);

        // Act
        var encontrado = repository.findById(salvo.getId());

        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals(receptor.getId(), encontrado.get().getReceptor().getId());
    }
}

