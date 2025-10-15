package com.fatec.nexo.despesas;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.nexo.usuario.UsuarioModel;

@Repository
public interface DespesasRepository extends JpaRepository<DespesasModel, Integer> {
    List<DespesasModel> findByDataBetween(LocalDate start, LocalDate end);
    List<DespesasModel> findByUsuarioEmail(String email);
    List<DespesasModel> findByUsuario(UsuarioModel usuario);
}
