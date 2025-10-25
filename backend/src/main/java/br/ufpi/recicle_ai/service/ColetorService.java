package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.ColetorDTO;
import br.ufpi.recicle_ai.domain.model.Coletor;
import br.ufpi.recicle_ai.domain.form.ColetorForm;
import br.ufpi.recicle_ai.repository.ColetorRepository;
import br.ufpi.recicle_ai.mapper.ColetorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColetorService {

    private final ColetorRepository coletorRepository;
    private final ColetorMapper coletorMapper;

    @Autowired
    public ColetorService(ColetorRepository coletorRepository, ColetorMapper coletorMapper) {
        this.coletorRepository = coletorRepository;
        this.coletorMapper = coletorMapper;
    }

    @Transactional(readOnly = true)
    public List<ColetorDTO> findAll() {
        return coletorRepository.findAll().stream()
                .map(coletorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ColetorDTO findById(Long id) {
        return coletorRepository.findById(id)
                .map(coletorMapper::toDTO)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public Coletor findEntityById(Long id) {
        return coletorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Coletor não encontrado!"));
    }

    @Transactional
    public ColetorDTO create(ColetorForm form) {
        Coletor coletor = coletorMapper.toModel(form);
        coletor = coletorRepository.save(coletor);
        return coletorMapper.toDTO(coletor);
    }

    @Transactional
    public ColetorDTO update(Long id, ColetorForm form) {
        return coletorRepository.findById(id).map(coletor -> {
            coletor.setNome(form.getNome());
            coletor.setTipoAgente(form.getTipoAgente());
            coletor.setCpf(form.getCpf());
            coletor.setCnpj(form.getCnpj());
            coletor = coletorRepository.save(coletor);
            return coletorMapper.toDTO(coletor);
        }).orElse(null);
    }

    @Transactional
    public void delete(Long id) {
        coletorRepository.deleteById(id);
    }
}
