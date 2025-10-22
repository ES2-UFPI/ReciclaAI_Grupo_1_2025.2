package br.ufpi.recicle_ai.domain.service;

import br.ufpi.recicle_ai.domain.mapper.ColetorMapper;
import br.ufpi.recicle_ai.domain.model.Coletor;
import br.ufpi.recicle_ai.domain.model.dto.ColetorDTO;
import br.ufpi.recicle_ai.domain.model.dto.ItensDTO;
import br.ufpi.recicle_ai.domain.model.dto.form.ColetorForm;
import br.ufpi.recicle_ai.domain.model.dto.form.ItensForm;
import br.ufpi.recicle_ai.domain.repository.ColetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ufpi.recicle_ai.domain.repository.ItensRepository;
import br.ufpi.recicle_ai.repository.ProdutorRepository;
import br.ufpi.recicle_ai.domain.mapper.ItensMapper;
import br.ufpi.recicle_ai.domain.model.Itens;
import br.ufpi.recicle_ai.domain.model.Produtor;

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

    @Transactional
    public ItensDTO addItensProdutor(Long produtorId, ItensForm form) throws NotFoundException {
    Produtor produtor = produtorRepository.findById(produtorId)
            .orElseThrow(() -> new NotFoundException()); 
    // Use uma exceção de sua aplicação (ex: NotFoundException)

    Itens novoItem = itensMapper.fromForm(form);
    
    // 1. Associa o Produtor ao Item de Inventário
    novoItem.setProdutor(produtor); 
    
    // 2. Salva a nova entrada de inventário
    Itens itemSalvo = itensRepository.save(novoItem); 
    
    // Opcional: Se você usa o relacionamento @OneToMany em Produtor para leitura, 
    // você pode atualizar a lista em memória (mas não é necessário para a persistência)
    produtor.getItens().add(itemSalvo);
    produtorRepository.save(produtor); 
    
    return itensMapper.toDTO(itemSalvo);
}

    

    // O produtorService.findItemByIdAndProdutorId precisa ser ajustado para buscar o Objeto Itens
// ou você pode criar um método no ItensRepository para buscar o Itens:
// Optional<Itens> findByNomeItemAndProdutorId(String nomeItem, Long produtorId);

    @Transactional
    public ItensDTO updateItensProdutor(Long produtorId, ItensForm form) {
        // 1. Busque o Item de Inventário existente no banco de dados
        Itens itemExistente = itensRepository.findByNomeItemAndProdutorId(form.getNomeItem(), produtorId)
                .orElseThrow(() -> new IllegalArgumentException("Item de inventário não encontrado para atualização."));
        
        // Validação 3: A quantidade informada deve ser positiva
        if (form.getQuantidadeEstoque() <= 0) {
            throw new IllegalArgumentException("A quantidade a adicionar deve ser positiva.");
        }

        // 2. Incrementa a quantidade no objeto gerenciado
        Double novaQuantidade = itemExistente.getQuantidadeEstoque() + form.getQuantidadeEstoque();
        
        // Aplica a lógica de conversão para inteiro se a unidade for "unidade"
        if ("unidade".equalsIgnoreCase(itemExistente.getUnidadeItem())) {
            itemExistente.setQuantidadeEstoque((double) novaQuantidade.intValue());
        } else {
            itemExistente.setQuantidadeEstoque(novaQuantidade);
        }
        
        // 3. Salva a alteração (o item já está "attached", mas salvar garante a persistência)
        Itens itemAtualizado = itensRepository.save(itemExistente);
        
        // 4. Se o ItensForm possui campos como nomeItem e unidadeItem, você não deve atualizá-los
        // aqui, pois esta é uma operação de ADIÇÃO de estoque, não de alteração de cadastro.
        
        return itensMapper.toDTO(itemAtualizado);
    }
}