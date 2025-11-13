package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.model.Receptor;
import br.ufpi.recicle_ai.domain.model.coleta.PontoColeta;
import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BeneficiamentoRepositoryTest {

    @Autowired
    private BeneficiamentoRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    private PontoColeta criarPontoColeta() {
        PontoColeta ponto = new PontoColeta();
        ponto.setBairro("Centro");
        ponto.setCep("64000-000");
        ponto.setLogradouro("Rua das Árvores");
        ponto.setNumero("123");
        return entityManager.persist(ponto);
    }

    @Test
    @DisplayName("Deve buscar beneficiamentos por ID do receptor e ordenar por data de início ASC")
    void deveBuscarBeneficiamentosPorReceptorIdOrdenadoPorData() {
        // Arrange
        PontoColeta ponto = criarPontoColeta();

        Receptor receptor1 = new Receptor();
        receptor1.setNome("Centro de Reciclagem XYZ");
        entityManager.persist(receptor1);

        // Criando beneficiamentos com datas diferentes para testar a ordenação
        Beneficiamento b2 = new Beneficiamento();
        b2.setReceptor(receptor1);
        b2.setPontoColeta(ponto);
        b2.setDataInicio(LocalDateTime.now().plusDays(1)); // Data futura
        entityManager.persist(b2);

        Beneficiamento b1 = new Beneficiamento();
        b1.setReceptor(receptor1);
        b1.setPontoColeta(ponto);
        b1.setDataInicio(LocalDateTime.now()); // Data atual
        entityManager.persist(b1);

        entityManager.flush();

        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Beneficiamento> resultado = repository.findAllByReceptorIdOrderByDataInicioAsc(receptor1.getId(), pageable);

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.getTotalElements());
        List<Beneficiamento> conteudo = resultado.getContent();
        // Verifica se o primeiro item da lista é o que tem a data mais antiga
        assertThat(conteudo.get(0).getDataInicio()).isBefore(conteudo.get(1).getDataInicio());
        assertTrue(conteudo.stream().allMatch(b -> b.getReceptor().getId().equals(receptor1.getId())));
    }

    @Test
    @DisplayName("Deve retornar página vazia se receptor não tiver beneficiamentos")
    void deveRetornarPaginaVaziaQuandoNaoExistirBeneficiamentosParaReceptor() {
        // Arrange
        Receptor receptor = new Receptor();
        receptor.setNome("Receptor Inativo");
        entityManager.persist(receptor);
        entityManager.flush();

        Pageable pageable = PageRequest.of(0, 10);

        // Act
        Page<Beneficiamento> resultado = repository.findAllByReceptorIdOrderByDataInicioAsc(receptor.getId(), pageable);

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Deve salvar e recuperar Beneficiamento pelo método padrão do JPA")
    void deveSalvarEBuscarBeneficiamentoPorId() {
        // Arrange
        PontoColeta ponto = criarPontoColeta();

        Receptor receptor = new Receptor();
        receptor.setNome("Centro Ambiental");
        entityManager.persist(receptor);

        Beneficiamento beneficiamento = new Beneficiamento();
        beneficiamento.setReceptor(receptor);
        beneficiamento.setPontoColeta(ponto);
        beneficiamento.setDataInicio(LocalDateTime.now());
        Beneficiamento salvo = repository.save(beneficiamento);

        // Act
        var encontrado = repository.findById(salvo.getId());

        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals(receptor.getId(), encontrado.get().getReceptor().getId());
        assertEquals(ponto.getId(), encontrado.get().getPontoColeta().getId());
    }
}
