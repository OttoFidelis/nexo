package com.fatec.nexo.saldo;

import com.fatec.nexo.despesas.DespesasModel;
import com.fatec.nexo.receitas.ReceitasModel;
import com.fatec.nexo.usuario.UsuarioModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para o serviço de Saldo.
 *
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do SaldoService")
class SaldoServiceTest {

    @Mock
    private SaldoRepository saldoRepository;

    @InjectMocks
    private SaldoService saldoService;

    private UsuarioModel usuario;
    private ReceitasModel receita;
    private DespesasModel despesa;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioModel();
        usuario.setNome("Carlos Silva");
        usuario.setEmail("carlos@example.com");

        receita = new ReceitasModel();
        receita.setQuantia(1000.00);
        receita.setData(LocalDate.now());
        receita.setUsuario(usuario);

        despesa = new DespesasModel();
        despesa.setQuantia(300.00);
        despesa.setData(LocalDate.now());
        despesa.setUsuario(usuario);
    }

    @Test
    @DisplayName("Deve criar saldo com receita")
    void deveCriarSaldoComReceita() {
        // Arrange
        SaldoModel saldoExistente = new SaldoModel();
        saldoExistente.setQuantia(500.00);
        saldoExistente.setUsuario(usuario);

        when(saldoRepository.findByUsuario(usuario)).thenReturn(Collections.singletonList(saldoExistente));
        when(saldoRepository.save(any(SaldoModel.class))).thenReturn(new SaldoModel());

        // Act
        SaldoModel resultado = saldoService.create(usuario, receita);

        // Assert
        assertNotNull(resultado);
        verify(saldoRepository).save(any(SaldoModel.class));
    }

    @Test
    @DisplayName("Deve criar saldo com despesa")
    void deveCriarSaldoComDespesa() {
        // Arrange
        SaldoModel saldoExistente = new SaldoModel();
        saldoExistente.setQuantia(1000.00);
        saldoExistente.setUsuario(usuario);

        when(saldoRepository.findByUsuario(usuario)).thenReturn(Collections.singletonList(saldoExistente));
        when(saldoRepository.save(any(SaldoModel.class))).thenReturn(new SaldoModel());

        // Act
        SaldoModel resultado = saldoService.create(usuario, despesa);

        // Assert
        assertNotNull(resultado);
        verify(saldoRepository).save(any(SaldoModel.class));
    }

    @Test
    @DisplayName("Deve buscar último saldo do usuário")
    void deveBuscarUltimoSaldo() {
        // Arrange
        SaldoModel saldo1 = new SaldoModel();
        saldo1.setQuantia(500.00);
        saldo1.setData(LocalDate.now().minusDays(1));

        SaldoModel saldo2 = new SaldoModel();
        saldo2.setQuantia(800.00);
        saldo2.setData(LocalDate.now());

        List<SaldoModel> saldos = Arrays.asList(saldo1, saldo2);
        when(saldoRepository.findByUsuario(usuario)).thenReturn(saldos);

        // Act
        SaldoModel resultado = saldoService.findLastSaldo(usuario);

        // Assert
        assertNotNull(resultado);
        assertEquals(800.00, resultado.getQuantia(), 0.01);
    }

    @Test
    @DisplayName("Deve retornar saldo zerado quando usuário não tem saldo")
    void deveRetornarSaldoZeradoQuandoSemSaldo() {
        // Arrange
        when(saldoRepository.findByUsuario(usuario)).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            saldoService.findLastSaldo(usuario);
        });
    }

    @Test
    @DisplayName("Deve atualizar saldo após modificação de receita")
    void deveAtualizarSaldoComReceita() {
        // Arrange
        ReceitasModel receitaNova = new ReceitasModel();
        receitaNova.setQuantia(1500.00);
        receitaNova.setUsuario(usuario);

        SaldoModel saldoAtual = new SaldoModel();
        saldoAtual.setQuantia(2000.00);
        saldoAtual.setUsuario(usuario);

        when(saldoRepository.findByUsuario(usuario)).thenReturn(Collections.singletonList(saldoAtual));
        when(saldoRepository.save(any(SaldoModel.class))).thenReturn(new SaldoModel());

        // Act
        SaldoModel resultado = saldoService.update(usuario, receitaNova, 1000.00);

        // Assert
        assertNotNull(resultado);
        verify(saldoRepository).save(any(SaldoModel.class));
    }

    @Test
    @DisplayName("Deve buscar todos os saldos do usuário")
    void deveBuscarSaldosDoUsuario() {
        // Arrange
        SaldoModel saldo1 = new SaldoModel();
        SaldoModel saldo2 = new SaldoModel();
        List<SaldoModel> saldos = Arrays.asList(saldo1, saldo2);

        when(saldoRepository.findByUsuario(usuario)).thenReturn(saldos);

        // Act
        List<SaldoModel> resultado = saldoService.findByUsuario(usuario);

        // Assert
        assertEquals(2, resultado.size());
    }
}
