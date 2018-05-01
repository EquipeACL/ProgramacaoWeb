package br.uepb.biblio.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.uepb.biblio.service.CadastroLivroService;

@Configuration
@ComponentScan(basePackageClasses = CadastroLivroService.class)
public class ServiceConfig {
	
}
