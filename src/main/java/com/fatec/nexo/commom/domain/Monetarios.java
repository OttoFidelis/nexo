package com.fatec.nexo.commom.domain;

import java.time.LocalDate;

import com.fatec.nexo.categoria.CategoriaModel;
import com.fatec.nexo.usuario.UsuarioModel;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

/**  
 * Classe pai para os modelos da receita e da despesa, que possui os atrbutos em comum
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
*/
@MappedSuperclass
@Data
public abstract class Monetarios {
     /**
     * Identificador único da receita
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    /**
     * Descrição breve da receita ou despesa
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    protected String descricao;

    /**
     * Valor monetário da receita ou despesa
     * Armazenado com no máximo duas casas decimais
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    protected double quantia;

    /**
     * Data em que a receita ou despesa foi realizada
     * Inicializada automaticamente com a data atual no construtor
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    protected LocalDate data;

      /**
     * Usuário que registrou a receita
     * Relacionamento com a entidade Usuario
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @ManyToOne
    protected UsuarioModel usuario;

    /**
     * Categoria à qual a receita pertence
     * Relacionamento com a entidade Categoria
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @ManyToOne
    protected CategoriaModel categoria;
}
