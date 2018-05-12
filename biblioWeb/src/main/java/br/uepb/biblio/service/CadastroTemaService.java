package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Temas;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Tema;
import br.uepb.model.jpaEntity.EntityTema;

@Service
public class CadastroTemaService {
	
	@Autowired
	private Temas temas;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Tema tema) {
		EntityTema newEntity = new EntityTema(tema);
		Optional <EntityTema> temaOptional = temas.findByNomeIgnoreCase(newEntity.getNome());
		if(temaOptional.isPresent()){
			throw new ItemDuplicadoException(" Tema já Cadastrado!");
		}
		temas.save(newEntity);
	}
		
	@Transactional
	public List<Tema> buscarPorNome (String busca) {
		return manager.createQuery("select a from Tema a where a.nome like '%"+busca+"%'",Tema.class).getResultList();
	}
	
	@Transactional
	public void remover (int  id) {
		if(id != 0){
			temas.delete(id);
		}
		
	}
	@Transactional
	public void atualizar(Tema tema) throws Exception {
		EntityTema newEntity = new EntityTema(tema);
		try{
			if(newEntity.getId()!=0){
				temas.save(newEntity);
			}
		}catch(Exception e){
			throw new Exception("Erro na atualização");
		}
	}
}
