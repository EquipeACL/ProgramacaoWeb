package br.uepb.biblio.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.uepb.model.usuarios.Usuario;

@Controller
@RequestMapping("/usuario")
public class UsuariosController {

	@RequestMapping("/novo")
	public ModelAndView novo(Usuario usuario) { 
		ModelAndView mv = new ModelAndView("usuario/CadastroUsuario");
		return mv;
		
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastro(@Valid Usuario usuario, BindingResult result,RedirectAttributes attributes, Model model ) {
		if(result.hasErrors()) {
			return novo(usuario);
		}
		
		attributes.addAttribute("mensagem", "Usuario adicionado com sucesso");
		
		return new ModelAndView("redirect:/usuario/novo");
		
	}
}
