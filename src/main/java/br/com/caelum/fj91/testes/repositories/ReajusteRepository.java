package br.com.caelum.fj91.testes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.caelum.fj91.testes.domain.Funcionario;
import br.com.caelum.fj91.testes.domain.Reajuste;

@Repository
public interface ReajusteRepository extends JpaRepository<Reajuste, Long> {

	List<Reajuste> findAllByFuncionario(Funcionario funcionario);

	Reajuste findTopByFuncionarioOrderByIdDesc(Funcionario funcionario);

}
