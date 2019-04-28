package br.edu.ufab.model.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufab.model.entities.Curso;
/**
 * Repositorio utilizado para utilizar operações crud com a entidade Curso.
 * @author EquipeACL
 *
 */
@Repository
public interface CursoRepository extends CrudRepository<Curso, Long> {
	
	public Optional<Curso> findByNome(String nome);
	
}
