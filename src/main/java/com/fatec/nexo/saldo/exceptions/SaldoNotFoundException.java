package com.fatec.nexo.saldo.exceptions;

/**
 * Exceção lançada quando um saldo específico não é encontrado pelo ID.
 * Estende RuntimeException para indicar um erro em tempo de execução.
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
public class SaldoNotFoundException extends RuntimeException {
    /**
     * Construtor da exceção SaldoNotFoundException
     * @param id O ID do saldo que não foi encontrado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public SaldoNotFoundException(Integer id) {
        super("Saldo não encontrado! Id inválido: " + id);
    }
}
