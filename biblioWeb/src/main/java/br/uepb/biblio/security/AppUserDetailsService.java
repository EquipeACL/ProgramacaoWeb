package br.uepb.biblio.security;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.uepb.biblio.repository.Funcionarios;
import br.uepb.model.usuarios.Funcionario;

@Service
public class AppUserDetailsService implements UserDetailsService{

	
	@Autowired
	private Funcionarios funcionarios;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Optional <Funcionario> funcionarioOptional = funcionarios.findByLoginIgnoreCase(login);
		
		Funcionario funcionario = funcionarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuario e/ou senha incorretos"));
		return new User(funcionario.getEmail(), funcionario.getSenha(), new HashSet<>());
	}

	
}
