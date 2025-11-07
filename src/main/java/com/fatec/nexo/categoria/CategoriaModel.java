package com.fatec.nexo.categoria;

import com.fatec.nexo.saldo.SaldoModel;
import com.fatec.nexo.usuario.UsuarioModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Modelo que representa uma categoria no sistema
    * @author Otto Fidelis
    * @since 1.0
    * @version 1.1
 */
@Table(name = "Categoria")
@Entity
@Data
public class CategoriaModel {
    /**
     * Identificador único da categoria
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * Nome da categoria
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private String nome;
    /**
     * Descrição breve da categoria
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private String descricao;

    /**
     * Saldo associado à categoria
     * @author Otto Fidelis
     * @since 1.1
     * @version 1.0
     */
    @ManyToOne
    private SaldoModel saldo;

    public CategoriaModel() {
    }
}
