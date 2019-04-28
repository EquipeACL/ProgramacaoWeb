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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ufab.controller.EditoraController;
import br.edu.ufab.model.entities.Editora;
/**
 * Teste unitario da classe EditoraController 
 * @author EquipeACL
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestControllerEditora extends ConfigTeste{
		
	@Autowired
	private EditoraController editoraController;
	
    
    @Before
    public void setup(){
    	this.MAPPER = new ObjectMapper();
    	this.mockMvc = MockMvcBuilders.standaloneSetup(editoraController).build();
    }
    
    @Test
    public void teste01AdicionarEditora() throws IOException, Exception{
    	Editora e = new Editora();
    	
    	e.setNome("SARAIVA");
    	mockMvc.perform(post("/editoras")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(e)))
                .andExpect(status().isOk());
    	
    	e = new Editora();
    	
    	mockMvc.perform(post("/editoras")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(e)))
                .andExpect(status().isBadRequest());
    	
    	e.setNome("ABRIL");
    	mockMvc.perform(post("/editoras")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(e)))
                .andExpect(status().isOk());
    	
    }
    
    
    @Test
	public void teste02BuscarTodas() throws Exception {
    	
		String response = mockMvc.perform(MockMvcRequestBuilders.get("/editoras")).andReturn().getResponse().getContentAsString();
		
		List<Editora> editoras = MAPPER.readValue(
				response,
				MAPPER.getTypeFactory().constructCollectionType(List.class,
						Editora.class));
		assertTrue(editoras.size()==2);
		assertEquals(editoras.get(0).getNome(), "SARAIVA");
		assertEquals(editoras.get(1).getNome(), "ABRIL");
	}
    
    @Test
	public void teste03BuscarUma() throws Exception {
    	   	
		String response = mockMvc.perform(MockMvcRequestBuilders.get("/editoras/2")).andReturn().getResponse().getContentAsString();
    	
		Editora editora = MAPPER.readValue(response,Editora.class);
		
		assertEquals(editora.getId(), 2);
		assertEquals(editora.getNome(), "ABRIL");
	}
    
    @Test
	public void teste04AtualizarEditora() throws Exception {
    	   	
		String response = mockMvc.perform(MockMvcRequestBuilders.get("/editoras/2")).andReturn().getResponse().getContentAsString();
    	
		Editora editora = MAPPER.readValue(response,Editora.class);
		
		assertEquals(editora.getId(), 2);
		assertEquals(editora.getNome(), "ABRIL");
		
		editora.setNome("NovoMundo");
		mockMvc.perform(put("/editoras/"+editora.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(editora)))
                .andExpect(status().isOk());
	
		response = mockMvc.perform(MockMvcRequestBuilders.get("/editoras/2")).andReturn().getResponse().getContentAsString();
    	
		editora = MAPPER.readValue(response,Editora.class);
		
		assertEquals(editora.getId(), 2);
		assertNotEquals(editora.getNome(), "ABRIL");
		assertEquals(editora.getNome(), "NovoMundo");
	}
    
    @Test
    public void teste05DeletarEditora() throws Exception{
    
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/editoras")).andReturn().getResponse().getContentAsString();
    	
        List<Editora> editoras = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, Editora.class));
    	for(Editora temp : editoras){
    		mockMvc.perform(delete("/editoras/"+temp.getId())).andExpect(status().isNoContent());
    	}      
    	
    }
  	

}
