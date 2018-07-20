package br.com.caelum.fj91.testes.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.caelum.fj91.testes.domain.Cargo;
import br.com.caelum.fj91.testes.domain.Funcionario;
import br.com.caelum.fj91.testes.exceptions.BusinessException;
import br.com.caelum.fj91.testes.repositories.CargoRepository;
import br.com.caelum.fj91.testes.repositories.FuncionarioRepository;

@Service
public class FuncionarioService {
	
	private final FuncionarioRepository funcionarioRepository;
	private final CargoRepository cargoRepository;

	public FuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoRepository = cargoRepository;
	}
	
	public void salvar(Funcionario novo) {
		BigDecimal salario = novo.getSalario();
		Cargo cargo = cargoRepository.getOne(novo.getCargo().getId());
		
		if (salario.compareTo(cargo.getSalarioMinimo()) < 0) {
			throw new BusinessException("Salário do funcionário não pode ser menor do que o salário mínimo de seu cargo");
		}
		
		if (salario.compareTo(cargo.getSalarioMaximo()) > 0) {
			throw new BusinessException("Salário do funcionário não pode ser maior do que salário máximo de seu cargo");
		}
		
		funcionarioRepository.save(novo);
	}
	
	public Funcionario buscarPorId(Long id) {
		return funcionarioRepository.getOne(id);
	}
	
	public List<Funcionario> listarTodos() {
		return funcionarioRepository.findAll();
	}

	public void excluir(Long id) {
		funcionarioRepository.deleteById(id);
	}

}
