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
import br.uepb.biblio.service.CadastroFuncionarioService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.usuarios.Funcionario;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	CadastroFuncionarioService cadastroFuncionarioService;
	
	@Autowired
	Funcionarios funcionarios;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Funcionario funcionario) { 
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		return mv;
		
	}
	
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
		attributes.addFlashAttribute("mensagem", "Funcionário salvo com sucesso!");
		
		return new ModelAndView("redirect:/usuarios/novo");
		
	}
}
