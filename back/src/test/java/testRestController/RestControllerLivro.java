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
import br.edu.ufab.controller.EditoraController;
import br.edu.ufab.controller.itens.LivroController;
import br.edu.ufab.model.entities.Autor;
import br.edu.ufab.model.entities.Editora;
import br.edu.ufab.model.entities.itens.Livro;
/**
 * Teste unitario da classe LivroController 
 * @author EquipeACL
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestControllerLivro extends ConfigTeste{
	private Set<Autor> autores;
	private Editora editora;
	private MockMvc mockaux;
	private MockMvc mockaux2;
	
	@Autowired
	private LivroController livroController;
	
	@Autowired
	private AutorController autorController;
	
	@Autowired
	private EditoraController editoraController;
    
    @Before
    public void setup(){
    	this.MAPPER = new ObjectMapper();
    	this.mockMvc = MockMvcBuilders.standaloneSetup(livroController).build();
    	this.mockaux = MockMvcBuilders.standaloneSetup(autorController).build();
    	this.mockaux2 = MockMvcBuilders.standaloneSetup(editoraController).build();
    }
    
    @Test
    public void teste01AdicionarLivro() throws IOException, Exception{
    	
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
    	
    	response = mockaux2.perform(MockMvcRequestBuilders.get("/editoras")).andReturn().getResponse().getContentAsString();
    	List<Editora> editoras = MAPPER.readValue(
				response,
				MAPPER.getTypeFactory().constructCollectionType(List.class,
						Editora.class));
    	if(editoras.size()==0){
    		Editora e = new Editora();
    		e.setNome("EditoraReal");
        	mockaux2.perform(post("/editoras")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(MAPPER.writeValueAsString(e)))
                    .andExpect(status().isOk());
        	response = mockaux2.perform(MockMvcRequestBuilders.get("/editoras")).andReturn().getResponse().getContentAsString();
        	editoras = MAPPER.readValue(
    				response,
    				MAPPER.getTypeFactory().constructCollectionType(List.class,
    						Editora.class));
        	
    	}
    	
    	editora = editoras.get(0);

    	Livro livro  = new Livro();
    	livro.setAutores(autores);
    	livro.setEdicao("1");
    	livro.setEditora(editora);
    	livro.setIsbn(2848);
    	livro.setNumpaginas(1000);
    	livro.setTitulo("Deitel");
    	
    	mockMvc.perform(post("/livros")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(livro)))
                .andExpect(status().isOk());
    	
    	livro = new Livro();
    	
    	mockMvc.perform(post("/livros")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(livro)))
                .andExpect(status().isBadRequest());
    	
    	livro = new Livro();
    	livro.setAutores(autores);
    	livro.setEdicao("2");
    	livro.setEditora(editora);
    	livro.setIsbn(2849);
    	livro.setNumpaginas(1002);
    	livro.setTitulo("Deitel 2");
    	
    	
    	mockMvc.perform(post("/livros")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(livro)))
                .andExpect(status().isOk());
    }
       
    @Test
	public void teste02BuscarTodos() throws Exception {

    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/livros")).andReturn().getResponse().getContentAsString();
    	List<Livro> livros = MAPPER.readValue(response,
    	           MAPPER.getTypeFactory().constructCollectionType(List.class, Livro.class));
    			assertTrue(livros.size()==2);
    			assertEquals(livros.get(0).getTitulo(), "Deitel");
    			assertEquals(livros.get(1).getTitulo(), "Deitel 2");
        
	}
    
    @Test
	public void teste03BuscarUm() throws Exception {
    	
		String response = mockMvc.perform(MockMvcRequestBuilders.get("/livros/1")).andReturn().getResponse().getContentAsString();
    	
		Livro livro = MAPPER.readValue(response,Livro.class);
		
		assertEquals(livro.getId(), 1);
		assertEquals(livro.getTitulo(), "Deitel");
		assertEquals(livro.getEdicao(), "1");
		assertEquals(livro.getEditora().getNome(),"EditoraReal");
		assertEquals(livro.getIsbn(),2848);
		assertEquals(livro.getNumpaginas(),1000 );
		
	}
    
    @Test
	public void teste04AtualizarLivro() throws Exception {
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/livros/1")).andReturn().getResponse().getContentAsString();
    	
		Livro livro = MAPPER.readValue(response,Livro.class);	
		
		livro.setTitulo("Deitel 3");
		livro.setIsbn(2849);

		mockMvc.perform(put("/livros/"+livro.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(livro)))
                .andExpect(status().isOk());
	
		response = mockMvc.perform(MockMvcRequestBuilders.get("/livros/1")).andReturn().getResponse().getContentAsString();
    	
		livro = MAPPER.readValue(response,Livro.class);
		
		assertEquals(livro.getTitulo(), "Deitel 3");
		assertEquals(livro.getIsbn(), 2849);
	
    }
    
    @Test
    public void teste05DeletarLivro() throws Exception{
    	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/livros")).andReturn().getResponse().getContentAsString();
    	
        List<Livro> livros = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, Livro.class));
    	for(Livro temp : livros){
    		mockMvc.perform(delete("/livros/"+temp.getId())).andExpect(status().isNoContent());
    	}
        
    	response = mockaux.perform(MockMvcRequestBuilders.get("/autores")).andReturn().getResponse().getContentAsString();
    	
        List<Autor> autores = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, Autor.class));
    	for(Autor temp : autores){
    		mockaux.perform(delete("/autores/"+temp.getId())).andExpect(status().isNoContent());
    	}
    	
		response = mockaux2.perform(MockMvcRequestBuilders.get("/editoras")).andReturn().getResponse()
				.getContentAsString();

		List<Editora> editoras = MAPPER.readValue(response,
				MAPPER.getTypeFactory().constructCollectionType(List.class, Editora.class));
		for (Editora temp : editoras) {
			mockaux2.perform(delete("/editoras/" + temp.getId())).andExpect(status().isNoContent());
		}
    	
    }
    

}
