package br.uepb.biblio.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.uepb.biblio.service.CadastroAutorService;

@Configuration
@ComponentScan(basePackageClasses = CadastroAutorService.class)
public class ServiceConfig {
	
	
	
}
