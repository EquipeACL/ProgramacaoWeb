package br.uepb.biblio.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.uepb.model.acervo.Jornal;

@Controller
@RequestMapping("/jornais")
public class JornaisController {
	
	@RequestMapping("/novo")
	public ModelAndView novo(Jornal jornal) {
		ModelAndView model = new ModelAndView("jornal/CadastroJornal");
		return model;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar (@Valid Jornal jornal, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(jornal);
		}
		//salvar no banco
		
		attributes.addFlashAttribute("mensagem", "Jornal salvo com sucesso!");
		return new ModelAndView("redirect:/jornais/novo");
	}
}
