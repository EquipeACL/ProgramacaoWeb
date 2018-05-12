package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.AreasConhecimento;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.AreaConhecimento;
import br.uepb.model.jpaEntity.EntityAreaConhecimento;


@Service
public class CadastroAreaConhecimento {
	@Autowired
	private AreasConhecimento areasConhecimento;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (AreaConhecimento area) {
		EntityAreaConhecimento newEntity = new EntityAreaConhecimento(area);
		Optional <EntityAreaConhecimento> areaOptional = areasConhecimento.findByNomeIgnoreCase(newEntity.getNome());
		if(areaOptional.isPresent()){
			throw new ItemDuplicadoException(" Area já Cadastrada!");
		}
		areasConhecimento.save(newEntity);
	}
	
	@Transactional
	public void atualizar(AreaConhecimento area) throws Exception {
		EntityAreaConhecimento newEntity = new EntityAreaConhecimento(area);
		try{
			if(area.getId()!=0){
				areasConhecimento.save(newEntity);
			}
		}catch(Exception e){
			throw new Exception("Erro na atualização");
		}
	}
	
	@Transactional
	public List<AreaConhecimento> buscarPorNome (String busca) {
		return manager.createQuery("select a from AreaConhecimento a where a.nome like '%"+busca+"%'",AreaConhecimento.class).getResultList();
	}

	@Transactional
	public void remover (int  id) {
		if(id != 0){
			areasConhecimento.delete(id);
		}
		
	}
}
