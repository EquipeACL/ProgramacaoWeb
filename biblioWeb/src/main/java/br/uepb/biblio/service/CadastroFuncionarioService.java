package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Funcionarios;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.usuarios.Aluno;
import br.uepb.model.usuarios.Funcionario;

@Service
public class CadastroFuncionarioService {

	@Autowired
	Funcionarios funcionarios;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Funcionario funcionario) {
		Optional <Funcionario> alunoOptional = funcionarios.findByNomeIgnoreCase(funcionario.getNome());
		if(alunoOptional.isPresent()){
			throw new ItemDuplicadoException(" Aluno(a) já Cadastrado!");
		}
		funcionarios.save(funcionario);
	}
	
	@Transactional
	public List<Funcionario> buscarPorNome (String busca) {
		return manager.createQuery("select a from Funcionario a where a.nomeCompleto like '%"+busca+"%'",Funcionario.class).getResultList();
	}
	
}
