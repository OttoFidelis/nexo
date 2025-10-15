package com.fatec.nexo.saldo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fatec.nexo.usuario.UsuarioModel;

@Repository
public interface SaldoRepository extends JpaRepository<SaldoModel, Integer> {
    List<SaldoModel> findByUsuarioEmail(String email);
    List<SaldoModel> findByUsuario(UsuarioModel usuario);
}
