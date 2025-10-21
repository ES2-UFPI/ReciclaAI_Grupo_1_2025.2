package br.ufpi.recicle_ai.domain.service;

import br.ufpi.recicle_ai.domain.mapper.ColetorMapper;
import br.ufpi.recicle_ai.domain.model.Coletor;
import br.ufpi.recicle_ai.domain.model.dto.ColetorDTO;
import br.ufpi.recicle_ai.domain.model.dto.ItensDTO;
import br.ufpi.recicle_ai.domain.model.dto.form.ColetorForm;
import br.ufpi.recicle_ai.domain.model.dto.form.ItensForm;
import br.ufpi.recicle_ai.domain.repository.ColetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ufpi.recicle_ai.domain.repository.ItensRepository;
import br.ufpi.recicle_ai.repository.ProdutorRepository;
import br.ufpi.recicle_ai.domain.mapper.ItensMapper;
import br.ufpi.recicle_ai.domain.model.Itens;
import br.ufpi.recicle_ai.domain.model.Produtor;
import br.ufpi.recicle_ai.domain.model.Coletor;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItensService {

    private final ItensRepository itensRepository;
    private final ItensMapper itensMapper;
    private final ProdutorRepository produtorRepository;
    
    @Autowired
    public ItensService(ItensRepository itensRepository, ItensMapper itensMapper, ProdutorRepository produtorRepository) {
        this.itensRepository = itensRepository;
        this.itensMapper = itensMapper;
        this.produtorRepository = produtorRepository;
    }


    public ItensDTO addItensProdutor(Long produtorId, ItensForm form) {
        Produtor produtor = produtorRepository.findById(produtorId)
                .orElseThrow();

        Itens novoItem = itensMapper.fromForm(form);
        produtor.getItens().add(novoItem);
        itensRepository.save(novoItem);
        return itensMapper.toDTO(novoItem);
    }
}