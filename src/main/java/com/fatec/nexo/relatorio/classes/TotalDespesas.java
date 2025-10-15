package com.fatec.nexo.relatorio.classes;

import java.math.BigDecimal;
import java.util.List;

import com.fatec.nexo.despesas.DespesasModel;

public class TotalDespesas {
    public double getTotalDespesas(List<DespesasModel> despesas) {
        BigDecimal valorDespesas = BigDecimal.valueOf(0.00);
        for(int i = 0; i < despesas.size(); i++) {
            valorDespesas = valorDespesas.add(BigDecimal.valueOf(despesas.get(i).getQuantia()));
        }
        return valorDespesas.doubleValue();
    }
}
