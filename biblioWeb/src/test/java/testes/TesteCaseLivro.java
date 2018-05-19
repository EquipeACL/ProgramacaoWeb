package testes;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
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
import br.uepb.biblio.service.CadastroAreaConhecimento;
import br.uepb.biblio.service.CadastroAutorService;
import br.uepb.biblio.service.CadastroEditoraService;
import br.uepb.biblio.service.CadastroTemaService;
import br.uepb.biblio.service.CrudLivroService;
import br.uepb.model.AreaConhecimento;
import br.uepb.model.Autor;
import br.uepb.model.Editora;
import br.uepb.model.Tema;
import br.uepb.model.acervo.Livro;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class,H2Config.class,ServiceConfig.class,SecurityConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
@WithMockUser(username="caio",password="123",roles={"Funcionario","Administrador"})
public class TesteCaseLivro {

	@Autowired
	private CrudLivroService livroDao;

	@Autowired
	private CadastroEditoraService editoraDao;
	
	@Autowired
	private CadastroAreaConhecimento areaDao;
	
	@Autowired
	private CadastroTemaService temaDao;

	@Autowired
	private CadastroAutorService autorDao;
	
	private Livro livro;
	private Editora editora;
	private AreaConhecimento area;
	private Tema tema;
	private Autor autor1;
	private Autor autor2;
	
	@Before
	public void setup() throws Exception{

		editora = new Editora(1, "EditoraTesteLivrooo");
		area = new AreaConhecimento();
		tema = new Tema();
		autor1 = new Autor();
		autor2 = new Autor();
		
		editora.setNome("EditoraTesteLivro");
	
		System.out.println(editora.getNome());
		assertTrue(editoraDao.salvar(editora)!=null);
		editora = new Editora(editoraDao.buscarPorNome("EditoraTesteLivro").get(0));
				
		area.setNome("AreaTesteLivro");
		areaDao.salvar(area);
		area = new AreaConhecimento(areaDao.buscarPorNome("AreaTesteLivro").get(0));
		
		tema.setNome("TemaTesteLivro");
		tema.setArea(area);
		temaDao.salvar(tema);
		tema = new Tema(temaDao.buscarPorNome("TemaTesteLivro").get(0));
		
		autor1.setNome("Autor1");
		autor2.setNome("Autor2");
		autorDao.salvar(autor1);
		autorDao.salvar(autor2);
		autor1 = new Autor(autorDao.buscarPorNome("Autor1").get(0));
		autor2 = new Autor( autorDao.buscarPorNome("Autor2").get(0));
	}
	
	@Test
	public void testeCreateLivro(){
		
		livro = new Livro(123456, "Livro Teste", new ArrayList<Autor>(){{add(autor1);add(autor2);}}, 
				editora, new Date(System.currentTimeMillis()), 1, 200, tema);

		assertTrue(livroDao.salvar(livro)!=null);
		livro = new Livro(livroDao.buscarPorTitulo("Livro Teste").get(0));
		assertTrue(livroDao.remover(livro.getId()));
	}
	
	@Test
	public void testeUpdateLivro(){
		livro = new Livro(123456, "Livro Teste", new ArrayList<Autor>(){{add(autor1);add(autor2);}}, 
				editora, new Date(System.currentTimeMillis()), 1, 200, tema);
		assertTrue(livroDao.salvar(livro)!=null);
		livro = new Livro(livroDao.buscarPorTitulo("Livro Teste").get(0));
		livro.setTitulo("NovoTitulo");
		assertTrue(livroDao.atualizar(livro));
		livro = new Livro(livroDao.buscarPorTitulo("NovoTitulo").get(0));
		assertTrue(livroDao.remover(livro.getId()));
	}
	
	@Test
	public void testeSearchLivro(){
		livro = new Livro(123456, "Livro Teste", new ArrayList<Autor>(){{add(autor1);add(autor2);}}, 
				editora, new Date(System.currentTimeMillis()), 1, 200, tema);
		assertTrue(livroDao.salvar(livro)!=null);
		livro = new Livro(livroDao.buscarPorTitulo("Livro Teste").get(0));
		livro.setTitulo("NovoTitulo");
		assertTrue(livroDao.atualizar(livro));
		livro = new Livro(livroDao.buscarPorTitulo("NovoTitulo").get(0));
		assertEquals(livro.getTitulo(),livroDao.buscarPorTitulo("NovoTitulo").get(0).getTitulo());
		//preciso comprar os valores de livro e novoLivro
		assertTrue(livroDao.remover(livro.getId()));
	}
	
	@After
	public void clean(){
		temaDao.remover(tema.getId());
		areaDao.remover(area.getId());		
		editoraDao.remover(editora.getId());		
		autorDao.remover(autor1.getId());
		autorDao.remover(autor2.getId());
		
	}

}

