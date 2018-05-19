package br.uepb.biblio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.uepb.model.Emprestimo;
@Repository
public interface Emprestimos extends JpaRepository<Emprestimo, Integer>{

}
