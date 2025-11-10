package com.fatec.nexo.despesas;

import java.time.LocalDate;

import com.fatec.nexo.common.domain.Monetarios;
import com.fatec.nexo.common.valueobjects.Quantia;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Modelo que representa uma despesa no sistema
    * @author Otto Fidelis
    * @since 1.0
    * @version 1.0
 */
@Table(name = "Despesas")
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class DespesasModel extends Monetarios{
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

    @Override
    public void setQuantia(double quantia){
        this.quantia = new Quantia().setQuantia(quantia);
    }
}
