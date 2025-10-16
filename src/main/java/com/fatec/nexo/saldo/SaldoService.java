package com.fatec.nexo.saldo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.nexo.despesas.DespesasModel;
import com.fatec.nexo.receitas.ReceitasModel;
import com.fatec.nexo.saldo.exceptions.SaldoNotFoundException;
import com.fatec.nexo.saldo.exceptions.SaldosNotFoundException;
import com.fatec.nexo.usuario.UsuarioModel;

/**
    * Serviço para gerenciar operações relacionadas a saldos.
    * @author Otto Fidelis
    * @since 1.0
    * @version 1.0.1
 */
@Service
public class SaldoService {
    /** Repositório de saldos 
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
    */
    private final SaldoRepository saldoRepository;

    /**
     * Construtor do serviço de saldo
     * @param saldoRepository Repositório de saldos a ser injetado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public SaldoService(SaldoRepository saldoRepository) {
        this.saldoRepository = saldoRepository;
    }

    /**
     * Cria um novo saldo quando o usuário cria uma nova receita ou despesa
     * @param usuario usuario que irá criar a receita ou despesa do saldo
     * @param receita receita que o usuário está criando
     * @return saldo criado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public SaldoModel create(UsuarioModel usuario, ReceitasModel receita) {
        SaldoModel saldo = findLastSaldo(usuario);
        BigDecimal quantia = BigDecimal.valueOf(saldo.getQuantia()).add(saldo.calcularQuantiaReceitas(receita));
        saldo.setQuantia(quantia.doubleValue());
        saldo.setUsuario(usuario);
        return saldoRepository.save(saldo);
    }

    /**
     * Cria um novo saldo quando o usuário cria uma nova receita ou despesa
     * @param usuario usuario que irá criar a receita ou despesa do saldo
     * @param despesa despesa que o usuário está criando
     * @return saldo criado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public SaldoModel create(UsuarioModel usuario, DespesasModel despesa) {
        SaldoModel saldo = findLastSaldo(usuario);
        BigDecimal quantia = BigDecimal.valueOf(saldo.getQuantia()).subtract(saldo.calcularQuantiaDespesas(despesa));
        saldo.setQuantia(quantia.doubleValue());
        saldo.setUsuario(usuario);
        return saldoRepository.save(saldo);
    }

    /**
     * Cria um novo saldo quando o usuário já possui receitas e despesas
     * @param usuario usuario que irá criar as receitas e despesas do saldo
     * @param receitas lista de receitas que o usuário está criando
     * @param despesas lista de despesas que o usuário está criando
     * @return saldo criado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public SaldoModel create(UsuarioModel usuario, List<ReceitasModel> receitas, List<DespesasModel> despesas) {
        SaldoModel saldo = new SaldoModel();
        BigDecimal quantia = saldo.calcularQuantiaReceitas(receitas).subtract(saldo.calcularQuantiaDespesas(despesas));
        saldo.setQuantia(quantia.doubleValue());
        saldo.setUsuario(usuario);
        return saldoRepository.save(saldo);
    }

    /**
     * Busca todos os saldos de um usuário específico
     * @param usuario O usuário cujos saldos serão buscados
     * @return Lista de saldos do usuário
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public List<SaldoModel> findByUsuario(UsuarioModel usuario) {
        return saldoRepository.findByUsuario(usuario);
    }

    /**
     * Busca todos os saldos de um usuário específico pelo email
     * @param email O email do usuário cujos saldos serão buscados
     * @return Lista de saldos do usuário
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public List<SaldoModel> findByUsuario(String email) {
        return saldoRepository.findByUsuarioEmail(email);
    }

    /**
     * Atualiza um saldo quando o usuário atualiza uma receita
     * @param usuario  usuario que irá atualizar a receita do saldo
     * @param receitaNova receita que o usuário está atualizando
     * @param quantiaAntiga quantia da receita antiga
     * @return saldo atualizado
     * @throws SecurityException caso o usuário que está tentando atualizar a receita não seja o dono da mesma
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public SaldoModel update(UsuarioModel usuario, ReceitasModel receitaNova, double quantiaAntiga) {
        if(!usuario.getEmail().equals(receitaNova.getUsuario().getEmail())) {
            throw new SecurityException("Acesso negado! Você não pode atualizar o saldo de outro usuário.");
        }
        SaldoModel saldo = findLastSaldo(usuario);
        BigDecimal quantia = BigDecimal.valueOf(saldo.getQuantia()).add(BigDecimal.valueOf(receitaNova.getQuantia())).subtract(BigDecimal.valueOf(quantiaAntiga));
        saldo.setQuantia(quantia.doubleValue());
        return saldoRepository.save(saldo);
    }

    /**
     * Atualiza um saldo quando o usuário atualiza uma despesa
     * @param usuario usuario que irá atualizar a despesa do saldo
     * @param despesaNova despesa que o usuário está atualizando
     * @param quantiaAntiga quantia da despesa antiga
     * @return saldo atualizado
     * @throws SecurityException caso o usuário que está tentando atualizar a despesa não seja o dono da mesma
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0.1
     */
    public SaldoModel update(UsuarioModel usuario, DespesasModel despesaNova, double quantiaAntiga) {
        if(!usuario.getEmail().equals(despesaNova.getUsuario().getEmail())) {
            throw new SecurityException("Acesso negado! Você não pode atualizar o saldo de outro usuário.");
        }
        SaldoModel saldo = findLastSaldo(usuario);
        BigDecimal quantia = BigDecimal.valueOf(saldo.getQuantia()).subtract(BigDecimal.valueOf(despesaNova.getQuantia())).add(BigDecimal.valueOf(quantiaAntiga));
        return update(usuario, quantia);
    }

    /**
     * Atualiza um saldo diretamente com uma nova quantia
     * @param usuario usuario que irá atualizar o saldo
     * @param quantia nova quantia do saldo
     * @return saldo atualizado
     * @throws SecurityException caso o usuário que está tentando atualizar o saldo não seja o dono do mesmo
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public SaldoModel update(UsuarioModel usuario, BigDecimal quantia) {
        SaldoModel saldo = findLastSaldo(usuario);
        saldo.setQuantia(quantia.doubleValue());
        return saldoRepository.save(saldo);
    }

    /**
     * Busca todos os saldos cadastrados no sistema
     * @return Lista de todos os saldos
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public List<SaldoModel> findAll(){
        return saldoRepository.findAll();
    }

    /**
     * Busca um saldo pelo ID
     * @param id O ID do saldo a ser buscado
     * @param usuario O usuário que está solicitando o saldo
     * @return O saldo encontrado
     * @throws SaldoNotFoundException se não encontrar o saldo com o ID especificado
     * @throws SecurityException se o usuário não tiver permissão para acessar o saldo, ou seja, se o saldo não pertencer ao usuário
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public SaldoModel findById(Integer id, UsuarioModel usuario){
        if(saldoRepository.findById(id).isEmpty()){
            throw new SaldoNotFoundException(id);
        }
        if(!saldoRepository.findById(id).get().getUsuario().getEmail().equals(usuario.getEmail())) {
            throw new SecurityException("Acesso negado! Você não pode acessar o saldo de outro usuário.");
        }
        return saldoRepository.findById(id)
            .orElseThrow(() -> new SaldoNotFoundException(id));
    }

    /**
     * <p>Remove um saldo do sistema pelo ID</p>
     * <strong>CUIDADO: </strong>
     * <p>Isso é uma ação permamente e não pode ser desfeita.</p>
     * <p>Ao deletar uma entidade, não se esqueça de verificar se ela não irá deixar outras entidades órfãs</p>
     * @param id O ID do saldo a ser removido
     * @param usuario O usuário que está solicitando a remoção do saldo
     * @throws SaldoNotFoundException se não encontrar o saldo com o ID especificado
     * @throws SecurityException se o usuário não tiver permissão para remover o saldo, ou seja, se o saldo não pertencer ao usuário
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public void delete(Integer id, UsuarioModel usuario){
        findById(id, usuario);
        if(saldoRepository.existsById(id)) saldoRepository.deleteById(id);
        else throw new SaldoNotFoundException(id);
    }

    /**
     * Busca o último saldo de um usuário específico
     * @param email O email do usuário cujo último saldo será buscado
     * @return O último saldo do usuário
     * @throws SaldosNotFoundException se não encontrar nenhum saldo para o usuário com o email especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public SaldoModel findLastSaldo(String email){
        List<SaldoModel> saldos = findByUsuario(email);
        for(int i = 0; i < saldos.size(); i++){
            if(i == saldos.size()-1){
                return saldos.get(i);
            }
        }
        throw new SaldosNotFoundException(email);
    }

    /**
     * Busca o último saldo de um usuário específico
     * @param usuario O usuário cujo último saldo será buscado
     * @return O último saldo do usuário
     * @throws SaldosNotFoundException se não encontrar nenhum saldo para o usuário especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public SaldoModel findLastSaldo(UsuarioModel usuario){
        List<SaldoModel> saldos = findByUsuario(usuario);
        for(int i = 0; i < saldos.size(); i++){
            if(i == saldos.size()-1){
                return saldos.get(i);
            }
        }
        throw new SaldosNotFoundException(usuario.getEmail());
    }
}
