package br.com.caelum.fj91.rh.services;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.caelum.fj91.rh.builders.CargoBuilder;
import br.com.caelum.fj91.rh.builders.FuncionarioBuilder;
import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.domain.Funcionario;
import br.com.caelum.fj91.rh.exceptions.BusinessException;
import br.com.caelum.fj91.rh.repositories.CargoRepository;
import br.com.caelum.fj91.rh.repositories.FuncionarioRepository;

public class FuncionarioServiceTest {
	
	private FuncionarioService service;
	private FuncionarioRepository funcionarioRepository;
	private CargoRepository cargoRepository;
	private Cargo gerente;
	
	@Before
	public void setup() {
		this.funcionarioRepository = Mockito.mock(FuncionarioRepository.class);
		this.cargoRepository = Mockito.mock(CargoRepository.class);
		this.service = new FuncionarioService(this.funcionarioRepository, this.cargoRepository);
		this.gerente = new CargoBuilder().comSalarioMinimo(new BigDecimal("5000")).comSalarioMaximo(new BigDecimal("8000")).build();
		Mockito.when(cargoRepository.getOne(gerente.getId())).thenReturn(gerente);
	}
	
	private Funcionario criaFuncionario(BigDecimal salario) {
		return new FuncionarioBuilder().comCargo(gerente).comSalario(salario).build();
	}
	
	@Test(expected = BusinessException.class)
	public void salarioDoFuncionarioNaoPodeSerMenorQueSalarioMinimoDeSeuCargo() {
		Funcionario joao = criaFuncionario(new BigDecimal("1000"));
		service.salvar(joao);
	}
	
	@Test(expected = BusinessException.class)
	public void salarioDoFuncionarioNaoPodeSerMaiorQueSalarioMaximoDeSeuCargo() {
		Funcionario joao = criaFuncionario(new BigDecimal("9000"));
		service.salvar(joao);
	}
	
	@Test
	public void salarioDoFuncionarioPodeSerIgualAoSalarioMinimoDeSeuCargo() {
		Funcionario joao = criaFuncionario(new BigDecimal("5000"));
		service.salvar(joao);
		Mockito.verify(funcionarioRepository).save(joao);
	}
	
	@Test
	public void salarioDoFuncionarioPodeSerIgualAoSalarioMaximoDeSeuCargo() {
		Funcionario joao = criaFuncionario(new BigDecimal("8000"));
		service.salvar(joao);
		Mockito.verify(funcionarioRepository).save(joao);
	}
	
	@Test
	public void salarioDoFuncionarioPodeTerValorQueEstejaEntreAFaixaSalarialDeSeuCargo() {
		Funcionario joao = criaFuncionario(new BigDecimal("7500"));
		service.salvar(joao);
		Mockito.verify(funcionarioRepository).save(joao);
	}
	
}
