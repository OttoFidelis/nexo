package com.fatec.nexo.relatorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositório para operações CRUD na entidade Relatório
    * @author Otto Fidelis
    * @since 1.0
    * @version 1.0
 */
@Repository
public interface RelatorioRepository extends JpaRepository<RelatorioModel, Integer> {
}
