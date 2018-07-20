package br.com.caelum.fj91.rh.domain.validacao.cargo.exclusao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.caelum.fj91.rh.builders.CargoBuilder;
import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.exceptions.BusinessException;
import br.com.caelum.fj91.rh.repositories.FuncionarioRepository;

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
