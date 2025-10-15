package com.fatec.nexo.relatorio.classes;

import java.time.LocalDate;
import java.util.List;

import com.fatec.nexo.despesas.DespesasModel;
import com.fatec.nexo.receitas.ReceitasModel;

public class DataInicio {
    public LocalDate getDataInicio(List<DespesasModel> despesas, List<ReceitasModel> receitas) {
        LocalDate dataPrimeiraDespesa=null;
        LocalDate dataPrimeiraReceita=null;
        for(int i = 0; i < despesas.size(); i++) {
            if(i==0) dataPrimeiraDespesa = despesas.get(i).getData();
            if(i==0) dataPrimeiraReceita = receitas.get(i).getData(); 
        }
        if(dataPrimeiraDespesa!=null && dataPrimeiraReceita!=null) {
            if(dataPrimeiraDespesa.isBefore(dataPrimeiraReceita)) return dataPrimeiraDespesa;
            else return dataPrimeiraReceita;
        }
        return null;
    }
}
