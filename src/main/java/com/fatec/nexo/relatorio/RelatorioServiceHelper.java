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

@Component
public class RelatorioServiceHelper {
	private final DespesasService despesasService;
	private final ReceitasService receitasService;

	public RelatorioServiceHelper(DespesasService despesasService, ReceitasService receitasService) {
		this.despesasService = despesasService;
		this.receitasService = receitasService;
	}

	public List<DespesasModel> getDespesasMesAnterior(UsuarioModel usuario) {
		LocalDate dataRelatorio = LocalDate.now();
		LocalDate primeiroDiaMesAnterior = dataRelatorio.minusMonths(1).withDayOfMonth(1);
		LocalDate ultimoDiaMesAnterior = dataRelatorio.minusMonths(1).withDayOfMonth(dataRelatorio.minusMonths(1).lengthOfMonth());  
		return despesasService.findByDataBetween(primeiroDiaMesAnterior, ultimoDiaMesAnterior, usuario);
	}

	public List<ReceitasModel> getReceitasMesAnterior(UsuarioModel usuario) {
		LocalDate dataRelatorio = LocalDate.now();
		LocalDate primeiroDiaMesAnterior = dataRelatorio.minusMonths(1).withDayOfMonth(1);
		LocalDate ultimoDiaMesAnterior = dataRelatorio.minusMonths(1).withDayOfMonth(dataRelatorio.minusMonths(1).lengthOfMonth());  
		return receitasService.findByDataBetween(primeiroDiaMesAnterior, ultimoDiaMesAnterior, usuario);
	}

	public List<DespesasModel> getDespesasSemanaAnterior(UsuarioModel usuario) {
		LocalDate dataRelatorio = LocalDate.now();
		LocalDate primeiroDiaSemanaAnterior = dataRelatorio.minusWeeks(1).with(DayOfWeek.MONDAY);
		LocalDate ultimoDiaSemanaAnterior = dataRelatorio.minusWeeks(1).with(DayOfWeek.SUNDAY);  
		return despesasService.findByDataBetween(primeiroDiaSemanaAnterior, ultimoDiaSemanaAnterior, usuario);
	}

	public List<ReceitasModel> getReceitasSemanaAnterior(UsuarioModel usuario) {
		LocalDate dataRelatorio = LocalDate.now();
		LocalDate primeiroDiaSemanaAnterior = dataRelatorio.minusWeeks(1).with(DayOfWeek.MONDAY);
		LocalDate ultimoDiaSemanaAnterior = dataRelatorio.minusWeeks(1).with(DayOfWeek.SUNDAY);  
		return receitasService.findByDataBetween(primeiroDiaSemanaAnterior, ultimoDiaSemanaAnterior, usuario);
	}

	public List<DespesasModel> getDespesasAnoAnterior(UsuarioModel usuario) {
		LocalDate dataRelatorio = LocalDate.now();
		LocalDate primeiroDiaAnoAnterior = dataRelatorio.minusYears(1).withDayOfYear(1);
		LocalDate ultimoDiaAnoAnterior = dataRelatorio.minusYears(1).withDayOfYear(dataRelatorio.minusYears(1).lengthOfYear());  
		return despesasService.findByDataBetween(primeiroDiaAnoAnterior, ultimoDiaAnoAnterior, usuario);
	}

	public List<ReceitasModel> getReceitasAnoAnterior(UsuarioModel usuario) {
		LocalDate dataRelatorio = LocalDate.now();
		LocalDate primeiroDiaAnoAnterior = dataRelatorio.minusYears(1).withDayOfYear(1);
		LocalDate ultimoDiaAnoAnterior = dataRelatorio.minusYears(1).withDayOfYear(dataRelatorio.minusYears(1).lengthOfYear());  
		return receitasService.findByDataBetween(primeiroDiaAnoAnterior, ultimoDiaAnoAnterior, usuario);
	}

	public List<DespesasModel> getDespesasData(LocalDate start, LocalDate end, UsuarioModel usuario) {
		 if(start.isAfter(end)) {
            LocalDate temp = start;
            start = end;
            end = temp;
        }
		return despesasService.findByDataBetween(start, end, usuario);
	}

	public List<ReceitasModel> getReceitasData(LocalDate start, LocalDate end, UsuarioModel usuario) {
		 if(start.isAfter(end)) {
			LocalDate temp = start;
			start = end;
			end = temp;
		}
		return receitasService.findByDataBetween(start, end, usuario);
	}
}
