package br.com.caelum.fj91.testes.domain.validacao.cargo.cadastro;

import org.springframework.stereotype.Service;

import br.com.caelum.fj91.testes.domain.Cargo;
import br.com.caelum.fj91.testes.exceptions.BusinessException;

@Service
public class ValidadorSalarioMinimoMenorQueMaximo implements ValidadorCadastroCargo {
	
	@Override
	public void valida(Cargo cargo) {
		if (cargo.getSalarioMinimo().compareTo(cargo.getSalarioMaximo()) > 0) {
			throw new BusinessException("Salário mínimo deve ser menor do que o salário máximo");
		}
	}

}
