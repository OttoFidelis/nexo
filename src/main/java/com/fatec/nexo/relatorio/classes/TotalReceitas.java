package com.fatec.nexo.relatorio.classes;

import java.math.BigDecimal;
import java.util.List;

import com.fatec.nexo.receitas.ReceitasModel;

public class TotalReceitas {
    public double getTotalReceitas(List<ReceitasModel> receitas) {
        BigDecimal valorReceitas = BigDecimal.valueOf(0.00);
        for(int i = 0; i < receitas.size(); i++) {  
            valorReceitas = valorReceitas.add(BigDecimal.valueOf(receitas.get(i).getQuantia()));
        }
        return valorReceitas.doubleValue();
    }  
}
