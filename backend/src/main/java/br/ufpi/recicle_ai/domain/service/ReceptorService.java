package br.ufpi.recicle_ai.domain.service;

import br.ufpi.recicle_ai.domain.dto.AgenteDTO;
import br.ufpi.recicle_ai.domain.mapper.ReceptorMapper;
import br.ufpi.recicle_ai.domain.model.Receptor;
import br.ufpi.recicle_ai.domain.form.ReceptorForm;
import br.ufpi.recicle_ai.domain.repository.ReceptorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceptorService {

    private final ReceptorRepository receptorRepository;
    private final ReceptorMapper receptorMapper;

    @Autowired
    public ReceptorService(ReceptorRepository receptorRepository, ReceptorMapper receptorMapper) {
        this.receptorRepository = receptorRepository;
        this.receptorMapper = receptorMapper;
    }

    @Transactional(readOnly = true)
    public List<AgenteDTO.ReceptorDTO> findAll() {
        return receptorRepository.findAll().stream()
                .map(receptorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AgenteDTO.ReceptorDTO findById(Long id) {
        return receptorRepository.findById(id)
                .map(receptorMapper::toDTO)
                .orElse(null); // Ou lançar uma exceção
    }

    @Transactional
    public AgenteDTO.ReceptorDTO create(ReceptorForm form) {
        Receptor receptor = receptorMapper.toModel(form);
        receptor = receptorRepository.save(receptor);
        return receptorMapper.toDTO(receptor);
    }

    @Transactional
    public AgenteDTO.ReceptorDTO update(Long id, ReceptorForm form) {
        return receptorRepository.findById(id).map(receptor -> {
            // Atualize os campos necessários do receptor aqui
            receptor.setNome(form.getNome());
            receptor.setTipoAgente(form.getTipoAgente());
            receptor.setCpf(form.getCpf());
            receptor.setCnpj(form.getCnpj());
            receptor = receptorRepository.save(receptor);
            return receptorMapper.toDTO(receptor);
        }).orElse(null); // Ou lançar uma exceção
    }

    @Transactional
    public void delete(Long id) {
        receptorRepository.deleteById(id);
    }
}
