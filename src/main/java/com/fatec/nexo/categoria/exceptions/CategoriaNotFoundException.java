package com.fatec.nexo.categoria.exceptions;

/**
 * Exceção lançada quando uma categoria específica não é encontrada pelo ID.
 * Estende RuntimeException para indicar um erro em tempo de execução.
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
public class CategoriaNotFoundException extends RuntimeException {
    /**
     * Construtor da exceção CategoriaNotFoundException
     * @param id O ID da categoria que não foi encontrada
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public CategoriaNotFoundException(Integer id) {
        super("Categoria não encontrada! Id inválido " + id);
    }
}
