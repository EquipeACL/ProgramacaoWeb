package testes;

import static org.junit.Assert.*;

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
import br.uepb.biblio.service.CadastroAreaConhecimento;
import br.uepb.biblio.service.CadastroTemaService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.AreaConhecimento;
import br.uepb.model.Tema;
import br.uepb.model.jpaEntity.EntityTema;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class,H2Config.class,ServiceConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class TesteCaseTema {

	private Tema tema;
	private AreaConhecimento area;
	
	@Autowired
	private CadastroTemaService temaDAO;
	
	@Autowired
	private CadastroAreaConhecimento areaDAO;
	
	
	@Before
	public void setup() throws Exception {
		tema = new Tema();
		area = new AreaConhecimento();
		area.setNome("EXATAS");
		areaDAO.salvar(area);
		area = new AreaConhecimento(areaDAO.buscarPorNome("EXATAS").get(0));
		
	}
	@Test
	public void createTema() {
		tema.setNome("Filosofia");
		tema.setArea(area);
		assertTrue(temaDAO.salvar(tema)!=null);
		tema = new Tema(temaDAO.buscarPorNome("Filosofia").get(0));
		temaDAO.remover(tema.getId());
		assertTrue(areaDAO.remover(area.getId()));
	}
	@Test
	public void createTemaDuplicado() {
		tema.setNome("Filosofia");
		tema.setArea(area);
		assertTrue(temaDAO.salvar(tema)!=null);
		
		tema.setNome("Filosofia");
		tema.setArea(area);
		try {
			assertTrue(temaDAO.salvar(tema)!=null);//lança expetion ItemDuplicado
			fail();
		} catch (ItemDuplicadoException e) {
			tema = new Tema(temaDAO.buscarPorNome("Filosofia").get(0));
			temaDAO.remover(tema.getId());
			assertTrue(areaDAO.remover(area.getId()));
		}
		
		
	}
	
	@Test
	public void removeTema() {
		Tema tema2 = new Tema();
		tema2.setNome("Programacao");
		tema2.setArea(area);
		temaDAO.salvar(tema2);
		tema2 = new Tema(temaDAO.buscarPorNome("Programacao").get(0));
		assertTrue(temaDAO.remover(tema2.getId()));		
		assertTrue(areaDAO.remover(area.getId()));
	}
	
	@Test
	public void updateRemoveTema() throws Exception {
		
		tema.setNome("Filosofia");
		tema.setArea(area);
		assertTrue(temaDAO.salvar(tema)!=null);
		List<EntityTema> listTema = new ArrayList<EntityTema>();
		listTema = temaDAO.buscarPorNome("Filosofia");
		for(EntityTema t: listTema) {
			t.setNome("Humanas");
			assertTrue(temaDAO.atualizar(new Tema(t)));
			assertTrue(temaDAO.remover(t.getId()));
		}
		assertTrue(areaDAO.remover(area.getId()));
	}
	
	

}
