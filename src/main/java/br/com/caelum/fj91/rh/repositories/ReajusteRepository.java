package br.com.caelum.fj91.rh.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.caelum.fj91.rh.domain.Funcionario;
import br.com.caelum.fj91.rh.domain.Reajuste;

@Repository
public interface ReajusteRepository extends JpaRepository<Reajuste, Long> {

	List<Reajuste> findAllByFuncionario(Funcionario funcionario);

	Reajuste findTopByFuncionarioOrderByIdDesc(Funcionario funcionario);

}
