package com.fatec.nexo.usuario;

import org.springframework.stereotype.Service;

import com.fatec.nexo.usuario.exceptions.UsuarioNotFoundException;

@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioModel create(UsuarioModel usuario) {
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel findById(String email, UsuarioModel usuario) {
        if(usuario.getEmail() == null ? email != null : !usuario.getEmail().equals(email)) {
            throw new SecurityException("Acesso negado! Você não pode acessar outro usuário.");
        }
        return usuarioRepository.findById(email).orElseThrow(() -> new UsuarioNotFoundException(email));
    }

    public void delete(String email, UsuarioModel usuario) {
        if(usuarioRepository.existsById(email)) usuarioRepository.deleteById(email);
        else throw new UsuarioNotFoundException(email);
    }

    public UsuarioModel updateNome(String email, UsuarioModel usuario) {
        UsuarioModel _usuario = findById(email, usuario);
        _usuario.setNome(usuario.getNome());
        return usuarioRepository.save(usuario);
        
    }
    public UsuarioModel updateSenha(String email, UsuarioModel usuario) {
        UsuarioModel _usuario = findById(email, usuario);
        _usuario.setSenha(usuario.getSenha());
        return usuarioRepository.save(usuario);
    }
}
