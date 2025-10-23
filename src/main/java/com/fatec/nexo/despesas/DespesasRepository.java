package com.fatec.nexo.despesas;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.nexo.usuario.UsuarioModel;

/**
 * Repositório para operações CRUD na entidade Despesas
    * @author Otto Fidelis
    * @since 1.0
    * @version 1.0
 */
@Repository
public interface DespesasRepository extends JpaRepository<DespesasModel, Integer> {
    /**
     * Busca despesas dentro de um intervalo de datas para um usuário específico
     * @param start Data inicial do intervalo
     * @param end Data final do intervalo
     * @param usuario Usuário associado às despesas
     * @return Lista de despesas dentro do intervalo especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.1
     */
    List<DespesasModel> findByDataBetweenAndUsuario(LocalDate start, LocalDate end, UsuarioModel usuario);
    /**
     * Busca despesas associadas a um usuário específico
     * @param usuario Usuário cujas despesas serão buscadas
     * @return Lista de despesas do usuário especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    List<DespesasModel> findByUsuarioEmail(String email);
    /**
     * Busca despesas associadas a um usuário específico
     * @param usuario Usuário cujas despesas serão buscadas
     * @return Lista de despesas do usuário especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    List<DespesasModel> findByUsuario(UsuarioModel usuario);
}
