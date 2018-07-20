package br.com.caelum.fj91.rh.repositories;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.caelum.fj91.rh.builders.CargoBuilder;
import br.com.caelum.fj91.rh.builders.FuncionarioBuilder;
import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.domain.Funcionario;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class FuncionarioRepositoryTest {

	@Autowired
	private FuncionarioRepository repository;
	
	@Autowired
	private TestEntityManager em;
	
	private Cargo gerente;
	
	@Before
	public void setup() {
		this.gerente = new CargoBuilder().comNome("Gerente").comSalarioMinimo(new BigDecimal("5000")).comSalarioMaximo(new BigDecimal("8000")).build();
		this.em.persist(gerente);
	}
	
	@Test
	public void deveriaIndicarQueExisteFuncionarioComOCargo() {
		Funcionario joao = new FuncionarioBuilder().comCargo(gerente).build();
		this.em.persist(joao);
		
		boolean existe = repository.existsByCargo(gerente);
		Assert.assertTrue(existe);
	}
	
	@Test
	public void deveriaIndicarQueNaoExisteFuncionarioComOCargo() {
		boolean existe = repository.existsByCargo(gerente);
		Assert.assertFalse(existe);
	}

}
