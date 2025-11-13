package br.ufpi.recicle_ai.service;

import br.ufpi.recicle_ai.domain.dto.beneficiamento.EventoBeneficiamentoDTO;
import br.ufpi.recicle_ai.domain.model.beneficiamento.Beneficiamento;
import br.ufpi.recicle_ai.domain.model.beneficiamento.EventoBeneficiamento;
import br.ufpi.recicle_ai.domain.model.coleta.PontoColeta;
import br.ufpi.recicle_ai.domain.enuns.StatusBeneficiamentoEnum;
import br.ufpi.recicle_ai.mapper.EventoBeneficiamentoMapper;
import br.ufpi.recicle_ai.repository.EventoBeneficiamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoBeneficiamentoServiceTest {

    @Mock
    private EventoBeneficiamentoRepository repository;

    @Mock
    private EventoBeneficiamentoMapper mapper;

    @Mock
    private BeneficiamentoService beneficiamentoService;

    @Mock
    private ColetorService coletorService;

    @InjectMocks
    private EventoBeneficiamentoService service;

    private EventoBeneficiamento eventoMock;
    private EventoBeneficiamentoDTO dtoMock;
    private PontoColeta pontoColetaMock;
    private Beneficiamento beneficiamentoMock;
    private Coletor coletorMock;

    @BeforeEach
    void setUp() {
        pontoColetaMock = new PontoColeta();
        pontoColetaMock.setId(1L);
        pontoColetaMock.setBairro("Centro");
        pontoColetaMock.setLogradouro("Rua A");
        pontoColetaMock.setNumero("123");
        pontoColetaMock.setCep("64000-000");

        beneficiamentoMock = new Beneficiamento();
        beneficiamentoMock.setId(1L);
        beneficiamentoMock.setPontoColeta(pontoColetaMock);

        coletorMock = new Coletor();
        coletorMock.setId(1L);
        coletorMock.setNome("Coletor Teste");

        eventoMock = new EventoBeneficiamento();
        eventoMock.setId(1L);
        eventoMock.setBeneficiamento(beneficiamentoMock);
        eventoMock.setColetor(coletorMock);
        eventoMock.setStatus(StatusBeneficiamentoEnum.AGENDADA);

        dtoMock = new EventoBeneficiamentoDTO();
        dtoMock.setId(1L);
        dtoMock.setStatus(StatusBeneficiamentoEnum.AGENDADA);
    }

    @Test
    @DisplayName("Deve atualizar evento com sucesso quando dados válidos forem fornecidos")
    void testUpdate_Success() {
        // Arrange
        Long eventoId = 1L;
        EventoBeneficiamentoForm form = new EventoBeneficiamentoForm();
        form.setBeneficiamentoId(2L);
        form.setColetorId(2L);

        Beneficiamento novoBeneficiamento = new Beneficiamento();
        novoBeneficiamento.setId(2L);

        Coletor novoColetor = new Coletor();
        novoColetor.setId(2L);
        novoColetor.setNome("Novo Coletor");

        when(repository.findById(eventoId)).thenReturn(Optional.of(eventoMock));
        when(beneficiamentoService.findEntityById(2L)).thenReturn(novoBeneficiamento);
        when(coletorService.findEntityById(2L)).thenReturn(novoColetor);
        when(repository.save(any(EventoBeneficiamento.class))).thenReturn(eventoMock);
        when(mapper.toDTO(any(EventoBeneficiamento.class))).thenReturn(dtoMock);

        // Act
        EventoBeneficiamentoDTO result = service.update(eventoId, form);

        // Assert
        assertNotNull(result);
        verify(repository, times(1)).findById(eventoId);
        verify(beneficiamentoService, times(1)).findEntityById(2L);
        verify(coletorService, times(1)).findEntityById(2L);
        verify(repository, times(1)).save(eventoMock);
    }

    @Test
    @DisplayName("Deve lançar exceção quando evento não for encontrado")
    void testUpdate_EventoNotFound() {
        // Arrange
        Long eventoId = 999L;
        EventoBeneficiamentoForm form = new EventoBeneficiamentoForm();
        form.setBeneficiamentoId(1L);
        form.setColetorId(1L);

        when(repository.findById(eventoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RegraDeNegocioException.class, () -> service.update(eventoId, form));
        verify(repository, times(1)).findById(eventoId);
        verify(repository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar apenas beneficiamento quando coletor não mudar")
    void testUpdate_OnlyBeneficiamento() {
        // Arrange
        Long eventoId = 1L;
        EventoBeneficiamentoForm form = new EventoBeneficiamentoForm();
        form.setBeneficiamentoId(2L);
        form.setColetorId(1L); // Mesmo coletor

        Beneficiamento novoBeneficiamento = new Beneficiamento();
        novoBeneficiamento.setId(2L);

        when(repository.findById(eventoId)).thenReturn(Optional.of(eventoMock));
        when(beneficiamentoService.findEntityById(2L)).thenReturn(novoBeneficiamento);
        when(repository.save(any(EventoBeneficiamento.class))).thenReturn(eventoMock);
        when(mapper.toDTO(any(EventoBeneficiamento.class))).thenReturn(dtoMock);

        // Act
        EventoBeneficiamentoDTO result = service.update(eventoId, form);

        // Assert
        assertNotNull(result);
        verify(beneficiamentoService, times(1)).findEntityById(2L);
        verify(coletorService, never()).findEntityById(any());
    }
}
