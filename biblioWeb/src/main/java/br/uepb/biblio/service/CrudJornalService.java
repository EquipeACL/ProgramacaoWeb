package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Jornais;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Jornal;
import br.uepb.model.jpaEntity.acervo.EntityJornal;

@Service
public class CrudJornalService  {
	@Autowired
	private Jornais jornais;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Jornal jornal) {
		EntityJornal newEntity = new EntityJornal(jornal);
		Optional <EntityJornal> jornalOptional = jornais.findByTituloIgnoreCase(newEntity.getTitulo());
		if(jornalOptional.isPresent()){
			throw new ItemDuplicadoException(" Jornal já Cadastrado!");
		}
		jornais.save(newEntity);
	}
	
	@Transactional
	public List<EntityJornal> buscarPorTitulo (String busca) {
		return manager.createQuery("select j from EntityJornal j where j.titulo like '%"+busca+"%'",EntityJornal.class).getResultList();
	}
	
	@Transactional
	public void remover (int id) {
		if(id>0){
			jornais.delete(id);
		}
	}

}
