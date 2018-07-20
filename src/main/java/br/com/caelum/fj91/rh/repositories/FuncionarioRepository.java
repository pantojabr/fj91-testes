package br.com.caelum.fj91.rh.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.caelum.fj91.rh.domain.Cargo;
import br.com.caelum.fj91.rh.domain.Funcionario;

@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

	boolean existsByCargo(Cargo cargo);

}
