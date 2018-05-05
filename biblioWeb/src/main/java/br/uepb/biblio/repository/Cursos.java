package br.uepb.biblio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.Curso;
@Repository
public interface Cursos extends JpaRepository<Curso, Integer>{
	
	public Optional <Curso> findByNomeIgnoreCase(String nome);

}
