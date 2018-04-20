package testes;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import br.uepb.dao.AreaConhecimentoDAO;
import br.uepb.dao.CursoDAO;
import br.uepb.model.AreaConhecimento;
import br.uepb.model.Curso;
import br.uepb.model.enums.Tipo_curso;

public class TesteCaseCurso {
	private Curso curso;
	private CursoDAO cursoDao;
	private AreaConhecimento area;
	private AreaConhecimentoDAO areaDao;
	
	@Before
	public void setup() throws Exception{
		curso = new Curso();
		cursoDao = new CursoDAO();
	}
	
	@Test
	public void testeCreateCurso(){
		curso.setNome("Computa��o");
		curso.setSigla("CC");
		curso.setTipo(Tipo_curso.GRADUACAO);
		curso.setArea(new AreaConhecimento(1,"EXATAS"));//A area de conhecimento ja deve estar cadastrada
		assertTrue(cursoDao.createItemDependencia(curso));
		
		curso = new Curso();
		curso.setNome("Teste");
		assertFalse(cursoDao.createItemDependencia(curso));
		
		curso.setNome("Computa��o");
		ArrayList<Curso> lista = new ArrayList<Curso>();
		lista = cursoDao.searchItemDependencia("Teste");
		for(Curso c : lista){
			assertTrue(cursoDao.removeItemDependencia(c));
		}
	}
	
	@Test
	public void testeRemoveCurso(){
		curso.setNome("Computa��o1");
		curso.setSigla("CC");
		curso.setTipo(Tipo_curso.GRADUACAO);
		curso.setArea(new AreaConhecimento(1,"EXATAS"));//A area de conhecimento ja deve est� cadastrada
		assertTrue(cursoDao.createItemDependencia(curso));
		
		curso.setNome("Computa��o2");
		curso.setTipo(Tipo_curso.GRADUACAO);
		curso.setArea(new AreaConhecimento(1,"EXATAS"));//A area de conhecimento ja deve est� cadastrada
		assertTrue(cursoDao.createItemDependencia(curso));
		
		curso.setNome("Computa��o3");
		curso.setTipo(Tipo_curso.GRADUACAO);
		curso.setArea(new AreaConhecimento(1,"EXATAS"));//A area de conhecimento ja deve est� cadastrada
		assertTrue(cursoDao.createItemDependencia(curso));
		
		curso.setNome("Computa��o");
		ArrayList<Curso> lista = new ArrayList<Curso>();
		lista = cursoDao.searchItemDependencia("Computa��o");
		for(Curso c : lista){
			assertTrue(cursoDao.removeItemDependencia(c));
		}
	}
	
	@Test
	public void testeUpdateCurso(){
		curso.setNome("Computa��o1");
		curso.setSigla("CC");
		curso.setTipo(Tipo_curso.GRADUACAO);
		curso.setArea(new AreaConhecimento(1,"EXATAS"));//A area de conhecimento ja deve est� cadastrada
		assertTrue(cursoDao.createItemDependencia(curso));
		
		ArrayList<Curso> lista = new ArrayList<Curso>();
		lista = cursoDao.searchItemDependencia("Computa��o1");
		curso = lista.get(0);
		
		curso.setNome("Computa��oNovo");
		curso.setTipo(Tipo_curso.POS_GRADUACAO);
		curso.setArea(new AreaConhecimento(1,"EXATAS"));//A area de conhecimento ja deve est� cadastrada
		assertTrue(cursoDao.updateItemDependencia(curso));
		
		curso.setNome("Computa��o");
		lista = new ArrayList<Curso>();
		lista = cursoDao.searchItemDependencia("Computa��o");
		for(Curso c : lista){
			assertTrue(cursoDao.removeItemDependencia(c));
		}
		
	}
}
