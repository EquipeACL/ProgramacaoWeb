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

	private Collection<? extends GrantedAuthority> getPermissoes(EntityAluno aluno) {

		Set<SimpleGrantedAuthority> authorities = new HashSet<>();

		// lista de permissões do aluno
		List <String> permissoes = cadastroAlunoService.permissoes(aluno);
		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority("ROLE_" + p.toUpperCase())));
		
		
		return authorities;
	}

	private Collection<? extends GrantedAuthority> getPermissoes(Funcionario funcionario) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();

		// lista de permissões do Funcionario
		List <String> permissoes = cadastroFuncionarioService.permissoes(funcionario);
		permissoes.forEach(p -> authorities.add(new SimpleGrantedAuthority("ROLE_" + p.toUpperCase())));
		
		
		return authorities;
	}

}
