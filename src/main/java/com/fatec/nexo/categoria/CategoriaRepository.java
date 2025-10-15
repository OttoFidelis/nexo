package com.fatec.nexo.categoria;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, Integer> {
    
}
