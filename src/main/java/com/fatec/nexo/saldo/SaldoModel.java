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
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import lombok.Data;

@Table(name = "Saldo")
@Entity
@Data
public class SaldoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double quantia;
    private LocalDate data;
    private UsuarioModel usuario;

    @OneToMany
    @JoinTable(name = "Saldo_categorias_Possui",
        joinColumns = @JoinColumn(name = "saldo_id"), 
        inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    private List<CategoriaModel> categorias;

    public SaldoModel() {
        this.data = LocalDate.now();
        this.quantia = 0;
    }

    public BigDecimal calcularQuantiaReceitas(List<ReceitasModel> receitas){
        BigDecimal quantia = BigDecimal.ZERO;
        for(ReceitasModel r : receitas){
            quantia = quantia.add(BigDecimal.valueOf(r.getQuantia()));
        }
        return quantia;
    }

    public BigDecimal calcularQuantiaDespesas(List<DespesasModel> despesas){
        BigDecimal quantia = BigDecimal.ZERO;
        for(DespesasModel d : despesas){
            quantia = quantia.add(BigDecimal.valueOf(d.getQuantia()));
        }
        return quantia;
    }

    public BigDecimal calcularQuantiaReceitas(ReceitasModel receita){
        BigDecimal quantia = BigDecimal.ZERO;
        quantia = quantia.add(BigDecimal.valueOf(receita.getQuantia()));
        return quantia;
    }

    public BigDecimal calcularQuantiaDespesas(DespesasModel despesa){
        BigDecimal quantia = BigDecimal.ZERO;
        quantia = quantia.add(BigDecimal.valueOf(despesa.getQuantia()));
        return quantia;
    }
}
