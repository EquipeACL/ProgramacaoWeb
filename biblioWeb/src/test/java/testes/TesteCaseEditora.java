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
		
	}

	@Test
	public void removeEditora(){
		editora = new Editora(1,"SARAAAAIVA");
		assertTrue(editoraDAO.salvar(editora)!=null);
		editora = new Editora(editoraDAO.buscarPorNome("SARAAAAIVA").get(0));
		assertTrue(editoraDAO.remover(editora.getId()));
	}
}
