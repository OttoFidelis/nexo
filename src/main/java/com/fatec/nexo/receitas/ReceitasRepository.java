package com.fatec.nexo.receitas;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.nexo.usuario.UsuarioModel;

@Repository
public interface ReceitasRepository extends JpaRepository<ReceitasModel, Integer> {
    List<ReceitasModel> findByDataBetween(LocalDate start, LocalDate end);
    List<ReceitasModel> findByUsuario(UsuarioModel usuario);
    List<ReceitasModel> findByUsuarioEmail(String email);
}
