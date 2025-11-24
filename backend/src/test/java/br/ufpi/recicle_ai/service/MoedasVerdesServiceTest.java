package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.model.Produtor;
import br.ufpi.recicle_ai.domain.model.eventoColeta.EventoColeta;
import br.ufpi.recicle_ai.domain.model.item.Item;
import br.ufpi.recicle_ai.domain.model.item.ItemEventoColeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes para MoedasVerdesService")
class MoedasVerdesServiceTest {

    @Mock
    private ProdutorService produtorService;

    @InjectMocks
    private MoedasVerdesService moedasVerdesService;

    private Produtor produtor;
    private EventoColeta eventoColeta;

    @BeforeEach
    void setUp() {
        produtor = new Produtor();
        produtor.setId(1L);
        produtor.setSaldoMoedasVerdes(new BigDecimal("100.00")); // Saldo inicial

        eventoColeta = new EventoColeta();
        eventoColeta.setProdutor(produtor);
        eventoColeta.setItens(new ArrayList<>());
    }

    @Test
    @DisplayName("Deve creditar moedas corretamente para um evento com itens válidos")
    void creditarMoedasPorEvento_ComItensValidos_DeveAtualizarSaldo() {
        // Arrange
        Item item1 = new Item();
        item1.setValorMoedas(new BigDecimal("10.50")); // Cotação do item 1
        ItemEventoColeta itemEvento1 = new ItemEventoColeta();
        itemEvento1.setItem(item1);
        itemEvento1.setQuantidade(2); // 2 * 10.50 = 21.00

        Item item2 = new Item();
        item2.setValorMoedas(new BigDecimal("5.00")); // Cotação do item 2
        ItemEventoColeta itemEvento2 = new ItemEventoColeta();
        itemEvento2.setItem(item2);
        itemEvento2.setQuantidade(10); // 10 * 5.00 = 50.00

        eventoColeta.setItens(List.of(itemEvento1, itemEvento2));

        when(produtorService.findEntityById(1L)).thenReturn(produtor);

        // Act
        moedasVerdesService.creditarMoedasPorEvento(eventoColeta);

        // Assert
        // Saldo inicial (100.00) + moedas ganhas (21.00 + 50.00 = 71.00) = 171.00
        BigDecimal saldoEsperado = new BigDecimal("171.00");
        verify(produtorService, times(1)).findEntityById(1L);
        assertThat(produtor.getSaldoMoedasVerdes()).isEqualByComparingTo(saldoEsperado);
    }

    @Test
    @DisplayName("Não deve creditar moedas se os itens tiverem valor nulo ou zero")
    void creditarMoedasPorEvento_ComItensSemValor_NaoDeveAlterarSaldo() {
        // Arrange
        Item item1 = new Item();
        item1.setValorMoedas(null); // Item sem cotação
        ItemEventoColeta itemEvento1 = new ItemEventoColeta();
        itemEvento1.setItem(item1);
        itemEvento1.setQuantidade(5);

        Item item2 = new Item();
        item2.setValorMoedas(BigDecimal.ZERO); // Item com cotação zero
        ItemEventoColeta itemEvento2 = new ItemEventoColeta();
        itemEvento2.setItem(item2);
        itemEvento2.setQuantidade(10);

        eventoColeta.setItens(List.of(itemEvento1, itemEvento2));

        // Act
        moedasVerdesService.creditarMoedasPorEvento(eventoColeta);

        // Assert
        // O saldo deve permanecer o inicial (100.00)
        BigDecimal saldoInicial = new BigDecimal("100.00");
        verify(produtorService, never()).findEntityById(anyLong()); // O método não deve ser chamado se o total for zero
        assertThat(produtor.getSaldoMoedasVerdes()).isEqualByComparingTo(saldoInicial);
    }

    @Test
    @DisplayName("Não deve alterar o saldo se o evento não tiver itens")
    void creditarMoedasPorEvento_ComListaDeItensVazia_NaoDeveAlterarSaldo() {
        // Arrange
        // A lista de itens já está vazia pelo setUp()

        // Act
        moedasVerdesService.creditarMoedasPorEvento(eventoColeta);

        // Assert
        BigDecimal saldoInicial = new BigDecimal("100.00");
        verify(produtorService, never()).findEntityById(anyLong());
        assertThat(produtor.getSaldoMoedasVerdes()).isEqualByComparingTo(saldoInicial);
    }
}
