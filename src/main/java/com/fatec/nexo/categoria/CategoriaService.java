package com.fatec.nexo.categoria;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public CategoriaModel create(CategoriaModel categoria) {
        return categoriaRepository.save(categoria);
    }

    public void deleteCategoria(Integer id) {
        if(categoriaRepository.existsById(id)) categoriaRepository.deleteById(id);
        else throw new RuntimeException("Categoria não encontrada! Id inválido " + id);
    }

    public CategoriaModel update(Integer id, CategoriaModel categoriaDetails) {
        CategoriaModel categoria = findById(id);
        categoria.setNome(categoriaDetails.getNome());
        categoria.setDescricao(categoriaDetails.getDescricao());
        return categoriaRepository.save(categoria);
    }

    public CategoriaModel findById(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada! Id inválido " + id));
    }

    public List<CategoriaModel> findAll() {
        return categoriaRepository.findAll();
    }
}
