package com.fatec.nexo.receitas;

import java.time.LocalDate;

import com.fatec.nexo.commom.domain.Monetarios;
import com.fatec.nexo.commom.valueobjects.Quantia;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Modelo que representa uma receita no sistema
 * @author Otto Fidelis
 * @since 1.0
 * @version 2.0
 */
@Table(name = "Receitas")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class ReceitasModel extends Monetarios{
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
     * Sobrescreve o setter padrão do Lombok para adicionar validação
     * @param quantia Valor a ser definido
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    @Override
    public void setQuantia(double quantia) {
        this.quantia = new Quantia().setQuantia(quantia);
    }
}
