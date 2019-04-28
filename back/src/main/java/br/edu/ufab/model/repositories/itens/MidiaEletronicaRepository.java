package br.edu.ufab.model.repositories.itens;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.edu.ufab.model.entities.itens.MidiaEletronica;
/**
 * Repositorio utilizado para utilizar operações crud com a entidade MidiaEletonica.
 * @author EquipeACL
 *
 */
@Repository
public interface MidiaEletronicaRepository extends CrudRepository<MidiaEletronica, Long> {
	
	public Optional<MidiaEletronica> findByTitulo(String titulo);

}
