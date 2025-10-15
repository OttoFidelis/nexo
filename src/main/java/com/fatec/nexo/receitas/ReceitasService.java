package com.fatec.nexo.receitas;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fatec.nexo.receitas.exceptions.ReceitasNotFoundException;
import com.fatec.nexo.saldo.SaldoService;
import com.fatec.nexo.usuario.UsuarioModel;

@Service
public class ReceitasService {
    private final ReceitasRepository receitasRepository;
    private final SaldoService saldoService;
    public ReceitasService(ReceitasRepository receitasRepository, SaldoService saldoService) {
        this.receitasRepository = receitasRepository;
        this.saldoService = saldoService;
    }

    public ReceitasModel create(ReceitasModel receita) {
        saldoService.create(receita.getUsuario(), receita);
        return receitasRepository.save(receita);
    }

    public List<ReceitasModel> findAll() {
        return receitasRepository.findAll();
    }

    public List<ReceitasModel> findByUsuario(UsuarioModel usuario) {
        return receitasRepository.findByUsuario(usuario);
    }

    public List<ReceitasModel> findByUsuario(String email) {
        return receitasRepository.findByUsuarioEmail(email);
    }

    public ReceitasModel findById(Integer id, UsuarioModel usuario) {
        Optional<ReceitasModel> receita = receitasRepository.findById(id);
        if(receita.isPresent()){ 
            ReceitasModel _receita = receita.get();
            if(usuario != _receita.getUsuario()) {
                throw new SecurityException("Acesso negado! Você não pode acessar a receita de outro usuário.");
            }
            return _receita;
        }
        else throw new ReceitasNotFoundException(id);
    }

    public ReceitasModel update(Integer id, ReceitasModel receita, UsuarioModel usuario) {
        ReceitasModel _receita = findById(id, usuario);
        _receita.setDescricao(receita.getDescricao());
        _receita.setQuantia(receita.getQuantia());
        saldoService.update(_receita.getUsuario(), receita);
        return receitasRepository.save(_receita);
    }

    public void delete(Integer id, UsuarioModel usuario) {
        if(findById(id, usuario).getUsuario()!=null)
        receitasRepository.deleteById(id);
    }

    public List<ReceitasModel> findByDataBetween(LocalDate start, LocalDate end) {
        return receitasRepository.findByDataBetween(start, end);
    }
}
