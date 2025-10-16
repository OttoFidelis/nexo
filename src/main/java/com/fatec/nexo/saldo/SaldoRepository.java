package com.fatec.nexo.saldo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.nexo.usuario.UsuarioModel;

/**
 * Repositório para operações CRUD na entidade Saldo
    * @author Otto Fidelis
    * @since 1.0
    * @version 1.0
 */
@Repository
public interface SaldoRepository extends JpaRepository<SaldoModel, Integer> {
    /**
     * Busca saldos associados a um usuário específico
     * @param email Email do usuário cujos saldos serão buscados
     * @return Lista de saldos do usuário especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    List<SaldoModel> findByUsuarioEmail(String email);
    /**
     * Busca saldos associados a um usuário específico
     * @param usuario Usuário cujos saldos serão buscados
     * @return Lista de saldos do usuário especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    List<SaldoModel> findByUsuario(UsuarioModel usuario);
}
