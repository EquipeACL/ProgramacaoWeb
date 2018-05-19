package br.uepb.biblio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.uepb.biblio.security.AppUserDetailsService;

@EnableWebSecurity
@ComponentScan(basePackageClasses = AppUserDetailsService.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.inMemoryAuthentication().withUser("admin").password("admin").roles("CADASTRO_ACERVO");
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/usuarios/novo").hasAuthority("CADASTRAR_USUARIO")
			.antMatchers("/alunos/novo").hasAuthority("CADASTRAR_ALUNO")
			.antMatchers("/tccs/novo").hasAuthority("CADASTRAR_ACERVO")
			.antMatchers("/autores/novo").hasAuthority("CADASTRAR_ACERVO")
			.antMatchers("/areasconhecimento/novo").hasAuthority("CADASTRAR_ACERVO")
			.antMatchers("/anais/novo").hasAuthority("CADASTRAR_ACERVO")
			.antMatchers("/cursos/novo").hasAuthority("CADASTRAR_ACERVO")
			.antMatchers("/livros/novo").hasAuthority("CADASTRAR_ACERVO")
			.antMatchers("/jornais/novo").hasAuthority("CADASTRAR_ACERVO")
			.antMatchers("/editoras/novo").hasAuthority("CADASTRAR_ACERVO")
			.antMatchers("/revistas/novo").hasAuthority("CADASTRAR_ACERVO")
			.antMatchers("/editoras/novo").hasAuthority("CADASTRAR_ACERVO")
			.antMatchers("/orientadores/novo").hasAuthority("CADASTRAR_ACERVO")
			.antMatchers("/temas/novo").hasAuthority("CADASTRAR_ACERVO")
			.antMatchers("/midias/novo").hasAuthority("CADASTRAR_ACERVO")
			.antMatchers("/emprestimo/novo").hasAnyAuthority("CADASTRAR_EMPRESTIMO")
			
			.antMatchers("/usuarios/editar").hasAuthority("EDITAR_USUARIO")
			.antMatchers("/alunos/editar").hasAuthority("EDITAR_ALUNO")
			.antMatchers("/tccs/editar").hasAuthority("EDITAR_ACERVO")
			.antMatchers("/autores/editar").hasAuthority("EDITAR_ACERVO")
			.antMatchers("/areasconhecimento/editar").hasAuthority("EDITAR_ACERVO")
			.antMatchers("/anais/editar").hasAuthority("EDITAR_ACERVO")
			.antMatchers("/cursos/editar").hasAuthority("EDITAR_ACERVO")
			.antMatchers("/livros/editar").hasAuthority("EDITAR_ACERVO")
			.antMatchers("/jornais/editar").hasAuthority("EDITAR_ACERVO")
			.antMatchers("/editoras/editar").hasAuthority("EDITAR_ACERVO")
			.antMatchers("/revistas/editar").hasAuthority("EDITAR_ACERVO")
			.antMatchers("/editoras/editar").hasAuthority("EDITAR_ACERVO")
			.antMatchers("/orientadores/editar").hasAuthority("EDITAR_ACERVO")
			.antMatchers("/temas/editar").hasAuthority("EDITAR_ACERVO")
			.antMatchers("/midias/editar").hasAuthority("EDITAR_ACERVO")
			.antMatchers("/emprestimo/editar").hasAnyAuthority("EDITAR_EMPRESTIMO")
			
			.antMatchers("/usuarios/remover").hasAuthority("DELETAR_USUARIO")
			.antMatchers("/alunos/remover").hasAuthority("DELETAR_ALUNO")
			.antMatchers("/tccs/remover").hasAuthority("DELETAR_ACERVO")
			.antMatchers("/autores/remover").hasAuthority("DELETAR_ACERVO")
			.antMatchers("/areasconhecimento/remover").hasAuthority("DELETAR_ACERVO")
			.antMatchers("/anais/remover").hasAuthority("DELETAR_ACERVO")
			.antMatchers("/cursos/remover").hasAuthority("DELETAR_ACERVO")
			.antMatchers("/livros/remover").hasAuthority("DELETAR_ACERVO")
			.antMatchers("/jornais/remover").hasAuthority("DELETAR_ACERVO")
			.antMatchers("/editoras/remover").hasAuthority("DELETAR_ACERVO")
			.antMatchers("/revistas/remover").hasAuthority("DELETAR_ACERVO")
			.antMatchers("/editoras/remover").hasAuthority("DELETAR_ACERVO")
			.antMatchers("/orientadores/remover").hasAuthority("DELETAR_ACERVO")			
			.antMatchers("/temas/remover").hasAuthority("DELETAR_ACERVO")
			.antMatchers("/midias/remover").hasAuthority("DELETAR_ACERVO")
			.antMatchers("/emprestimo/REMOVER").hasAnyAuthority("DELETAR_EMPRESTIMO")
			
			.antMatchers("/emprestimo/quitar").hasAnyAuthority("QUITAR_EMPRESTIMO")
			.anyRequest().authenticated()
			
			.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.and()
			.exceptionHandling().accessDeniedPage("/403")
			.and()
			.csrf().disable(); 
		
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers("/layout/**")
		.antMatchers("/images/**");
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
