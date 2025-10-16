package com.fatec.nexo.relatorio.exceptions;

/**
 * Exceção lançada quando um relatório com o ID especificado não é encontrado.
 * Estende RuntimeException para indicar um erro em tempo de execução.
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
public class RelatorioNotFoundException extends RuntimeException {
    /**
     * Construtor da exceção RelatorioNotFoundException
     * @param id O ID do relatório que não foi encontrado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public RelatorioNotFoundException(Integer id) {
        super("Relatório não encontrado! Id inválido: " + id);
    }
    
}
