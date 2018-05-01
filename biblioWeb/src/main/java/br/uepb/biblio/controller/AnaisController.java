package br.uepb.biblio.controller;


import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.uepb.model.acervo.Anais;
import br.uepb.model.enums.Tipo_anal;



@Controller
@RequestMapping("/anais")
public class AnaisController {
	
	@RequestMapping("/novo")
	public ModelAndView novo(Anais anal) {
		ModelAndView mv = new ModelAndView("/anais/CadastroAnais");
		mv.addObject("tipos", Tipo_anal.values());
		return mv;
	}
	
	@RequestMapping(value="/novo",method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Anais anal, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(anal);
		}
		//salvar no banco
		attributes.addFlashAttribute("mensagem", "Anal salvo com sucesso!");
		return new ModelAndView("redirect:/anais/novo");
	}
}
