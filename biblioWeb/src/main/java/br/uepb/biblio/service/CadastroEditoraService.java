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
import br.uepb.model.AreaConhecimento;
import br.uepb.model.Editora;

@Service
public class CadastroEditoraService {

	@Autowired
	private Editoras editoras;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar(Editora editora) {
		Optional <Editora> optionalEditora = editoras.findByNomeIgnoreCase(editora.getNome());
		if(optionalEditora.isPresent()) {
			throw new ItemDuplicadoException("Editora já Cadastrada");
		}
		editoras.save(editora);
	}
	
	@Transactional
	public List<Editora> buscarPorNome (String busca) {
		return manager.createQuery("select a from Editora a where a.nome like '%"+busca+"%'",Editora.class).getResultList();
	}
}
