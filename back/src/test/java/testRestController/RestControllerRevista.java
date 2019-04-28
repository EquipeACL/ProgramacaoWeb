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
import br.edu.ufab.controller.itens.RevistaController;
import br.edu.ufab.model.entities.Editora;
import br.edu.ufab.model.entities.itens.Revista;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Teste unitario da classe RevistaController 
 * @author EquipeACL
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestControllerRevista extends ConfigTeste{
	private Editora editora = new Editora();
	private MockMvc mockaux;
	
	@Autowired
	private RevistaController revistaController;
	
	@Autowired
	private EditoraController editoraController;
	
    
    @Before
    public void setup(){
    	this.MAPPER = new ObjectMapper();
    	this.mockMvc = MockMvcBuilders.standaloneSetup(revistaController).build();
    	this.mockaux = MockMvcBuilders.standaloneSetup(editoraController).build();
    }
    
    @Test
    public void teste01AdicionarRevista() throws IOException, Exception{
   	
    	editora.setNome("SARAIVA");
    	mockaux.perform(post("/editoras")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(editora)))
                .andExpect(status().isOk());
    
		String response = mockaux.perform(MockMvcRequestBuilders.get("/editoras")).andReturn().getResponse()
				.getContentAsString();

		List<Editora> editoras = MAPPER.readValue(response,
				MAPPER.getTypeFactory().constructCollectionType(List.class, Editora.class));
		editora = editoras.get(0);
    	Revista r = new Revista();
    	
    	r.setTitulo("Veja");
    	r.setDatapublicacao(new Date(System.currentTimeMillis()));
    	r.setEdicao("1");
    	r.setEditora(editora);
    	r.setNumpaginas(125);
    	mockMvc.perform(post("/revistas")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(r)))
                .andExpect(status().isOk());
    	
    	r = new Revista();
    	
    	mockMvc.perform(post("/revistas")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(r)))
                .andExpect(status().isBadRequest());
    	
    	r.setTitulo("Rolling Stones");
    	r.setDatapublicacao(new Date(System.currentTimeMillis()));
    	r.setEdicao("2");
    	r.setEditora(editora);
    	r.setNumpaginas(150);
    	mockMvc.perform(post("/revistas")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(r)))
                .andExpect(status().isOk());
		
    }
    
   
    @Test
	public void teste02BuscarTodas() throws Exception {
    	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/revistas")).andReturn().getResponse().getContentAsString();
    	
        List<Revista> revistas = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, Revista.class));
        
		assertTrue(revistas.size()==2);
		assertEquals(revistas.get(0).getTitulo(), "Veja");
		assertEquals(revistas.get(1).getTitulo(), "Rolling Stones");
	}
    
    @Test
	public void teste03BuscarUma() throws Exception {
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/revistas")).andReturn().getResponse().getContentAsString();
    	
        List<Revista> revistas = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, Revista.class));
        Revista revistaesperada = revistas.get(0);
    
		response = mockMvc.perform(MockMvcRequestBuilders.get("/revistas/"+revistaesperada.getId())).andReturn().getResponse().getContentAsString();
    	System.out.println(response);
		
    	Revista revista = MAPPER.readValue(response,Revista.class);
    	
		assertEquals(revista.getId(), revistaesperada.getId());
		assertEquals(revista.getTitulo(), revistaesperada.getTitulo());
		assertEquals(revista.getEdicao(), revistaesperada.getEdicao());
		assertEquals(revista.getNumpaginas(), revistaesperada.getNumpaginas());
		assertEquals(revista.getEditora().getNome(), revistaesperada.getEditora().getNome());
		
	}
    
    @Test
	public void teste04AtualizarRevista() throws Exception {
		String response = mockMvc.perform(MockMvcRequestBuilders.get("/revistas")).andReturn().getResponse()
				.getContentAsString();

		List<Revista> revistas = MAPPER.readValue(response,
				MAPPER.getTypeFactory().constructCollectionType(List.class, Revista.class));
		Revista revistaesperada = revistas.get(0);

    	response = mockMvc.perform(MockMvcRequestBuilders.get("/revistas/"+revistaesperada.getId())).andReturn().getResponse().getContentAsString();
    	
		Revista revista = MAPPER.readValue(response,Revista.class);

		
		revista.setTitulo("NovoTitulo");
		revista.setEdicao("2");
		revista.setNumpaginas(150);
		mockMvc.perform(put("/revistas/"+revista.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(revista)))
                .andExpect(status().isOk());
	
		response = mockMvc.perform(MockMvcRequestBuilders.get("/revistas/"+revista.getId())).andReturn().getResponse().getContentAsString();
    	
		revista = MAPPER.readValue(response,Revista.class);
		
		assertEquals(revista.getTitulo(), "NovoTitulo");
		assertEquals(revista.getEdicao(), "2");
		assertEquals(revista.getNumpaginas(),150);

	}  
    
    @Test
    public void teste05DeletarRevista() throws Exception{
    	
    	    	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/revistas")).andReturn().getResponse().getContentAsString();
    	
        List<Revista> revistas = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, Revista.class));
    	for(Revista temp : revistas){
    		mockMvc.perform(delete("/revistas/"+temp.getId())).andExpect(status().isNoContent());
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
