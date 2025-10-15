package com.fatec.nexo.relatorio.exceptions;

public class RelatorioNotFoundException extends RuntimeException {
    public RelatorioNotFoundException(Integer id) {
        super("Relatório não encontrado! Id inválido: " + id);
    }
    
}
