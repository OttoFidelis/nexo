package com.fatec.nexo.usuario.exceptions;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(String email) {
        super("Usuário não encontrado! Email inválido: " + email);
    }
}
