package br.edu.ufab.model.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufab.model.entities.Editora;
/**
 * Repositorio utilizado para utilizar operações crud com a entidade Editora.
 * @author EquipeACL
 *
 */
@Repository
public interface EditoraRepository extends CrudRepository <Editora, Long> {
	
	public Optional<Editora> findByNome(String nome);

}
