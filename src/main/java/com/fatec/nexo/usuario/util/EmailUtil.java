package com.fatec.nexo.usuario.util;

/**
 * Classe que representa um email e valida seu formato.
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.2
 */
public class EmailUtil {

    /**
     * Obtém e valida o email do usuário.
     * @param email O email a ser validado
     * @return O email do usuário.
     * @throws IllegalArgumentException se o email for inválido
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.1
     */
    public static String setEmail(String email) {
        if(!email.contains("@") || !email.contains(".")){
            throw new IllegalArgumentException("Email inválido");
        }
        return email;
    }
}
