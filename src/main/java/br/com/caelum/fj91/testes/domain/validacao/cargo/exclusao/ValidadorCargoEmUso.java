package br.com.caelum.fj91.testes.domain.validacao.cargo.exclusao;

import org.springframework.stereotype.Service;

import br.com.caelum.fj91.testes.domain.Cargo;
import br.com.caelum.fj91.testes.exceptions.BusinessException;
import br.com.caelum.fj91.testes.repositories.FuncionarioRepository;

@Service
public class ValidadorCargoEmUso implements ValidadorExclusaoCargo {
	
	private final FuncionarioRepository repository;

	public ValidadorCargoEmUso(FuncionarioRepository repository) {
		this.repository = repository;
	}

	@Override
	public void valida(Cargo cargo) {
		boolean cargoEmUso = repository.existsByCargo(cargo);
		if (cargoEmUso) {
			throw new BusinessException("Cargo não pode ser excluído pois está atributido a algum funcionário");
		}
	}
	
}
