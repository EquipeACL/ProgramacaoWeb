package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

import br.uepb.biblio.config.H2Config;
import br.uepb.biblio.config.ServiceConfig;
import br.uepb.biblio.config.init.AppInitializer;
import br.uepb.biblio.service.CrudJornalService;
import br.uepb.model.acervo.Jornal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class,H2Config.class,ServiceConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class TesteCaseJornal {

	private Jornal jornal;
	
	@Autowired
	private CrudJornalService jornalDao;
	

	@Before
	public void setup() throws Exception{
		jornal = new Jornal();
	}
	
	@Test
	public void testeCreateJornal() {
		jornal.setTitulo("Jornal da Correio");
		jornal.setEdicao(2);
		Date data = new Date(System.currentTimeMillis());
		jornal.setData(data);
		assertTrue(jornalDao.salvar(jornal)!=null);
		
	}
	@Test
	public void testeBuscaJornal() {
		jornal.setTitulo("Jornal da Paraiba");
		jornal.setEdicao(5);
		Date data = new Date(System.currentTimeMillis());
		jornal.setData(data);
			
		assertTrue(jornalDao.salvar(jornal)!=null);	
		assertEquals(jornal.getTitulo(),jornalDao.buscarPorTitulo("Jornal da Paraiba").get(0).getTitulo());
	
	}
	
	@Test
	public void testRemoveJornal() {
		jornal.setTitulo("Jornal da Paraiba");
		jornal.setEdicao(5);
		Date data = new Date(System.currentTimeMillis());
		jornal.setData(data);
		jornal.setId(1);
		
		assertTrue(jornalDao.remover(2));
		assertFalse(jornalDao.remover(-1));
	}

	@Test
	public void testUpdateJornal(){
		jornal.setTitulo("Jornal da Rua");
		jornal.setEdicao(5);
		Date data = new Date(System.currentTimeMillis());
		jornal.setData(data);
		jornal.setId(2);
		assertTrue(jornalDao.atualizar(jornal));
		assertTrue(jornalDao.buscarPorTitulo("Jornal da Rua")!=null);
	}
}
