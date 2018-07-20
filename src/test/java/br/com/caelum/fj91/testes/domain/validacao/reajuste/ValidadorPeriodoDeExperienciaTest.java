package br.com.caelum.fj91.testes.domain.validacao.reajuste;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.fj91.testes.builders.FuncionarioBuilder;
import br.com.caelum.fj91.testes.builders.ReajusteBuilder;
import br.com.caelum.fj91.testes.domain.Funcionario;
import br.com.caelum.fj91.testes.domain.Reajuste;
import br.com.caelum.fj91.testes.exceptions.BusinessException;

public class ValidadorPeriodoDeExperienciaTest {
	
	private ValidadorPeriodoDeExperiencia validador;
	
	@Before
	public void setup() {
		this.validador = new ValidadorPeriodoDeExperiencia();
	}
	
	private Reajuste criaReajuste(Funcionario funcionario) {
		return new ReajusteBuilder().comData(LocalDate.now()).comFuncionario(funcionario).build();
	}
	
	private Funcionario criaFuncionario(LocalDate dataDeAdmissao) {
		return new FuncionarioBuilder().comDataDeAdmissao(dataDeAdmissao).build();
	}

	@Test(expected = BusinessException.class)
	public void funcionarioEmPeriodoDeExperienciaNaoPodeReceberReajuste() {
		LocalDate mesPassado = LocalDate.now().minusMonths(1);
		Funcionario recemContratado = criaFuncionario(mesPassado);
		Reajuste bonus = criaReajuste(recemContratado);
		
		validador.valida(bonus);
	}
	
	@Test
	public void funcionarioRecemSaidoDoPeriodoDeExperienciaPodeReceberReajuste() {
		LocalDate tresMesesAtras = LocalDate.now().minusMonths(3);
		Funcionario recemContratado = criaFuncionario(tresMesesAtras);
		Reajuste bonus = criaReajuste(recemContratado);

		validador.valida(bonus);
	}
	
	@Test
	public void funcionarioForaDoPeriodoDeExperienciaPodeReceberReajuste() {
		LocalDate seisMesesAtras = LocalDate.now().minusMonths(6);
		Funcionario recemContratado = criaFuncionario(seisMesesAtras);
		Reajuste bonus = criaReajuste(recemContratado);

		validador.valida(bonus);
	}

}
