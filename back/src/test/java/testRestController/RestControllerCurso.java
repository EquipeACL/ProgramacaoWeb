package testRestController;

import static org.junit.Assert.assertEquals;
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

import br.edu.ufab.controller.CursoController;
import br.edu.ufab.model.entities.Curso;
import br.edu.ufab.model.enums.AreaDeCurso;
import br.edu.ufab.model.enums.TipoDeCurso;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * Teste unitario da classe CursoController 
 * @author EquipeACL
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestControllerCurso extends ConfigTeste{
		
	@Autowired
	private CursoController cursoController;
	
    
    @Before
    public void setup(){
    	this.MAPPER = new ObjectMapper();
    	this.mockMvc = MockMvcBuilders.standaloneSetup(cursoController).build();
    }
    
    @Test
    public void teste01AdicionarCurso() throws IOException, Exception{
    	Curso c = new Curso();
    	
    	c.setNome("Computação");
    	c.setCodigo("1122");
    	c.setTipo(TipoDeCurso.MESTRADO);
    	c.setArea(AreaDeCurso.CIENCIAS_EXATAS);
    	mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(c)))
                .andExpect(status().isOk());
    	
    	c = new Curso();
    	
    	mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(c)))
                .andExpect(status().isBadRequest());
    	
    	c.setNome("Letras");
    	c.setCodigo("1133");
    	c.setTipo(TipoDeCurso.GRADUACAO);
    	c.setArea(AreaDeCurso.CIENCIAS_HUMANAS);
    	mockMvc.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(c)))
                .andExpect(status().isOk());
		
    }
    
    
    @Test
	public void teste02BuscarTodos() throws Exception {
    	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/cursos")).andReturn().getResponse().getContentAsString();
    	
        List<Curso> cursos = MAPPER.readValue(response,
           MAPPER.getTypeFactory().constructCollectionType(List.class, Curso.class));
		assertTrue(cursos.size()==2);
		assertEquals(cursos.get(0).getNome(), "Computação");
		assertEquals(cursos.get(1).getNome(), "Letras");
	}
    
    @Test
	public void teste03BuscarUm() throws Exception {
    	   	
		String response = mockMvc.perform(MockMvcRequestBuilders.get("/cursos/1")).andReturn().getResponse().getContentAsString();
    	
		Curso curso = MAPPER.readValue(response,Curso.class);
		
		assertEquals(curso.getId(), 1);
		assertEquals(curso.getNome(), "Computação");
		assertEquals(curso.getCodigo(), "1122");
		assertEquals(curso.getArea(), AreaDeCurso.CIENCIAS_EXATAS);
		assertEquals(curso.getTipo(), TipoDeCurso.MESTRADO);
		
	}
    
    @Test
	public void teste04AtualizarCurso() throws Exception {
    	   	
		String response = mockMvc.perform(MockMvcRequestBuilders.get("/cursos/1")).andReturn().getResponse().getContentAsString();
    	
		Curso curso = MAPPER.readValue(response,Curso.class);
		
		assertEquals(curso.getId(), 1);
		assertEquals(curso.getNome(), "Computação");
		assertEquals(curso.getCodigo(), "1122");
		assertEquals(curso.getArea(), AreaDeCurso.CIENCIAS_EXATAS);
		assertEquals(curso.getTipo(), TipoDeCurso.MESTRADO);
		
		curso.setNome("Computação-UEPB");
		curso.setCodigo("1234");
		curso.setTipo(TipoDeCurso.GRADUACAO);
		
		mockMvc.perform(put("/cursos/"+curso.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(curso)))
                .andExpect(status().isOk());
	
		response = mockMvc.perform(MockMvcRequestBuilders.get("/cursos/1")).andReturn().getResponse().getContentAsString();
    	
		curso = MAPPER.readValue(response,Curso.class);
		
		assertEquals(curso.getId(), 1);
		assertEquals(curso.getNome(), "Computação-UEPB");
		assertEquals(curso.getCodigo(), "1234");
		assertEquals(curso.getArea(), AreaDeCurso.CIENCIAS_EXATAS);
		assertEquals(curso.getTipo(), TipoDeCurso.GRADUACAO);
	}
    
    @Test
    public void teste05DeletarCurso() throws Exception{
    	  	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/cursos")).andReturn().getResponse().getContentAsString();
    	
         List<Curso> cursos = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, Curso.class));
    	for(Curso temp : cursos){
    		mockMvc.perform(delete("/cursos/"+temp.getId())).andExpect(status().isNoContent());
    	}
         
    	
    }

}
