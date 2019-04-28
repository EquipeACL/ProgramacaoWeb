package testRestController;

import java.nio.charset.Charset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ufab.AppInitializer;
/**
 * Classe de configuração para executar os testes unitarios.
 * @author EquipeACL
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
@TestPropertySource(locations="classpath:test.properties")
public class ConfigTeste {
	protected static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	protected MockMvc mockMvc;
	protected ObjectMapper MAPPER;
	
	@Test
	public void loadContext(){
		
	}

}
