package testRestController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.edu.ufab.controller.AutorController;
import br.edu.ufab.model.entities.Autor;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Teste unitario da classe AutorController 
 * @author EquipeACL
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestControllerAutor extends ConfigTeste{
		
	@Autowired
	private AutorController autorController;
	
    
    @Before
    public void setup(){
    	this.MAPPER = new ObjectMapper();
    	this.mockMvc = MockMvcBuilders.standaloneSetup(autorController).build();
    }
    
    @Test
    public void teste01AdicionarAutor() throws IOException, Exception{
    	Autor a = new Autor();
    	
    	a.setNome("Carlos Drummond");
    	mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(a)))
                .andExpect(status().isOk());
    	
    	a = new Autor();
    	
    	mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(a)))
                .andExpect(status().isBadRequest());
    	
    	a.setNome("Machado de Assis");
    	mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(a)))
                .andExpect(status().isOk());
    }
    
  
    
    @Test
	public void teste02BuscarTodos() throws Exception {
    	    	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/autores")).andReturn().getResponse().getContentAsString();
    	
        List<Autor> autores = MAPPER.readValue(response,
           MAPPER.getTypeFactory().constructCollectionType(List.class, Autor.class));
		assertTrue(autores.size()==2);
		assertEquals(autores.get(0).getNome(), "Carlos Drummond");
		assertEquals(autores.get(1).getNome(), "Machado de Assis");
	}
    
    @Test
	public void teste03BuscarUm() throws Exception {
    	   	
		String response = mockMvc.perform(MockMvcRequestBuilders.get("/autores")).andReturn().getResponse()
				.getContentAsString();

		List<Autor> autores = MAPPER.readValue(response,
				MAPPER.getTypeFactory().constructCollectionType(List.class, Autor.class));

		Autor autor1 = autores.get(0);
		response = mockMvc.perform(MockMvcRequestBuilders.get("/autores/"+autor1.getId())).andReturn().getResponse().getContentAsString();
    	
		Autor autor = MAPPER.readValue(response,Autor.class);
		
		assertEquals(autor.getId(), autor1.getId());
		assertEquals(autor.getNome(),autor1.getNome());
	}
    
    @Test
	public void teste04AtualizarAutor() throws Exception {
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/autores")).andReturn().getResponse()
				.getContentAsString();

		List<Autor> autores = MAPPER.readValue(response,
				MAPPER.getTypeFactory().constructCollectionType(List.class, Autor.class));

		Autor autor1 = autores.get(0);
    	   	
		response = mockMvc.perform(MockMvcRequestBuilders.get("/autores/"+autor1.getId())).andReturn().getResponse().getContentAsString();
    	
		Autor autor = MAPPER.readValue(response,Autor.class);
		
		assertEquals(autor.getId(), autor1.getId());
		assertEquals(autor.getNome(), autor1.getNome());
		
		autor.setNome("Antonio Carlos da Silva");
		mockMvc.perform(put("/autores/"+autor.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(autor)))
                .andExpect(status().isOk());
	
		response = mockMvc.perform(MockMvcRequestBuilders.get("/autores/"+autor.getId())).andReturn().getResponse().getContentAsString();
    	
		autor = MAPPER.readValue(response,Autor.class);
		
		assertNotEquals(autor.getNome(), "Antonio Carlos");
		assertEquals(autor.getNome(), "Antonio Carlos da Silva");
	}
    
    @Test
    public void teste05DeletarAutor() throws Exception{
    	   	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/autores")).andReturn().getResponse().getContentAsString();
    	
         List<Autor> autores = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, Autor.class));
    	for(Autor temp : autores){
    		mockMvc.perform(delete("/autores/"+temp.getId())).andExpect(status().isNoContent());
    	}
         
    	
    }

}
