package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.Permissao;
@Repository
public interface Permissoes  extends JpaRepository<Permissao,Integer>{
	
	Optional <Permissao> findByNomeIgnoreCase(String nome);

}
