package com.fatec.nexo.saldo.exceptions;

/**
 * Exceção lançada quando nenhum saldo é encontrado para um usuário específico.
 * Estende RuntimeException para indicar um erro em tempo de execução.
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
public class SaldosNotFoundException extends RuntimeException {
    /**
     * Construtor da exceção SaldosNotFoundException
     * @param email O email do usuário para o qual nenhum saldo foi encontrado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public SaldosNotFoundException(String email) {
        super("Nenhum saldo encontrado para o usuário com email: " + email);
    }
}
