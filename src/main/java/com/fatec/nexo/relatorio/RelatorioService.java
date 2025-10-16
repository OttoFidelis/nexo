package com.fatec.nexo.relatorio;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fatec.nexo.relatorio.exceptions.RelatorioNotFoundException;
import com.fatec.nexo.saldo.SaldoService;
import com.fatec.nexo.usuario.UsuarioModel;

/**
 * Serviço responsável pelo gerenciamento de relatórios financeiros
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
@Service
public class RelatorioService {
    /**
     * Repositório de relatórios injetado no serviço
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private final RelatorioRepository relatorioRepository;

    /**
     * Serviço de saldo injetado no serviço de relatórios
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private final SaldoService saldoService;

    /**
     * Helper do serviço de relatórios para operações auxiliares
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private final RelatorioServiceHelper relatorioServiceHelper;

    /**
     * Construtor do serviço de relatórios
     * @param relatorioRepository Repositório de relatórios a ser injetado
     * @param saldoService Serviço de saldo a ser injetado
     * @param relatorioServiceHelper Helper de relatórios a ser injetado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public RelatorioService(RelatorioRepository relatorioRepository, SaldoService saldoService, RelatorioServiceHelper relatorioServiceHelper) {
        this.relatorioRepository = relatorioRepository;
        this.saldoService = saldoService;
        this.relatorioServiceHelper = relatorioServiceHelper;
    }

    /**
     * Cria um relatório mensal com dados do mês anterior
     * @param usuario Usuário para o qual o relatório será criado
     * @return O relatório mensal criado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public RelatorioModel createMensal(UsuarioModel usuario) {
        RelatorioModel _relatorio = new RelatorioModel(
            relatorioServiceHelper.getDespesasMesAnterior(usuario),
            relatorioServiceHelper.getReceitasMesAnterior(usuario),
            saldoService.findLastSaldo(usuario).getQuantia()
        );
        _relatorio.setTipo("Mensal");
        return relatorioRepository.save(_relatorio);
    }

    /**
     * Cria um relatório semanal com dados da semana anterior
     * @param usuario Usuário para o qual o relatório será criado
     * @return O relatório semanal criado ou null se não houver dados
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public RelatorioModel createSemanal(UsuarioModel usuario) {
        RelatorioModel _relatorio = new RelatorioModel(
            relatorioServiceHelper.getDespesasSemanaAnterior(usuario),
            relatorioServiceHelper.getReceitasSemanaAnterior(usuario),
            saldoService.findLastSaldo(usuario).getQuantia()
        );
        if(_relatorio.getDespesas().isEmpty() && _relatorio.getReceitas().isEmpty()) {
            return null;
        }
        _relatorio.setTipo("Semanal");
        return relatorioRepository.save(_relatorio);
    }

    /**
     * Cria um relatório anual com dados do ano anterior
     * @param usuario Usuário para o qual o relatório será criado
     * @return O relatório anual criado ou null se não houver dados
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public RelatorioModel createAnual(UsuarioModel usuario) {
        RelatorioModel _relatorio = new RelatorioModel(
            relatorioServiceHelper.getDespesasAnoAnterior(usuario),
            relatorioServiceHelper.getReceitasAnoAnterior(usuario),
            saldoService.findLastSaldo(usuario).getQuantia()
        );
        if(_relatorio.getDespesas().isEmpty() && _relatorio.getReceitas().isEmpty()) {
            return null;
        }
        _relatorio.setTipo("Anual");
        return relatorioRepository.save(_relatorio);
    }

    /**
     * Cria um relatório personalizado com período definido pelo usuário
     * @param usuario Usuário para o qual o relatório será criado
     * @param start Data inicial do período
     * @param end Data final do período
     * @return O relatório personalizado criado ou null se não houver dados
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public RelatorioModel createPersonalizado(UsuarioModel usuario, LocalDate start, LocalDate end) {
        RelatorioModel _relatorio = new RelatorioModel(
            relatorioServiceHelper.getDespesasData(start, end, usuario),
            relatorioServiceHelper.getReceitasData(start, end, usuario),
            saldoService.findLastSaldo(usuario).getQuantia()
        );
        if(_relatorio.getDespesas().isEmpty() && _relatorio.getReceitas().isEmpty()) {
            return null;
        }
        _relatorio.setTipo("Personalizado");
        return relatorioRepository.save(_relatorio);
    }

    /**
     * Retorna todos os relatórios cadastrados no sistema
     * @return Lista de todos os relatórios
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public List<RelatorioModel> findAll() {
        return relatorioRepository.findAll();
    }

    /**
     * Busca um relatório pelo ID
     * @param id O ID do relatório a ser buscado
     * @param usuario O usuário que está solicitando o relatório
     * @return O relatório encontrado
     * @throws RelatorioNotFoundException se não encontrar o relatório com o ID especificado
     * @throws SecurityException se o usuário não tiver permissão para acessar o relatório
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
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
    
    /**
     * Atualiza o formato de visualização de um relatório existente
     * @param id O ID do relatório a ser atualizado
     * @param relatorio O objeto com o novo formato do relatório
     * @param usuario O usuário que está solicitando a atualização
     * @return O relatório atualizado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public RelatorioModel update(Integer id, RelatorioModel relatorio, UsuarioModel usuario) {
        RelatorioModel _relatorio = findById(id, usuario);
        _relatorio.setFormato(relatorio.getFormato());
        return relatorioRepository.save(_relatorio);
    }

    /**
     * Remove um relatório do sistema pelo ID
     * @param id O ID do relatório a ser removido
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public void delete(Integer id) {
        UsuarioModel usuario = findById(id, null).getDespesas().get(0).getUsuario();
        if(findById(id, usuario) != null) relatorioRepository.deleteById(id);
    }
}
