package com.fatec.nexo.categoria;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Modelo que representa uma categoria no sistema
    * @author Otto Fidelis
    * @since 1.0
    * @version 1.0
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
     * Construtor padrão
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */ 
    public CategoriaModel() {
    }
}
