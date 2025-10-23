package com.fatec.nexo.relatorio;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fatec.nexo.despesas.DespesasModel;
import com.fatec.nexo.despesas.DespesasService;
import com.fatec.nexo.receitas.ReceitasModel;
import com.fatec.nexo.receitas.ReceitasService;
import com.fatec.nexo.usuario.UsuarioModel;

/**
 * Helper do serviço de relatórios para operações auxiliares
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
@Component
public class RelatorioServiceHelper {
	/**
	 * Serviço de despesas injetado no helper
	 * @author Otto Fidelis
	 * @since 1.0
	 * @version 1.0
	 */
	private final DespesasService despesasService;
	/**
	 * Serviço de receitas injetado no helper
	 * @author Otto Fidelis
	 * @since 1.0
	 * @version 1.0
	 */
	private final ReceitasService receitasService;

	/**
	 * Construtor do helper de relatórios
	 * @param despesasService Serviço de despesas a ser injetado
	 * @param receitasService Serviço de receitas a ser injetado
	 * @author Otto Fidelis
	 * @since 1.0
	 * @version 1.0
	 */
	public RelatorioServiceHelper(DespesasService despesasService, ReceitasService receitasService) {
		this.despesasService = despesasService;
		this.receitasService = receitasService;
	}

	/**
	 * Método auxiliar para obter despesas do mês anterior de um usuário
	 * @author Otto Fidelis
	 * @param usuario O usuário cujas despesas serão consultadas
	 * @since 1.0
	 * @version 1.0
	 */
	public List<DespesasModel> getDespesasMesAnterior(UsuarioModel usuario) {
		LocalDate dataRelatorio = LocalDate.now();
		LocalDate primeiroDiaMesAnterior = dataRelatorio.minusMonths(1).withDayOfMonth(1);
		LocalDate ultimoDiaMesAnterior = dataRelatorio.minusMonths(1).withDayOfMonth(dataRelatorio.minusMonths(1).lengthOfMonth());  
		return despesasService.findByDataBetween(primeiroDiaMesAnterior, ultimoDiaMesAnterior, usuario);
	}

	/**
	 * Método auxiliar para obter receitas do mês anterior de um usuário
	 * @author Otto Fidelis
	 * @param usuario O usuário cujas receitas serão consultadas
	 * @since 1.0
	 * @version 1.0
	 */
	public List<ReceitasModel> getReceitasMesAnterior(UsuarioModel usuario) {
		LocalDate dataRelatorio = LocalDate.now();
		LocalDate primeiroDiaMesAnterior = dataRelatorio.minusMonths(1).withDayOfMonth(1);
		LocalDate ultimoDiaMesAnterior = dataRelatorio.minusMonths(1).withDayOfMonth(dataRelatorio.minusMonths(1).lengthOfMonth());  
		return receitasService.findByDataBetween(primeiroDiaMesAnterior, ultimoDiaMesAnterior, usuario);
	}

	/**
	 * Método auxiliar para obter despesas da semana anterior de um usuário
	 * @author Otto Fidelis
	 * @param usuario O usuário cujas despesas serão consultadas
	 * @since 1.0
	 * @version 1.0
	 */
	public List<DespesasModel> getDespesasSemanaAnterior(UsuarioModel usuario) {
		LocalDate dataRelatorio = LocalDate.now();
		LocalDate primeiroDiaSemanaAnterior = dataRelatorio.minusWeeks(1).with(DayOfWeek.MONDAY);
		LocalDate ultimoDiaSemanaAnterior = dataRelatorio.minusWeeks(1).with(DayOfWeek.SUNDAY);  
		return despesasService.findByDataBetween(primeiroDiaSemanaAnterior, ultimoDiaSemanaAnterior, usuario);
	}

	/**
	 * Método auxiliar para obter receitas da semana anterior de um usuário
	 * @author Otto Fidelis
	 * @param usuario O usuário cujas receitas serão consultadas
	 * @since 1.0
	 * @version 1.0
	 */
	public List<ReceitasModel> getReceitasSemanaAnterior(UsuarioModel usuario) {
		LocalDate dataRelatorio = LocalDate.now();
		LocalDate primeiroDiaSemanaAnterior = dataRelatorio.minusWeeks(1).with(DayOfWeek.MONDAY);
		LocalDate ultimoDiaSemanaAnterior = dataRelatorio.minusWeeks(1).with(DayOfWeek.SUNDAY);  
		return receitasService.findByDataBetween(primeiroDiaSemanaAnterior, ultimoDiaSemanaAnterior, usuario);
	}

	/**
	 * Método auxiliar para obter despesas do ano anterior de um usuário
	 * @author Otto Fidelis
	 * @param usuario O usuário cujas despesas serão consultadas
	 * @since 1.0
	 * @version 1.0
	 */
	public List<DespesasModel> getDespesasAnoAnterior(UsuarioModel usuario) {
		LocalDate dataRelatorio = LocalDate.now();
		LocalDate primeiroDiaAnoAnterior = dataRelatorio.minusYears(1).withDayOfYear(1);
		LocalDate ultimoDiaAnoAnterior = dataRelatorio.minusYears(1).withDayOfYear(dataRelatorio.minusYears(1).lengthOfYear());  
		return despesasService.findByDataBetween(primeiroDiaAnoAnterior, ultimoDiaAnoAnterior, usuario);
	}

	/**
	 * Método auxiliar para obter receitas do ano anterior de um usuário
	 * @author Otto Fidelis
	 * @param usuario O usuário cujas receitas serão consultadas
	 * @since 1.0
	 * @version 1.0
	 */
	public List<ReceitasModel> getReceitasAnoAnterior(UsuarioModel usuario) {
		LocalDate dataRelatorio = LocalDate.now();
		LocalDate primeiroDiaAnoAnterior = dataRelatorio.minusYears(1).withDayOfYear(1);
		LocalDate ultimoDiaAnoAnterior = dataRelatorio.minusYears(1).withDayOfYear(dataRelatorio.minusYears(1).lengthOfYear());  
		return receitasService.findByDataBetween(primeiroDiaAnoAnterior, ultimoDiaAnoAnterior, usuario);
	}

	/**
	 * Método auxiliar para obter despesas em um intervalo de datas para um usuário
	 * @author Otto Fidelis
	 * @param start Data inicial do período
	 * @param end Data final do período
	 * @param usuario O usuário cujas despesas serão consultadas
	 * @since 1.0
	 * @version 1.0
	 */
	public List<DespesasModel> getDespesasData(LocalDate start, LocalDate end, UsuarioModel usuario) {
		 if(start.isAfter(end)) {
            LocalDate temp = start;
            start = end;
            end = temp;
        }
		return despesasService.findByDataBetween(start, end, usuario);
	}

	/**
	 * Método auxiliar para obter receitas em um intervalo de datas para um usuário
	 * @author Otto Fidelis
	 * @param start Data inicial do período
	 * @param end Data final do período
	 * @param usuario O usuário cujas receitas serão consultadas
	 * @since 1.0
	 * @version 1.0
	 */
	public List<ReceitasModel> getReceitasData(LocalDate start, LocalDate end, UsuarioModel usuario) {
		 if(start.isAfter(end)) {
			LocalDate temp = start;
			start = end;
			end = temp;
		}
		return receitasService.findByDataBetween(start, end, usuario);
	}
}
