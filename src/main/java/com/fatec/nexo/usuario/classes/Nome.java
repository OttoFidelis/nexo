package com.fatec.nexo.usuario.classes;

/**
 * Classe para validar o nome do usuário
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
public class Nome {
    /**
     * Nome do usuário
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private String nome;

    /**
     * Construtor padrão.
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public Nome() {
    }

    /**
     * Define o nome do usuário, garantindo que não contenha números.
     * @param nome O nome a ser definido
     * @return O nome validado
     * @throws IllegalArgumentException se o nome contiver números
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public String setNome(String nome) {
        for(int i=0; i<nome.length(); i++){
            char c = nome.charAt(i);
            if(Character.isDigit(c)){
                throw new IllegalArgumentException("Nome não pode conter números");
            }
        }
        this.nome = nome;
        return this.nome;
    }
}
