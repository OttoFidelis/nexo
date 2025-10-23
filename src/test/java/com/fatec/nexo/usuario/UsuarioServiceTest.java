package com.fatec.nexo.usuario;

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

/**
 * Testes unitários para o serviço de Usuários.
 *
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do UsuarioService")
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private UsuarioModel usuarioValido;

    @BeforeEach
    void setUp() {
        usuarioValido = new UsuarioModel(); 
        usuarioValido.setNome("Ana Paula");
        usuarioValido.setEmail("ana.paula@example.com");
        usuarioValido.setSenha("senha123");
    }

    @Test
    @DisplayName("Deve criar um novo usuário")
    void deveCriarUsuario() {
        // Arrange
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuarioValido);

        // Act
        UsuarioModel resultado = usuarioService.create(usuarioValido);

        // Assert
        assertNotNull(resultado);
        assertEquals("Ana Paula", resultado.getNome());
        assertEquals("ana.paula@example.com", resultado.getEmail());
        verify(usuarioRepository, times(1)).save(usuarioValido);
    }

    @Test
    @DisplayName("Deve buscar usuário por email (próprio usuário)")
    void deveBuscarUsuarioPorEmail() {
        // Arrange
        when(usuarioRepository.findById("ana.paula@example.com")).thenReturn(Optional.of(usuarioValido));

        // Act
        UsuarioModel resultado = usuarioService.findById("ana.paula@example.com", usuarioValido);

        // Assert
        assertNotNull(resultado);
        assertEquals("Ana Paula", resultado.getNome());
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar acessar outro usuário")
    void deveLancarExcecaoAoAcessarOutroUsuario() {
        // Arrange
        UsuarioModel outroUsuario = new UsuarioModel();
        outroUsuario.setEmail("outro@example.com");

        // Act & Assert
        assertThrows(SecurityException.class, () -> {
            usuarioService.findById("ana.paula@example.com", outroUsuario);
        });
    }

    @Test
    @DisplayName("Deve atualizar nome do usuário")
    void deveAtualizarNomeUsuario() {
        // Arrange
        UsuarioModel usuarioAtualizado = new UsuarioModel();
        usuarioAtualizado.setEmail("ana.paula@example.com");
        usuarioAtualizado.setNome("Ana Paula Silva");

        when(usuarioRepository.findById("ana.paula@example.com")).thenReturn(Optional.of(usuarioValido));
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuarioAtualizado);

        // Act
        UsuarioModel resultado = usuarioService.updateNome("ana.paula@example.com", usuarioAtualizado);

        // Assert
        assertNotNull(resultado);
        verify(usuarioRepository).save(any(UsuarioModel.class));
    }

    @Test
    @DisplayName("Deve atualizar senha do usuário")
    void deveAtualizarSenhaUsuario() {
        // Arrange
        UsuarioModel usuarioAtualizado = new UsuarioModel();
        usuarioAtualizado.setEmail("ana.paula@example.com");
        usuarioAtualizado.setSenha("asd123@#");

        when(usuarioRepository.findById("ana.paula@example.com")).thenReturn(Optional.of(usuarioValido));
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuarioAtualizado);

        // Act
        UsuarioModel resultado = usuarioService.updateSenha("ana.paula@example.com", usuarioAtualizado);

        // Assert
        assertNotNull(resultado);
        verify(usuarioRepository).save(any(UsuarioModel.class));
    }

    @Test
    @DisplayName("Deve deletar usuário")
    void deveDeletarUsuario() {
        // Arrange
        when(usuarioRepository.existsById("ana.paula@example.com")).thenReturn(true);
        doNothing().when(usuarioRepository).deleteById("ana.paula@example.com");

        // Act & Assert
        assertDoesNotThrow(() -> usuarioService.delete("ana.paula@example.com", usuarioValido));
        verify(usuarioRepository, times(1)).deleteById("ana.paula@example.com");
    }
}
