package com.fatec.nexo.relatorio.util;

import java.math.BigDecimal;
import java.util.List;

import com.fatec.nexo.despesas.DespesasModel;

/**
 * Classe auxiliar para calcular o total de despesas em um período
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
public class TotalDespesasUtil {
    /**
     * Calcula o total de despesas em uma lista de despesas
     * @param despesas Lista de despesas do usuário
     * @return O total de despesas
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public static double getTotalDespesas(List<DespesasModel> despesas) {
        BigDecimal valorDespesas = BigDecimal.valueOf(0.00);
        for(int i = 0; i < despesas.size(); i++) {
            valorDespesas = valorDespesas.add(BigDecimal.valueOf(despesas.get(i).getQuantia()));
        }
        return valorDespesas.doubleValue();
    }
}
