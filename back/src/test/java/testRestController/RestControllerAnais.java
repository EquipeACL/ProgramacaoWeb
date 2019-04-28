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
import br.edu.ufab.controller.itens.AnaisController;
import br.edu.ufab.model.entities.Autor;
import br.edu.ufab.model.entities.itens.Anais;
import br.edu.ufab.model.enums.TipoDeAnais;
/**
 * Teste unitario da classe AnaisController 
 * @author EquipeACL
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestControllerAnais extends ConfigTeste{
	private Set<Autor> autores;
	private MockMvc mockaux;
	
	@Autowired
	private AnaisController anaisController;
	
	@Autowired
	private AutorController autorController;
    
    @Before
    public void setup(){
    	this.MAPPER = new ObjectMapper();
    	this.mockMvc = MockMvcBuilders.standaloneSetup(anaisController).build();
    	this.mockaux = MockMvcBuilders.standaloneSetup(autorController).build();
    }
    
    @Test
    public void teste01AdicionarAnalDeCongresso() throws IOException, Exception{
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
    	
    	
    	Anais an = new Anais();
    	
    	an.setAutores(autores);
    	an.setLocal("Brasil");
    	an.setNomecongreco("Enect2018");
    	an.setTipo(TipoDeAnais.ARTIGO);
    	an.setTitulo("Um titulo qualquer");
    	mockMvc.perform(post("/anais")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(an)))
                .andExpect(status().isOk());
    	
    	an = new Anais();
    	
    	mockMvc.perform(post("/anais")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(an)))
                .andExpect(status().isBadRequest());
    	
    	an = new Anais();
    	
    	an.setAutores(autores);
    	an.setLocal("Campina Grande");
    	an.setNomecongreco("CongressoUepb");
    	an.setTipo(TipoDeAnais.POSTER);
    	an.setTitulo("Um outro titulo qualquer");
    	mockMvc.perform(post("/anais")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(an)))
                .andExpect(status().isOk());
      
    }
       
    @Test
	public void teste02BuscarTodos() throws Exception {

    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/anais")).andReturn().getResponse().getContentAsString();
    	 List<Anais> anais = MAPPER.readValue(response,
    	           MAPPER.getTypeFactory().constructCollectionType(List.class, Anais.class));
    			assertTrue(anais.size()==2);
    			assertEquals(anais.get(0).getTitulo(), "Um titulo qualquer");
    			assertEquals(anais.get(1).getTitulo(), "Um outro titulo qualquer");
        
	}
    
    @Test
	public void teste03BuscarUm() throws Exception {
    	
		String response = mockMvc.perform(MockMvcRequestBuilders.get("/anais/1")).andReturn().getResponse().getContentAsString();
    	
		Anais an = MAPPER.readValue(response,Anais.class);
		
		assertEquals(an.getId(), 1);
		assertEquals(an.getTitulo(), "Um titulo qualquer");
		assertEquals(an.getLocal(), "Brasil");
		assertEquals(an.getNomecongreco(), "Enect2018");
		assertEquals(an.getTipo(), TipoDeAnais.ARTIGO);
		
	}
    
    @Test
	public void teste04AtualizarAnalDeCongresso() throws Exception {
    	   	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/anais/1")).andReturn().getResponse().getContentAsString();
    	
		Anais an = MAPPER.readValue(response,Anais.class);
		
		assertEquals(an.getId(), 1);
		assertEquals(an.getTitulo(), "Um titulo qualquer");
		assertEquals(an.getLocal(), "Brasil");
		assertEquals(an.getNomecongreco(), "Enect2018");
		assertEquals(an.getTipo(), TipoDeAnais.ARTIGO);
		
		an.setTitulo("NovoTitulo");
		an.setTipo(TipoDeAnais.RESUMO);
		an.setNomecongreco("NovoNomeCongresso");
		mockMvc.perform(put("/anais/"+an.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(an)))
                .andExpect(status().isOk());
	
		response = mockMvc.perform(MockMvcRequestBuilders.get("/anais/1")).andReturn().getResponse().getContentAsString();
    	
		an = MAPPER.readValue(response,Anais.class);
		
		assertEquals(an.getId(), 1);
		assertEquals(an.getTitulo(), "NovoTitulo");
		assertEquals(an.getLocal(), "Brasil");
		assertEquals(an.getNomecongreco(), "NovoNomeCongresso");
		assertEquals(an.getTipo(), TipoDeAnais.RESUMO);
	}
    
    @Test
    public void teste05DeletarAnalDeCongresso() throws Exception{
    	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/anais")).andReturn().getResponse().getContentAsString();
    	
        List<Anais> anais = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, Anais.class));
    	for(Anais temp : anais){
    		mockMvc.perform(delete("/anais/"+temp.getId())).andExpect(status().isNoContent());
    	}
        
    	response = mockaux.perform(MockMvcRequestBuilders.get("/autores")).andReturn().getResponse().getContentAsString();
    	
        List<Autor> autores = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, Autor.class));
    	for(Autor temp : autores){
    		mockaux.perform(delete("/autores/"+temp.getId())).andExpect(status().isNoContent());
    	}
    	
    	
    }
    

}
