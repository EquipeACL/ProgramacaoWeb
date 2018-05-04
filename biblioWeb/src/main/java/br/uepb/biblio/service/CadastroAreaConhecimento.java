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


@Service
public class CadastroAreaConhecimento {
	@Autowired
	private AreasConhecimento areasConhecimento;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (AreaConhecimento area) {
		Optional <AreaConhecimento> areaOptional = areasConhecimento.findByNomeIgnoreCase(area.getNome());
		if(areaOptional.isPresent()){
			throw new ItemDuplicadoException(" Area já Cadastrada!");
		}
		areasConhecimento.save(area);
	}
	
	@Transactional
	public List<AreaConhecimento> buscarPorNome (String busca) {
		return manager.createQuery("select a from AreaConhecimento a where a.nome like '%"+busca+"%'",AreaConhecimento.class).getResultList();
	}

}
