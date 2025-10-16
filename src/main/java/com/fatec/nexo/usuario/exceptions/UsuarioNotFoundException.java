package com.fatec.nexo.usuario.exceptions;

/**
 * Exceção lançada quando um usuário não é encontrado no sistema.
 * Estende RuntimeException para indicar um erro em tempo de execução.
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
public class UsuarioNotFoundException extends RuntimeException {
    /**
     * Construtor da exceção UsuarioNotFoundException
     * @param email O email do usuário que não foi encontrado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public UsuarioNotFoundException(String email) {
        super("Usuário não encontrado! Email inválido: " + email);
    }
}
