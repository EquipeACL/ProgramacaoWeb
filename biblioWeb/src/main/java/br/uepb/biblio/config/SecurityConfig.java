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
			.antMatchers("/usuarios/novo").hasRole("CADASTRAR_USUARIO")
			.antMatchers("/alunos/novo").hasRole("CADASTRAR_ALUNO")
			.antMatchers("/tccs/novo").hasRole("CADASTRAR_ACERVO")
			.antMatchers("/autores/novo").hasRole("CADASTRAR_ACERVO")
			.antMatchers("/areasconhecimento/novo").hasRole("CADASTRAR_ACERVO")
			.antMatchers("/anais/novo").hasRole("CADASTRAR_ACERVO")
			.antMatchers("/curso/novo").hasRole("CADASTRAR_ACERVO")
			.antMatchers("/livros/novo").hasRole("CADASTRAR_ACERVO")
			.antMatchers("/jornais/novo").hasRole("CADASTRAR_ACERVO")
			.antMatchers("/editoras/novo").hasRole("CADASTRAR_ACERVO")
			.antMatchers("/revistas/novo").hasRole("CADASTRAR_ACERVO")
			.antMatchers("/editoras/novo").hasRole("CADASTRAR_ACERVO")
			.antMatchers("/orientadores/novo").hasRole("CADASTRAR_ACERVO")
			.antMatchers("/temas/novo").hasRole("CADASTRAR_ACERVO")
			
			.antMatchers("/usuarios/editar").hasRole("EDITAR_USUARIO")
			.antMatchers("/alunos/editar").hasRole("EDITAR_ALUNO")
			.antMatchers("/tccs/editar").hasRole("EDITAR_ACERVO")
			.antMatchers("/autores/editar").hasRole("EDITAR_ACERVO")
			.antMatchers("/areasconhecimento/editar").hasRole("EDITAR_ACERVO")
			.antMatchers("/anais/editar").hasRole("EDITAR_ACERVO")
			.antMatchers("/curso/editar").hasRole("EDITAR_ACERVO")
			.antMatchers("/livros/editar").hasRole("EDITAR_ACERVO")
			.antMatchers("/jornais/editar").hasRole("EDITAR_ACERVO")
			.antMatchers("/editoras/editar").hasRole("EDITAR_ACERVO")
			.antMatchers("/revistas/editar").hasRole("EDITAR_ACERVO")
			.antMatchers("/editoras/editar").hasRole("EDITAR_ACERVO")
			.antMatchers("/orientadores/editar").hasRole("EDITAR_ACERVO")
			.antMatchers("/temas/editar").hasRole("EDITAR_ACERVO")
			
			.antMatchers("/usuarios/remover").hasRole("DELETAR_USUARIO")
			.antMatchers("/alunos/remover").hasRole("DELETAR_ALUNO")
			.antMatchers("/tccs/remover").hasRole("DELETAR_ACERVO")
			.antMatchers("/autores/remover").hasRole("DELETAR_ACERVO")
			.antMatchers("/areasconhecimento/remover").hasRole("DELETAR_ACERVO")
			.antMatchers("/anais/remover").hasRole("DELETAR_ACERVO")
			.antMatchers("/curso/remover").hasRole("DELETAR_ACERVO")
			.antMatchers("/livros/remover").hasRole("DELETAR_ACERVO")
			.antMatchers("/jornais/remover").hasRole("DELETAR_ACERVO")
			.antMatchers("/editoras/remover").hasRole("DELETAR_ACERVO")
			.antMatchers("/revistas/remover").hasRole("DELETAR_ACERVO")
			.antMatchers("/editoras/remover").hasRole("DELETAR_ACERVO")
			.antMatchers("/orientadores/remover").hasRole("DELETAR_ACERVO")			
			.antMatchers("/temas/remover").hasRole("DELETAR_ACERVO")
			
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
