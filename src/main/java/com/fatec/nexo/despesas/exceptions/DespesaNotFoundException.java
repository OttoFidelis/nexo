package com.fatec.nexo.despesas.exceptions;

public class DespesaNotFoundException extends RuntimeException {
    public DespesaNotFoundException(Integer id) {
        super("Despesa não encontrada! Id inválido: " + id);
    }
}