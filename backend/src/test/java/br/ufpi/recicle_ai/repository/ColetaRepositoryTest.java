package br.ufpi.recicle_ai.repository;

import br.ufpi.recicle_ai.domain.model.coleta.Coleta;
import br.ufpi.recicle_ai.domain.model.Coletor;
import br.ufpi.recicle_ai.domain.model.coleta.PontoColeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = "spring.main.web-application-type=none")
class ColetaRepositoryTest {

    @Autowired
    private ColetaRepository coletaRepository;

    private Coleta coletaCentro;
    private Coleta coletaSul;
    private Coletor coletor1;
    private Coletor coletor2;

    @BeforeEach
    void setup() {
        // Criando coletores fictícios
        coletor1 = new Coletor();
        coletor1.setId(1L);
        coletor1.setNome("João");

        coletor2 = new Coletor();
        coletor2.setId(2L);
        coletor2.setNome("Maria");

        // Criando pontos de coleta fictícios
        PontoColeta pontoCentro = new PontoColeta();
        pontoCentro.setId(1L);
        pontoCentro.setBairro("Centro");

        PontoColeta pontoSul = new PontoColeta();
        pontoSul.setId(2L);
        pontoSul.setBairro("Zona Sul");

        // Criando coletas fictícias
        coletaCentro = new Coleta();
        coletaCentro.setId(1L);
        coletaCentro.setPontoColeta(pontoCentro);
        coletaCentro.setColetor(coletor1);

        coletaSul = new Coleta();
        coletaSul.setId(2L);
        coletaSul.setPontoColeta(pontoSul);
        coletaSul.setColetor(coletor2);

        coletaRepository.saveAll(List.of(coletaCentro, coletaSul));
    }

    @Test
    @DisplayName("Deve buscar coletas por bairro ignorando maiúsculas/minúsculas e substring")
    void testFindAllByPontoColetaBairroContainingIgnoreCase() {
        Page<Coleta> result = coletaRepository
                .findAllByPontoColetaBairroContainingIgnoreCase("cent", PageRequest.of(0, 10));

        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .extracting(c -> c.getPontoColeta().getBairro())
                .contains("Centro");
    }

    @Test
    @DisplayName("Deve retornar página vazia quando nenhum bairro combinar")
    void testFindAllByPontoColetaBairroContainingIgnoreCaseEmpty() {
        Page<Coleta> result = coletaRepository
                .findAllByPontoColetaBairroContainingIgnoreCase("norte", PageRequest.of(0, 10));

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Deve buscar coletas pelo ID do coletor corretamente")
    void testFindAllByColetorId() {
        Page<Coleta> result = coletaRepository.findAllByColetor_id(1L, PageRequest.of(0, 10));

        assertThat(result).isNotEmpty();
        assertThat(result.getContent())
                .extracting(c -> c.getColetor().getId())
                .contains(1L);
    }

    @Test
    @DisplayName("Deve retornar página vazia ao buscar coletas de coletor inexistente")
    void testFindAllByColetorIdEmpty() {
        Page<Coleta> result = coletaRepository.findAllByColetor_id(999L, PageRequest.of(0, 10));

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Deve retornar todas as coletas salvas no repositório")
    void testFindAllDefaultJpaMethod() {
        List<Coleta> all = coletaRepository.findAll();

        assertThat(all).extracting(Coleta::getId).contains(1L, 2L);
    }

    @Test
    @DisplayName("Deve persistir e buscar coleta pelo ID corretamente")
    void testSaveAndFindById() {
        Coleta nova = new Coleta();
        nova.setId(3L);
        nova.setColetor(coletor1);

        coletaRepository.save(nova);

        Coleta found = coletaRepository.findById(3L).orElseThrow();
        assertThat(found.getId()).isEqualTo(3L);
        assertThat(found.getColetor().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("Deve deletar uma coleta corretamente")
    void testDeleteById() {
        coletaRepository.deleteById(1L);
        assertThat(coletaRepository.findById(1L)).isEmpty();
    }
}

