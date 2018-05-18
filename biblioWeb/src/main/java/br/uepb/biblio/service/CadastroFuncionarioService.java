package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Funcionarios;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.biblio.service.exception.LoginDuplicadoException;
import br.uepb.model.usuarios.Funcionario;

@Service
public class CadastroFuncionarioService {

	@Autowired
	Funcionarios funcionarios;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PersistenceContext
    private EntityManager manager;
	
	@Transactional
	public void salvar (Funcionario funcionario) {
		Optional <Funcionario> alunoOptional = funcionarios.findByNomeIgnoreCase(funcionario.getNome());
		if(alunoOptional.isPresent()){
			throw new ItemDuplicadoException(" Aluno(a) já Cadastrado!");
		}
		alunoOptional = funcionarios.findByLoginIgnoreCase(funcionario.getLogin());
		if(alunoOptional.isPresent()) {
			throw new LoginDuplicadoException(" Já há um usuário cadastrado com este Login!");
		}
		
//		if(funcionario.isNovo() &&StringUtils.isEmpty(funcionario.getSenha())) {
//			throw new SenhaObrigatoriaUsuarioException("Senha é obrigatória para novo usuário");
//		}
		funcionario.setSenha(this.passwordEncoder.encode(funcionario.getSenha()));
		funcionario.setConfirmacaoSenha(funcionario.getSenha());
		funcionarios.save(funcionario);
	}
	
	@Transactional
	public List<Funcionario> buscarPorNome (String busca) {
		return manager.createQuery("select a from Funcionario a where a.nomeCompleto like '%"+busca+"%'",Funcionario.class).getResultList();
	}
	
}
