package com.fatec.nexo.categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações CRUD na entidade Categoria
    * @author Otto Fidelis
    * @since 1.0
    * @version 1.0
 */
@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, Integer> {
    
}
