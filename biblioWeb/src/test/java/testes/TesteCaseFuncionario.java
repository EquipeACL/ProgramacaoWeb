//package testes.usuarios;
//
//import static org.junit.Assert.*;
//
//import org.junit.Before;
//import org.junit.Test;
//
//import br.uepb.model.usuarios.Administrador;
//import br.uepb.model.usuarios.Funcionario;
//
//public class TesteCaseFuncionario {
//	private Funcionario funcionario;
//	private Administrador administrador;
//	
//	@Before
//	public void setup() {
//		funcionario = new Funcionario();
//		administrador = new Administrador();
//	} 
//	
//	@Test
//	public void testeCreateFuncionario() {
//		funcionario = new Funcionario(123,"Funcionario1",123,"Brasil","Rua da ladeira",1234,"email@gmail.com","senha123","func1");
//		assertTrue(administrador.createFuncionario(funcionario));
//		funcionario = administrador.searchFuncionario("Funcionario1").get(0);
//		assertTrue(administrador.removeFuncionario(funcionario));
//	}
//	
//	@Test
//	public void testeRemoveFuncionario() {
//		funcionario = new Funcionario(123,"Funcionario1",123,"Brasil","Rua da ladeira",1234,"email@gmail.com","senha123","func1");
//		assertTrue(administrador.createFuncionario(funcionario));
//		funcionario = new Funcionario(123,"Funcionario2",123,"Brasil","Rua da ladeira",1234,"email@gmail.com","senha123","func2");
//		assertTrue(administrador.createFuncionario(funcionario));
//		funcionario = new Funcionario(123,"Funcionario3",123,"Brasil","Rua da ladeira",1234,"email@gmail.com","senha123","func3");
//		assertTrue(administrador.createFuncionario(funcionario));
//		funcionario.setNomeCompleto("Funcionario");		
//		for(Funcionario f:administrador.searchFuncionario("Funcionario")) {
//			assertTrue(administrador.removeFuncionario(f));
//		}
//	}
//	
//	@Test
//	public void testeUpdateFuncionario() {
//		funcionario = new Funcionario(123,"Funcionario1",123,"Brasil","Rua da ladeira",1234,"email@gmail.com","senha123","func1");
//		assertTrue(administrador.createFuncionario(funcionario));
//		funcionario = administrador.searchFuncionario("Funcionario1").get(0);
//		funcionario.setNomeCompleto("Novo Nome");
//		funcionario.setEmail("novoemail@gmail.com");
//		assertTrue(administrador.updateFuncionario(funcionario));
//	}
//	
//	@Test
//	public void testeSearchFuncionario() {
//		funcionario = new Funcionario(123,"Funcionario1",123,"Brasil","Rua da ladeira",1234,"email@gmail.com","senha123","func1");
//		assertTrue(administrador.createFuncionario(funcionario));
//		
//		funcionario = administrador.searchFuncionario("Funcionario1").get(0);
//		assertEquals("Funcionario1",funcionario.getNomeCompleto());
//		assertEquals(123,funcionario.getCpf());
//		assertEquals("Rua da ladeira",funcionario.getEndereco());
//		assertEquals("email@gmail.com",funcionario.getEmail());
//		assertEquals("senha123",funcionario.getSenhaAcesso());
//		assertEquals("func1",funcionario.getNomeUsuario());
//	}
//	
//	@Test
//	public void testeValidaFuncionario(){
//		funcionario.setCpf(0);
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setCpf(123);
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setEmail("");
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setEmail("email@gmail.com");
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setEndereco("");
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setEndereco("Rua da laderia");
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setNaturalidade("");
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setNaturalidade("Brasil");
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setNomeCompleto("");
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setNomeCompleto("Funcionario");
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setNomeUsuario("");
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setNomeUsuario("func");
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setRg(-1);
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setRg(123);
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setSenhaAcesso("");
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setSenhaAcesso("senha");
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setTelefone(-1);
//		assertFalse(administrador.createFuncionario(funcionario));
//		funcionario.setTelefone(3333);
//		assertTrue(administrador.createFuncionario(funcionario));
//		for(Funcionario f: administrador.searchFuncionario("Funcionario")) {
//			assertTrue(administrador.removeFuncionario(f));
//		}
//		
//	}
//}
