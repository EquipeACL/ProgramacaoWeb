package testes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import br.uepb.biblio.config.H2Config;
import br.uepb.biblio.config.ServiceConfig;
import br.uepb.biblio.config.init.AppInitializer;
import br.uepb.biblio.service.CadastroOrientadorService;
import br.uepb.model.Orientador;
import br.uepb.model.jpaEntity.EntityOrientador;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class,H2Config.class,ServiceConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class TesteCaseOrientador {
	
	private Orientador orientador;
	
	@Autowired
	private CadastroOrientadorService orientadorDao;
	
	@Before
	public void setup() throws Exception{
		orientador = new Orientador();
	}
	
	@Test(expected=Exception.class)
	public void testeCreateOrientador(){
		orientador.setNome("Orientador1");
		orientador.setFormacao("Graduado");
		assertTrue(orientadorDao.salvar(orientador)!=null);
		
		orientador.setNome("Orientador1");
		List<EntityOrientador> lista = new ArrayList<EntityOrientador>();
		lista = orientadorDao.buscarPorNome("Orientador");
		for(EntityOrientador o:lista){
			assertTrue(orientadorDao.remover(o.getId()));
		}
		
		orientador = new Orientador();
		assertFalse(orientadorDao.salvar(orientador)!=null);//lança exception pois faltam informações
	}
	
	@Test
	public void testeRemoveOrientador(){
		orientador.setNome("Orientador1");
		orientador.setFormacao("Graducação");
		assertTrue(orientadorDao.salvar(orientador)!=null);
		orientador.setNome("Orientador2");
		orientador.setFormacao("PosGraducação");
		assertTrue(orientadorDao.salvar(orientador)!=null);
		orientador.setNome("Orientador3");
		orientador.setFormacao("Graducação");
		assertTrue(orientadorDao.salvar(orientador)!=null);
		
		List<EntityOrientador> lista = new ArrayList<EntityOrientador>();
		lista = orientadorDao.buscarPorNome("Orientador");
		for(EntityOrientador o:lista){
			assertTrue(orientadorDao.remover(o.getId()));
		}
	}
	
	@Test
	public void testeUpdateOrientador() throws Exception{
		orientador.setNome("Orientador1");
		orientador.setFormacao("Graducação");
		assertTrue(orientadorDao.salvar(orientador)!=null);
		List<EntityOrientador> lista = new ArrayList<EntityOrientador>();
		lista = orientadorDao.buscarPorNome("Orientador");
		orientador = new Orientador(lista.get(0));
		orientador.setNome("OrientadorNovo");
		orientador.setFormacao("PosGraducação");
		assertTrue(orientadorDao.atualizar(orientador));
		
		orientador.setNome("Orientador");
		lista = new ArrayList<EntityOrientador>();
		lista = orientadorDao.buscarPorNome("Orientador");
		for(EntityOrientador o:lista){
			assertTrue(orientadorDao.remover(o.getId()));
		}
	}

}
