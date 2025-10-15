package com.fatec.nexo.receitas.exceptions;

public class ReceitasNotFoundException extends RuntimeException {
    public ReceitasNotFoundException(Integer id) {
        super("Receita não encontrada! Id inválido: " + id);
    }
}
