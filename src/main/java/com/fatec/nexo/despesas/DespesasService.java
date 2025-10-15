package com.fatec.nexo.despesas;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.nexo.saldo.SaldoService;
import com.fatec.nexo.usuario.UsuarioModel;

@Service
public class DespesasService {
    private final DespesasRepository despesasRepository;
    private final SaldoService saldoService;
    public DespesasService(DespesasRepository despesasRepository, SaldoService saldoService) {
        this.despesasRepository = despesasRepository;
        this.saldoService = saldoService;
    }

    public DespesasModel create(DespesasModel despesa) {
        saldoService.create(despesa.getUsuario(), despesa);
        return despesasRepository.save(despesa);
    }

    public DespesasModel findById(Integer id) {
        return despesasRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Despesa não encontrada! Id inválido " + id));
    }

    public List<DespesasModel> findByUsuario(UsuarioModel usuario) {
        return despesasRepository.findByUsuario(usuario);
    }

    public List<DespesasModel> findByUsuario(String email) {
        return despesasRepository.findByUsuarioEmail(email);
    }

    public DespesasModel update(Integer id, DespesasModel despesa) {
        DespesasModel _despesa = findById(id);
        _despesa.setDescricao(despesa.getDescricao());
        _despesa.setQuantia(despesa.getQuantia());
        saldoService.update(_despesa.getUsuario(), despesa);
        return despesasRepository.save(_despesa);
    }

    public void delete(Integer id) {
        if(despesasRepository.existsById(id)) despesasRepository.deleteById(id);
        else throw new RuntimeException("Despesa não encontrada! Id inválido " + id);
    }

    public List<DespesasModel> findAll() {
        return despesasRepository.findAll();
    }

    public List<DespesasModel> findByDataBetween(LocalDate start, LocalDate end) {
        return despesasRepository.findByDataBetween(start, end);
    }
}
