package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.usuarios.Funcionario;

/**
 * Esse é o repositório da classe Funcionarios, responsável por conter  métodos que tratam a Entidade Funcionario
 * @author EquipeACL
 *
 */
@Repository
public interface Funcionarios extends JpaRepository<Funcionario,Integer> {
	
	public Optional<Funcionario> findByNomeIgnoreCase(String nome);
	public Optional<Funcionario> findByLoginIgnoreCase(String login);
}

