package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Orientadores;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Orientador;

@Service
public class CadastroOrientadorService {

	@Autowired
	private Orientadores orientadores;
	
	@PersistenceContext
	private EntityManager manager;
	
	@Transactional
	public Orientador salvar(Orientador orientador) {
		Optional <Orientador> optionalOrientador = orientadores.findByNomeIgnoreCase(orientador.getNome());
		if(optionalOrientador.isPresent()) {
			throw new ItemDuplicadoException("Orientador já Cadastrado!");
		}
		return orientadores.saveAndFlush(orientador);
	}
	
	@Transactional
	public List<Orientador> buscarPorNome (String busca) {
		return manager.createQuery("select a from Orientador a where a.nome like '%"+busca+"%'",Orientador.class).getResultList();
	}
}
