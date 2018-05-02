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

import br.uepb.biblio.service.CadastroAutorService;
import br.uepb.biblio.service.exception.NomeAutorJaCadastradoException;
import br.uepb.model.Autor;

@Controller
@RequestMapping("/autores")
public class AutoresController {

	@Autowired
	CadastroAutorService cadastroAutorService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Autor autor) {
		ModelAndView mv = new ModelAndView("autor/CadastroAutor");
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar (@Valid Autor autor, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(autor);
		}
		//salvar no banco
		try {
			cadastroAutorService.salvar(autor);
		}
		catch(NomeAutorJaCadastradoException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(autor));
		}
		
		attributes.addFlashAttribute("mensagem", "Autor salvo com sucesso!");
		return new ModelAndView("redirect:/autores/novo");
	}
	
}
