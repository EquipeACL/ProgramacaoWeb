package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
import br.uepb.biblio.service.CrudFuncionarioService;
import br.uepb.model.usuarios.Funcionario;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class,H2Config.class,ServiceConfig.class,SecurityConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
WithSecurityContextTestExecutionListener.class})
public class TesteCaseFuncionario {
	private Funcionario funcionario;
	
	
	@Autowired
	private CrudFuncionarioService funcionarioDAO;
	
	@Before
	public void setup() {
		funcionario = new Funcionario();

	} 
	
	@Test
	public void testeCreateFuncionario() {
		funcionario = new Funcionario("123.040.484-33", "Funcionario1", "34655", "Brasil", "Rua da ladeira", "(83) 99864-6328", "lucas", "123", "lucas", "123");
		funcionario.setLogin("lucas");
		assertTrue(funcionarioDAO.salvar(funcionario)!=null);
		funcionarioDAO.remover(funcionarioDAO.buscarPorNome("Funcionario1").get(0).getId());
	}
	
	@Test
	public void testeRemoveFuncionario() {
		funcionario = new Funcionario("123.040.484-21", "Funcionario2", "34656", "Brasil", "Rua da ladeira", "(83) 99864-6328", "joaozinho", "123", "joaozinho", "123");
		funcionario.setLogin("joaozinho");
		assertTrue(funcionarioDAO.salvar(funcionario)!=null);
		assertTrue(funcionarioDAO.remover(funcionarioDAO.buscarPorNome("Funcionario2").get(0).getId()));
	}
	
}
