package com.fatec.nexo.relatorio.classes;

import java.math.BigDecimal;
import java.util.List;

import com.fatec.nexo.receitas.ReceitasModel;

/**
 * Classe auxiliar para calcular o total de receitas em um período
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
public class TotalReceitas {
    /**
     * Calcula o total de receitas em uma lista de receitas
     * @param receitas Lista de receitas do usuário
     * @return O total de receitas
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public double getTotalReceitas(List<ReceitasModel> receitas) {
        BigDecimal valorReceitas = BigDecimal.valueOf(0.00);
        for(int i = 0; i < receitas.size(); i++) {  
            valorReceitas = valorReceitas.add(BigDecimal.valueOf(receitas.get(i).getQuantia()));
        }
        return valorReceitas.doubleValue();
    }  
}
