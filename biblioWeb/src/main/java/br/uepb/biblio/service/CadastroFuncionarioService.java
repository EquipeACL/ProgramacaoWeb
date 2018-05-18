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
import br.uepb.biblio.service.exception.LoginDuplicadoException;
import br.uepb.model.jpaEntity.usuarios.EntityAluno;
import br.uepb.model.usuarios.Funcionario;

@Service
public class CadastroFuncionarioService {

	@Autowired
	Funcionarios funcionarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	Alunos alunos;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Funcionario funcionario) {
		Optional <Funcionario> funcionarioOptional = funcionarios.findByNomeIgnoreCase(funcionario.getNome());
		if(funcionarioOptional.isPresent()){
			throw new ItemDuplicadoException(" Funcionário(a) já Cadastrado!");
		}
		
		funcionarioOptional = funcionarios.findByLoginIgnoreCase(funcionario.getLogin());
		if(funcionarioOptional.isPresent()) {
			throw new LoginDuplicadoException(" Já há um usuário cadastrado com este Login!");
		}
		
		Optional <EntityAluno> alunoOptional = alunos.findByMatriculaIgnoreCase(funcionario.getLogin());
		if(funcionarioOptional.isPresent()) {
			throw new LoginDuplicadoException(" Já há um Aluno cadastrado com este Login!");
		}	

		funcionario.setSenha(this.passwordEncoder.encode(funcionario.getSenha()));
		funcionario.setConfirmacaoSenha(funcionario.getSenha());
		funcionarios.save(funcionario);
	}
	
	@Transactional
	public List<Funcionario> buscarPorNome (String busca) {
		return manager.createQuery("select a from Funcionario a where a.nome like '%"+busca+"%'",Funcionario.class).getResultList();
	}
	
}
