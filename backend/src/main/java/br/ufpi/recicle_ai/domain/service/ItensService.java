package br.ufpi.recicle_ai.domain.service;

import br.ufpi.recicle_ai.domain.dto.AgenteDTO;
import br.ufpi.recicle_ai.domain.form.ItensForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ufpi.recicle_ai.domain.repository.ItensRepository;
import br.ufpi.recicle_ai.repository.ProdutorRepository;
import br.ufpi.recicle_ai.domain.mapper.ItemInventarioMapper;
import br.ufpi.recicle_ai.domain.model.item.ItemInventario;
import br.ufpi.recicle_ai.domain.model.Produtor;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ItensService {

    private final ItensRepository itensRepository;
    private final ItemInventarioMapper itensMapper;
    private final ProdutorRepository produtorRepository;
    
    @Autowired
    public ItensService(ItensRepository itensRepository, ItemInventarioMapper itensMapper, ProdutorRepository produtorRepository) {
        this.itensRepository = itensRepository;
        this.itensMapper = itensMapper;
        this.produtorRepository = produtorRepository;
    }

    @Transactional
    public AgenteDTO.ItensDTO addItensProdutor(Long produtorId, ItensForm form) throws NotFoundException {
    Produtor produtor = produtorRepository.findById(produtorId)
            .orElseThrow(() -> new NotFoundException()); 
    // Use uma exceção de sua aplicação (ex: NotFoundException)

    ItemInventario novoItem = itensMapper.fromForm(form);
    
    // 1. Associa o Produtor ao Item de Inventário
    novoItem.setProdutor(produtor); 
    
    // 2. Salva a nova entrada de inventário
    ItemInventario itemSalvo = itensRepository.save(novoItem);
    
    // Opcional: Se você usa o relacionamento @OneToMany em Produtor para leitura, 
    // você pode atualizar a lista em memória (mas não é necessário para a persistência)
    //produtor.getItens().add(itemSalvo);
    //produtorRepository.save(produtor); 
    
    return itensMapper.toDTO(itemSalvo);
}

    

    // O produtorService.findItemByIdAndProdutorId precisa ser ajustado para buscar o Objeto Itens
// ou você pode criar um método no ItensRepository para buscar o Itens:
// Optional<Itens> findByNomeItemAndProdutorId(String nomeItem, Long produtorId);

    @Transactional
    public AgenteDTO.ItensDTO updateItensProdutor(Long produtorId, ItensForm form) {
        // 1. Busque o Item de Inventário existente no banco de dados
        ItemInventario itemExistente = itensRepository.findByNomeItemAndProdutorId(form.getNomeItem(), produtorId)
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
        ItemInventario itemAtualizado = itensRepository.save(itemExistente);
        
        // 4. Se o ItensForm possui campos como nomeItem e unidadeItem, você não deve atualizá-los
        // aqui, pois esta é uma operação de ADIÇÃO de estoque, não de alteração de cadastro.
        
        return itensMapper.toDTO(itemAtualizado);
    }

    @Transactional(readOnly = true)
    public List<AgenteDTO.ItensDTO> listarItensPorProdutor(Long produtorId) {
        // Busca os itens no banco pelo ID do produtor
        List<ItemInventario> itens = itensRepository.findByProdutorId(produtorId);

        // Converte a lista de entidades em DTOs (formato que o controller envia para o front)
        return itens.stream()
                .map(itensMapper::toDTO)
                .collect(Collectors.toList());
    }
}