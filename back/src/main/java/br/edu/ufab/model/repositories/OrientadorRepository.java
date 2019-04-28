package br.edu.ufab.model.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufab.model.entities.Orientador;
/**
 * Repositorio utilizado para utilizar operações crud com a entidade Orientador.
 * @author EquipeACL
 *
 */
@Repository
public interface OrientadorRepository extends CrudRepository<Orientador, Long> {

	public Optional<Orientador> findByNome(String nome);
}
