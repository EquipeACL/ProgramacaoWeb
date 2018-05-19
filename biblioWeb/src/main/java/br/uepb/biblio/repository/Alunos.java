package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.jpaEntity.usuarios.EntityAluno;

/**
 * Esse é o repositório da classe Alunos, responsável por conter  métodos que tratam a Entidade Aluno
 * @author EquipeACL
 *
 */
@Repository
public interface Alunos extends JpaRepository<EntityAluno,Integer>{
	
	public Optional <EntityAluno> findByNomeIgnoreCase(String nome);
	public Optional <EntityAluno> findByMatriculaIgnoreCase(String matricula);
	public Optional <EntityAluno> findByCpfIgnoreCase(String cpf);
}
