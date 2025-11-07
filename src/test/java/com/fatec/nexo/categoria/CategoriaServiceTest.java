package com.fatec.nexo.categoria;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fatec.nexo.categoria.exceptions.CategoriaNotFoundException;
import com.fatec.nexo.saldo.SaldoModel;
import com.fatec.nexo.usuario.UsuarioModel;

/**
 * Testes unitários para o serviço de Categorias.
 * Utiliza Mockito para isolar a lógica de negócio.
 *
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.1
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

        UsuarioModel usuarioValido = new UsuarioModel();
        usuarioValido.setEmail("fualno@gmail.com");
        usuarioValido.setNome("Fulano de Tal");
        usuarioValido.setSenha("senha123");

        SaldoModel saldoValido = new SaldoModel();
        saldoValido.setUsuario(usuarioValido);
        
        categoriaValida.setSaldo(saldoValido); 
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
    @DisplayName("Deve lançar exceção ao tentar acessar a categoria de outro usuário")
    void deveLancarExcecaoAoAcessarCategoriaDeOutroUsuario(){
        //Arrange
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoriaValida));
        UsuarioModel usuario1 = new UsuarioModel();
        usuario1.setEmail("bandido@gmail.com");
        usuario1.setNome("Bandido da Silva");
        usuario1.setSenha("senha456");

        //act & assert
        SecurityException exception = assertThrows(SecurityException.class, ()->{
            categoriaService.findById(1, usuario1);
        });
        
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar editar a categoria de outro usuário")
    void deveLancarExcecaoAoEditarCategoriaDeOutroUsuario(){
        //Arrange
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoriaValida));
        UsuarioModel usuario1 = new UsuarioModel();
        usuario1.setEmail("bandido@gmail.com");
        usuario1.setNome("Bandido da Silva");
        usuario1.setSenha("senha456");

        CategoriaModel categoriaAtualizada = new CategoriaModel();
        categoriaAtualizada.setNome("Transporte");
        categoriaAtualizada.setDescricao("Gastos com uber e combustível");

        //act & assert
        SecurityException exception = assertThrows(SecurityException.class, ()->{
            categoriaService.update(1, categoriaAtualizada, usuario1);
        });
        
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar excluir a categoria de outro usuário")
    void deveLancarExcecaoAoExcluirCategoriaDeOutroUsuario(){
        //Arrange
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoriaValida));
        UsuarioModel usuario1 = new UsuarioModel();
        usuario1.setEmail("bandido@gmail.com");
        usuario1.setNome("Bandido da Silva");
        usuario1.setSenha("senha456");

        //act & assert
        SecurityException exception = assertThrows(SecurityException.class, ()->{
            categoriaService.deleteCategoria(1, usuario1);
        });
        
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Deve buscar categoria por ID existente")
    void deveBuscarCategoriaPorId() {
        // Arrange
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoriaValida));
        UsuarioModel usuarioValido = new UsuarioModel();
        usuarioValido.setEmail("fualno@gmail.com");
        usuarioValido.setNome("Fulano de Tal");
        usuarioValido.setSenha("senha123");

        // Act
        CategoriaModel resultado = categoriaService.findById(1, usuarioValido);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Alimentação", resultado.getNome());
        assertEquals(usuarioValido.getEmail(), resultado.getSaldo().getUsuario().getEmail());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar categoria inexistente")
    void deveLancarExcecaoAoBuscarCategoriaInexistente() {
        // Arrange
        when(categoriaRepository.findById(999)).thenReturn(Optional.empty());
        UsuarioModel usuarioValido = new UsuarioModel();
        usuarioValido.setEmail("fualno@gmail.com");
        usuarioValido.setNome("Fulano de Tal");
        usuarioValido.setSenha("senha123");

        // Act & Assert
        CategoriaNotFoundException exception = assertThrows(CategoriaNotFoundException.class, () -> {
            categoriaService.findById(999, usuarioValido);
        });
        
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Deve atualizar categoria existente")
    void deveAtualizarCategoria() {
        // Arrange
        CategoriaModel categoriaAtualizada = new CategoriaModel();
        categoriaAtualizada.setNome("Transporte");
        categoriaAtualizada.setDescricao("Gastos com uber e combustível");
        UsuarioModel usuarioValido = new UsuarioModel();
        usuarioValido.setEmail("fualno@gmail.com");
        usuarioValido.setNome("Fulano de Tal");
        usuarioValido.setSenha("senha123");

        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoriaValida));
        when(categoriaRepository.save(any(CategoriaModel.class))).thenReturn(categoriaAtualizada);

        // Act
        CategoriaModel resultado = categoriaService.update(1, categoriaAtualizada, usuarioValido);

        // Assert
        assertNotNull(resultado);
        assertEquals("Transporte", resultado.getNome());
        verify(categoriaRepository).save(any(CategoriaModel.class));
    }

    @Test
    @DisplayName("Deve deletar categoria existente")
    void deveDeletarCategoria() {
        // Arrange
        when(categoriaRepository.findById(1)).thenReturn(Optional.of(categoriaValida));
        doNothing().when(categoriaRepository).deleteById(1);
        UsuarioModel usuarioValido = new UsuarioModel();
        usuarioValido.setEmail("fualno@gmail.com");
        usuarioValido.setNome("Fulano de Tal");
        usuarioValido.setSenha("senha123");

        // Act
        assertDoesNotThrow(() -> categoriaService.deleteCategoria(1, usuarioValido));

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
