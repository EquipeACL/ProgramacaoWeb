package br.uepb.biblio.config;

import java.math.BigDecimal;
import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ITemplateResolver;

import br.uepb.biblio.controller.LivrosController;
import br.uepb.model.formatter.DateToString;
import br.uepb.model.formatter.GrupoConverter;
import br.uepb.model.formatter.StringToDate;
import nz.net.ultraq.thymeleaf.LayoutDialect;
/**
 * Essa é a classe de configuração WEB, responsável por realizar as configurações na página sempre que for solicitada sua renderização.
 * @author EquipeACL
 *
 */
@Configuration
@ComponentScan(basePackageClasses = { LivrosController.class })
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
	
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
		this.applicationContext = applicationContext;
	}
	/**
	 * Método de configuração do Thymeleaf. Esse método especifica que a engine utilizada no front vai ser o Thymeleaf e especifica a configuração de carateres da página em UTF-8.
	 * @return resolver, que é o objeto do tipo Thymeleaf com as configurações.
	 */

	@Bean
	public ViewResolver viewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		resolver.setCharacterEncoding("UTF-8");
		return resolver;
	}
	
	/**
	 * Método de configuração do TemplateEngine. Esse método valida a utilização do TemplateResolver.
	 * @return engine, que é o objeto do tipo TemplateEngine configurado.
	 */
	
	@Bean
	public TemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setEnableSpringELCompiler(true);
		engine.setTemplateResolver(templateResolver());
		engine.addDialect(new LayoutDialect());
		
		return engine;
	}
	
	/**
	 * Método de configuração do TemplateResolver. Serve para resolver os templates do HTML e aplicar a extensão do arquivo configurado para .html.
	 * @return resolver, que é o objeto do tipo TemplateResolver configurado.
	 */
	//resolve os templates do html e diz onde estão
	private ITemplateResolver templateResolver() {
		SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
		resolver.setApplicationContext(applicationContext);
		resolver.setPrefix("classpath:/templates/");
		resolver.setCharacterEncoding("UTF-8");
		resolver.setSuffix(".html");
		resolver.setTemplateMode(TemplateMode.HTML);
		return resolver;
	}
	
	/**
	 * Método responsável por procurar todos os recursos estáticos aplicados na view no diretório especificado.
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	//vai procurar todos os recursos estáticos aqui
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		
	}
	
	/**
	 * Método de conversão de dados. Esse método é responsável por estabelecer máscaras para formatação de dados que serão renderizados na view.
	 * @return conversionService, que é o objeto default do serviço de conversão com as formatações definidas.
	 */
	@Bean
	public FormattingConversionService mvcConversionService() {
		DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
		
		NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00");
		conversionService.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);
		
		NumberStyleFormatter integerFormatter = new NumberStyleFormatter("#,##0");
		conversionService.addFormatterForFieldType(Integer.class, integerFormatter);
		
		conversionService.addConverter(new StringToDate());
		
		conversionService.addConverter(new DateToString());
		
		conversionService.addConverter(new GrupoConverter());
		
		return conversionService;
	}
	
	/**
	 * Método de definir região. Esse método é responsável por definir a região da linguagem que é utilizada nas views.
	 * @return new FixedLocaleResolver(new Locale("pt","BR")); , que é o objeto da configuração da linguagem Português Brasileiro.
	 */
	//Define a região da linguagem utlizada nas views
	@Bean LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt","BR"));
	}
	
}
