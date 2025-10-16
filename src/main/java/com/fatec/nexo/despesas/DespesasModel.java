package com.fatec.nexo.despesas;

import java.time.LocalDate;

import com.fatec.nexo.categoria.CategoriaModel;
import com.fatec.nexo.classesGerais.Quantia;
import com.fatec.nexo.usuario.UsuarioModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Modelo que representa uma despesa no sistema
    * @author Otto Fidelis
    * @since 1.0
    * @version 1.0
 */
@Table(name = "Despesas")
@Entity
@Data
public class DespesasModel{
    /**
     * Identificador único da despesa
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Descrição breve da despesa
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private String descricao;

    /**
     * Valor monetário da despesa
     * Armazenado com no máximo duas casas decimais
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private double quantia;

    /**
     * Data em que a despesa foi realizada
     * Inicializada automaticamente com a data atual no construtor
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private LocalDate data;

    /**
     * Usuário que registrou a despesa
     * Relacionamento com a entidade Usuario
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private UsuarioModel usuario;

    /**
     * Categoria à qual a despesa pertence
     * Relacionamento com a entidade Categoria
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private CategoriaModel categoria;

    /**
     * Construtor que inicializa a data com a data atual
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public DespesasModel() {
        this.data = LocalDate.now();
    }

    /**
     * Insere uma quantia, garantindo que tenha no máximo duas casas decimais
     * @param quantia
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
