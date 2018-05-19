package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Date;

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
import br.uepb.biblio.service.CadastroAlunoService;
import br.uepb.biblio.service.CadastroAreaConhecimento;
import br.uepb.biblio.service.CadastroCursosService;
import br.uepb.model.AreaConhecimento;
import br.uepb.model.Curso;
import br.uepb.model.enums.Tipo_curso;
import br.uepb.model.enums.Tipo_nivel;
import br.uepb.model.usuarios.Aluno;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class,H2Config.class,ServiceConfig.class,SecurityConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
WithSecurityContextTestExecutionListener.class})
public class TesteCaseAluno {
	private Aluno aluno;

	private AreaConhecimento area;
	private Curso curso;
	
	@Autowired
	private CadastroCursosService cursoDao;
	
	@Autowired
	private CadastroAreaConhecimento areasDao;
	
	@Autowired
	private CadastroAlunoService alunoDao;
	
	@Before
	public void setup(){
		
		area = new AreaConhecimento(1,"EXATAS");
		curso = new Curso("Computacao","CP", area, Tipo_curso.GRADUACAO);
		areasDao.salvar(area);
		area = new AreaConhecimento(areasDao.buscarPorNome("EXATAS").get(0));
		
		assertTrue(cursoDao.salvar(curso)!=null);
		curso = new Curso(cursoDao.buscarPorNome("Computacao").get(0));
		
	}
	
	@Test
	public void testeCreateAluno() throws Exception {
		aluno = new Aluno("123","123","CG","Lucas","Cleo","rua 2","123",curso,Tipo_nivel.GRADUACAO,"teste@gmail",new Date(System.currentTimeMillis()),1,"123","123");
		assertTrue(alunoDao.salvar(aluno)!=null);
		aluno = alunoDao.buscarPorNome("Lucas").get(0);
		assertTrue(alunoDao.remover(aluno.getId()));
		assertTrue(cursoDao.remover(curso.getId()));
		assertTrue(areasDao.remover(area.getId()));
	}
	
	@Test
	public void testeRemoveAluno() throws Exception {
		aluno = new Aluno("123","123","CG","Lucas","Cleo","rua 2","123",curso,Tipo_nivel.GRADUACAO,"teste@gmail",new Date(System.currentTimeMillis()),1,"123","123");
		assertTrue(alunoDao.salvar(aluno)!=null);
		aluno = new Aluno("123","123","CG","Lucas","Cleo","rua 2","123",curso,Tipo_nivel.GRADUACAO,"teste@gmail",new Date(System.currentTimeMillis()),1,"123","123");
		
		assertTrue(alunoDao.salvar(aluno)!=null);
		
		for(Aluno a:alunoDao.buscarPorNome("Lucas")){
			assertTrue(alunoDao.remover(a.getId()));
		}	
		assertTrue(cursoDao.remover(curso.getId()));
		assertTrue(areasDao.remover(area.getId()));
	}
	
	@Test
	public void testeUpdateAluno() throws Exception {
		aluno = new Aluno("123","123","CG","Lucas","Cleo","rua 2","123",curso,Tipo_nivel.GRADUACAO,"teste@gmail",new Date(System.currentTimeMillis()),1,"123","123");
		assertTrue(alunoDao.salvar(aluno)!=null);
		aluno = alunoDao.buscarPorNome("Lucas").get(0);
		aluno.setNome("Lucas Cosmo");
		aluno.setEmail("email@gmail.com");
		assertTrue(alunoDao.atualizar(aluno));	
		assertEquals("Lucas Cosmo",aluno.getNome());
		assertEquals("email@gmail.com",aluno.getEmail());
		assertTrue(alunoDao.remover(aluno.getId()));
		assertTrue(cursoDao.remover(curso.getId()));
		assertTrue(areasDao.remover(area.getId()));
	}
	
}
