package br.uepb.biblio.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import br.uepb.biblio.service.CadastroAutorService;
/**
 * Essa a classe de configuração dos serviços, que serve para indicar para o Spring onde está as classes de serviços, 
 * utilizando a classe CadastroAutorService como referência de escaneamento para as demais classes.
 * @author EquipeACL.
 *
 */
@Configuration
@ComponentScan(basePackageClasses = CadastroAutorService.class)
public class ServiceConfig {
	
	
	
}
