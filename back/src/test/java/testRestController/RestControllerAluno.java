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

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ufab.controller.CursoController;
import br.edu.ufab.controller.pessoas.AlunoController;
import br.edu.ufab.model.entities.Curso;
import br.edu.ufab.model.entities.pessoas.Aluno;
import br.edu.ufab.model.enums.AreaDeCurso;
import br.edu.ufab.model.enums.PeriodoDeIngresso;
import br.edu.ufab.model.enums.TipoDeCurso;
/**
 * Teste unitario da classe AlunoController 
 * @author EquipeACL
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RestControllerAluno extends ConfigTeste{
	private Curso c;
	private MockMvc mockaux;
	
	@Autowired
	private AlunoController alunosController;
	
	@Autowired
	private CursoController cursoController;
    
    @Before
    public void setup(){
    	this.MAPPER = new ObjectMapper();
    	this.mockMvc = MockMvcBuilders.standaloneSetup(alunosController).build();
    	this.mockaux = MockMvcBuilders.standaloneSetup(cursoController).build();
    }
    
    @Test
    public void teste01AdicionarAluno() throws IOException, Exception{
    	c = new Curso();
    	
    	c.setNome("Computação");
    	c.setCodigo("1122");
    	c.setTipo(TipoDeCurso.MESTRADO);
    	c.setArea(AreaDeCurso.CIENCIAS_EXATAS);
    	mockaux.perform(post("/cursos")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(c)))
                .andExpect(status().isOk());    	
    	String response = mockaux.perform(MockMvcRequestBuilders.get("/cursos")).andReturn().getResponse().getContentAsString();
    	
        List<Curso> cursos = MAPPER.readValue(response,
           MAPPER.getTypeFactory().constructCollectionType(List.class, Curso.class));
    	
        c = cursos.get(0);
        
        Aluno a = new Aluno();
        a.setAno(2018);
        a.setCpf("06033090388");
        a.setCurso(c);
        a.setDatanascimento(new Date(System.currentTimeMillis()));
        a.setEmail("email@gmail.com");
        a.setEndereco("Rua da Ladeira");
        a.setNaturalidade("Campina Grande");
        a.setNome("Antonio da Silva");
        a.setNomemae("NomeDaMae");
        a.setPeriodo(PeriodoDeIngresso.PRIMEIRO);
        a.setRg("2001214");
        a.setSenha("123");
        a.setTelefone("8399656");
    	mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(a)))
                .andExpect(status().isOk());
    	
    	a = new Aluno();
    	
    	mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(a)))
                .andExpect(status().isBadRequest());
    	
    	a = new Aluno();
    	
    	a.setAno(2018);
        a.setCpf("050652330");
        a.setCurso(c);
        a.setDatanascimento(new Date(System.currentTimeMillis()));
        a.setEmail("email2@gmail.com");
        a.setEndereco("Rua da Ladeira2");
        a.setNaturalidade("Queimadas");
        a.setNome("Maria da Silva");
        a.setNomemae("NomeDaMae");
        a.setPeriodo(PeriodoDeIngresso.SEGUNDO);
        a.setRg("2001214");
        a.setSenha("123");
        a.setTelefone("8399656");
    	mockMvc.perform(post("/alunos")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(a)))
                .andExpect(status().isOk());
      
    }
       
    @Test
	public void teste02BuscarTodos() throws Exception {

    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/alunos")).andReturn().getResponse().getContentAsString();
    	List<Aluno> alunos = MAPPER.readValue(response,
    	           MAPPER.getTypeFactory().constructCollectionType(List.class, Aluno.class));
    			assertTrue(alunos.size()==2);
    			assertEquals(alunos.get(0).getNome(), "Antonio da Silva");
    			assertEquals(alunos.get(1).getNome(), "Maria da Silva");
        
	}
    
    @Test
	public void teste03BuscarUm() throws Exception {
    	
		String response = mockMvc.perform(MockMvcRequestBuilders.get("/alunos/1")).andReturn().getResponse().getContentAsString();
    	
		Aluno a = MAPPER.readValue(response,Aluno.class);
		
		assertEquals(a.getId(), 1);
		assertEquals(a.getNome(), "Antonio da Silva");
		assertEquals(a.getAno(),2018);
		assertEquals(a.getCpf(), "06033090388");
		assertEquals(a.getCurso().getNome(), "Computação");
		assertEquals(a.getEmail(), "email@gmail.com");
		assertEquals(a.getEndereco(), "Rua da Ladeira");
		assertEquals(a.getNaturalidade(), "Campina Grande");
  		
	}
    
    @Test
	public void teste04AtualizarAluno() throws Exception {
    	   	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/alunos/1")).andReturn().getResponse().getContentAsString();
    	
		Aluno a = MAPPER.readValue(response,Aluno.class);
		
		
		a.setNome("Novo nome do aluno");
		a.setCpf("123456");
		a.setEmail("novoemail@gmail.com");
		a.setAno(2019);
		a.setEndereco("Novo endereco");
		a.setNaturalidade("Nova naturalidade");
		a.setNomemae("Novo nome da mae");
		mockMvc.perform(put("/alunos/"+a.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(MAPPER.writeValueAsString(a)))
                .andExpect(status().isOk());
	
		response = mockMvc.perform(MockMvcRequestBuilders.get("/alunos/1")).andReturn().getResponse().getContentAsString();
    	
		a = MAPPER.readValue(response,Aluno.class);
		
		assertEquals(a.getId(), 1);
		assertEquals(a.getNome(), "Novo nome do aluno");
		assertEquals(a.getCpf(), "123456");
		assertEquals(a.getEmail(), "novoemail@gmail.com");
		assertEquals(a.getAno(), 2019);
		assertEquals(a.getEndereco(), "Novo endereco");
		assertEquals(a.getNaturalidade(), "Nova naturalidade");
		assertEquals(a.getNomemae(), "Novo nome da mae");
	}
    
    @Test
    public void teste05DeletarAnalDeCongresso() throws Exception{
    	
    	String response = mockMvc.perform(MockMvcRequestBuilders.get("/alunos")).andReturn().getResponse().getContentAsString();
    	
        List<Aluno> alunos = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, Aluno.class));
    	for(Aluno temp : alunos){
    		mockMvc.perform(delete("/alunos/"+temp.getId())).andExpect(status().isNoContent());
    	}
        
    	response = mockaux.perform(MockMvcRequestBuilders.get("/cursos")).andReturn().getResponse().getContentAsString();
    	
        List<Curso> cursos = MAPPER.readValue(response,
            MAPPER.getTypeFactory().constructCollectionType(List.class, Curso.class));
    	for(Curso temp : cursos){
    		mockaux.perform(delete("/cursos/"+temp.getId())).andExpect(status().isNoContent());
    	}
    	
    	
    }
    

}
