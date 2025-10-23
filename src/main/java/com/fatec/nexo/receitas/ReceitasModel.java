package com.fatec.nexo.receitas;

import java.time.LocalDate;

import com.fatec.nexo.categoria.CategoriaModel;
import com.fatec.nexo.classesGerais.Quantia;
import com.fatec.nexo.usuario.UsuarioModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Modelo que representa uma receita no sistema
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
@Table(name = "Receitas")
@Entity
@Data
public class ReceitasModel {
    /**
     * Identificador único da receita
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Descrição breve da receita
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private String descricao;

    /**
     * Valor monetário da receita
     * Armazenado com no máximo duas casas decimais
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private double quantia;

    /**
     * Data em que a receita foi realizada
     * Inicializada automaticamente com a data atual no construtor
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private LocalDate data;

    /**
     * Usuário que registrou a receita
     * Relacionamento com a entidade Usuario
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @ManyToOne
    private UsuarioModel usuario;

    /**
     * Categoria à qual a receita pertence
     * Relacionamento com a entidade Categoria
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @ManyToOne
    private CategoriaModel categoria;

    /**
     * Construtor que inicializa a data com a data atual
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public ReceitasModel() {
        this.data = LocalDate.now();

    }

    /**
     * Insere uma quantia, garantindo que tenha no máximo duas casas decimais
     * @param quantia Valor a ser definido
     * @return A quantia inserida
     * @throws IllegalArgumentException se a quantia tiver mais de duas casas decimais
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public double setQuantia(double quantia) {
        this.quantia = new Quantia().setQuantia(quantia);
        return this.quantia;
    }
}
