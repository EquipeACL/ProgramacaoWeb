package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Editoras;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Editora;
import br.uepb.model.jpaEntity.EntityEditora;

@Service
public class CadastroEditoraService {

	@Autowired
	private Editoras editoras;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar(Editora editora) {
		EntityEditora newEntity = new EntityEditora(editora);
		Optional <EntityEditora> optionalEditora = editoras.findByNomeIgnoreCase(newEntity.getNome());
		if(optionalEditora.isPresent()) {
			throw new ItemDuplicadoException("Editora já Cadastrada");
		}
		editoras.save(newEntity);
	}
	
	@Transactional
	public List<EntityEditora> buscarPorNome (String busca) {
		return manager.createQuery("select a from EntityEditora a where a.nome like '%"+busca+"%'",EntityEditora.class).getResultList();
	}
	
	@Transactional
	public void atualizar(Editora editora) throws Exception {
		EntityEditora newEntity = new EntityEditora(editora);
		try{
			if(editora.getId()>0){
				editoras.save(newEntity);
			}
		}catch(Exception e){
			throw new Exception("Erro na atualização");
		}
	}
	
	@Transactional
	public void remover (int  id) {
		if(id > 0){
			editoras.delete(id);
		}
		
	}
}
