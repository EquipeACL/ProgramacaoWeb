package br.uepb.biblio.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.uepb.model.acervo.Revista;

@Controller
@RequestMapping("/revistas")
public class RevistasController {
	
	@RequestMapping("/novo")
	public ModelAndView novo(Revista revista) {
		ModelAndView model = new ModelAndView("/revista/CadastroRevista");
		return model;
	}
	
	@RequestMapping(value="/novo",method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Revista revista, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(revista);
		}
		//salvar no banco
		attributes.addFlashAttribute("mensagem", "Revista salva com sucesso!");
		return new ModelAndView("redirect:/revistas/novo");
	}
}