package testes;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
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
import br.uepb.biblio.service.CadastroAutorService;
import br.uepb.biblio.service.CadastroOrientadorService;
import br.uepb.biblio.service.CidadesService;
import br.uepb.biblio.service.CrudTccService;
import br.uepb.biblio.service.exception.ItemNaoEncontradoException;
import br.uepb.model.Autor;
import br.uepb.model.Cidade;
import br.uepb.model.Orientador;
import br.uepb.model.acervo.Tcc;
import br.uepb.model.enums.Tipo_tcc;
import br.uepb.model.jpaEntity.EntityAutor;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppInitializer.class,H2Config.class,ServiceConfig.class})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        DbUnitTestExecutionListener.class})
public class TesteCaseTcc {

	Tcc trabalho;
	Autor autor;
	Orientador orientador;
	Cidade cidade;
	
	@Autowired
	CrudTccService trabalhoDao;
	
	@Autowired
	CadastroAutorService autorDao;
	
	@Autowired
	CadastroOrientadorService orientadorDao;
	
	@Autowired
	CidadesService cidadeDao;
	
	@Before
	public void setup() throws Exception {
		trabalho = new Tcc();
		autor = new Autor();
		orientador = new Orientador();
		cidade = new Cidade(1294,2504009,"Campina Grande","PB");
		cidadeDao.salvar(cidade);
		cidade = new Cidade(cidadeDao.buscarPorNome("Campina Grande"));
	}
	@Test
	public void testCreateDeleteTcc() throws ItemNaoEncontradoException {
		orientador.setNome("Orientador1");
		orientador.setFormacao("Graduado");
		assertTrue(orientadorDao.salvar(orientador)!=null);
		orientador = new Orientador(orientadorDao.buscarPorNome("Orientador1").get(0));
		
		autor.setNome("Autor1");
		autorDao.salvar(autor);
		autor = new Autor(autorDao.buscarPorNome("Autor1").get(0));
		
		
		trabalho.setAutor(autor);
		trabalho.setAno_defesa(new Date(System.currentTimeMillis()));
		trabalho.setCidade(cidade);
		trabalho.setTitulo("Trabalho de conclusao");
		trabalho.setTipo(Tipo_tcc.MONOGRAFIA);
		trabalho.setOrientador(orientador);
		
		assertTrue(trabalhoDao.salvar(trabalho)!=null);
		trabalho = new Tcc(trabalhoDao.buscarPorTitulo("Trabalho de conclusao").get(0));
		assertTrue(trabalhoDao.remover(trabalho.getId()));
		assertTrue(orientadorDao.remover(orientador.getId()));		
		
	}
	
	@After
	public void limpar() {
		ArrayList<Autor> lista = new ArrayList<>();
		for(EntityAutor a: autorDao.buscarPorNome("Autor")){
			lista.add(new Autor(a));
		}
		for(Autor a: lista) {
			autorDao.remover(a.getId());
			
		}
		
		cidadeDao.remover(cidade.getId());
		
	}

}
