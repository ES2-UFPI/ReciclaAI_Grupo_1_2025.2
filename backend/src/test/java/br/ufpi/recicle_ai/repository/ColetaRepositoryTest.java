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

    @BeforeEach
    void setup() {
        Coletor coletor = new Coletor();
        coletor.setNome("João Coletor");
        coletorSalvo = entityManager.persist(coletor);

        PontoColeta pontoCentro = new PontoColeta();
        pontoCentro.setBairro("Centro");
        entityManager.persist(pontoCentro);

        PontoColeta pontoSul = new PontoColeta();
        pontoSul.setBairro("Zona Sul");
        entityManager.persist(pontoSul);

        // Criando coletas com datas diferentes para testar a ordenação
        Coleta coletaCentro2 = new Coleta();
        coletaCentro2.setColetor(coletorSalvo);
        coletaCentro2.setPontoColeta(pontoCentro);
        coletaCentro2.setDataInicio(LocalDateTime.now().plusDays(1)); // Data futura
        entityManager.persist(coletaCentro2);

        Coleta coletaCentro1 = new Coleta();
        coletaCentro1.setColetor(coletorSalvo);
        coletaCentro1.setPontoColeta(pontoCentro);
        coletaCentro1.setDataInicio(LocalDateTime.now()); // Data atual
        entityManager.persist(coletaCentro1);
        
        Coleta coletaSul = new Coleta();
        coletaSul.setColetor(coletorSalvo);
        coletaSul.setPontoColeta(pontoSul);
        coletaSul.setDataInicio(LocalDateTime.now());
        entityManager.persist(coletaSul);

        entityManager.flush();
    }

    @Test
    @DisplayName("Deve buscar coletas por bairro e ordenar por data de início ASC")
    void testFindAllByPontoColetaBairroContainingIgnoreCaseOrderByDataInicioAsc() {
        // Act
        Page<Coleta> result = coletaRepository
                .findAllByPontoColetaBairroContainingIgnoreCaseOrderByDataInicioAsc("cen", PageRequest.of(0, 10));

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(2);
        List<Coleta> conteudo = result.getContent();
        assertThat(conteudo.get(0).getPontoColeta().getBairro()).isEqualTo("Centro");
        // Verifica se o primeiro item da lista é o que tem a data mais antiga
        assertThat(conteudo.get(0).getDataInicio()).isBefore(conteudo.get(1).getDataInicio());
    }

    @Test
    @DisplayName("Deve buscar coletas por ID do coletor e ordenar por data de início ASC")
    void testFindAllByColetorIdOrderByDataInicioAsc() {
        // Act
        Pageable pageable = PageRequest.of(0, 10);
        Page<Coleta> result = coletaRepository.findAllByColetorIdOrderByDataInicioAsc(coletorSalvo.getId(), pageable);

        // Assert
        assertThat(result).isNotEmpty();
        assertThat(result.getTotalElements()).isEqualTo(3);
        List<Coleta> conteudo = result.getContent();
        // Verifica a ordenação
        assertThat(conteudo.get(0).getDataInicio()).isBeforeOrEqualTo(conteudo.get(1).getDataInicio());
        assertThat(conteudo.get(1).getDataInicio()).isBefore(conteudo.get(2).getDataInicio());
    }
}
