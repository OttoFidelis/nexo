package com.fatec.nexo.categoria;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fatec.nexo.categoria.exceptions.CategoriaNotFoundException;

/**
 * Serviço responsável pelo gerenciamento de categorias
    * @author Otto Fidelis
    * @since 1.0
    * @version 1.0
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
     * @throws CategoriaNotFoundException se não encontrar a categoria com o ID especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public void deleteCategoria(Integer id) {
        if(categoriaRepository.existsById(id)) categoriaRepository.deleteById(id);
        else throw new CategoriaNotFoundException(id);
    }

    /**
     * Atualiza os dados de uma categoria existente
     * 
     * @param id O ID da categoria a ser atualizada
     * @param categoriaDetails O objeto com os novos dados da categoria
     * @return A categoria atualizada
     * @throws CategoriaNotFoundException se não encontrar a categoria com o ID especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public CategoriaModel update(Integer id, CategoriaModel categoriaDetails) {
        CategoriaModel categoria = findById(id);
        categoria.setNome(categoriaDetails.getNome());
        categoria.setDescricao(categoriaDetails.getDescricao());
        return categoriaRepository.save(categoria);
    }

    /**
     * Encontra uma categoria pelo ID
     * @param id O ID da categoria a ser encontrada
     * @return A categoria encontrada
     * @throws CategoriaNotFoundException se não encontrar a categoria com o ID especificado
     * @author Otto Fidelis
     * @since 1.0
     * @version 1.0
     */
    public CategoriaModel findById(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
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
