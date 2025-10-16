package com.fatec.nexo.relatorio.classes;

import java.time.LocalDate;
import java.util.List;

import com.fatec.nexo.despesas.DespesasModel;
import com.fatec.nexo.receitas.ReceitasModel;

/**
 * Classe auxiliar para determinar a data de início do relatório
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
public class DataInicio {
    /**
     * Retorna a data da primeira despesa ou receita, o que ocorrer primeiro
     * @param despesas Lista de despesas do usuário
     * @param receitas Lista de receitas do usuário
     * @return A data da primeira despesa ou receita, ou null se nenhuma existir
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
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
