package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.BeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.form.beneficiamento.BeneficiamentoForm;
import br.ufpi.recicle_ai.domain.model.Receptor;
import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import br.ufpi.recicle_ai.domain.model.coleta.PontoColeta;
import br.ufpi.recicle_ai.exception.RegraDeNegocioException;
import br.ufpi.recicle_ai.mapper.BeneficiamentoMapper;
import br.ufpi.recicle_ai.repository.BeneficiamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        PontoColeta pontoColeta = pontoColetaService.create(form.getPontoColeta());
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
    public Page<BeneficiamentoDTO> findByReceptor(Long id, Pageable pageable){
        return repository.findAllByReceptorIdOrderByDataInicioAsc(id, pageable)
                .map(beneficiamentomapper::toDTO);
    }

    @Transactional(readOnly = true)
    public Page<BeneficiamentoDTO> findByBairro(String bairro, Pageable pageable) {
        return repository.findAllByPontoColetaBairroContainingIgnoreCaseOrderByDataInicioAsc(bairro, pageable)
                .map(beneficiamentomapper::toDTO);
    }
}
