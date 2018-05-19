package br.uepb.biblio.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.uepb.biblio.repository.Alunos;
import br.uepb.biblio.repository.Funcionarios;
import br.uepb.biblio.service.CadastroAlunoService;
import br.uepb.biblio.service.CadastroFuncionarioService;
import br.uepb.model.jpaEntity.usuarios.EntityAluno;
import br.uepb.model.usuarios.Funcionario;

/**
 * Essa é a classe responsável por conter os serviços relacionados ao login e cadastro dos usuários do sistema.
 * @author EquipeACL
 *
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private Funcionarios funcionarios;

	@Autowired
	private CadastroFuncionarioService cadastroFuncionarioService;
	
	@Autowired
	private CadastroAlunoService cadastroAlunoService;
	
	
	@Autowired
	private Alunos alunos;

	/**
	 * Esse método é responsável pelo direcionamento e validação na autenticação do usuário (admin ou funcionário) e do aluno no login.
	 * @param login, que é a String que contém os dados de login do usuário
	 * @return new User com as permissões do usuário que realizou o login
	 */
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Optional<Funcionario> funcionarioOptional = funcionarios.findByLoginIgnoreCase(login);
		Optional<EntityAluno> alunoOptional = alunos.findByMatriculaIgnoreCase(login);

		if (funcionarioOptional.isPresent()) {
			Funcionario funcionario = funcionarioOptional
					.orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou senha incorretos"));
			return new User(funcionario.getLogin(), funcionario.getSenha(), getPermissoes(funcionario));
		} else {
			EntityAluno aluno = alunoOptional
					.orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou senha incorretos"));
			return new User(aluno.getMatricula(), aluno.getSenha(), getPermissoes(aluno));
		}

	}

	/**
	 * Esse método é responsável por atribuir permissões de acesso de rotas a um Aluno
	 * @param aluno, que é o aluno que irá receber as permissões
	 * @return authorities, que são as autorizações do aluno (rotas que podem ser acessadas)
	 */
	private Collection<? extends GrantedAuthority> getPermissoes(EntityAluno aluno) {

		Set<SimpleGrantedAuthority> authorities = new HashSet<>();

		// lista de permissões do aluno
		List <String> permissoes = cadastroAlunoService.permissoes(aluno);
		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));
		
		
		return authorities;
	}

	/**
	 * Esse método é responsável por atribuir permissões de acesso de rotas a um Funcionário
	 * @param aluno, que é o aluno que irá receber as permissões
	 * @return authorities, que são as autorizações do funcionário (rotas que podem ser acessadas)
	 */
	private Collection<? extends GrantedAuthority> getPermissoes(Funcionario funcionario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();

		// lista de permissões do Funcionario
		List <String> permissoes = cadastroFuncionarioService.permissoes(funcionario);
		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority(p.toUpperCase())));
		
		
		return authorities;
	}

}
