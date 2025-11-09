package com.fatec.nexo.usuario;

import java.util.Base64;

import org.springframework.stereotype.Service;

import com.fatec.nexo.usuario.exceptions.UsuarioNotFoundException;

/**
 * Serviço responsável pelo gerenciamento de usuários
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.1
 */
@Service
public class UsuarioService {
    /**
     * Repositório de usuários injetado no serviço
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private final UsuarioRepository usuarioRepository;

    /**
     * Construtor do serviço de usuários
     * @param usuarioRepository Repositório de usuários a ser injetado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * Cria um novo usuário no sistema
     * @param usuario O objeto usuário a ser criado
     * @return O usuário criado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public UsuarioModel create(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }

    /**
     * Busca um usuário pelo email
     * @param email O email do usuário a ser buscado
     * @param usuario O usuário que está solicitando a busca
     * @return O usuário encontrado
     * @throws UsuarioNotFoundException se não encontrar o usuário com o email especificado
     * @throws SecurityException se o usuário não tiver permissão para acessar outro usuário
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public UsuarioModel findById(String email, UsuarioModel usuario) {
        if(usuario.getEmail() == null ? email != null : !usuario.getEmail().equals(email)) {
            throw new SecurityException("Acesso negado! Você não pode acessar outro usuário.");
        }
        return usuarioRepository.findById(email).orElseThrow(() -> new UsuarioNotFoundException(email));
    }

    /**
     * Remove um usuário do sistema pelo email
     * @param email O email do usuário a ser removido
     * @param usuario O usuário que está solicitando a remoção
     * @throws UsuarioNotFoundException se não encontrar o usuário com o email especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public void delete(String email, UsuarioModel usuario) {
        if(usuarioRepository.existsById(email)) usuarioRepository.deleteById(email);
        else throw new UsuarioNotFoundException(email);
    }

    /**
     * Atualiza o nome de um usuário existente
     * @param email O email do usuário a ser atualizado
     * @param usuario O objeto com o novo nome do usuário
     * @return O usuário atualizado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public UsuarioModel updateNome(String email, UsuarioModel usuario) {
        UsuarioModel _usuario = findById(email, usuario);
        _usuario.setNome(usuario.getNome());
        return usuarioRepository.save(usuario);
        
    }

    /**
     * Atualiza a senha de um usuário existente
     * @param email O email do usuário a ser atualizado
     * @param usuario O objeto com a nova senha do usuário
     * @return O usuário atualizado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public UsuarioModel updateSenha(String email, UsuarioModel usuario) {
        UsuarioModel _usuario = findById(email, usuario);
        _usuario.setSenha(usuario.getSenha());
        return usuarioRepository.save(usuario);
    }

    /**
     * Realiza o login de um usuário com email e senha
     * @param email O email do usuário
     * @param senha A senha do usuário
     * @return O usuário autenticado
     * @throws UsuarioNotFoundException se o email ou senha estiverem incorretos
     * @author Otto Fidelis
     * @since 1.1
     * @version 1.0
     */
    public UsuarioModel login(String email, String senha) {
        if(usuarioRepository.findByEmailAndSenha(email, Base64.getEncoder().encodeToString(senha.getBytes())).isPresent()) {
            return usuarioRepository.findByEmailAndSenha(email, Base64.getEncoder().encodeToString(senha.getBytes())).get();
        }
        else throw new UsuarioNotFoundException(email, senha);
    }
}
