package com.fatec.nexo.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações CRUD na entidade Usuário
    * @author Otto Fidelis
    * @since 1.0
    * @version 1.0
 */
@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, String> {
    
}
