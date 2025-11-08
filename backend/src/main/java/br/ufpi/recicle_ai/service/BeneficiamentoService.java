package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.BeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import br.ufpi.recicle_ai.exception.RegraDeNegocioException;
import br.ufpi.recicle_ai.mapper.BeneficiamentoMapper;
import br.ufpi.recicle_ai.repository.BeneficiamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BeneficiamentoService {

    private final BeneficiamentoRepository repository;
    private final BeneficiamentoMapper mapper;

    @Transactional(readOnly = true)
    public BeneficiamentoDTO findById(Long id) {
       Beneficiamento beneficiamento = findEntityById(id);
       return mapper.toDTO(beneficiamento);
    }

    @Transactional(readOnly = true)
    public Beneficiamento findEntityById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Beneficiamento não encontrado!"));
    }
}
