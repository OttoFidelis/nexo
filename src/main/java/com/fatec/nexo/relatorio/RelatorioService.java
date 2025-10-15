package com.fatec.nexo.relatorio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fatec.nexo.relatorio.exceptions.RelatorioNotFoundException;
import com.fatec.nexo.saldo.SaldoService;
import com.fatec.nexo.usuario.UsuarioModel;

@Service
public class RelatorioService {
    private final RelatorioRepository relatorioRepository;
    private final SaldoService saldoService;
    private final RelatorioServiceHelper relatorioServiceHelper;
    public RelatorioService(RelatorioRepository relatorioRepository, SaldoService saldoService, RelatorioServiceHelper relatorioServiceHelper) {
        this.relatorioRepository = relatorioRepository;
        this.saldoService = saldoService;
        this.relatorioServiceHelper = relatorioServiceHelper;
    }

    public RelatorioModel createMensal(UsuarioModel usuario) {
        RelatorioModel _relatorio = new RelatorioModel(
            relatorioServiceHelper.getDespesasMesAnterior(),
            relatorioServiceHelper.getReceitasMesAnterior(),
            saldoService.findLastSaldo(usuario).getQuantia()
        );
        _relatorio.setTipo("Mensal");
        return relatorioRepository.save(_relatorio);
    }

    public RelatorioModel createSemanal(UsuarioModel usuario) {
        RelatorioModel _relatorio = new RelatorioModel(
            relatorioServiceHelper.getDespesasSemanaAnterior(),
            relatorioServiceHelper.getReceitasSemanaAnterior(),
            saldoService.findLastSaldo(usuario).getQuantia()
        );
        if(_relatorio.getDespesas().isEmpty() && _relatorio.getReceitas().isEmpty()) {
            return null;
        }
        _relatorio.setTipo("Semanal");
        return relatorioRepository.save(_relatorio);
    }

    public RelatorioModel createAnual(UsuarioModel usuario) {
        RelatorioModel _relatorio = new RelatorioModel(
            relatorioServiceHelper.getDespesasAnoAnterior(),
            relatorioServiceHelper.getReceitasAnoAnterior(),
            saldoService.findLastSaldo(usuario).getQuantia()
        );
        if(_relatorio.getDespesas().isEmpty() && _relatorio.getReceitas().isEmpty()) {
            return null;
        }
        _relatorio.setTipo("Anual");
        return relatorioRepository.save(_relatorio);
    }

    public RelatorioModel createPersonalizado(UsuarioModel usuario, LocalDate start, LocalDate end) {
        RelatorioModel _relatorio = new RelatorioModel(
            relatorioServiceHelper.getDespesasData(start, end),
            relatorioServiceHelper.getReceitasData(start, end),
            saldoService.findLastSaldo(usuario).getQuantia()
        );
        if(_relatorio.getDespesas().isEmpty() && _relatorio.getReceitas().isEmpty()) {
            return null;
        }
        _relatorio.setTipo("Personalizado");
        return relatorioRepository.save(_relatorio);
    }

    public List<RelatorioModel> findAll() {
        return relatorioRepository.findAll();
    }

    public RelatorioModel findById(Integer id, UsuarioModel usuario) {
        Optional<RelatorioModel> relatorioOpt = relatorioRepository.findById(id);
        if(relatorioOpt.isEmpty()) throw new RelatorioNotFoundException(id);
        RelatorioModel relatorio = relatorioOpt.get();
        if(!relatorio.getDespesas().get(0).getUsuario().equals(usuario)) {
            throw new SecurityException("Acesso negado ao relatório de outro usuário.");
        }
        return relatorioRepository.findById(id)
            .orElseThrow(() -> new RelatorioNotFoundException(id));
    }
    
    public RelatorioModel update(Integer id, RelatorioModel relatorio, UsuarioModel usuario) {
        RelatorioModel _relatorio = findById(id, usuario);
        _relatorio.setFormato(relatorio.getFormato());
        return relatorioRepository.save(_relatorio);
    }

    public void delete(Integer id) {
        UsuarioModel usuario = findById(id, null).getDespesas().get(0).getUsuario();
        if(findById(id, usuario) != null) relatorioRepository.deleteById(id);
    }
}
