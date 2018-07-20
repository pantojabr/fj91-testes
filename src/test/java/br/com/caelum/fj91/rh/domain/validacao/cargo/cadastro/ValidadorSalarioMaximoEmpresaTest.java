package br.com.caelum.fj91.rh.domain.validacao.cargo.cadastro;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.fj91.rh.builders.CargoBuilder;
import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.exceptions.BusinessException;

public class ValidadorSalarioMaximoEmpresaTest {

	private ValidadorSalarioMaximoEmpresa validador;
	private CargoBuilder builder;
	
	@Before
	public void setup() {
		this.validador = new ValidadorSalarioMaximoEmpresa();
		this.builder = new CargoBuilder();
	}

	@Test(expected = BusinessException.class)
	public void cargoNaoPodeTerSalarioMaximoSuperiorAoMaximoPermitidoPelaEmpresa() {
		Cargo gerente = builder.comSalarioMaximo(new BigDecimal("200000.00")).build();
		validador.valida(gerente);
	}

	@Test
	public void cargoPodeTerSalarioMaximoIgualAoMaximoPermitidoPelaEmpresa() {
		Cargo gerente = builder.comSalarioMaximo(new BigDecimal("100000.00")).build();
		validador.valida(gerente);
	}
	
	@Test
	public void cargoPodeTerSalarioMaximoInferiorAoMaximoPermitidoPelaEmpresa() {
		Cargo gerente = builder.comSalarioMaximo(new BigDecimal("80000.00")).build();
		validador.valida(gerente);
	}

}
