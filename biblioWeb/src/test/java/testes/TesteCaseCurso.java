package testes;

import static org.junit.Assert.*;

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
import br.uepb.biblio.service.CadastroAreaConhecimento;
import br.uepb.biblio.service.CadastroCursosService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.AreaConhecimento;
import br.uepb.model.Curso;
import br.uepb.model.enums.Tipo_curso;
import br.uepb.model.jpaEntity.EntityCurso;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class,H2Config.class,ServiceConfig.class,SecurityConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
@WithMockUser(username="caio",password="123",roles={"Funcionario","Administrador"})
public class TesteCaseCurso {
	private Curso curso;
	private AreaConhecimento area;
	
	@Autowired
	private CadastroAreaConhecimento areaDao;
	
	@Autowired
	private CadastroCursosService cursoDao;
	
	@Before
	public void setup() throws Exception{
		curso = new Curso();
		area = new AreaConhecimento();
		area.setNome("EXATAS");
		areaDao.salvar(area);
		area = new AreaConhecimento(areaDao.buscarPorNome("EXATAS").get(0));
	}
	
	@Test(expected=Exception.class)
	public void testeCreateCurso(){
		
		curso.setNome("Computação");
		curso.setSigla("CC");
		curso.setTipo(Tipo_curso.GRADUACAO);
		curso.setArea(area);
		assertTrue(cursoDao.salvar(curso)!=null);
		
		List<EntityCurso> lista = new ArrayList<EntityCurso>();
		lista = cursoDao.buscarPorNome("Computação");
		for(EntityCurso c : lista){
			assertTrue(cursoDao.remover(c.getId()));
		}
		
		assertTrue(areaDao.remover(area.getId()));
		
		curso = new Curso();
		curso.setNome("Teste");
		assertFalse(cursoDao.salvar(curso)!=null);//Lança exception pois faltam informações
	}
	
	@Test
	public void testeCreateCursoDuplicado(){
		
		curso.setNome("Computação");
		curso.setSigla("CC");
		curso.setTipo(Tipo_curso.GRADUACAO);
		curso.setArea(area);
		assertTrue(cursoDao.salvar(curso)!=null);
		
			
		curso.setNome("Computação");
		curso.setSigla("CP");
		curso.setTipo(Tipo_curso.GRADUACAO);
		curso.setArea(area);
		try {
			assertTrue(cursoDao.salvar(curso)!=null);//Lança exception pois o item é duplicado
			fail();
		} catch (ItemDuplicadoException e) {
			List<EntityCurso> lista = new ArrayList<EntityCurso>();
			lista = cursoDao.buscarPorNome("Computação");
			for(EntityCurso c : lista){
				assertTrue(cursoDao.remover(c.getId()));
			}
			assertTrue(areaDao.remover(area.getId()));
		}
		
	}
	
	@Test
	public void testeRemoveCurso(){
		
		curso.setNome("Computação1");
		curso.setSigla("C1");
		curso.setTipo(Tipo_curso.GRADUACAO);
		curso.setArea(area);
		assertTrue(cursoDao.salvar(curso)!=null);
		
		curso.setNome("Computação2");
		curso.setSigla("C2");
		curso.setTipo(Tipo_curso.GRADUACAO);
		curso.setArea(area);
		assertTrue(cursoDao.salvar(curso)!=null);
		
		curso.setNome("Computação3");
		curso.setSigla("C3");
		curso.setTipo(Tipo_curso.GRADUACAO);
		curso.setArea(area);
		assertTrue(cursoDao.salvar(curso)!=null);
		
		List<EntityCurso> lista = new ArrayList<EntityCurso>();
		lista = cursoDao.buscarPorNome("Computação");
		for(EntityCurso c : lista){
			assertTrue(cursoDao.remover(c.getId()));
		}
		
		assertTrue(areaDao.remover(area.getId()));

	}
	
	@Test
	public void testeUpdateCurso(){
		
		curso.setNome("Computação1");
		curso.setSigla("CC");
		curso.setTipo(Tipo_curso.GRADUACAO);
		curso.setArea(area);
		assertTrue(cursoDao.salvar(curso)!=null);
		
		List<EntityCurso> lista = new ArrayList<EntityCurso>();
		lista = cursoDao.buscarPorNome("Computação1");
		curso = new Curso(lista.get(0));
		
		curso.setNome("ComputaçãoNovo");
		curso.setTipo(Tipo_curso.POS_GRADUCAO);
		curso.setArea(area);
		assertTrue(cursoDao.atualizar(curso));
		
		curso.setNome("Computação");
		lista = new ArrayList<EntityCurso>();
		lista = cursoDao.buscarPorNome("Computação");
		for(EntityCurso c : lista){
			assertTrue(cursoDao.remover(c.getId()));
		}
		
		assertTrue(areaDao.remover(area.getId()));
		
	}
}
