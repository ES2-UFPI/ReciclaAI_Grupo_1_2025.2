package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.ColetorDTO;
import br.ufpi.recicle_ai.domain.dto.coleta.ColetaDTO;
import br.ufpi.recicle_ai.domain.model.Coletor;
import br.ufpi.recicle_ai.domain.model.coleta.Coleta;
import br.ufpi.recicle_ai.domain.model.coleta.PontoColeta;
import br.ufpi.recicle_ai.mapper.ColetaMapper;
import br.ufpi.recicle_ai.repository.ColetaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ufpi.recicle_ai.domain.form.coleta.ColetaForm;
import br.ufpi.recicle_ai.service.ColetorService;
import br.ufpi.recicle_ai.service.PontoColetaService;

@Service
@RequiredArgsConstructor
public class ColetaService {

    private final ColetaRepository coletaRepository;
    private final ColetaMapper coletaMapper;
    private final ColetorService coletorService;    
    private final PontoColetaService pontoColetaService;

    @Transactional(readOnly = true)
    public Page<ColetaDTO> findAll(Pageable pageable) {
        return coletaRepository.findAll(pageable).map(coletaMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Coleta findEntityById(Long id) {
        return coletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coleta não encontrada!"));
    }

    @Transactional(readOnly = true)
    public Page<ColetaDTO> findByBairro(String bairro, Pageable pageable) {
        return coletaRepository.findAllByPontoColetaBairroContainingIgnoreCase(bairro, pageable)
                .map(coletaMapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<ColetaDTO> findByColetor(long id, Pageable pageable) {
        return coletaRepository.findAllByColetor_id(id, pageable)
                .map(coletaMapper::toDTO);
    }
  
    @Transactional
    public ColetaDTO createColetas(ColetaForm form) {
        Coletor coletor = coletorService.findEntityById(form.getColetorId());
        Coleta coleta = coletaMapper.toModel(form);
        coleta.setColetor(coletor);
        PontoColeta pontoColeta = pontoColetaService.create(form.getPontoColeta());
        coleta.setPontoColeta(pontoColeta);
        coleta = coletaRepository.save(coleta);
        return coletaMapper.toDTO(coleta);
    }
}
