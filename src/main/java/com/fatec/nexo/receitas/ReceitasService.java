package com.fatec.nexo.receitas;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.fatec.nexo.receitas.exceptions.ReceitasNotFoundException;
import com.fatec.nexo.saldo.SaldoService;
import com.fatec.nexo.usuario.UsuarioModel;

/**
 * Serviço responsável pelo gerenciamento de receitas
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
@Service
public class ReceitasService {
    /**
     * Repositório de receitas injetado no serviço
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private final ReceitasRepository receitasRepository;

    /**
     * Serviço de saldo injetado no serviço de receitas
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    private final SaldoService saldoService;

    /**
     * Construtor do serviço de receitas
     * @param receitasRepository Repositório de receitas a ser injetado
     * @param saldoService Serviço de saldo a ser injetado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public ReceitasService(ReceitasRepository receitasRepository, SaldoService saldoService) {
        this.receitasRepository = receitasRepository;
        this.saldoService = saldoService;
    }

    /**
     * Cria uma nova receita no sistema
     * @param receita O objeto receita a ser criado
     * @return A receita criada com o ID gerado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public ReceitasModel create(ReceitasModel receita) {
        saldoService.create(receita.getUsuario(), receita);
        return receitasRepository.save(receita);
    }

    /**
     * Retorna todas as receitas cadastradas no sistema
     * @return Lista de todas as receitas
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public List<ReceitasModel> findAll() {
        return receitasRepository.findAll();
    }

    /**
     * Busca receitas associadas a um usuário específico
     * @param usuario Usuário cujas receitas serão buscadas
     * @return Lista de receitas do usuário especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public List<ReceitasModel> findByUsuario(UsuarioModel usuario) {
        return receitasRepository.findByUsuario(usuario);
    }

    /**
     * Busca receitas associadas ao email de um usuário específico
     * @param email Email do usuário cujas receitas serão buscadas
     * @return Lista de receitas do usuário especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public List<ReceitasModel> findByUsuario(String email) {
        return receitasRepository.findByUsuarioEmail(email);
    }

    /**
     * Busca uma receita pelo ID
     * @param id O ID da receita a ser buscada
     * @param usuario O usuário que está solicitando a receita
     * @return A receita encontrada
     * @throws ReceitasNotFoundException se não encontrar a receita com o ID especificado
     * @throws SecurityException se o usuário não tiver permissão para acessar a receita, ou seja, se a receita não pertencer ao usuário
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
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

    /**
     * Atualiza os dados de uma receita existente
     * @param id O ID da receita a ser atualizada
     * @param receita O objeto com os novos dados da receita
     * @param usuario O usuário que está solicitando a atualização
     * @return A receita atualizada
     * @throws ReceitasNotFoundException se não encontrar a receita com o ID especificado
     * @throws SecurityException se o usuário não tiver permissão para atualizar a receita, ou seja, se a receita não pertencer ao usuário
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public ReceitasModel update(Integer id, ReceitasModel receita, UsuarioModel usuario) {
        ReceitasModel _receita = findById(id, usuario);
        saldoService.update(_receita.getUsuario(), receita, _receita.getQuantia());
        _receita.setDescricao(receita.getDescricao());
        _receita.setQuantia(receita.getQuantia());
        return receitasRepository.save(_receita);
    }

    /**
     * Remove uma receita do sistema pelo ID
     * @param id O ID da receita a ser removida
     * @param usuario O usuário que está solicitando a remoção
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public void delete(Integer id, UsuarioModel usuario) {
        if(findById(id, usuario).getUsuario()!=null)
        receitasRepository.deleteById(id);
    }

    /**
     * Busca receitas dentro de um intervalo de datas para um usuário específico
     * @param start Data inicial do intervalo
     * @param end Data final do intervalo
     * @param usuario Usuário associado às receitas
     * @return Lista de receitas dentro do intervalo especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public List<ReceitasModel> findByDataBetween(LocalDate start, LocalDate end, UsuarioModel usuario) {
        return receitasRepository.findByDataBetween(start, end, usuario);
    }
}
