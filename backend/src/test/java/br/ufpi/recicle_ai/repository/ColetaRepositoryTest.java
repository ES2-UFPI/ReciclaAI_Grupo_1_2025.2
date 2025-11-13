package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.model.coleta.Coleta;
import br.ufpi.recicle_ai.domain.model.Coletor;
import br.ufpi.recicle_ai.domain.model.coleta.PontoColeta;
import org.junit.jupiter.api.BeforeEach;
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

@DataJpaTest
class ColetaRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ColetaRepository coletaRepository;

    private Coletor coletorSalvo;
    private PontoColeta pontoCentroSalvo;

    @BeforeEach
    void setup() {
        Coletor coletor = new Coletor();
        coletor.setNome("João Coletor");
        coletorSalvo = entityManager.persist(coletor);

        pontoCentroSalvo = new PontoColeta();
        pontoCentroSalvo.setBairro("Centro");
        entityManager.persist(pontoCentroSalvo);

        PontoColeta pontoSul = new PontoColeta();
        pontoSul.setBairro("Zona Sul");
        entityManager.persist(pontoSul);

        Coleta coleta2 = new Coleta();
        coleta2.setColetor(coletorSalvo);
        coleta2.setPontoColeta(pontoSul);
        coleta2.setDataInicio(LocalDateTime.now().plusDays(1)); // Data futura
        entityManager.persist(coleta2);

        Coleta coleta1 = new Coleta();
        coleta1.setColetor(coletorSalvo);
        coleta1.setPontoColeta(pontoCentroSalvo);
        coleta1.setDataInicio(LocalDateTime.now()); // Data atual
        entityManager.persist(coleta1);

        entityManager.flush();
    }

    @Test
    @DisplayName("Deve buscar coletas por bairro ignorando maiúsculas/minúsculas")
    void testFindAllByPontoColetaBairroContainingIgnoreCase() {
        // Act
        Page<Coleta> result = coletaRepository
                .findAllByPontoColetaBairroContainingIgnoreCase("cen", PageRequest.of(0, 10));

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getPontoColeta().getBairro()).isEqualTo("Centro");
    }

    @Test
    @DisplayName("Deve buscar coletas por ID do coletor e ordenar por data de início ASC")
    void testFindAllByColetorIdOrderByDataInicioAsc() {
        // Act
        Pageable pageable = PageRequest.of(0, 10);
        Page<Coleta> result = coletaRepository.findAllByColetorIdOrderByDataInicioAsc(coletorSalvo.getId(), pageable);

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(2);
        List<Coleta> conteudo = result.getContent();
        // Verifica se o primeiro item da lista é o que tem a data mais antiga
        assertThat(conteudo.get(0).getDataInicio()).isBefore(conteudo.get(1).getDataInicio());
    }

    @Test
    @DisplayName("Deve retornar página vazia quando nenhum bairro combinar")
    void testFindByBairro_NotFound() {
        // Act
        Page<Coleta> result = coletaRepository
                .findAllByPontoColetaBairroContainingIgnoreCase("norte", PageRequest.of(0, 10));

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar página vazia ao buscar coletas de coletor inexistente")
    void testFindByColetorId_NotFound() {
        // Act
        Page<Coleta> result = coletaRepository.findAllByColetorIdOrderByDataInicioAsc(999L, PageRequest.of(0, 10));

        // Assert
        assertThat(result).isEmpty();
    }
}
