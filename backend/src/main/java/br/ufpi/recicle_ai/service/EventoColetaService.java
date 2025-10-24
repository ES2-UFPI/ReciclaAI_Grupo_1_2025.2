package br.ufpi.recicle_ai.service;


import java.util.List;
import java.util.stream.Collectors;

import br.ufpi.recicle_ai.domain.dto.AgenteDTO;
import br.ufpi.recicle_ai.domain.dto.EventoColetaResponseDTO;
import org.springframework.stereotype.Service;

import br.ufpi.recicle_ai.mapper.EventoColetaMapper;
import br.ufpi.recicle_ai.domain.model.EventoColeta;
import br.ufpi.recicle_ai.repository.EventoColetaRepository;

@Service
public class EventoColetaService {

    private final EventoColetaRepository repository;
    // Assumindo que você tem um Mapper (ex: MapStruct, ou manual)
    private final EventoColetaMapper mapper; 

    public EventoColetaService(EventoColetaRepository repository, EventoColetaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper; // Injeção do Mapper (substitua por seu próprio Mapper)
    }

    public List<EventoColetaResponseDTO> buscarEventosPorBairro(String bairro) {
        if (bairro == null || bairro.trim().isEmpty()) {
            // Em um ambiente real, você pode lançar uma exceção 400 Bad Request
            throw new IllegalArgumentException("O nome do bairro não pode ser vazio.");
        }
        
        List<EventoColeta> eventos = repository.findByPontoColeta_Bairro(bairro);
        return null;
        // Conversão Entity -> DTO
//        return eventos.stream()
//                      .map(mapper::toDTO) // O 'mapper' faria a conversão completa
//                      .collect(Collectors.toList());
    }
}