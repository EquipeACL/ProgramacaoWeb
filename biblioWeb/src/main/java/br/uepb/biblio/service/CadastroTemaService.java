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
import br.uepb.model.AreaConhecimento;
import br.uepb.model.Tema;

@Service
public class CadastroTemaService {
	
	@Autowired
	private Temas temas;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Tema tema) {
		Optional <Tema> temaOptional = temas.findByNomeIgnoreCase(tema.getNome());
		if(temaOptional.isPresent()){
			throw new ItemDuplicadoException(" Tema já Cadastrado!");
		}
		temas.save(tema);
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
		try{
			if(tema.getId()!=0){
				temas.save(tema);
			}
		}catch(Exception e){
			throw new Exception("Erro na atualização");
		}
	}
}
