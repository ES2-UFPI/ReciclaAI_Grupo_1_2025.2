package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.item.ItemInventarioDTO;
import br.ufpi.recicle_ai.domain.form.item.ItemInventarioForm;
import br.ufpi.recicle_ai.domain.form.item.ItemInventarioUpdateForm;
import br.ufpi.recicle_ai.domain.enuns.TipoPessoaEnum;
import br.ufpi.recicle_ai.domain.model.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ufpi.recicle_ai.repository.ItemInventarioRepository;
import br.ufpi.recicle_ai.mapper.ItemInventarioMapper;
import br.ufpi.recicle_ai.domain.model.item.ItemInventario;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ItemInventarioService {

    private final ItemInventarioRepository itemInventarioRepository;
    private final ItemInventarioMapper itemInventarioMapper;
    private final ItemService itemService;

    @Transactional
    public ItemInventarioDTO criarItemInventario(ItemInventarioForm form) {
        ItemInventario novoItem = itemInventarioMapper.fromForm(form);
        Item item = itemService.buscarPorId(form.getItemId());
        novoItem.setItem(item);
        ItemInventario itemSalvo = itemInventarioRepository.save(novoItem);
        return itemInventarioMapper.toDTO(itemSalvo);
    }

    @Transactional(readOnly = true)
    public List<ItemInventarioDTO> listarItensPorPessoa(Long pessoaId, TipoPessoaEnum tipoPessoa) {
        List<ItemInventario> itens = itemInventarioRepository.findByPessoaIdAndTipoPessoa(pessoaId, tipoPessoa);
        return itens.stream()
                .map(itemInventarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemInventarioDTO atualizarQuantidade(Long itemInventarioId, ItemInventarioUpdateForm form) {
        ItemInventario itemInventario = itemInventarioRepository.findById(itemInventarioId)
                .orElseThrow(() -> new RuntimeException("Item do inventário não encontrado!"));

        itemInventario.setQuantidade(form.getQuantidade());
        ItemInventario itemSalvo = itemInventarioRepository.save(itemInventario);
        return itemInventarioMapper.toDTO(itemSalvo);
    }
}