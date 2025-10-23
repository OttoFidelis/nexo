package com.fatec.nexo.saldo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.fatec.nexo.categoria.CategoriaModel;
import com.fatec.nexo.despesas.DespesasModel;
import com.fatec.nexo.receitas.ReceitasModel;
import com.fatec.nexo.usuario.UsuarioModel;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Modelo que representa o saldo de um usuário no sistema
 * 
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
@Table(name = "Saldo")
@Entity
@Data
public class SaldoModel {
    /**
     * Identificador único do saldo
     * 
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Valor monetário do saldo atual
     * 
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private double quantia;

    /**
     * Data de criação ou atualização do saldo
     * Inicializada automaticamente com a data atual no construtor
     * 
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private LocalDate data;

    /**
     * Usuário proprietário do saldo
     * Relacionamento com a entidade Usuario
     * 
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @ManyToOne
    private UsuarioModel usuario;

    /**
     * Lista de categorias associadas ao saldo
     * Relacionamento One-to-Many com CategoriaModel
     * 
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @OneToMany
    @JoinTable(name = "Saldo_categorias_Possui", joinColumns = @JoinColumn(name = "saldo_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<CategoriaModel> categorias;

    /**
     * Construtor que inicializa a data e quantia com valores padrão
     * 
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public SaldoModel() {
        this.data = LocalDate.now();
        this.quantia = 0;
    }

    /**
     * Calcula o total de receitas a partir de uma lista
     * 
     * @param receitas Lista de receitas a serem somadas
     * @return O valor total das receitas
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public BigDecimal calcularQuantiaReceitas(List<ReceitasModel> receitas) {
        BigDecimal total = BigDecimal.ZERO;
        if (receitas != null)
            for (ReceitasModel r : receitas) {
                total = total.add(BigDecimal.valueOf(r.getQuantia()));
            }
        return total;
    }

    /**
     * Calcula o total de despesas a partir de uma lista
     * 
     * @param despesas Lista de despesas a serem somadas
     * @return O valor total das despesas
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public BigDecimal calcularQuantiaDespesas(List<DespesasModel> despesas) {
        BigDecimal total = BigDecimal.ZERO;
        if (despesas != null)
            for (DespesasModel d : despesas) {
                total = total.add(BigDecimal.valueOf(d.getQuantia()));
            }
        return total;
    }

    /**
     * Calcula a quantia de uma única receita
     * 
     * @param receita Receita a ser calculada
     * @return O valor da receita
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public BigDecimal calcularQuantiaReceitas(ReceitasModel receita) {
        BigDecimal total = BigDecimal.ZERO;
        total = total.add(BigDecimal.valueOf(receita.getQuantia()));
        return total;
    }

    public BigDecimal calcularQuantiaDespesas(DespesasModel despesa) {
        BigDecimal total = BigDecimal.ZERO;
        total = total.add(BigDecimal.valueOf(despesa.getQuantia()));
        return total;
    }
}
