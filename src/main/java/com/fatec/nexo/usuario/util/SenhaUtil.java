package com.fatec.nexo.usuario.util;

import java.util.Base64;

/**
 * Classe auxiliar para manipulação e validação de senhas
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.1
 */
public class SenhaUtil {
    /**
     * Define a senha do usuário, garantindo que tenha pelo menos 8 caracteres.
     * A senha é armazenada de forma encriptada.
     * @param senha A senha a ser definida
     * @return A senha encriptada
     * @throws IllegalArgumentException se a senha tiver menos de 8 caracteres
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.1
     */
    public static String setSenha(String senha) {
        if(senha.length() < 8){
            throw new IllegalArgumentException("Senha deve ter pelo menos 8 caracteres");
        }
        return encrypt(senha);
    }

    /**
     * Encripta a senha usando Base64
     * @param senha A senha a ser encriptada
     * @return A senha encriptada
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.1
    */
     private static String encrypt(String senha) {
        return Base64.getEncoder().encodeToString(senha.getBytes());
    }

    /**
     * Desencripta a senha usando Base64
     * @param senhaEncriptada A senha encriptada a ser desencriptada
     * @return A senha desencriptada
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.1
    */
    public static String desencriptar(String senhaEncriptada) {
        return new String(Base64.getDecoder().decode(senhaEncriptada));
    }
}
