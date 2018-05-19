package br.uepb.biblio.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.uepb.biblio.repository.Funcionarios;
import br.uepb.biblio.repository.Grupos;
import br.uepb.biblio.service.CrudFuncionarioService;
import br.uepb.biblio.service.CrudGrupoService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.biblio.service.exception.LoginDuplicadoException;
import br.uepb.biblio.service.exception.SenhaObrigatoriaUsuarioException;
import br.uepb.model.enums.Tipo_nivel;
import br.uepb.model.usuarios.Aluno;
import br.uepb.model.usuarios.Funcionario;
/**
 * Essa é a classe Controller da classe Usuario, e é responsável por fazer a ponte entre as views referentes a esse objeto e os Models, de acordo com as solicitações realizadas nas rotas.
 * @author EquipeACL
 *
 */
@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private CrudFuncionarioService cadastroFuncionarioService;
	
	@Autowired
	private CrudGrupoService cadastroGrupoService;
	
	@Autowired
	private Funcionarios funcionarios;
	
	@Autowired
	private Grupos grupos;
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota usuarios/novo	
	 * @param funcionario, que é o objeto a ser acessado
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/novo")
	public ModelAndView novo(Funcionario funcionario) { 
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		mv.addObject("grupos",cadastroGrupoService.buscaGrupos());
		
		return mv;
		
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView mv = new ModelAndView("usuario/PesquisaUsuario");
		if(busca!=null){
			mv.addObject("listaUsuarios",cadastroFuncionarioService.buscarPorNome(busca));
		}else{
			mv.addObject("listaUsuarios",funcionarios.findAll());
		}
		return mv;
	}
	
	@RequestMapping("/editar")
	ModelAndView editar(String id) {
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		mv.addObject("funcionario", funcionarios.findOne(Integer.parseInt(id)));
		mv.addObject("grupos",cadastroGrupoService.buscaGrupos());
		mv.addObject("listaUsuarios",funcionarios.findAll());
		return mv;
	}
	
	/**
	 * Esse é o método que irá acessar a rota funcionarios/novo, porém com uma requisição do tipo POST, que servirá para salvar o objeto passado por parâmetro no banco
	 * @param funcionario, que é o objeto que será mapeado no formulário para salvar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/usuarios/novo"), que renderiza a página no endereço usuarios/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastro(@Valid Funcionario funcionario, BindingResult result,RedirectAttributes attributes, Model model ) {
		if(result.hasErrors()) {
			return novo(funcionario);
		}
		try {
			cadastroFuncionarioService.salvar(funcionario);
		}
		catch (ItemDuplicadoException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(funcionario));
		}
		catch(LoginDuplicadoException e) {
			result.rejectValue("login", e.getMessage(),e.getMessage());
			return (novo(funcionario));
		}
		catch(SenhaObrigatoriaUsuarioException e){
			result.rejectValue("senha", e.getMessage(),e.getMessage());
		}
		attributes.addFlashAttribute("mensagem", "Funcionário salvo com sucesso!");
		
		return new ModelAndView("redirect:/usuarios/novo");
		
	}
	
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	public ModelAndView atualizar(@Valid Funcionario funcionario, BindingResult result,RedirectAttributes attributes, Model model ) {
		if(result.hasErrors()) {
			return novo(funcionario);
		}
		try {
			cadastroFuncionarioService.atualizar(funcionario);
		}
		catch (ItemDuplicadoException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(funcionario));
		}
		catch(LoginDuplicadoException e) {
			result.rejectValue("login", e.getMessage(),e.getMessage());
			return (novo(funcionario));
		}
		catch(SenhaObrigatoriaUsuarioException e){
			result.rejectValue("senha", e.getMessage(),e.getMessage());
		}
		attributes.addFlashAttribute("mensagem", " Funcionário atualizado com sucesso!");
		
		return new ModelAndView("redirect:/usuarios/novo");
		
	}
	
	@RequestMapping(value="/remover",method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Funcionario funcionario,RedirectAttributes attributes){
		try {
			//vai tentar remover no banco
			cadastroFuncionarioService.remover(funcionario.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
}
