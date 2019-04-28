package br.edu.ufab.model.repositories.pessoas;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufab.model.entities.pessoas.Aluno;

@Repository
public interface AlunoRepository extends CrudRepository<Aluno, Long> {
	
	public Optional<Aluno> findByNome(String nome);

}
