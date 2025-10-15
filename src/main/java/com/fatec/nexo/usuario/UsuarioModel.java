package com.fatec.nexo.usuario;

import com.fatec.nexo.usuario.classes.Email;
import com.fatec.nexo.usuario.classes.Nome;
import com.fatec.nexo.usuario.classes.Senha;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Table(name = "Usuario")
@Entity
@Data
public class UsuarioModel {
    @Id
    private String email;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String senha;

    public UsuarioModel() {
    }

    public UsuarioModel(String email, String nome, String senha) {
        this.email = new Email().setEmail(email);
        this.nome = new Nome().setNome(nome);
        this.senha = new Senha().setSenha(senha);
    }

    public void setNome(String nome) {
        this.nome = new Nome().setNome(nome);
    }
    
    public void setEmail(String email) {
        this.email = new Email().setEmail(email);
    }

    public String getSenha() {
        return new Senha().desencriptar(this.senha);
    }

    public void setSenha(String senha) {
        this.senha = new Senha().setSenha(senha);
    }
}
