package testes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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

import com.github.springtestdbunit.DbUnitTestExecutionListener;

import br.uepb.biblio.config.H2Config;
import br.uepb.biblio.config.SecurityConfig;
import br.uepb.biblio.config.ServiceConfig;
import br.uepb.biblio.config.init.AppInitializer;
import br.uepb.biblio.service.CadastroAutorService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Autor;
import br.uepb.model.jpaEntity.EntityAutor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class,H2Config.class,ServiceConfig.class,SecurityConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
@WithMockUser(username="caio",password="123",roles={"Funcionario","Administrador"})
public class TesteCaseAutor {
	private Autor autor;
		
	@Autowired
	private CadastroAutorService autorDao;
	
	@Before
	public void setup() throws Exception{
		autor = new Autor();
	}
	
	@Test
	public void testeCreateAutor(){
		autor.setNome("Autor1");
		assertTrue(autorDao.salvar(autor)!=null);
		
		autor = new Autor();
		try {
			assertFalse(autorDao.salvar(autor)==null);//lança pois faltam informações
			fail();
		} catch (Exception e) {
			autor.setNome("Autor1");
			try {
				assertTrue(autorDao.salvar(autor)!=null);//lança exception ItemDuplicado
				fail();
			} catch (ItemDuplicadoException e1) {
				
			}
		}
		
		
	}
	
	@Test
	public void testeRemoveAutor(){
		
		autor.setNome("Autor2");
		assertTrue(autorDao.salvar(autor)!=null);
		autor.setNome("Autor3");
		assertTrue(autorDao.salvar(autor)!=null);
		
		List<EntityAutor> lista = new ArrayList<EntityAutor>();
		lista = autorDao.buscarPorNome("Autor");
		
		for(EntityAutor a:lista){
			assertTrue(autorDao.remover(a));
		}
	}
	
	@Test
	public void testeUpdateAutor(){
		autor.setNome("Autor1");
		assertTrue(autorDao.salvar(autor)!=null);
		List<EntityAutor> lista = new ArrayList<EntityAutor>();
		lista = autorDao.buscarPorNome("Autor1");
		autor = new Autor(lista.get(0));
		autor.setNome("AutorNovo");
		assertTrue(autorDao.atualizar(autor));
		
		autor.setNome("Autor");
		lista = new ArrayList<EntityAutor>();
		lista = autorDao.buscarPorNome("Autor");
		for(EntityAutor a:lista){
			assertTrue(autorDao.remover(a));
		}
	}

	
}
