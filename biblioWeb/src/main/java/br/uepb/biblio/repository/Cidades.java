package br.uepb.biblio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.Cidade;

@Repository
public interface Cidades extends JpaRepository <Cidade,Integer>{
	

}
