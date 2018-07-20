package br.com.caelum.fj91.testes.repositories;

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

import br.com.caelum.fj91.testes.builders.CargoBuilder;
import br.com.caelum.fj91.testes.domain.Cargo;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CargoRepositoryTest {

	@Autowired
	private CargoRepository repository;
	
	@Autowired
	private TestEntityManager em;
	
	private Cargo gerente;
	
	@Before
	public void setup() {
		this.gerente = new CargoBuilder().comNome("Gerente").comSalarioMinimo(new BigDecimal("3000")).comSalarioMaximo(new BigDecimal("6000")).build();
	}
	
	@Test
	public void deveriaIndicarQueExisteOutroCargoComOMesmoNome() {
		em.persist(gerente);
		boolean existeCargoComMesmoNome = repository.existsByNome(gerente.getNome());
		Assert.assertTrue(existeCargoComMesmoNome);
	}
	
	@Test
	public void deveriaIndicarQueNaoExisteOutroCargoComOMesmoNome() {
		boolean existeCargoComMesmoNome = repository.existsByNome(gerente.getNome());
		Assert.assertFalse(existeCargoComMesmoNome);
	}

}
