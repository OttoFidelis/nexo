package com.fatec.nexo.relatorio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fatec.nexo.despesas.DespesasModel;
import com.fatec.nexo.receitas.ReceitasModel;
import com.fatec.nexo.relatorio.classes.DataInicio;
import com.fatec.nexo.relatorio.classes.TotalDespesas;
import com.fatec.nexo.relatorio.classes.TotalReceitas;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Modelo que representa um relatório financeiro no sistema
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
@Table(name = "Relatorio")
@Entity
@Data
public class RelatorioModel {
    /**
     * Identificador único do relatório
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Total de receitas do período do relatório
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private double totalReceitas;

    /**
     * Total de despesas do período do relatório
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private double totalDespesas;

    /**
     * Saldo resultante (receitas - despesas)
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private double saldo;

    /**
     * Nome descritivo do relatório
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private String nome;

    /**
     * Tipo do relatório (Semanal, Mensal, Anual ou Personalizado)
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private String tipo;

    /**
     * Data de início do período do relatório
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private LocalDate dataInicio;

    /**
     * Data de término do período do relatório
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private LocalDate dataFim;

    /**
     * Formato de visualização do relatório (ex: Barras)
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private String formato;

    /**
     * Lista de despesas incluídas no relatório
     * Relacionamento Many-to-Many com DespesasModel
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @ManyToMany
    @JoinTable(name = "relatorio_despesas",
        joinColumns = @JoinColumn(name = "relatorio_id"), 
        inverseJoinColumns = @JoinColumn(name = "despesas_id"))
    private List<DespesasModel> despesas;

    /**
     * Lista de receitas incluídas no relatório
     * Relacionamento Many-to-Many com ReceitasModel
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @ManyToMany
    @JoinTable(name = "receitas_relatorio_possui",
        joinColumns = @JoinColumn(name = "relatorio_id"), 
        inverseJoinColumns = @JoinColumn(name = "receitas_id"))
    private List<ReceitasModel> receitas;

    /**
     * Construtor que inicializa um relatório com despesas, receitas e saldo
     * @param despesas Lista de despesas do período
     * @param receitas Lista de receitas do período
     * @param saldo Saldo atual do usuário
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public RelatorioModel(List<DespesasModel> despesas, List<ReceitasModel> receitas, double saldo) {
        this.nome = "Relatório de " + formatarData();
        this.formato = "Barras";
        this.despesas = despesas;
        this.receitas = receitas;
        this.totalReceitas = new TotalReceitas().getTotalReceitas(this.receitas);
        this.totalDespesas = new TotalDespesas().getTotalDespesas(this.despesas);
        this.dataInicio = new DataInicio().getDataInicio(this.despesas, this.receitas);
        this.dataFim = LocalDate.now();
    }
    
    /**
     * Formata a data atual para o padrão dd/MM/yyyy
     * @return String com a data formatada
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private String formatarData(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = LocalDate.now().format(formatter);
        return dataFormatada;
    }
}
