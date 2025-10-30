package com.fatec.nexo.categoria;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.nexo.categoria.exceptions.CategoriaNotFoundException;
import com.fatec.nexo.usuario.UsuarioModel;

/**
 * Serviço responsável pelo gerenciamento de categorias
    * @author Otto Fidelis
    * @since 1.0
    * @version 1.3.1
 */
@Service
public class CategoriaService {
    /** Repositório de categorias 
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
    */
    private final CategoriaRepository categoriaRepository;

    /**
     * Construtor do serviço de categorias
     * @param categoriaRepository Repositório de categorias a ser injetado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    /**
     * Cria uma nova categoria no sistema
     * @param categoria O objeto categoria a ser criado
     * @return A categoria criada com o ID gerado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public CategoriaModel create(CategoriaModel categoria) {
        return categoriaRepository.save(categoria);
    }

    /**
     * Remove uma categoria do sistema pelo ID
     * 
     * @param id O ID da categoria a ser removida
     * @param usuario O usuário que deseja remover a categoria
     * @throws CategoriaNotFoundException se não encontrar a categoria com o ID especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.1
     */
    public void deleteCategoria(Integer id, UsuarioModel usuario) {
        if(findById(id, usuario)!=null) categoriaRepository.deleteById(id);
    }

    /**
     * Atualiza os dados de uma categoria existente
     * 
     * @param id O ID da categoria a ser atualizada
     * @param categoriaDetails O objeto com os novos dados da categoria
     * @param usuario O usuário quer atualizar a categoria
     * @return A categoria atualizada
     * @throws CategoriaNotFoundException se não encontrar a categoria com o ID especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public CategoriaModel update(Integer id, CategoriaModel categoriaDetails, UsuarioModel usuario) {
        CategoriaModel categoria = findById(id, usuario);
        categoria.setNome(categoriaDetails.getNome());
        categoria.setDescricao(categoriaDetails.getDescricao());
        return categoriaRepository.save(categoria);
    }

    /**
     * Encontra uma categoria pelo ID
     * @param id O ID da categoria a ser encontrada
     * @param usuario O usuário que está solicitando a categoria
     * @return A categoria encontrada
     * @throws CategoriaNotFoundException se não encontrar a categoria com o ID especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.1.1
     */
    public CategoriaModel findById(Integer id, UsuarioModel usuario) {
        if(usuario.getEmail().equals(categoriaRepository.findById(id).get().getUsuario().getEmail())){
            return categoriaRepository.findById(id)
            .orElseThrow(()-> new CategoriaNotFoundException(id));
        }
        throw new SecurityException("Acesso negado! Você não pode acessar a categoria de outro usuário.");
    }

    /**
     * Retorna todas as categorias cadastradas no sistema
     * @return Lista de todas as categorias
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public List<CategoriaModel> findAll() {
        return categoriaRepository.findAll();
    }
}
