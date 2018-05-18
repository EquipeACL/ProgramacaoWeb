package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Alunos;
import br.uepb.biblio.repository.Funcionarios;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.jpaEntity.usuarios.EntityAluno;
import br.uepb.model.usuarios.Aluno;
@Service
public class CadastroAlunoService {

	@Autowired
	private Alunos alunos;
	
	@Autowired
	private Funcionarios funcionarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Aluno aluno) {
		EntityAluno entAluno = new EntityAluno(aluno);
		
		Optional <EntityAluno> alunoOptional = alunos.findByCpfIgnoreCase(aluno.getCpf());
		
		if(alunoOptional.isPresent()){
			throw new ItemDuplicadoException(" Aluno(a) já Cadastrado!");
		}
		
		
		entAluno.setSenha(this.passwordEncoder.encode(entAluno.getSenha()));
		entAluno.setConfirmacaoSenha(entAluno.getSenha());
		alunos.save(entAluno);
	}

	@Transactional
	public List<Aluno> buscarPorNome (String busca) {
		return manager.createQuery("select a from Aluno a where a.nome like '%"+busca+"%'",Aluno.class).getResultList();
	}
}
