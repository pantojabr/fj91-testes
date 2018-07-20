package br.com.caelum.fj91.testes.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
import br.com.caelum.fj91.testes.builders.FuncionarioBuilder;
import br.com.caelum.fj91.testes.builders.ReajusteBuilder;
import br.com.caelum.fj91.testes.domain.Cargo;
import br.com.caelum.fj91.testes.domain.Funcionario;
import br.com.caelum.fj91.testes.domain.Reajuste;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class ReajusteRepositoryTest {

	@Autowired
	private ReajusteRepository repository;
	
	@Autowired
	private TestEntityManager em;
	
	private Funcionario joao;
	
	@Before
	public void setup() {
		Cargo gerente = new CargoBuilder().comNome("Gerente").comSalarioMinimo(new BigDecimal("5000")).comSalarioMaximo(new BigDecimal("8000")).build();
		em.persist(gerente);

		this.joao = new FuncionarioBuilder().comNome("Joao").comSalario(new BigDecimal("6000")).build();
		em.persist(this.joao);
	}
	
	private Reajuste criaReajuste(BigDecimal valor) {
		return new ReajusteBuilder().comFuncionario(this.joao).comData(LocalDate.now()).comValor(valor).build();
	}
	
	@Test
	public void deveriaCarregarTodosOsReajustesDoFuncionario() {
		Reajuste primeiroBonus = criaReajuste(new BigDecimal("100"));
		Reajuste segundoBonus = criaReajuste(new BigDecimal("200"));
		em.persist(primeiroBonus);
		em.persist(segundoBonus);
		
		List<Reajuste> todos = repository.findAllByFuncionario(joao);
		
		Assert.assertEquals(2, todos.size());
		Assert.assertTrue(todos.contains(primeiroBonus));
		Assert.assertTrue(todos.contains(segundoBonus));
	}

}
