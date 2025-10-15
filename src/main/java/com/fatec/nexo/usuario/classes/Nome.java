package com.fatec.nexo.usuario.classes;

public class Nome {
    private String nome;

    public Nome() {
    }
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
