package testes;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import br.uepb.dao.AreaConhecimentoDAO;
import br.uepb.dao.TemaDAO;
import br.uepb.model.AreaConhecimento;
import br.uepb.model.Tema;

public class TesteCaseTema {

	private Tema tema;
	private TemaDAO temaDAO;
	private AreaConhecimento area;
	private AreaConhecimentoDAO areaDAO;
	@Before
	public void setup() throws Exception {
		tema = new Tema();
		temaDAO = new TemaDAO();
		area = new AreaConhecimento();
		areaDAO = new AreaConhecimentoDAO();
	}
	@Test
	public void createTema() {
		assertFalse(temaDAO.createItemDependencia(tema));
		tema.setNome("Filosofia");
		assertFalse(temaDAO.createItemDependencia(tema));
		area.setNome("COMPUTACAO");
		area.setId(1);
		tema.setArea(area);
		assertTrue(temaDAO.createItemDependencia(tema));
		tema = temaDAO.searchItemDependencia("Filosofia").get(0);
		temaDAO.removeItemDependencia(tema);
	}
	
	@Test
	public void removeTema() {
		Tema tema2 = new Tema();
		tema2.setNome("Programacao");
		area.setNome("COMPUTACAO");
		area.setId(1);
		tema2.setArea(area);
		temaDAO.createItemDependencia(tema2);
		tema2 = temaDAO.searchItemDependencia("Programacao").get(0);
		assertTrue(temaDAO.removeItemDependencia(tema2));		
	}
	
	@Test
	public void updateRemoveTema() {
		assertFalse(temaDAO.createItemDependencia(tema));
		tema.setNome("Filosofia");
		assertFalse(temaDAO.createItemDependencia(tema));
		area.setNome("COMPUTACAO");
		area.setId(1);
		tema.setArea(area);
		assertTrue(temaDAO.createItemDependencia(tema));
		ArrayList<Tema> listTema = new ArrayList<Tema>();
		listTema = temaDAO.searchItemDependencia("Filosofia");
		for(Tema t: listTema) {
			t.setNome("Humanas");
			temaDAO.updateItemDependencia(t);
			temaDAO.removeItemDependencia(t);
		}
				
	}
	
	

}
