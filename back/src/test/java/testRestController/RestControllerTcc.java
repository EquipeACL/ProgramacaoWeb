package testRestController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ufab.controller.AutorController;
import br.edu.ufab.controller.OrientadorController;
import br.edu.ufab.controller.itens.TCCController;
import br.edu.ufab.model.entities.Autor;
import br.edu.ufab.model.entities.Orientador;
import br.edu.ufab.model.entities.itens.TCC;
import br.edu.ufab.model.enums.TipoDeTCC;
/**
 * Teste unitario da classe TCCController 
 * @author EquipeACL
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestControllerTcc extends ConfigTeste{
	private Set<Autor> autores;
	private Set<Orientador> orientadores;
	private MockMvc mockaux;
	private MockMvc mockaux2;
	
	@Autowired
	private TCCController tccController;
	
	@Autowired
	private AutorController autorController;
	
	@Autowired
	private OrientadorController orientadorController;
    
    @Before
    public void setup(){
    	this.MAPPER = new ObjectMapper();
    	this.mockMvc = MockMvcBuilders.standaloneSetup(tccController).build();
    	this.mockaux = MockMvcBuilders.standaloneSetup(autorController).build();
    	this.mockaux2 = MockMvcBuilders.standaloneSetup(orientadorController).build();
    }
    
    @Test
    public void teste01AdicionarTcc() throws IOException, Exception{
    	
    	String response = mockaux.perform(MockMvcRequestBuilders.get("/autores")).andReturn().getResponse().getContentAsString();
    	autores = MAPPER.readValue(
				response,
				MAPPER.getTypeFactory().constructCollectionType(Set.class,
						Autor.class));
    	if(autores.size()==0){
    		Autor a = new Autor();
    		a.setNome("Autor1");
        	mockaux.perform(post("/autores")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(MAPPER.writeValueAsString(a)))
                    .andExpect(status().isOk());
        	response = mockaux.perform(MockMvcRequestBuilders.get("/autores")).andReturn().getResponse().getContentAsString();
        	autores = MAPPER.readValue(
    				response,
    				MAPPER.getTypeFactory().constructCollectionType(Set.class,
    						Autor.class));
        	
    	}
    	
    	response = mockaux2.perform(MockMvcRequestBuilders.get("/orientadores")).andReturn().getResponse().getContentAsString();
    	orientadores = MAPPER.readValue(
				response,
				MAPPER.getTypeFactory().constructCollectionType(Set.class,
						Orientador.class));
    	if(orientadores.size()==0){
    		Orientador o = new Orientador();
    		o.setNome("Orientador1");
        	mockaux2.perform(post("/orientadores")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(MAPPER.writeValueAsString(o)))
                    .andExpect(status().isOk());
        	response = mockaux2.perform(MockMvcRequestBuilders.get("/orientadores")).andReturn().getResponse().getContentAsString();
        	orientadores = MAPPER.readValue(
    				response,
    				MAPPER.getTypeFactory().constructCollectionType(Set.class,
    						Orientador.class));
        	
    	}
    	
    	

    	TCC tcc = new TCC();
    	tcc.setAutores(autores);
    	tcc.setLocal("Campina Grande");
    	tcc.setOrientadores(orientadores);
    	tcc.setTipo(TipoDeTCC.DISSERTACAO);
    	tcc.setTitulo("Tcc de Lucas");
    	
    	mockMvc.perform(post("/tccs")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(tcc)))
                .andExpect(status().isOk());
    	
    	tcc = new TCC();
    	
    	mockMvc.perform(post("/tccs")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(tcc)))
                .andExpect(status().isBadRequest());
    	
    	tcc = new TCC();
    	tcc.setAutores(autores);
    	tcc.setLocal("Iguatu - CE");
    	tcc.setOrientadores(orientadores);
    	tcc.setTipo(TipoDeTCC.TESE);
    	tcc.setTitulo("Tcc de Adalcino");
    	
    	mockMvc.perform(post("/tccs")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(tcc)))
                .andExpect(status().isOk());
    }
       
    @Test
	public void teste02BuscarTodos() throws Exception {

    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/tccs")).andReturn().getResponse().getContentAsString();
    	List<TCC> tccs = MAPPER.readValue(response,
    	           MAPPER.getTypeFactory().constructCollectionType(List.class, TCC.class));
    			assertTrue(tccs.size()==2);
    			assertEquals(tccs.get(0).getTitulo(), "Tcc de Lucas");
    			assertEquals(tccs.get(1).getTitulo(), "Tcc de Adalcino");
        
	}
    
    @Test
	public void teste03BuscarUm() throws Exception {
    	
		String response = mockMvc.perform(MockMvcRequestBuilders.get("/tccs/1")).andReturn().getResponse().getContentAsString();
    	
		TCC tcc = MAPPER.readValue(response,TCC.class);
		
		assertEquals(tcc.getId(), 1);
		assertEquals(tcc.getTitulo(), "Tcc de Lucas");
		assertEquals(tcc.getLocal(), "Campina Grande");
		assertEquals(tcc.getTipo(), TipoDeTCC.DISSERTACAO);
		
		
	}
    
    @Test
	public void teste04AtualizarTcc() throws Exception {
    	   	
		String response = mockMvc.perform(MockMvcRequestBuilders.get("/tccs/1")).andReturn().getResponse()
				.getContentAsString();
		TCC tcc = MAPPER.readValue(response, TCC.class);
		
		tcc.setTitulo("Tese de Lucas");
		tcc.setTipo(TipoDeTCC.TESE);

		mockMvc.perform(put("/tccs/"+tcc.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(tcc)))
                .andExpect(status().isOk());
	
		response = mockMvc.perform(MockMvcRequestBuilders.get("/tccs/1")).andReturn().getResponse().getContentAsString();
    	
		tcc = MAPPER.readValue(response,TCC.class);
		
		assertEquals(tcc.getTitulo(), "Tese de Lucas");
		assertEquals(tcc.getLocal(), "Campina Grande");
		assertEquals(tcc.getTipo(), TipoDeTCC.TESE);
	}
    
    @Test
    public void teste05DeletarTcc() throws Exception{
    	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/tccs")).andReturn().getResponse().getContentAsString();
    	
        List<TCC> tccs = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, TCC.class));
    	for(TCC temp : tccs){
    		mockMvc.perform(delete("/tccs/"+temp.getId())).andExpect(status().isNoContent());
    	}
        
    	response = mockaux.perform(MockMvcRequestBuilders.get("/autores")).andReturn().getResponse().getContentAsString();
    	
        List<Autor> autores = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, Autor.class));
    	for(Autor temp : autores){
    		mockaux.perform(delete("/autores/"+temp.getId())).andExpect(status().isNoContent());
    	}
    	
		response = mockaux2.perform(MockMvcRequestBuilders.get("/orientadores")).andReturn().getResponse()
				.getContentAsString();

		List<Orientador> orientadores = MAPPER.readValue(response,
				MAPPER.getTypeFactory().constructCollectionType(List.class, Orientador.class));
		for (Orientador temp : orientadores) {
			mockaux2.perform(delete("/orientadores/" + temp.getId())).andExpect(status().isNoContent());
		}
    	
    }
    

}
