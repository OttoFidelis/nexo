package com.fatec.nexo.usuario;

import com.fatec.nexo.usuario.util.EmailUtil;
import com.fatec.nexo.usuario.util.NomeUtil;
import com.fatec.nexo.usuario.util.SenhaUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Modelo que representa um usuário no sistema
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.1
 */
@Table(name = "Usuario")
@Entity
@Data
public class UsuarioModel {
    /**
     * Email do usuário, usado como identificador único
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @Id
    private String email;

    /**
     * Nome completo do usuário
     * Campo obrigatório
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @Column(nullable = false)
    private String nome;

    /**
     * Senha criptografada do usuário
     * Campo obrigatório
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @Column(nullable = false)
    private String senha;

    /**
     * Construtor padrão
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public UsuarioModel() {
    }

    /**
     * Construtor com parâmetros que valida e criptografa os dados
     * @param email Email do usuário
     * @param nome Nome completo do usuário
     * @param senha Senha do usuário (será criptografada)
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.1
     */
    public UsuarioModel(String email, String nome, String senha) {
        this.email = EmailUtil.setEmail(email);
        this.nome = NomeUtil.setNome(nome);
        this.senha = SenhaUtil.setSenha(senha);
    }

    /**
     * Define o nome do usuário com validação
     * @param nome Nome a ser definido
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.1
     */
    public void setNome(String nome) {
        this.nome = NomeUtil.setNome(nome);
    }
    
    /**
     * Define o email do usuário com validação
     * @param email Email a ser definido
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.1
     */
    public void setEmail(String email) {
        this.email = EmailUtil.setEmail(email);
    }

    /**
     * Retorna a senha desencriptada do usuário
     * @return Senha desencriptada
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.1
     */
    public String getSenha() {
        return SenhaUtil.desencriptar(this.senha);
    }

    /**
     * Define a senha do usuário com criptografia
     * @param senha Senha a ser definida (será criptografada)
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.1
     */
    public void setSenha(String senha) {
        this.senha = SenhaUtil.setSenha(senha);
    }
}
