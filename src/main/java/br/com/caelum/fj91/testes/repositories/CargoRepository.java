package br.com.caelum.fj91.testes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.caelum.fj91.testes.domain.Cargo;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

	boolean existsByNome(String nome);

}
