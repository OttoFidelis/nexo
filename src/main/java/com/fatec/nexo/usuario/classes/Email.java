package com.fatec.nexo.usuario.classes;

public class Email {
    private String email;

    public Email() {
    }
    public String setEmail(String email) {
        if(!email.contains("@") || !email.contains(".")){
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = email;
        return this.email;
    }
}
