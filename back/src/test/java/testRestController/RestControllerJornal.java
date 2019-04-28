package testRestController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.edu.ufab.controller.EditoraController;
import br.edu.ufab.controller.itens.JornalController;
import br.edu.ufab.model.entities.Editora;
import br.edu.ufab.model.entities.itens.Jornal;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Teste unitario da classe JornalController 
 * @author EquipeACL
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestControllerJornal extends ConfigTeste{
	private Editora editora = new Editora();
	private MockMvc mockaux;	
	
	@Autowired
	private JornalController jornalController;
	
	@Autowired
	private EditoraController editoraController;
    
    @Before
    public void setup(){
    	this.MAPPER = new ObjectMapper();
    	this.mockMvc = MockMvcBuilders.standaloneSetup(jornalController).build();
    	this.mockaux = MockMvcBuilders.standaloneSetup(editoraController).build();
    }
    
    @Test
    public void teste01AdicionarJornal() throws IOException, Exception{
    	String response = mockaux.perform(MockMvcRequestBuilders.get("/editoras")).andReturn().getResponse().getContentAsString();
    	List<Editora> editoras = MAPPER.readValue(
				response,
				MAPPER.getTypeFactory().constructCollectionType(List.class,
						Editora.class));
    	if(editoras.size()==0){
    		editora.setNome("SARAIVA");
        	mockaux.perform(post("/editoras")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(MAPPER.writeValueAsString(editora)))
                    .andExpect(status().isOk());
        	response = mockaux.perform(MockMvcRequestBuilders.get("/editoras")).andReturn().getResponse().getContentAsString();
        	editoras = MAPPER.readValue(
    				response,
    				MAPPER.getTypeFactory().constructCollectionType(List.class,
    						Editora.class));
        	
    	}
    	editora = editoras.get(0);
    	
    	Jornal j = new Jornal();
    	
    	j.setTitulo("Jornal da Paraíba");
    	j.setData(new Date(System.currentTimeMillis()));
    	j.setEdicao("1");
    	j.setEditora(editora);
    	j.setEdicao("1");
    	j.setNumpaginas(125);
    	mockMvc.perform(post("/jornais")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(j)))
                .andExpect(status().isOk());
    	
    	j = new Jornal();
    	
    	mockMvc.perform(post("/jornais")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(j)))
                .andExpect(status().isBadRequest());
    	j.setTitulo("Jornal da Correio");
    	j.setData(new Date(System.currentTimeMillis()));
    	j.setEdicao("21");
    	j.setEditora(editora);
    	j.setEdicao("1");
    	j.setNumpaginas(150);
    	mockMvc.perform(post("/jornais")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(j)))
                .andExpect(status().isOk());
    }
    
   
    
    @Test
	public void teste02BuscarTodos() throws Exception {

    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/jornais")).andReturn().getResponse().getContentAsString();
    	
        List<Jornal> jornais = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, Jornal.class));
        
		assertTrue(jornais.size()==2);
		assertEquals(jornais.get(0).getTitulo(), "Jornal da Paraíba");
		assertEquals(jornais.get(1).getTitulo(), "Jornal da Correio");
	}
    
    @Test
	public void teste03BuscarUm() throws Exception {
    	
		String response = mockMvc.perform(MockMvcRequestBuilders.get("/jornais/1")).andReturn().getResponse().getContentAsString();
    	
		Jornal jornal = MAPPER.readValue(response,Jornal.class);
		
		assertEquals(jornal.getId(), 1);
		assertEquals(jornal.getTitulo(), "Jornal da Paraíba");
		assertEquals(jornal.getEdicao(), "1");
		assertEquals(jornal.getNumpaginas(), 125);
		assertEquals(jornal.getEditora().getNome(), "SARAIVA");
		
	}
    
    @Test
	public void teste04AtualizarJornal() throws Exception {
    	   	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/jornais/1")).andReturn().getResponse().getContentAsString();
    	
		Jornal jornal = MAPPER.readValue(response,Jornal.class);
		
		assertEquals(jornal.getId(), 1);
		assertEquals(jornal.getTitulo(), "Jornal da Paraíba");
		assertEquals(jornal.getEdicao(), "1");
		assertEquals(jornal.getNumpaginas(), 125);
		assertEquals(jornal.getEditora().getNome(), "SARAIVA");
		
		jornal.setTitulo("Novo Titulo");
		jornal.setEdicao("2");
		jornal.setNumpaginas(150);
		mockMvc.perform(put("/jornais/"+jornal.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(jornal)))
                .andExpect(status().isOk());
	
		response = mockMvc.perform(MockMvcRequestBuilders.get("/jornais/1")).andReturn().getResponse().getContentAsString();
    	
		jornal = MAPPER.readValue(response,Jornal.class);
		
		assertEquals(jornal.getId(), 1);
		assertEquals(jornal.getTitulo(), "Novo Titulo");
		assertEquals(jornal.getEdicao(), "2");
		assertEquals(jornal.getNumpaginas(), 150);
	}
    
    @Test
    public void teste05DeletarJornal() throws Exception{
    	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/jornais")).andReturn().getResponse().getContentAsString();
    	
        List<Jornal> jornais = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, Jornal.class));
    	for(Jornal temp : jornais){
    		mockMvc.perform(delete("/jornais/"+temp.getId())).andExpect(status().isNoContent());
    	}
         
		response = mockaux.perform(MockMvcRequestBuilders.get("/editoras")).andReturn().getResponse()
				.getContentAsString();

		List<Editora> editoras = MAPPER.readValue(response,
				MAPPER.getTypeFactory().constructCollectionType(List.class, Editora.class));
		for (Editora temp : editoras) {
			mockaux.perform(delete("/editoras/" + temp.getId())).andExpect(status().isNoContent());
		}

    }
    

}
