package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

import br.uepb.biblio.config.H2Config;
import br.uepb.biblio.config.SecurityConfig;
import br.uepb.biblio.config.ServiceConfig;
import br.uepb.biblio.config.init.AppInitializer;
import br.uepb.biblio.service.CadastroAutorService;
import br.uepb.biblio.service.CidadesService;
import br.uepb.biblio.service.CrudAnaisService;
import br.uepb.model.Autor;
import br.uepb.model.Cidade;
import br.uepb.model.acervo.Anal;
import br.uepb.model.enums.Tipo_anal;
import br.uepb.model.jpaEntity.EntityAutor;
import br.uepb.model.jpaEntity.acervo.EntityAnal;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class,H2Config.class,ServiceConfig.class,SecurityConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
WithSecurityContextTestExecutionListener.class})

public class TesteCaseAnais {
	private Autor autor;
	private Cidade cidade;
	private Anal anais;
	
	@Autowired
	private CrudAnaisService anaisDao;
	
	@Autowired
	private CadastroAutorService autorDao;
	
	@Autowired
	private CidadesService cidadesService;
	
	@Before
	public void setup() throws Exception {
		anais = new Anal();
		autor = new Autor();
		
		cidade = new Cidade(1294,2504009,"Campina Grande","PB");
		cidadesService.salvar(cidade);	
		cidade = new Cidade(cidadesService.buscarPorNome("Campina Grande"));
		autor.setNome("Autor1");
		autorDao.salvar(autor);
		autor = new Autor(autorDao.buscarPorNome("Autor1").get(0));
		
	}
	
	@Test
	public void testeCreateAnais() {
				
		anais.setAnoPublicacao(new Date(System.currentTimeMillis()));
		ArrayList<Autor> autores = new ArrayList<Autor>();
		for(EntityAutor a : autorDao.buscarPorNome("Autor1")){
			autores.add(new Autor(a));
		}
		anais.setAutores(autores);		
		anais.setLocal(cidade);
		anais.setNome_congresso("Congreso1");
		anais.setTipo(Tipo_anal.ARTIGO);
		anais.setTitulo("Titulo1");
		anais.setEdicao(1);
		assertTrue(anaisDao.salvar(anais)!=null);
		anais = new Anal(anaisDao.buscarPorTitulo("Titulo1").get(0));
		assertTrue(anaisDao.remover(anais.getId()));
		assertTrue(autorDao.remover(autor.getId()));
		assertTrue(cidadesService.remover(cidade.getId()));
	}
	
	@Test
	public void testeRemoveAnais() {
		anais.setAnoPublicacao(new Date(System.currentTimeMillis()));
		ArrayList<Autor> autores = new ArrayList<Autor>();
		for(EntityAutor a : autorDao.buscarPorNome("Autor1")){
			autores.add(new Autor(a));
		}
		anais.setAutores(autores);		
		anais.setLocal(cidade);
		anais.setNome_congresso("Congreso1");
		anais.setTipo(Tipo_anal.ARTIGO);
		anais.setTitulo("Titulo1");
		assertTrue(anaisDao.salvar(anais)!=null);
		
		autor = new Autor();
		autor.setNome("Autor2");
		autorDao.salvar(autor);
		autor = new Autor(autorDao.buscarPorNome("Autor2").get(0));
		
		anais = new Anal();
		anais.setAnoPublicacao(new Date(System.currentTimeMillis()));
		autores = new ArrayList<Autor>();
		for(EntityAutor a : autorDao.buscarPorNome("Autor2")){
			autores.add(new Autor(a));
		}
		anais.setAutores(autores);		
		anais.setLocal(cidade);
		anais.setNome_congresso("Congreso2");
		anais.setTipo(Tipo_anal.POSTER);
		anais.setTitulo("Titulo2");
		assertTrue(anaisDao.salvar(anais)!=null);
		
		
		for(EntityAnal a : anaisDao.buscarPorTitulo("Titulo")){
			assertTrue(anaisDao.remover(a.getId()));
		}
		
		for(EntityAutor aut : autorDao.buscarPorNome("Autor")){
			autorDao.remover(aut.getId());
		}
		cidadesService.remover(cidade.getId());
		
	}
	
	@Test
	public void testeUpdateAnais() throws Exception {
				
		anais.setAnoPublicacao(new Date(System.currentTimeMillis()));
		ArrayList<Autor> autores = new ArrayList<Autor>();
		for(EntityAutor a : autorDao.buscarPorNome("Autor1")){
			autores.add(new Autor(a));
		}
		anais.setAutores(autores);		
		anais.setLocal(cidade);
		anais.setNome_congresso("Congreso1");
		anais.setTipo(Tipo_anal.ARTIGO);
		anais.setTitulo("Titulo1");
		assertTrue(anaisDao.salvar(anais)!=null);
				
		anais = new Anal(anaisDao.buscarPorTitulo("Titulo1").get(0));
		
		anais.setTitulo("NovoTitulo");
		anais.setTipo(Tipo_anal.POSTER);
		assertTrue(anaisDao.atualizar(anais));
		
		anais = new Anal(anaisDao.buscarPorTitulo("NovoTitulo").get(0));
		assertEquals("NovoTitulo",anais.getTitulo());
		assertEquals(Tipo_anal.POSTER,anais.getTipo());
		
		for(EntityAnal a : anaisDao.buscarPorTitulo("Titulo")){
			assertTrue(anaisDao.remover(a.getId()));
		}
		
		for(EntityAutor aut : autorDao.buscarPorNome("Autor")){
			autorDao.remover(aut.getId());
		}
		cidadesService.remover(cidade.getId());
		
	}
		
	
}
