package br.uepb.biblio.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.uepb.biblio.repository.Funcionarios;
import br.uepb.biblio.repository.Grupos;
import br.uepb.biblio.service.CadastroFuncionarioService;
import br.uepb.biblio.service.CadastroGrupoService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.biblio.service.exception.LoginDuplicadoException;
import br.uepb.biblio.service.exception.SenhaObrigatoriaUsuarioException;
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
	private CadastroFuncionarioService cadastroFuncionarioService;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
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
}
