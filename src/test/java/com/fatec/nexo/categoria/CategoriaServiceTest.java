package com.fatec.nexo.categoria;

import com.fatec.nexo.categoria.exceptions.CategoriaNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testes unitários para o serviço de Categorias.
 * Utiliza Mockito para isolar a lógica de negócio.
 *
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do CategoriaService")
class CategoriaServiceTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaService categoriaService;

    private CategoriaModel categoriaValida;

    @BeforeEach
    void setUp() {
        categoriaValida = new CategoriaModel();
        categoriaValida.setId(1);
        categoriaValida.setNome("Alimentação");
        categoriaValida.setDescricao("Gastos com comida e restaurantes");
    }

    @Test
    @DisplayName("Deve criar uma nova categoria com sucesso")
    void deveCriarCategoria() {
        // Arrange
        when(categoriaRepository.save(any(CategoriaModel.class))).thenReturn(categoriaValida);

        // Act
        CategoriaModel resultado = categoriaService.create(categoriaValida);

        // Assert
        assertNotNull(resultado);
        assertEquals("Alimentação", resultado.getNome());
        verify(categoriaRepository, times(1)).save(categoriaValida);
    }

    @Test
    @DisplayName("Deve buscar categoria por ID existente")
    void deveBuscarCategoriaPorId() {
        // Arrange
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoriaValida));

        // Act
        CategoriaModel resultado = categoriaService.findById(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Alimentação", resultado.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar categoria inexistente")
    void deveLancarExcecaoAoBuscarCategoriaInexistente() {
        // Arrange
        when(categoriaRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CategoriaNotFoundException.class, () -> {
            categoriaService.findById(999);
        });
    }

    @Test
    @DisplayName("Deve atualizar categoria existente")
    void deveAtualizarCategoria() {
        // Arrange
        CategoriaModel categoriaAtualizada = new CategoriaModel();
        categoriaAtualizada.setNome("Transporte");
        categoriaAtualizada.setDescricao("Gastos com uber e combustível");

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoriaValida));
        when(categoriaRepository.save(any(CategoriaModel.class))).thenReturn(categoriaAtualizada);

        // Act
        CategoriaModel resultado = categoriaService.update(1, categoriaAtualizada);

        // Assert
        assertNotNull(resultado);
        assertEquals("Transporte", resultado.getNome());
        verify(categoriaRepository).save(any(CategoriaModel.class));
    }

    @Test
    @DisplayName("Deve deletar categoria existente")
    void deveDeletarCategoria() {
        // Arrange
        when(categoriaRepository.existsById(1)).thenReturn(true);
        doNothing().when(categoriaRepository).deleteById(1);

        // Act
        assertDoesNotThrow(() -> categoriaService.deleteCategoria(1));

        // Assert
        verify(categoriaRepository, times(1)).deleteById(1);
    }

    @Test
    @DisplayName("Deve listar todas as categorias")
    void deveListarTodasCategorias() {
        // Arrange
        CategoriaModel categoria2 = new CategoriaModel();
        categoria2.setId(2);
        categoria2.setNome("Lazer");
        categoria2.setDescricao("Entretenimento");

        List<CategoriaModel> categorias = Arrays.asList(categoriaValida, categoria2);
        when(categoriaRepository.findAll()).thenReturn(categorias);

        // Act
        List<CategoriaModel> resultado = categoriaService.findAll();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Alimentação", resultado.get(0).getNome());
        assertEquals("Lazer", resultado.get(1).getNome());
    }
}
