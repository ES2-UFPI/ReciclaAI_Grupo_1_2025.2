package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.ColetorDTO;
import br.ufpi.recicle_ai.domain.dto.ReceptorDTO;
import br.ufpi.recicle_ai.domain.dto.beneficiamento.BeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.model.Coletor;
import br.ufpi.recicle_ai.domain.model.Receptor;
import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import br.ufpi.recicle_ai.domain.model.coleta.PontoColeta;
import br.ufpi.recicle_ai.exception.RegraDeNegocioException;
import br.ufpi.recicle_ai.mapper.BeneficiamentoMapper;
import br.ufpi.recicle_ai.repository.BeneficiamentoRepository;
import br.ufpi.recicle_ai.service.PontoColetaService;
import br.ufpi.recicle_ai.service.ReceptorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ufpi.recicle_ai.domain.form.beneficiamento.BeneficiamentoForm;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BeneficiamentoService {

    private final BeneficiamentoRepository repository;
    private final BeneficiamentoMapper beneficiamentomapper;
    private final PontoColetaService pontoColetaService;
    private final ReceptorService receptorService;

    @Transactional
    public BeneficiamentoDTO create(BeneficiamentoForm form) {
        Beneficiamento beneficiamento = beneficiamentomapper.toModel(form);
        
        Receptor receptor = receptorService.findEntityById(form.getReceptorId());
        beneficiamento.setReceptor(receptor);
        PontoColeta pontoColeta = pontoColetaService.findEntityById(form.getPontoColetaId());
        beneficiamento.setPontoColeta(pontoColeta);

        beneficiamento = repository.save(beneficiamento);
        return beneficiamentomapper.toDTO(beneficiamento);
    }
    @Transactional(readOnly = true)
    public BeneficiamentoDTO findById(Long id) {
       Beneficiamento beneficiamento = findEntityById(id);
       return beneficiamentomapper.toDTO(beneficiamento);
    }

    @Transactional(readOnly = true)
    public Beneficiamento findEntityById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RegraDeNegocioException("Beneficiamento não encontrado!"));
    }

    @Transactional(readOnly = true)
    public List<BeneficiamentoDTO> findEntityByReceptorId(Long id){
        return repository.findAllByReceptor_id(id)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
