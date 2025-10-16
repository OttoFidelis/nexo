package com.fatec.nexo.receitas;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.nexo.usuario.UsuarioModel;

/**
 * Repositório para operações CRUD na entidade Receitas
    * @author Otto Fidelis
    * @since 1.0
    * @version 1.0
 */
@Repository
public interface ReceitasRepository extends JpaRepository<ReceitasModel, Integer> {
    /**
     * Busca receitas dentro de um intervalo de datas
     * @param start Data inicial do intervalo
     * @param end Data final do intervalo
     * @param usuario Usuário associado às receitas
     * @return Lista de receitas dentro do intervalo especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    List<ReceitasModel> findByDataBetween(LocalDate start, LocalDate end, UsuarioModel usuario);
    /**
     * Busca receitas associadas a um usuário específico
     * @param usuario Usuário cujas receitas serão buscadas
     * @return Lista de receitas do usuário especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    List<ReceitasModel> findByUsuario(UsuarioModel usuario);
    /**
     * Busca receitas associadas a um usuário específico
     * @param usuario Usuário cujas receitas serão buscadas
     * @return Lista de receitas do usuário especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    List<ReceitasModel> findByUsuarioEmail(String email);
}
