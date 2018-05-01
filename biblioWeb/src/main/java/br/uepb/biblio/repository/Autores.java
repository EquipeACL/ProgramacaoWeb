package br.uepb.biblio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import br.uepb.model.Autor;

@Repository
public interface Autores extends JpaRepository<Autor,Integer> {

}
