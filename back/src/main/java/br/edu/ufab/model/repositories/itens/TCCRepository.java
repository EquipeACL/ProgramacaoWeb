package br.edu.ufab.model.repositories.itens;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufab.model.entities.itens.TCC;
/**
 * Repositorio utilizado para utilizar operações crud com a entidade TCC.
 * @author EquipeACL
 *
 */
@Repository
public interface TCCRepository extends CrudRepository<TCC, Long> {

	public Optional<TCC> findByTitulo(String titulo);
	
}
