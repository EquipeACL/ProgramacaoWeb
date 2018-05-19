package testes;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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

import br.uepb.biblio.config.H2Config;
import br.uepb.biblio.config.SecurityConfig;
import br.uepb.biblio.config.ServiceConfig;
import br.uepb.biblio.config.init.AppInitializer;
import br.uepb.biblio.service.CrudAreaConhecimento;
import br.uepb.model.AreaConhecimento;
import br.uepb.model.jpaEntity.EntityAreaConhecimento;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class,H2Config.class,ServiceConfig.class,SecurityConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})

public class TesteCaseAreaConhecimento {

	private AreaConhecimento area;
	
	@Autowired
	private CrudAreaConhecimento areaDAO;
	
	@Before
	public void setup() throws Exception {
		area = new AreaConhecimento();
	}
	
	@Test
	public void testCreateSearchRemoveArea() {
		area.setNome("Saude");
		assertTrue(areaDAO.salvar(area)!=null);
		List<EntityAreaConhecimento> areaList = new ArrayList<EntityAreaConhecimento>();
		areaList = areaDAO.buscarPorNome("Saude");
		for(EntityAreaConhecimento a:areaList) {
			areaDAO.remover(a.getId());
		} 
	}
	
	@Test
	public void testUpdateSearchRemove() throws Exception {
		AreaConhecimento area2 = new AreaConhecimento();
		area2.setNome("Humanas");
		areaDAO.salvar(area2);
		List<EntityAreaConhecimento> listArea = new ArrayList<EntityAreaConhecimento>();
		listArea = areaDAO.buscarPorNome("Humanas");
		for(EntityAreaConhecimento ar: listArea) {
			ar.setNome("Saude");
			assertTrue(areaDAO.atualizar(new AreaConhecimento(ar)));
			assertTrue(areaDAO.remover(ar.getId()));
		}
		
	}
	
}
