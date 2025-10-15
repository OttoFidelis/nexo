package com.fatec.nexo.saldo.exceptions;

public class SaldoNotFoundException extends RuntimeException {
    public SaldoNotFoundException(Integer id) {
        super("Saldo não encontrado! Id inválido: " + id);
    }
}
