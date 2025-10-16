package com.fatec.nexo.despesas;

/**
 * Exceção lançada quando uma despesa específica não é encontrada.
 * Estende RuntimeException para indicar um erro em tempo de execução.
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
public class DespesaNotFoundException extends RuntimeException {
    /**
     * Construtor da exceção DespesaNotFoundException
     * @param id O ID da despesa que não foi encontrada
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public DespesaNotFoundException(Integer id) {
        super("Despesa não encontrada! Id inválido " + id);
    }
    
}
