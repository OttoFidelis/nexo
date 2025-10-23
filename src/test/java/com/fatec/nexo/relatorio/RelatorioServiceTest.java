package com.fatec.nexo.relatorio;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fatec.nexo.despesas.DespesasModel;
import com.fatec.nexo.receitas.ReceitasModel;
import com.fatec.nexo.relatorio.exceptions.RelatorioNotFoundException;
import com.fatec.nexo.saldo.SaldoModel;
import com.fatec.nexo.saldo.SaldoService;
import com.fatec.nexo.usuario.UsuarioModel;

/**
 * Testes unitários para o serviço de Relatórios.
 *
 * @author Otto Fidelis
 * @since 1.0
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do RelatorioService")
class RelatorioServiceTest {

    @Mock
    private RelatorioRepository relatorioRepository;

    @Mock
    private SaldoService saldoService;

    @Mock
    private RelatorioServiceHelper relatorioServiceHelper;

    @InjectMocks
    private RelatorioService relatorioService;

    private UsuarioModel usuario;
    private DespesasModel despesa;
    private ReceitasModel receita;
    private RelatorioModel relatorioValido;
    private SaldoModel saldo;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioModel();
        usuario.setNome("João Silva");
        usuario.setEmail("joao@example.com");
        usuario.setSenha("senha123");

        despesa = new DespesasModel();
        despesa.setDescricao("Almoço");
        despesa.setQuantia(50.00);
        despesa.setData(LocalDate.now().minusDays(5));
        despesa.setUsuario(usuario);

        receita = new ReceitasModel();
        receita.setDescricao("Freelance");
        receita.setQuantia(1000.00);
        receita.setData(LocalDate.now().minusDays(3));
        receita.setUsuario(usuario);

        saldo = new SaldoModel();
        saldo.setQuantia(5000.00);
        saldo.setUsuario(usuario);

        relatorioValido = new RelatorioModel(
                Arrays.asList(despesa),
                Arrays.asList(receita),
                5000.00);
        relatorioValido.setId(1);
        relatorioValido.setTipo("Mensal");
    }

    @Test
    @DisplayName("Deve criar relatório mensal com sucesso")
    void deveCriarRelatorioMensal() {
        // Arrange
        when(relatorioServiceHelper.getDespesasMesAnterior(usuario))
                .thenReturn(Arrays.asList(despesa));
        when(relatorioServiceHelper.getReceitasMesAnterior(usuario))
                .thenReturn(Arrays.asList(receita));
        when(saldoService.findLastSaldo(usuario)).thenReturn(saldo);
        when(relatorioRepository.save(any(RelatorioModel.class))).thenReturn(relatorioValido);

        // Act
        RelatorioModel resultado = relatorioService.createMensal(usuario);

        // Assert
        assertNotNull(resultado);
        assertEquals("Mensal", resultado.getTipo());
        verify(relatorioRepository).save(any(RelatorioModel.class));
        verify(saldoService).findLastSaldo(usuario);
    }

    @Test
    @DisplayName("Deve criar relatório semanal com sucesso")
    void deveCriarRelatorioSemanal() {
        // Arrange
        when(relatorioServiceHelper.getDespesasSemanaAnterior(usuario))
                .thenReturn(Arrays.asList(despesa));
        when(relatorioServiceHelper.getReceitasSemanaAnterior(usuario))
                .thenReturn(Arrays.asList(receita));
        when(saldoService.findLastSaldo(usuario)).thenReturn(saldo);
        when(relatorioRepository.save(any(RelatorioModel.class))).thenReturn(relatorioValido);

        // Act
        RelatorioModel resultado = relatorioService.createSemanal(usuario);

        // Assert
        assertNotNull(resultado);
        verify(relatorioRepository).save(any(RelatorioModel.class));
    }

    @Test
    @DisplayName("Deve criar relatório anual com sucesso")
    void deveCriarRelatorioAnual() {
        // Arrange
        when(relatorioServiceHelper.getDespesasAnoAnterior(usuario))
                .thenReturn(Arrays.asList(despesa));
        when(relatorioServiceHelper.getReceitasAnoAnterior(usuario))
                .thenReturn(Arrays.asList(receita));
        when(saldoService.findLastSaldo(usuario)).thenReturn(saldo);
        when(relatorioRepository.save(any(RelatorioModel.class))).thenReturn(relatorioValido);

        // Act
        RelatorioModel resultado = relatorioService.createAnual(usuario);

        // Assert
        assertNotNull(resultado);
        verify(relatorioRepository).save(any(RelatorioModel.class));
    }

    @Test
    @DisplayName("Deve criar relatório personalizado com período específico")
    void deveCriarRelatorioPersonalizado() {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 10, 1);
        LocalDate fim = LocalDate.of(2025, 10, 31);

        when(relatorioServiceHelper.getDespesasData(inicio, fim, usuario))
                .thenReturn(Arrays.asList(despesa));
        when(relatorioServiceHelper.getReceitasData(inicio, fim, usuario))
                .thenReturn(Arrays.asList(receita));
        when(saldoService.findLastSaldo(usuario)).thenReturn(saldo);
        when(relatorioRepository.save(any(RelatorioModel.class))).thenReturn(relatorioValido);

        // Act
        RelatorioModel resultado = relatorioService.createPersonalizado(usuario, inicio, fim);

        // Assert
        assertNotNull(resultado);
        verify(relatorioRepository).save(any(RelatorioModel.class));
        verify(relatorioServiceHelper).getDespesasData(inicio, fim, usuario);
        verify(relatorioServiceHelper).getReceitasData(inicio, fim, usuario);
    }

    @Test
    @DisplayName("Deve buscar relatório por ID")
    void deveBuscarRelatorioPorId() {
        // Arrange
        when(relatorioRepository.findById(1)).thenReturn(Optional.of(relatorioValido));

        // Act
        RelatorioModel resultado = relatorioService.findById(1, usuario);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        assertEquals("Mensal", resultado.getTipo());
    }

    @Test
    @DisplayName("Deve lançar exceção ao buscar relatório inexistente")
    void deveLancarExcecaoAoBuscarRelatorioInexistente() {
        // Arrange
        when(relatorioRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RelatorioNotFoundException.class, () -> {
            relatorioService.findById(999, usuario);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção ao acessar relatório de outro usuário")
    void deveLancarExcecaoAoAcessarRelatorioDeOutroUsuario() {
        // Arrange
        UsuarioModel outroUsuario = new UsuarioModel();
        outroUsuario.setEmail("outro@example.com");

        when(relatorioRepository.findById(1)).thenReturn(Optional.of(relatorioValido));

        // Act & Assert
        assertThrows(SecurityException.class, () -> {
            relatorioService.findById(1, outroUsuario);
        });
    }

    @Test
    @DisplayName("Deve atualizar formato do relatório")
    void deveAtualizarFormatoRelatorio() {
        // Arrange
        RelatorioModel relatorioAtualizado = new RelatorioModel(
                Arrays.asList(despesa),
                Arrays.asList(receita),
                5000.00);
        relatorioAtualizado.setFormato("Pizza");

        when(relatorioRepository.findById(1)).thenReturn(Optional.of(relatorioValido));
        when(relatorioRepository.save(any(RelatorioModel.class))).thenReturn(relatorioValido);

        // Act
        RelatorioModel resultado = relatorioService.update(1, relatorioAtualizado, usuario);

        // Assert
        assertNotNull(resultado);
        verify(relatorioRepository).save(any(RelatorioModel.class));
    }

    @Test
    @DisplayName("Deve listar todos os relatórios")
    void deveListarTodosRelatorios() {
        // Arrange
        RelatorioModel relatorio2 = new RelatorioModel(
                Arrays.asList(despesa),
                Arrays.asList(receita),
                4500.00);
        relatorio2.setTipo("Semanal");

        List<RelatorioModel> relatorios = Arrays.asList(relatorioValido, relatorio2);
        when(relatorioRepository.findAll()).thenReturn(relatorios);

        // Act
        List<RelatorioModel> resultado = relatorioService.findAll();

        // Assert
        assertEquals(2, resultado.size());
        assertTrue(resultado.stream().anyMatch(r -> r.getTipo().equals("Mensal")));
        assertTrue(resultado.stream().anyMatch(r -> r.getTipo().equals("Semanal")));
    }

    @Test
    @DisplayName("Deve deletar relatório existente")
    void deveDeletarRelatorio() {
        usuario = new UsuarioModel();
        usuario.setNome("João Silva");
        usuario.setEmail("joao@example.com");
        usuario.setSenha("senha123");
        // Arrange
        when(relatorioRepository.findById(1)).thenReturn(Optional.of(relatorioValido));
        doNothing().when(relatorioRepository).deleteById(1);

        // Act & Assert
        assertDoesNotThrow(() -> relatorioService.delete(1, usuario));
        verify(relatorioRepository).deleteById(1);
    }

    @Test
    @DisplayName("Deve calcular totais corretamente no relatório")
    void deveCalcularTotaisCorretamente() {
        // Arrange
        DespesasModel despesa2 = new DespesasModel();
        despesa2.setQuantia(150.00);
        despesa2.setUsuario(usuario);

        ReceitasModel receita2 = new ReceitasModel();
        receita2.setQuantia(2000.00);
        receita2.setUsuario(usuario);

        when(relatorioServiceHelper.getDespesasMesAnterior(usuario))
                .thenReturn(Arrays.asList(despesa, despesa2));
        when(relatorioServiceHelper.getReceitasMesAnterior(usuario))
                .thenReturn(Arrays.asList(receita, receita2));
        when(saldoService.findLastSaldo(usuario)).thenReturn(saldo);
        when(relatorioRepository.save(any(RelatorioModel.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        RelatorioModel resultado = relatorioService.createMensal(usuario);

        
        // Assert
        assertNotNull(resultado);
        assertEquals("Mensal", resultado.getTipo());
        verify(relatorioServiceHelper).getDespesasMesAnterior(usuario);
        verify(relatorioServiceHelper).getReceitasMesAnterior(usuario);
    }
}
