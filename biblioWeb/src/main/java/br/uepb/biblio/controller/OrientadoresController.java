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

import br.uepb.model.Orientador;

@Controller
@RequestMapping("/orientadores")
public class OrientadoresController {

	@Autowired
	//CadastroAutorService cadastroAutorService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Orientador orientador) {
		ModelAndView mv = new ModelAndView("/orientador/CadastroOrientador");
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar (@Valid Orientador orientador, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(orientador);
		}
		//salvar no banco
		//cadastroAutorService.salvar(autor);
		attributes.addFlashAttribute("mensagem", "Orientador salvo com sucesso!");
		return new ModelAndView("redirect:/orientadores/novo");
	}
	
}