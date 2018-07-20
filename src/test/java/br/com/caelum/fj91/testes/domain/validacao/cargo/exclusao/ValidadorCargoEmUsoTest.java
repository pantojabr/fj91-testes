package br.com.caelum.fj91.testes.domain.validacao.cargo.exclusao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.caelum.fj91.testes.builders.CargoBuilder;
import br.com.caelum.fj91.testes.domain.Cargo;
import br.com.caelum.fj91.testes.exceptions.BusinessException;
import br.com.caelum.fj91.testes.repositories.FuncionarioRepository;

public class ValidadorCargoEmUsoTest {

	private FuncionarioRepository repository;
	private ValidadorCargoEmUso validador;
	private Cargo gerente;
	
	@Before
	public void setup() {
		this.repository = Mockito.mock(FuncionarioRepository.class);
		this.validador = new ValidadorCargoEmUso(repository);
		this.gerente = new CargoBuilder().comNome("Gerente").build();
	}
	
	@Test
	public void devePermitirExcluirCargoQueNaoTemFuncionarioVinculado() {
		Mockito.when(repository.existsByCargo(gerente)).thenReturn(false);
		validador.valida(gerente);
	}
	
	@Test(expected = BusinessException.class)
	public void naoDevePermitirExcluirCargoQueTemFuncionarioVinculado() {
		Mockito.when(repository.existsByCargo(gerente)).thenReturn(true);
		validador.valida(gerente);
	}

}
