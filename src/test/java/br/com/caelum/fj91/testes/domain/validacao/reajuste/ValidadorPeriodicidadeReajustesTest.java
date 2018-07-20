package br.com.caelum.fj91.testes.domain.validacao.reajuste;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.caelum.fj91.testes.builders.FuncionarioBuilder;
import br.com.caelum.fj91.testes.builders.ReajusteBuilder;
import br.com.caelum.fj91.testes.domain.Funcionario;
import br.com.caelum.fj91.testes.domain.Reajuste;
import br.com.caelum.fj91.testes.exceptions.BusinessException;
import br.com.caelum.fj91.testes.repositories.ReajusteRepository;

public class ValidadorPeriodicidadeReajustesTest {

	private ValidadorPeriodicidadeReajustes validador;
	private ReajusteRepository repository;
	private ReajusteBuilder builder;
	private Funcionario joao;
	private Reajuste bonus;
	
	@Before
	public void setup() {
		this.repository = Mockito.mock(ReajusteRepository.class);
		this.validador = new ValidadorPeriodicidadeReajustes(repository);
		this.builder = new ReajusteBuilder();
		this.joao = new FuncionarioBuilder().build();
		this.bonus = builder.comFuncionario(joao).comData(LocalDate.now()).build();
	}

	@Test(expected = BusinessException.class)
	public void funcionarioNaoPodeReceberReajusteSeTiverRecebidoOutroHaMenosDe6Meses() {
		Reajuste doMesPassado = builder.comFuncionario(joao).comData(LocalDate.now().minusMonths(1)).build();
		Mockito.when(repository.findTopByFuncionarioOrderByIdDesc(joao)).thenReturn(doMesPassado);
		
		validador.valida(bonus);
	}
	
	@Test
	public void funcionarioPodeReceberReajusteSeTiverRecebidoOutroHaExatos6Meses() {
		Reajuste de6MesesAtras = builder.comFuncionario(joao).comData(LocalDate.now().minusMonths(6)).build();
		Mockito.when(repository.findTopByFuncionarioOrderByIdDesc(joao)).thenReturn(de6MesesAtras);
		
		validador.valida(bonus);
	}
	
	@Test
	public void funcionarioPodeReceberReajusteSeTiverRecebidoOutroHaMaisDe6Meses() {
		Reajuste de1AnoAtras = builder.comFuncionario(joao).comData(LocalDate.now().minusYears(1)).build();
		Mockito.when(repository.findTopByFuncionarioOrderByIdDesc(joao)).thenReturn(de1AnoAtras);
		
		validador.valida(bonus);
	}
	
	@Test
	public void funcionarioQueNuncaRecebeuReajustesPodeReceberPrimeiroReajuste() {
		Mockito.when(repository.findTopByFuncionarioOrderByIdDesc(joao)).thenReturn(null);

		validador.valida(bonus);
	}

}
