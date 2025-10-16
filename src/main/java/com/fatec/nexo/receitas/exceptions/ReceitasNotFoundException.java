package com.fatec.nexo.receitas.exceptions;

/**
 * Exceção lançada quando uma receita específica não é encontrada pelo ID.
 * Estende RuntimeException para indicar um erro em tempo de execução.
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
public class ReceitasNotFoundException extends RuntimeException {
    /**
     * Construtor da exceção ReceitasNotFoundException
     * @param id O ID da receita que não foi encontrada
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public ReceitasNotFoundException(Integer id) {
        super("Receita não encontrada! Id inválido: " + id);
    }
}
