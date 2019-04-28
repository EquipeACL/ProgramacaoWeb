package br.edu.ufab.model.repositories.itens;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufab.model.entities.itens.Revista;
/**
 * Repositorio utilizado para utilizar operações crud com a entidade Revista.
 * @author EquipeACL
 *
 */
@Repository
public interface RevistaRepository extends CrudRepository<Revista, Long> {
	
	public Optional<Revista> findByTitulo(String titulo);

}
