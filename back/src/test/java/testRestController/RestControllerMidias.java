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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.edu.ufab.controller.itens.MidiaEletronicaController;
import br.edu.ufab.model.entities.itens.MidiaEletronica;
import br.edu.ufab.model.enums.TipoDeMidia;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Teste unitario da classe MidiaEletronicaController 
 * @author EquipeACL
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestControllerMidias extends ConfigTeste{
		
	@Autowired
	private MidiaEletronicaController midiasController;
	
    
    @Before
    public void setup(){
    	this.MAPPER = new ObjectMapper();
    	this.mockMvc = MockMvcBuilders.standaloneSetup(midiasController).build();
    }
    
    @Test
    public void teste01AdicionarMidia() throws IOException, Exception{
    	MidiaEletronica m = new MidiaEletronica();
    	
    	m.setTitulo("Avioes do Forro");
    	m.setData(new Date(System.currentTimeMillis()));
    	m.setTipo(TipoDeMidia.CD);
    	mockMvc.perform(post("/midias")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(m)))
                .andExpect(status().isOk());
    	
    	m = new MidiaEletronica();
    	
    	mockMvc.perform(post("/midias")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(m)))
                .andExpect(status().isBadRequest());
    	
    	m.setTitulo("Raça Negra");
    	m.setData(new Date(System.currentTimeMillis()));
    	m.setTipo(TipoDeMidia.DVD);
    	mockMvc.perform(post("/midias")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(m)))
                .andExpect(status().isOk());
		
    }
    
    
    @Test
	public void teste02BuscarTodas() throws Exception {

    	    	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/midias")).andReturn().getResponse().getContentAsString();
    	
        List<MidiaEletronica> midias = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, MidiaEletronica.class));
        
		assertTrue(midias.size()==2);
		assertEquals(midias.get(0).getTitulo(), "Avioes do Forro");
		assertEquals(midias.get(1).getTitulo(), "Raça Negra");
	}
    
    @Test
	public void teste03BuscarUma() throws Exception {
    	   	
		String response = mockMvc.perform(MockMvcRequestBuilders.get("/midias/1")).andReturn().getResponse().getContentAsString();
    	
		MidiaEletronica midia = MAPPER.readValue(response,MidiaEletronica.class);
		
		assertEquals(midia.getId(), 1);
		assertEquals(midia.getTitulo(), "Avioes do Forro");
		assertEquals(midia.getTipo(), TipoDeMidia.CD);
		
		
	}
    
    @Test
	public void teste04AtualizarMidia() throws Exception {
    	   	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/midias/1")).andReturn().getResponse().getContentAsString();
    	
		MidiaEletronica midia = MAPPER.readValue(response,MidiaEletronica.class);
		
		assertEquals(midia.getId(), 1);
		assertEquals(midia.getTitulo(), "Avioes do Forro");
		assertEquals(midia.getTipo(), TipoDeMidia.CD);
		
		midia.setTitulo("Novo Titulo");
		midia.setTipo(TipoDeMidia.DVD);
		
		mockMvc.perform(put("/midias/"+midia.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(midia)))
                .andExpect(status().isOk());
	
		response = mockMvc.perform(MockMvcRequestBuilders.get("/midias/1")).andReturn().getResponse().getContentAsString();
    	
		midia = MAPPER.readValue(response,MidiaEletronica.class);
		
		assertEquals(midia.getId(), 1);
		assertEquals(midia.getTitulo(), "Novo Titulo");
		assertEquals(midia.getTipo(), TipoDeMidia.DVD);
	}
    
    @Test
    public void teste05DeletarMidia() throws Exception{
    	    	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/midias")).andReturn().getResponse().getContentAsString();
    	
        List<MidiaEletronica> midias = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, MidiaEletronica.class));
    	for(MidiaEletronica temp : midias){
    		mockMvc.perform(delete("/midias/"+temp.getId())).andExpect(status().isNoContent());
    	}
         
    }

}
