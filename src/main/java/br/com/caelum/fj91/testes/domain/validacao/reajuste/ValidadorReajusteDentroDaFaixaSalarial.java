package br.com.caelum.fj91.testes.domain.validacao.reajuste;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.com.caelum.fj91.testes.domain.Reajuste;
import br.com.caelum.fj91.testes.exceptions.BusinessException;

@Service
public class ValidadorReajusteDentroDaFaixaSalarial implements ValidadorReajuste {

	@Override
	public void valida(Reajuste reajuste) {
		BigDecimal salarioAtual = reajuste.getFuncionario().getSalario();
		BigDecimal salarioReajustado = salarioAtual.add(reajuste.getValor());
		BigDecimal salarioMaximoDoCargo = reajuste.getFuncionario().getCargo().getSalarioMaximo();

		if (salarioReajustado.compareTo(salarioMaximoDoCargo) > 0) {
			throw new BusinessException("Salário reajustado do funcionário não pode ultrapassar o valor do salário máximo de seu cargo");
		}
	}

}
