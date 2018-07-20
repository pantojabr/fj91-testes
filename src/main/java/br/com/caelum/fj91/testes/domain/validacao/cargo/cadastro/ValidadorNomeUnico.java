package br.com.caelum.fj91.testes.domain.validacao.cargo.cadastro;

import org.springframework.stereotype.Service;

import br.com.caelum.fj91.testes.domain.Cargo;
import br.com.caelum.fj91.testes.exceptions.BusinessException;
import br.com.caelum.fj91.testes.repositories.CargoRepository;

@Service
public class ValidadorNomeUnico implements ValidadorCadastroCargo {
	
	private final CargoRepository repository;
	
	public ValidadorNomeUnico(CargoRepository repository) {
		this.repository = repository;
	}

	@Override
	public void valida(Cargo cargo) {
		boolean existeOutroComMesmoNome = repository.existsByNome(cargo.getNome());
		if (existeOutroComMesmoNome) {
			throw new BusinessException("Já existe outro cargo cadastrado com esse nome");
		}
	}

}
