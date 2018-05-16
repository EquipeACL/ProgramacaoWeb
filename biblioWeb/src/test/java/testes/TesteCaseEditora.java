package testes;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import br.uepb.biblio.service.CadastroEditoraService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Editora;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class,H2Config.class,ServiceConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class TesteCaseEditora {
	
	private Editora editora;
	
	@Autowired
	private CadastroEditoraService editoraDAO;
	
	@Before
	public void setup(){
		editora = new Editora(1, "SARAAAAAIVA");
	}
	
	@Test
	public void createEditora() {
		editora.setNome("SARAAAAIVA");
		assertTrue(editoraDAO.salvar(editora)!=null);
		
		editora = new Editora();
		
		try{
			assertFalse(editoraDAO.salvar(editora)==null);
			fail();
		} catch (Exception e1){
			editora.setNome("SARAAAAIVA");
			try{
				assertFalse(editoraDAO.salvar(editora)==null);
				fail();
			} catch (ItemDuplicadoException e2){
				
			}
		}
		
	}

	@Test
	public void removeEditora(){
		editora = new Editora(1,"SARAAAAIVA");
		editoraDAO.salvar(editora);
		
		assertTrue(editoraDAO.remover(editora.getId()));
	}
}
