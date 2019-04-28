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

import br.edu.ufab.controller.OrientadorController;
import br.edu.ufab.model.entities.Orientador;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Teste unitario da classe OrientadorController 
 * @author EquipeACL
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestControllerOrientador extends ConfigTeste{
		
	@Autowired
	private OrientadorController orientadorController;
	
    
    @Before
    public void setup(){
    	this.MAPPER = new ObjectMapper();
    	this.mockMvc = MockMvcBuilders.standaloneSetup(orientadorController).build();
    }
    
    @Test
    public void teste01AdicionarOrientador() throws IOException, Exception{
    	Orientador o = new Orientador();
    	
    	o.setNome("Carlos Drummond");
    	mockMvc.perform(post("/orientadores")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(o)))
                .andExpect(status().isOk());
    	
    	o = new Orientador();
    	
    	mockMvc.perform(post("/orientadores")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(o)))
                .andExpect(status().isBadRequest());
    	
    	o.setNome("Machado de Assis");
    	mockMvc.perform(post("/orientadores")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(o)))
                .andExpect(status().isOk());
	
    }
    

    
    @Test
	public void teste02BuscarTodos() throws Exception {
  	
    	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/orientadores")).andReturn().getResponse().getContentAsString();
    	
        List<Orientador> orientadores = MAPPER.readValue(
				response,
				MAPPER.getTypeFactory().constructCollectionType(List.class,
						Orientador.class));
		assertTrue(orientadores.size()==2);
		assertEquals(orientadores.get(0).getNome(), "Carlos Drummond");
		assertEquals(orientadores.get(1).getNome(), "Machado de Assis");
	}
    
    @Test
	public void teste03BuscarUm() throws Exception {
		String response = mockMvc.perform(MockMvcRequestBuilders.get("/orientadores")).andReturn().getResponse()
				.getContentAsString();

		List<Orientador> orientadores = MAPPER.readValue(response,
				MAPPER.getTypeFactory().constructCollectionType(List.class, Orientador.class));
		
		Orientador orientadoresperado = orientadores.get(0);
		response = mockMvc.perform(MockMvcRequestBuilders.get("/orientadores/"+orientadoresperado.getId())).andReturn().getResponse().getContentAsString();
    	
		Orientador orientador = MAPPER.readValue(response,Orientador.class);
		
		assertEquals(orientador.getId(), orientadoresperado.getId());
		assertEquals(orientador.getNome(), orientadoresperado.getNome());
	}
    
    @Test
	public void teste04AtualizarOrientador() throws Exception {
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/orientadores")).andReturn().getResponse()
				.getContentAsString();

		List<Orientador> orientadores = MAPPER.readValue(response,
				MAPPER.getTypeFactory().constructCollectionType(List.class, Orientador.class));
		
		Orientador orientadoresperado = orientadores.get(0);   	
		response = mockMvc.perform(MockMvcRequestBuilders.get("/orientadores/"+orientadoresperado.getId())).andReturn().getResponse().getContentAsString();
    	
		
		Orientador orientador = MAPPER.readValue(response,Orientador.class);
		
		orientador.setNome("Carlos Drummond de Andrade");
		mockMvc.perform(put("/orientadores/"+orientador.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(orientador)))
                .andExpect(status().isOk());
	
		response = mockMvc.perform(MockMvcRequestBuilders.get("/orientadores/"+orientador.getId())).andReturn().getResponse().getContentAsString();
		
		orientador = MAPPER.readValue(response,Orientador.class);
		
		assertNotEquals(orientador.getNome(), "Carlos Drummond");
		assertEquals(orientador.getNome(), "Carlos Drummond de Andrade");
	}
    
    @Test
    public void teste05DeletarOrientador() throws Exception{
    	
     	String response = mockMvc.perform(MockMvcRequestBuilders.get("/orientadores")).andReturn().getResponse().getContentAsString();
    	
    	List<Orientador> orientadores = MAPPER.readValue(
				response,
				MAPPER.getTypeFactory().constructCollectionType(List.class,
						Orientador.class));
		for (Orientador temp : orientadores) {
			mockMvc.perform(delete("/orientadores/" + temp.getId())).andExpect(
					status().isNoContent());
		}  
    	
    }

}
