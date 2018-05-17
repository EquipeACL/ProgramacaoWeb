package testes;

import static org.junit.Assert.*;

import java.util.Date;

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
import br.uepb.biblio.service.CrudMidiasEletronicasService;
import br.uepb.model.acervo.MidiasEletronicas;
import br.uepb.model.enums.Tipo_midia;
import br.uepb.model.jpaEntity.acervo.EntityMidiasEletronicas;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class,H2Config.class,ServiceConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class TesteCaseMidia {
	
	private MidiasEletronicas midia;
	
	@Autowired
	private CrudMidiasEletronicasService midiaDao;
	
	@Before
	public void setup() throws Exception{
		midia = new MidiasEletronicas();		
	}
	
	@Test
	public void testeCreateMidia(){
		midia.setTitulo("Teste");
		midia.setTipo(Tipo_midia.CD);
		Date data = new Date(System.currentTimeMillis());
		midia.setData_gravacao(data);		
		assertTrue(midiaDao.salvar(midia)!=null);
		
		midia = new MidiasEletronicas();		
		try {
			assertFalse(midiaDao.salvar(midia)!=null);//lança excption pois faltam informações
			fail();
		} catch (Exception e) {
			for(EntityMidiasEletronicas m:midiaDao.buscarPorTitulo("Teste")){
				assertTrue(midiaDao.remover(m.getId()));
			}
		}		
		
	}
	
	@Test
	public void testeUpdateMidia(){
		midia.setTitulo("Teste");
		midia.setTipo(Tipo_midia.CD);
		Date data = new Date(System.currentTimeMillis());
		midia.setData_gravacao(data);		
		assertTrue(midiaDao.salvar(midia)!=null);
		
		midia = new MidiasEletronicas(midiaDao.buscarPorTitulo("Teste").get(0));
		midia.setTitulo("Novo Teste");
		midia.setTipo(Tipo_midia.DVD);
		
		assertTrue(midiaDao.atualizar(midia));
		
		for(EntityMidiasEletronicas m:midiaDao.buscarPorTitulo("Novo Teste")){
			assertTrue(midiaDao.remover(m.getId()));
		}
	}
	
	@Test
	public void testeRemoveMidia(){
		midia.setTitulo("Teste1");
		midia.setTipo(Tipo_midia.CD);
		Date data = new Date(System.currentTimeMillis());
		midia.setData_gravacao(data);		
		assertTrue(midiaDao.salvar(midia)!=null);
		
		midia.setTitulo("Teste2");
		midia.setTipo(Tipo_midia.CD);
		data = new Date(System.currentTimeMillis());
		midia.setData_gravacao(data);		
		assertTrue(midiaDao.salvar(midia)!=null);
		
		midia.setTitulo("Teste3");
		midia.setTipo(Tipo_midia.CD);
		data = new Date(System.currentTimeMillis());
		midia.setData_gravacao(data);		
		assertTrue(midiaDao.salvar(midia)!=null);
		
		for(EntityMidiasEletronicas m : midiaDao.buscarPorTitulo("Teste")){
			assertTrue(midiaDao.remover(m.getId()));
		}
	}

}
