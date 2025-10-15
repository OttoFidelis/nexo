package com.fatec.nexo.receitas;

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

@Table(name = "Receitas")
@Entity
@Data
public class ReceitasModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String descricao;
    private double quantia;
    private LocalDate data;
    private UsuarioModel usuario;
    private CategoriaModel categoria;

    public ReceitasModel() {
        this.data = LocalDate.now();
    }
    public double setQuantia(double quantia) {
        this.quantia = new Quantia().setQuantia(quantia);
        return this.quantia;
    }
}
