package com.fatec.nexo.saldo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.nexo.despesas.DespesasModel;
import com.fatec.nexo.receitas.ReceitasModel;
import com.fatec.nexo.saldo.exceptions.SaldoNotFoundException;
import com.fatec.nexo.usuario.UsuarioModel;

@Service
public class SaldoService {
    private final SaldoRepository saldoRepository;
    public SaldoService(SaldoRepository saldoRepository) {
        this.saldoRepository = saldoRepository;
    }

    public SaldoModel create(UsuarioModel usuario, ReceitasModel receita) {
        SaldoModel saldo = findLastSaldo(usuario);
        BigDecimal quantia = BigDecimal.valueOf(saldo.getQuantia()).add(saldo.calcularQuantiaReceitas(receita));
        saldo.setQuantia(quantia.doubleValue());
        saldo.setUsuario(usuario);
        return saldoRepository.save(saldo);
    }
    public SaldoModel create(UsuarioModel usuario, DespesasModel despesa) {
        SaldoModel saldo = findLastSaldo(usuario);
        BigDecimal quantia = BigDecimal.valueOf(saldo.getQuantia()).subtract(saldo.calcularQuantiaDespesas(despesa));
        saldo.setQuantia(quantia.doubleValue());
        saldo.setUsuario(usuario);
        return saldoRepository.save(saldo);
    }
    public SaldoModel create(UsuarioModel usuario, List<ReceitasModel> receitas, List<DespesasModel> despesas) {
        SaldoModel saldo = new SaldoModel();
        BigDecimal quantia = saldo.calcularQuantiaReceitas(receitas).subtract(saldo.calcularQuantiaDespesas(despesas));
        saldo.setQuantia(quantia.doubleValue());
        saldo.setUsuario(usuario);
        return saldoRepository.save(saldo);
    }
    public List<SaldoModel> findByUsuario(UsuarioModel usuario) {
        return saldoRepository.findByUsuario(usuario);
    }
    public List<SaldoModel> findByUsuario(String email) {
        return saldoRepository.findByUsuarioEmail(email);
    }
    public SaldoModel update(UsuarioModel usuario, ReceitasModel receita) {
        if(usuario != receita.getUsuario()) {
            throw new SecurityException("Acesso negado! Você não pode atualizar o saldo de outro usuário.");
        }
        SaldoModel saldo = findLastSaldo(usuario);
        BigDecimal quantia = BigDecimal.valueOf(saldo.getQuantia()).add(saldo.calcularQuantiaReceitas(receita));
        saldo.setQuantia(quantia.doubleValue());
        return saldoRepository.save(saldo);
    }
    public SaldoModel update(UsuarioModel usuario, DespesasModel despesa) {
        SaldoModel saldo = findLastSaldo(usuario);
        BigDecimal quantia = BigDecimal.valueOf(saldo.getQuantia()).subtract(saldo.calcularQuantiaDespesas(despesa));
        saldo.setQuantia(quantia.doubleValue());
        return saldoRepository.save(saldo);
    }
    public List<SaldoModel> findAll(){
        return saldoRepository.findAll();
    }
    public SaldoModel findById(Integer id, UsuarioModel usuario){
        if(saldoRepository.findById(id).isEmpty()){
            throw new SaldoNotFoundException(id);
        }
        if(saldoRepository.findById(id).get().getUsuario() != usuario) {
            throw new SecurityException("Acesso negado! Você não pode acessar o saldo de outro usuário.");
        }
        return saldoRepository.findById(id)
            .orElseThrow(() -> new SaldoNotFoundException(id));
    }
    public void delete(Integer id, UsuarioModel usuario){
        if(findById(id, usuario).getUsuario() != usuario) {
            throw new SecurityException("Acesso negado! Você não pode deletar o saldo de outro usuário.");
        }
        if(saldoRepository.existsById(id)) saldoRepository.deleteById(id);
        else throw new SaldoNotFoundException(id);
    }
    public SaldoModel findLastSaldo(String email){
        List<SaldoModel> saldos = findByUsuario(email);
        for(int i = 0; i < saldos.size(); i++){
            if(i == saldos.size()-1){
                return saldos.get(i);
            }
        }
        return new SaldoModel();
    }
    public SaldoModel findLastSaldo(UsuarioModel usuario){
        List<SaldoModel> saldos = findByUsuario(usuario);
        for(int i = 0; i < saldos.size(); i++){
            if(i == saldos.size()-1){
                return saldos.get(i);
            }
        }
        return new SaldoModel();
    }
}
