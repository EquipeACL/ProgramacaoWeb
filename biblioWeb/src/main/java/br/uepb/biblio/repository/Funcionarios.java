package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.usuarios.Funcionario;

@Repository
public interface Funcionarios extends JpaRepository<Funcionario,Integer> {
	
	public Optional<Funcionario> findByNomeIgnoreCase(String nome);
}

