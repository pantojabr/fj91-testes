package br.com.caelum.fj91.testes.domain.validacao.cargo.cadastro;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.caelum.fj91.testes.builders.CargoBuilder;
import br.com.caelum.fj91.testes.domain.Cargo;
import br.com.caelum.fj91.testes.exceptions.BusinessException;
import br.com.caelum.fj91.testes.repositories.CargoRepository;

public class ValidadorNomeUnicoTest {
	
	private CargoRepository repository;
	private ValidadorNomeUnico validador;
	private Cargo gerente;
	
	@Before
	public void setup() {
		this.repository = Mockito.mock(CargoRepository.class);
		this.validador = new ValidadorNomeUnico(repository);
		this.gerente = new CargoBuilder().comNome("Gerente").build();
	}
	
	@Test
	public void devePermitirCadastrarCargoCasoSeuNomeSejaUnico() {
		Mockito.when(repository.existsByNome(gerente.getNome())).thenReturn(false);
		validador.valida(gerente);
	}
	
	@Test(expected = BusinessException.class)
	public void naoDevePermitirCadastrarCargoComMesmoNomeDeOutroCargo() {
		Mockito.when(repository.existsByNome(gerente.getNome())).thenReturn(true);
		validador.valida(gerente);
	}

}
