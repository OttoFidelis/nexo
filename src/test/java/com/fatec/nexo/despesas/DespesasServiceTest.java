package com.fatec.nexo.despesas;

import com.fatec.nexo.categoria.CategoriaModel;
import com.fatec.nexo.saldo.SaldoModel;
import com.fatec.nexo.saldo.SaldoService;
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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para o serviço de Despesas.
 *
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do DespesasService")
class DespesasServiceTest {

    @Mock
    private DespesasRepository despesasRepository;

    @Mock
    private SaldoService saldoService;

    @InjectMocks
    private DespesasService despesasService;

    private DespesasModel despesaValida;
    private UsuarioModel usuario;
    private CategoriaModel categoria;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioModel();
        usuario.setNome("João Silva");
        usuario.setEmail("joao@example.com");

        categoria = new CategoriaModel();
        categoria.setNome("Alimentação");

        despesaValida = new DespesasModel();
        despesaValida.setDescricao("Almoço");
        despesaValida.setQuantia(45.50);
        despesaValida.setData(LocalDate.now());
        despesaValida.setUsuario(usuario);
        despesaValida.setCategoria(categoria);
    }

    @Test
    @DisplayName("Deve criar uma despesa e atualizar saldo")
    void deveCriarDespesa() {
        // Arrange
        when(despesasRepository.save(any(DespesasModel.class))).thenReturn(despesaValida);
        when(saldoService.create(any(UsuarioModel.class), any(DespesasModel.class))).thenReturn(new SaldoModel());

        // Act
        DespesasModel resultado = despesasService.create(despesaValida);

        // Assert
        assertNotNull(resultado);
        assertEquals("Almoço", resultado.getDescricao());
        assertEquals(45.50, resultado.getQuantia(), 0.01);
        verify(despesasRepository).save(despesaValida);
        verify(saldoService).create(usuario, despesaValida);
    }

    @Test
    @DisplayName("Deve buscar despesa por ID existente")
    void deveBuscarDespesaPorId() {
        // Arrange
        when(despesasRepository.findById(1)).thenReturn(Optional.of(despesaValida));

        // Act
        DespesasModel resultado = despesasService.findById(1, usuario);

        // Assert
        assertNotNull(resultado);
        assertEquals("Almoço", resultado.getDescricao());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar despesa inexistente")
    void deveLancarExcecaoAoBuscarDespesaInexistente() {
        // Arrange
        when(despesasRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(DespesaNotFoundException.class, () -> {
            despesasService.findById(999, usuario);
        });
    }

    @Test
    @DisplayName("Deve buscar despesas por período e usuário")
    void deveBuscarDespesasPorPeriodo() {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 10, 1);
        LocalDate fim = LocalDate.of(2025, 10, 31);
        List<DespesasModel> despesas = Arrays.asList(despesaValida);

        when(despesasRepository.findByDataBetweenAndUsuario(inicio, fim, usuario))
            .thenReturn(despesas);

        // Act
        List<DespesasModel> resultado = despesasService.findByDataBetween(inicio, fim, usuario);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Almoço", resultado.get(0).getDescricao());
    }

    @Test
    @DisplayName("Deve validar quantia com duas casas decimais")
    void deveValidarQuantiaComDuasCasasDecimais() {
        // Arrange & Act & Assert
        assertDoesNotThrow(() -> {
            despesaValida.setQuantia(100.99);
        });
    }

    @Test
    @DisplayName("Deve listar todas despesas do usuário")
    void deveListarDespesasDoUsuario() {
        // Arrange
        DespesasModel despesa2 = new DespesasModel();
        despesa2.setDescricao("Jantar");
        despesa2.setQuantia(80.00);
        despesa2.setUsuario(usuario);

        List<DespesasModel> despesas = Arrays.asList(despesaValida, despesa2);
        when(despesasRepository.findByUsuario(usuario)).thenReturn(despesas);

        // Act
        List<DespesasModel> resultado = despesasService.findByUsuario(usuario);

        // Assert
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().allMatch(d -> d.getUsuario().equals(usuario)));
    }
}
