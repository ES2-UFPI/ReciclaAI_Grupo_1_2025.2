package br.ufpi.recicle_ai.domain.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.ufpi.recicle_ai.domain.model.EventoColeta;

public interface EventoColetaRepository extends JpaRepository<EventoColeta, Long> {

    /**
     * Busca todos os Eventos de Coleta cujo PontoColeta tem o bairro especificado.
     * Assumindo que EventoColeta tem um campo 'pontoColeta' que é uma entidade.
     * * @param bairro O nome do bairro a ser filtrado.
     * @return Uma lista de EventoColeta.
     */
    List<EventoColeta> findByPontoColeta_Bairro(String bairro);

    // OU (se você precisar ignorar caixa alta/baixa, dependendo do SGBD)
    // List<EventoColeta> findByPontoColeta_BairroIgnoreCase(String bairro);
}

