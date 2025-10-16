package com.fatec.nexo.usuario.classes;

import java.util.Base64;

/**
 * Classe auxiliar para manipulação e validação de senhas
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0.1
 */
public class Senha {
    /**
     * Senha do usuário
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private String senha;

    /**
     * Construtor padrão.
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public Senha() {
    }

    /**
     * Define a senha do usuário, garantindo que tenha pelo menos 8 caracteres.
     * A senha é armazenada de forma encriptada.
     * @param senha A senha a ser definida
     * @return A senha encriptada
     * @throws IllegalArgumentException se a senha tiver menos de 8 caracteres
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0.1
     */
    public String setSenha(String senha) {
        if(senha.length() != 8){
            throw new IllegalArgumentException("Senha deve ter 8 caracteres");
        }
        this.senha = encrypt(senha);
        return this.senha;
    }

    /**
     * Encripta a senha usando Base64
     * @param senha A senha a ser encriptada
     * @return A senha encriptada
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
    */
     private String encrypt(String senha) {
        return Base64.getEncoder().encodeToString(senha.getBytes());
    }

    /**
     * Desencripta a senha usando Base64
     * @param senhaEncriptada A senha encriptada a ser desencriptada
     * @return A senha desencriptada
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
    */
    public String desencriptar(String senhaEncriptada) {
        return new String(Base64.getDecoder().decode(senhaEncriptada));
    }
}
