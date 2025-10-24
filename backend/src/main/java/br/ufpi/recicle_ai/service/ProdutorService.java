package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.AgenteDTO;
import br.ufpi.recicle_ai.mapper.ProdutorMapper;
import br.ufpi.recicle_ai.domain.model.Produtor;
import br.ufpi.recicle_ai.domain.form.ProdutorForm;
import br.ufpi.recicle_ai.repository.ProdutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutorService {

    private final ProdutorRepository produtorRepository;
    private final ProdutorMapper produtorMapper;

    @Transactional(readOnly = true)
    public List<AgenteDTO.ProdutorDTO> findAll() {
        return produtorRepository.findAll().stream()
                .map(produtorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AgenteDTO.ProdutorDTO findById(Long id) {
        return produtorRepository.findById(id)
                .map(produtorMapper::toDTO)
                .orElse(null);
    }

    @Transactional
    public AgenteDTO.ProdutorDTO create(ProdutorForm form) {
        Produtor produtor = produtorMapper.toModel(form);
        produtor = produtorRepository.save(produtor);
        return produtorMapper.toDTO(produtor);
    }

    @Transactional
    public AgenteDTO.ProdutorDTO update(Long id, ProdutorForm form) {
        return produtorRepository.findById(id).map(produtor -> {
            produtor.setNome(form.getNome());
            produtor.setTipoAgente(form.getTipoAgente());
            produtor.setCpf(form.getCpf());
            produtor.setCnpj(form.getCnpj());
            produtor = produtorRepository.save(produtor);
            return produtorMapper.toDTO(produtor);
        }).orElse(null);
    }

    @Transactional
    public void delete(Long id) {
        produtorRepository.deleteById(id);
    }

    @Transactional
    public boolean findItemByIdAndProdutorId(String nomeItem, Long produtorId) {
        Produtor produtor = produtorRepository.findById(produtorId).orElseThrow();
        return produtor.getItens().stream()
                .anyMatch(item -> item.getNomeItem().equalsIgnoreCase(nomeItem));
    }
}
