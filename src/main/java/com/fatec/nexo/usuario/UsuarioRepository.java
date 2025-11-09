package com.fatec.nexo.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações CRUD na entidade Usuário
    * @author Otto Fidelis
    * @since 1.0
    * @version 1.1
 */
@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, String> {
   /**
   * Busca um usuário pelo email e senha
   * @param email email que o usuário digitou
   * @param senha senha que o usuário digitou
   * @return UsuarioModel
   * @author Otto Fidelis
   * @since 1.1
   * @version 1.0
   */
   public Optional<UsuarioModel> findByEmailAndSenha(String email, String senha);
}
