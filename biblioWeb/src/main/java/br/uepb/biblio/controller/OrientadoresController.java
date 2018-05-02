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

import br.uepb.biblio.service.CadastroOrientadorService;
import br.uepb.biblio.service.exception.NomeOrientadorJaCadastradoException;
import br.uepb.model.Orientador;
import br.uepb.model.enums.Tipo_nivel_aluno;

@Controller
@RequestMapping("/orientadores")
public class OrientadoresController {

	@Autowired
	CadastroOrientadorService cadastroOrientadorService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Orientador orientador) {
		ModelAndView mv = new ModelAndView("/orientador/CadastroOrientador");
		mv.addObject("formacoes", Tipo_nivel_aluno.values());
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar (@Valid Orientador orientador, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(orientador);
		}
		//
		try {
			cadastroOrientadorService.salvar(orientador);
		}
		catch(NomeOrientadorJaCadastradoException e) {
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return novo(orientador);
		}
		
		attributes.addFlashAttribute("mensagem", "Orientador salvo com sucesso!");
		return new ModelAndView("redirect:/orientadores/novo");
	}
	
}
