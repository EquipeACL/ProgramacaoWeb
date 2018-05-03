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

import br.uepb.biblio.repository.AreasConhecimento;
import br.uepb.biblio.repository.Editoras;
import br.uepb.model.acervo.Livro;

@Controller
public class LivrosController {

	@Autowired
	Editoras editoras;
	@Autowired
	AreasConhecimento areas;
	
	@RequestMapping("/livros/novo")
	public ModelAndView novo(Livro livro) {
		ModelAndView mv = new ModelAndView("livro/CadastroLivro");
		mv.addObject("editoras", editoras.findAll());
		mv.addObject("areas", areas.findAll());
		
		//mv.addObject("editoras",edito);
		return mv;
	}
	
	@RequestMapping(value = "/livros/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar (@Valid Livro livro, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(livro);
		}
		//salvar no banco
		
		attributes.addFlashAttribute("mensagem", "Livro salvo com sucesso!");
		return new ModelAndView("redirect:/livros/novo");
	}
	
}
