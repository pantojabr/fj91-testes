package br.com.caelum.fj91.rh.domain.validacao.cargo.cadastro;

import org.springframework.stereotype.Service;

import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.exceptions.BusinessException;
import br.com.caelum.fj91.rh.repositories.CargoRepository;

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
