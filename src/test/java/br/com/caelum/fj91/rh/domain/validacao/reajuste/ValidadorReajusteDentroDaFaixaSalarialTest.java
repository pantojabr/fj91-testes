package br.com.caelum.fj91.rh.domain.validacao.reajuste;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.fj91.rh.builders.CargoBuilder;
import br.com.caelum.fj91.rh.builders.FuncionarioBuilder;
import br.com.caelum.fj91.rh.builders.ReajusteBuilder;
import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.domain.Funcionario;
import br.com.caelum.fj91.rh.domain.Reajuste;
import br.com.caelum.fj91.rh.exceptions.BusinessException;

public class ValidadorReajusteDentroDaFaixaSalarialTest {

	private ValidadorReajusteDentroDaFaixaSalarial validador;
	
	@Before
	public void setup() {
		this.validador = new ValidadorReajusteDentroDaFaixaSalarial();
	}
	
	private Reajuste criaReajuste(BigDecimal valor, Funcionario funcionario) {
		return new ReajusteBuilder().comValor(valor).comFuncionario(funcionario).build();
	}
	
	private Funcionario criaFuncionario() {
		Cargo gerente = new CargoBuilder().comSalarioMinimo(new BigDecimal("5000")).comSalarioMaximo(new BigDecimal("8000")).build();
		return new FuncionarioBuilder().comSalario(new BigDecimal("6000")).comCargo(gerente).build();
	}
	
	@Test(expected = BusinessException.class)
	public void salarioReajustadoDoFuncionarioNaoPodeUltrapassarFaixaSalarialDeSeuCargo() {
		Funcionario joao = criaFuncionario();
		Reajuste bonus = criaReajuste(new BigDecimal("3000"), joao);
		
		validador.valida(bonus);
	}
	
	@Test
	public void salarioReajustadoDoFuncionarioPodeSerIgualAoSalarioMaximoDeSeuCargo() {
		Funcionario joao = criaFuncionario();
		Reajuste bonus = criaReajuste(new BigDecimal("2000"), joao);
		
		validador.valida(bonus);
	}
	
	@Test
	public void salarioReajustadoDoFuncionarioPodeSerMenorDoQueSalarioMaximoDeSeuCargo() {
		Funcionario joao = criaFuncionario();
		Reajuste bonus = criaReajuste(new BigDecimal("1000"), joao);
		
		validador.valida(bonus);
	}
	
}
