package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.ReceptorDTO;
import br.ufpi.recicle_ai.domain.form.ReceptorForm;
import br.ufpi.recicle_ai.mapper.ReceptorMapper;
import br.ufpi.recicle_ai.domain.model.Receptor;
import br.ufpi.recicle_ai.repository.ReceptorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReceptorService {

    private final ReceptorRepository receptorRepository;
    private final ReceptorMapper receptorMapper;

    @Transactional(readOnly = true)
    public List<ReceptorDTO> findAll() {
        return receptorRepository.findAll().stream()
                .map(receptorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReceptorDTO findById(Long id) {
        return receptorRepository.findById(id)
                .map(receptorMapper::toDTO)
                .orElse(null);
    }

    @Transactional
    public ReceptorDTO create(ReceptorForm form) {
        Receptor receptor = receptorMapper.toModel(form);
        receptor = receptorRepository.save(receptor);
        return receptorMapper.toDTO(receptor);
    }

    @Transactional
    public ReceptorDTO update(Long id, ReceptorForm form) {
        return receptorRepository.findById(id).map(receptor -> {
            receptor.setNome(form.getNome());
            receptor.setTipoAgente(form.getTipoAgente());
            receptor.setCpf(form.getCpf());
            receptor.setCnpj(form.getCnpj());
            receptor = receptorRepository.save(receptor);
            return receptorMapper.toDTO(receptor);
        }).orElse(null);
    }

    @Transactional
    public void delete(Long id) {
        receptorRepository.deleteById(id);
    }
}
