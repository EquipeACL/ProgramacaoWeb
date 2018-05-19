package testes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Date;

import org.junit.After;
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

import br.uepb.biblio.config.H2Config;
import br.uepb.biblio.config.SecurityConfig;
import br.uepb.biblio.config.ServiceConfig;
import br.uepb.biblio.config.init.AppInitializer;
import br.uepb.biblio.repository.Alunos;
import br.uepb.biblio.repository.AreasConhecimento;
import br.uepb.biblio.repository.Cursos;
import br.uepb.biblio.service.CrudAlunoService;
import br.uepb.biblio.service.CrudAreaConhecimento;
import br.uepb.biblio.service.CrudCursosService;
import br.uepb.model.AreaConhecimento;
import br.uepb.model.Curso;
import br.uepb.model.enums.Tipo_curso;
import br.uepb.model.enums.Tipo_nivel;
import br.uepb.model.jpaEntity.usuarios.EntityAluno;
import br.uepb.model.usuarios.Aluno;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
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
	private CrudCursosService cursoDao;
	
	@Autowired
	private Cursos cursosRepository;
	
	@Autowired
	private CrudAreaConhecimento areasDao;
	
	@Autowired
	private AreasConhecimento areasRepository;
	
	@Autowired
	private Alunos alunosRepository;
	
	@Autowired
	private CrudAlunoService alunoDao;
	
	@Before
	public void setUp(){
		aluno = new Aluno();
		area = new AreaConhecimento();
		curso = new Curso();
		
	}
	
	@Test
	public void testeCreateAluno() throws Exception {
		
		area = new AreaConhecimento(1,"EXATAS");
		
		areasDao.salvar(area);
		area = new AreaConhecimento(areasDao.buscarPorNome("EXATAS").get(0));
		
		curso = new Curso("Computacao1","CC", area, Tipo_curso.GRADUACAO);
		assertTrue(cursoDao.salvar(curso)!=null);
		curso = new Curso(cursoDao.buscarPorNome("Computacao").get(0));
		
		aluno = new Aluno("123","123","CG","Lucas","Cleo","rua 2","123",curso,Tipo_nivel.GRADUACAO,"teste@gmail",new Date(System.currentTimeMillis()),1,"123","123");
		assertTrue(alunoDao.salvar(aluno)!=null);
		aluno = new Aluno(alunoDao.buscarPorNome("Lucas").get(0));
		
		assertTrue(alunoDao.remover(aluno.getId()));
		assertTrue(cursoDao.remover(curso.getId()));
		assertTrue(areasDao.remover(area.getId()));
	}
	
	@Test
	public void testeRemoveAluno() throws Exception {
		
		area = new AreaConhecimento(2,"HUMANAS");

		areasDao.salvar(area);
		area = new AreaConhecimento(areasDao.buscarPorNome("HUMANAS").get(0));
		
		
		curso = new Curso("Computacao2","CP", area, Tipo_curso.GRADUACAO);
		assertTrue(cursoDao.salvar(curso)!=null);
		curso = new Curso(cursoDao.buscarPorNome("Computacao").get(0));
		
		aluno = new Aluno("123","123","CG","Lucas","Cleo","rua 2","123",curso,Tipo_nivel.GRADUACAO,"teste@gmail",new Date(System.currentTimeMillis()),1,"123","123");
		assertTrue(alunoDao.salvar(aluno)!=null);
		aluno = new Aluno();
		aluno = new Aluno("456","456","CG","Lucas Cosmo","MaeLucas","rua 2","123",curso,Tipo_nivel.GRADUACAO,"teste@gmail",new Date(System.currentTimeMillis()),1,"789","789");
		assertTrue(alunoDao.salvar(aluno)!=null);
		aluno = new Aluno();
		aluno = new Aluno("789","789","CG","Lucas Rocha","MaeCaio","rua 2","123",curso,Tipo_nivel.GRADUACAO,"teste@gmail",new Date(System.currentTimeMillis()),1,"456","456");
		assertTrue(alunoDao.salvar(aluno)!=null);
		
		
		for(EntityAluno a:alunoDao.buscarPorNome("Lucas")){
			assertTrue(alunoDao.remover(a.getId()));
		}	
		
	}
	
		
	@Test
	public void testeSearchAluno() throws Exception {
		
		area = new AreaConhecimento(3,"NATURAIS");

		areasDao.salvar(area);
		area = new AreaConhecimento(areasDao.buscarPorNome("NATURAIS").get(0));
		
		curso = new Curso("Computacao4","PC", area, Tipo_curso.GRADUACAO);
		assertTrue(cursoDao.salvar(curso)!=null);
		curso = new Curso(cursoDao.buscarPorNome("Computacao").get(0));
		
		aluno = new Aluno("123","123","CG","Lucas","Cleo","rua 2","123",curso,Tipo_nivel.GRADUACAO,"teste@gmail",new Date(System.currentTimeMillis()),1,"789","789");
		assertTrue(alunoDao.salvar(aluno)!=null);
		aluno = new Aluno();
		
		Aluno a1 = new Aluno(alunoDao.buscarPorNome("Lucas").get(0));
		
		
		
		assertEquals(a1.getNome(), "Lucas");
		assertEquals(a1.getCpf(), "123");
		
		assertTrue(alunoDao.remover(a1.getId()));
				
		assertTrue(cursoDao.remover(a1.getCurso().getId()));
		
		assertTrue(areasDao.remover(a1.getCurso().getArea().getId()));
	}
	
	
	
}
