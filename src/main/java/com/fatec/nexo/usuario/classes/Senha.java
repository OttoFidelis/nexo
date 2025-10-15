package com.fatec.nexo.usuario.classes;

import java.util.Base64;

public class Senha {
    private String senha;

    public Senha() {
    }

    public String setSenha(String senha) {
        if(senha.length() < 8){
            throw new IllegalArgumentException("Senha deve ter 8 caracteres");
        }
        this.senha = encrypt(senha);
        return this.senha;
    }

     private String encrypt(String senha) {
        return Base64.getEncoder().encodeToString(senha.getBytes());
    }

    public String desencriptar(String senhaEncriptada) {
        return new String(Base64.getDecoder().decode(senhaEncriptada));
    }
}
