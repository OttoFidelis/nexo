package com.fatec.nexo.usuario.util;

/**
 * Classe para validar o nome do usuário
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.1
 */
public class NomeUtil {
    /**
     * Define o nome do usuário, garantindo que não contenha números.
     * @param nome O nome a ser definido
     * @return O nome validado
     * @throws IllegalArgumentException se o nome contiver números
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.1
     */
    public static String setNome(String nome) {
        for(int i=0; i<nome.length(); i++){
            char c = nome.charAt(i);
            if(Character.isDigit(c)){
                throw new IllegalArgumentException("Nome não pode conter números");
            }
        }
        return nome;
    }
}
