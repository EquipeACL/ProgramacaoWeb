package testes;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
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

import com.github.springtestdbunit.DbUnitTestExecutionListener;

import br.uepb.biblio.config.H2Config;
import br.uepb.biblio.config.SecurityConfig;
import br.uepb.biblio.config.ServiceConfig;
import br.uepb.biblio.config.init.AppInitializer;
import br.uepb.biblio.service.CadastroEditoraService;
import br.uepb.biblio.service.CrudRevistaService;
import br.uepb.model.Editora;
import br.uepb.model.acervo.Revista;
import br.uepb.model.jpaEntity.acervo.EntityRevista;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class,H2Config.class,ServiceConfig.class,SecurityConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
@WithMockUser(username="caio",password="123",roles={"Funcionario","Administrador"})
public class TesteCaseRevista {
	private Revista rev;
	private Editora ed;
	
	@Autowired
	private CrudRevistaService revDao;
	
	@Autowired
	private CadastroEditoraService edDao;
	
	
	@Before
	public void setup() throws Exception {
		rev = new Revista();
		ed = new Editora();
		
		ed.setNome("Editora Tec");		
		edDao.salvar(ed);		
		ed = new Editora(edDao.buscarPorNome(ed.getNome()).get(0));
	}
	
	@Test
	public void testCreate() {		
		
		rev.setTitulo("MasterClass");
		rev.setNum_pag(355);
		rev.setEditora(ed);
		Date data = new Date(System.currentTimeMillis());
		rev.setData(data);
		
		rev.setEdicao(5);
		
		assertTrue(revDao.salvar(rev)!=null);
		rev = new Revista(revDao.buscarPorTitulo(rev.getTitulo()).get(0));
		assertTrue(revDao.remover(rev.getId()));
		assertTrue(edDao.remover(ed.getId()));
		
	}
	@Test
	public void testDelete() {
				
		rev.setTitulo("MasterClass1");
		rev.setNum_pag(355);
		rev.setEditora(ed);
		Date data = new Date(System.currentTimeMillis());
		rev.setData(data);		
		rev.setEdicao(5);		
		assertTrue(revDao.salvar(rev)!=null);
		
		rev.setTitulo("MasterClass2");
		rev.setNum_pag(35);
		rev.setEditora(ed);
		data = new Date(System.currentTimeMillis());
		rev.setData(data);		
		rev.setEdicao(5);		
		assertTrue(revDao.salvar(rev)!=null);
		
		for(EntityRevista r : revDao.buscarPorTitulo("MasterClass")){
			assertTrue(revDao.remover(r.getId()));
		}		
		assertTrue(edDao.remover(ed.getId()));
		
	}
	
	@Test
	public void testBusca() {
			
		rev.setTitulo("MasterClass");
		rev.setNum_pag(355);
		rev.setEditora(ed);
		Date data = new Date(System.currentTimeMillis());
		rev.setData(data);
		
		rev.setEdicao(5);
		
		assertTrue(revDao.salvar(rev)!=null);
		
		List<EntityRevista> lista = revDao.buscarPorTitulo("MasterClass");
		
		assertFalse(rev.getId() == lista.get(0).getId());
		assertEquals(rev.getTitulo(),lista.get(0).getTitulo());		
		
		
		for(EntityRevista r:lista) {
			assertTrue(revDao.remover(r.getId()));
		}
		
		assertTrue(edDao.remover(ed.getId()));
	}
	
}
