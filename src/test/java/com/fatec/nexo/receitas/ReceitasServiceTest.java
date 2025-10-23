package com.fatec.nexo.receitas;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fatec.nexo.categoria.CategoriaModel;
import com.fatec.nexo.receitas.exceptions.ReceitasNotFoundException;
import com.fatec.nexo.saldo.SaldoModel;
import com.fatec.nexo.saldo.SaldoService;
import com.fatec.nexo.usuario.UsuarioModel;

/**
 * Testes unitários para o serviço de Receitas.
 *
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ReceitasService")
class ReceitasServiceTest {

    @Mock
    private ReceitasRepository receitasRepository;

    @Mock
    private SaldoService saldoService;

    @InjectMocks
    private ReceitasService receitasService;

    private ReceitasModel receitaValida;
    private UsuarioModel usuario;
    private CategoriaModel categoria;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioModel();
        usuario.setNome("Maria Santos");
        usuario.setEmail("maria@example.com");

        categoria = new CategoriaModel();
        categoria.setNome("Salário");

        receitaValida = new ReceitasModel();
        receitaValida.setDescricao("Salário Outubro");
        receitaValida.setQuantia(5000.00);
        receitaValida.setData(LocalDate.now());
        receitaValida.setUsuario(usuario);
        receitaValida.setCategoria(categoria);
    }

    @Test
    @DisplayName("Deve criar uma receita e atualizar saldo")
    void deveCriarReceita() {
        // Arrange
        when(receitasRepository.save(any(ReceitasModel.class))).thenReturn(receitaValida);
        when(saldoService.create(any(UsuarioModel.class), any(ReceitasModel.class))).thenReturn(new SaldoModel());

        // Act
        ReceitasModel resultado = receitasService.create(receitaValida);

        // Assert
        assertNotNull(resultado);
        assertEquals("Salário Outubro", resultado.getDescricao());
        assertEquals(5000.00, resultado.getQuantia(), 0.01);
        verify(receitasRepository).save(receitaValida);
        verify(saldoService).create(usuario, receitaValida);
    }

    @Test
    @DisplayName("Deve buscar receita por ID")
    void deveBuscarReceitaPorId() {
        // Arrange
        when(receitasRepository.findById(1)).thenReturn(Optional.of(receitaValida));

        // Act
        ReceitasModel resultado = receitasService.findById(1, usuario);

        // Assert
        assertNotNull(resultado);
        assertEquals("Salário Outubro", resultado.getDescricao());
        assertEquals(5000.00, resultado.getQuantia(), 0.01);
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar receita inexistente")
    void deveLancarExcecaoAoBuscarReceitaInexistente() {
        // Arrange
        when(receitasRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        ReceitasNotFoundException ex = assertThrows(ReceitasNotFoundException.class,
            () -> receitasService.findById(999, usuario));
        assertNotNull(ex);
    }

    @Test
    @DisplayName("Deve atualizar receita mantendo usuário original")
    void deveAtualizarReceita() {
        // Arrange
        ReceitasModel receitaAtualizada = new ReceitasModel();
        receitaAtualizada.setDescricao("Salário Atualizado");
        receitaAtualizada.setQuantia(5500.00);
        receitaAtualizada.setUsuario(usuario);

        when(receitasRepository.findById(1)).thenReturn(Optional.of(receitaValida));
        when(receitasRepository.save(any(ReceitasModel.class))).thenReturn(receitaValida);
        when(saldoService.update(any(UsuarioModel.class), any(ReceitasModel.class), anyDouble())).thenReturn(new SaldoModel());

        // Act
        ReceitasModel resultado = receitasService.update(1, receitaAtualizada, usuario);

        // Assert
        assertNotNull(resultado);
        verify(receitasRepository).save(any(ReceitasModel.class));
    }

    @Test
    @DisplayName("Deve buscar receitas por período")
    void deveBuscarReceitasPorPeriodo() {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 10, 1);
        LocalDate fim = LocalDate.of(2025, 10, 31);
        List<ReceitasModel> receitas = Arrays.asList(receitaValida);

        when(receitasRepository.findByDataBetweenAndUsuario(inicio, fim, usuario))
            .thenReturn(receitas);

        // Act
        List<ReceitasModel> resultado = receitasService.findByDataBetween(inicio, fim, usuario);

        // Assert
        assertEquals(1, resultado.size());
        assertEquals("Salário Outubro", resultado.get(0).getDescricao());
    }

    @Test
    @DisplayName("Deve deletar receita e atualizar saldo")
    void deveDeletarReceita() {
        // Arrange
        when(receitasRepository.findById(1)).thenReturn(Optional.of(receitaValida));
        doNothing().when(receitasRepository).deleteById(1);

        // Act
        assertDoesNotThrow(() -> receitasService.delete(1, usuario));

        // Assert
        verify(receitasRepository).deleteById(1);
    }
}
