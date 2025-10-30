package com.fatec.nexo.usuario.util;

/**
 * Classe que representa um email e valida seu formato.
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
public class EmailUtil {
    /**
     * O email do usuário.
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private String email;

    /**
     * Construtor padrão.
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    
    public EmailUtil() {
    }

    /**
     * Obtém o email do usuário.
     * @return O email do usuário.
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public String setEmail(String email) {
        if(!email.contains("@") || !email.contains(".")){
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email;
        return this.email;
    }
}
