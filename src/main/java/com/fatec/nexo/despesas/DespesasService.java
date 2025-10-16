package com.fatec.nexo.despesas;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.nexo.saldo.SaldoService;
import com.fatec.nexo.usuario.UsuarioModel;

/**
 * Serviço responsável pelo gerenciamento de despesas
    * @author Otto Fidelis
    * @since 1.0
    * @version 1.0
 */
@Service
public class DespesasService {

    /**
     * Repositório de despesas injetado no serviço
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private final DespesasRepository despesasRepository;

    /**
     * Serviço de saldo injetado no serviço de despesas
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private final SaldoService saldoService;

    /**
     * Construtor do serviço de despesas
     * @param despesasRepository Repositório de despesas a ser injetado
     * @param saldoService Serviço de saldo a ser injetado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public DespesasService(DespesasRepository despesasRepository, SaldoService saldoService) {
        this.despesasRepository = despesasRepository;
        this.saldoService = saldoService;
    }

    /**
     * Cria uma nova despesa no sistema
     * @param despesa O objeto despesa a ser criado
     * @return A despesa criada com o ID gerado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public DespesasModel create(DespesasModel despesa) {
        saldoService.create(despesa.getUsuario(), despesa);
        return despesasRepository.save(despesa);
    }

    /**
     * Busca uma despesa pelo ID
     * @param id O ID da despesa a ser buscada
     * @param usuario O usuário que está solicitando a despesa
     * @return A despesa encontrada
     * @throws DespesaNotFoundException se não encontrar a despesa com o ID especificado
     * @throws SecurityException se o usuário não tiver permissão para acessar a despesa, ou seja, se a despesa não pertencer ao usuário
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public DespesasModel findById(Integer id, UsuarioModel usuario) {
            if(despesasRepository.findById(id).isPresent()){
                if(despesasRepository.findById(id).get().getUsuario().getEmail().equals(usuario.getEmail())) return despesasRepository.findById(id).get();
                else throw new SecurityException("Acesso negado para a despesa com id " + id);
            }
            else throw new DespesaNotFoundException(id);
    }

    /**
     * Busca despesas associadas a um usuário específico
     * @param usuario Usuário cujas despesas serão buscadas
     * @return Lista de despesas do usuário especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public List<DespesasModel> findByUsuario(UsuarioModel usuario) {
        return despesasRepository.findByUsuario(usuario);
    }

    /**
     * Busca despesas associadas ao email de um usuário específico
     * @param email Email do usuário cujas despesas serão buscadas
     * @return Lista de despesas do usuário especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public List<DespesasModel> findByUsuario(String email) {
        return despesasRepository.findByUsuarioEmail(email);
    }

    /**
     * Atualiza os dados de uma despesa existente
     * @param id O ID da despesa a ser atualizada
     * @param despesa O objeto com os novos dados da despesa
     * @param usuario O usuário que está solicitando a atualização
     * @return A despesa atualizada
     * @throws DespesaNotFoundException se não encontrar a despesa com o ID especificado
     * @throws SecurityException se o usuário não tiver permissão para atualizar a despesa, ou seja, se a despesa não pertencer ao usuário
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.1
     */
    public DespesasModel update(Integer id, DespesasModel despesa, UsuarioModel usuario) {
        DespesasModel _despesa = findById(id, usuario);
        saldoService.update(_despesa.getUsuario(), despesa, _despesa.getQuantia());
        _despesa.setDescricao(despesa.getDescricao());
        _despesa.setQuantia(despesa.getQuantia());
        return despesasRepository.save(_despesa);
    }

    /**
     * Remove uma despesa do sistema pelo ID
     * @param id O ID da despesa a ser removida
     * @param usuario O usuário que está solicitando a remoção
     * @throws RuntimeException se não encontrar a despesa com o ID especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public void delete(Integer id, UsuarioModel usuario) {
        findById(id, usuario);
        if(despesasRepository.existsById(id)) despesasRepository.deleteById(id);
        else throw new RuntimeException("Despesa não encontrada! Id inválido " + id);
    }

    /**
     * Retorna todas as despesas cadastradas no sistema
     * @return Lista de todas as despesas
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public List<DespesasModel> findAll() {
        return despesasRepository.findAll();
    }

    /**
     * Busca despesas dentro de um intervalo de datas para um usuário específico
     * @param start Data inicial do intervalo
     * @param end Data final do intervalo
     * @param usuario Usuário associado às despesas
     * @return Lista de despesas dentro do intervalo especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public List<DespesasModel> findByDataBetween(LocalDate start, LocalDate end, UsuarioModel usuario) {
        return despesasRepository.findByDataBetween(start, end, usuario);
    }
}
