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

@Table(name = "Relatorio")
@Entity
@Data
public class RelatorioModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double totalReceitas;
    private double totalDespesas;
    private double saldo;
    private String nome;
    private String tipo;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String formato;

    @ManyToMany
    @JoinTable(name = "relatorio_despesas",
        joinColumns = @JoinColumn(name = "relatorio_id"), 
        inverseJoinColumns = @JoinColumn(name = "despesas_id"))
    private List<DespesasModel> despesas;

    @ManyToMany
    @JoinTable(name = "receitas_relatorio_possui",
        joinColumns = @JoinColumn(name = "relatorio_id"), 
        inverseJoinColumns = @JoinColumn(name = "receitas_id"))
    private List<ReceitasModel> receitas;

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
    
    private String formatarData(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataFormatada = LocalDate.now().format(formatter);
        return dataFormatada;
    }
}
