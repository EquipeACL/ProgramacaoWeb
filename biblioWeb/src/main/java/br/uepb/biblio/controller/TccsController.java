package br.uepb.biblio.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.uepb.model.acervo.Tcc;
import br.uepb.model.enums.Tipo_tcc;

@Controller
@RequestMapping("/tccs")
public class TccsController {

	@RequestMapping("/novo")
	public ModelAndView novo(Tcc tcc) {
		ModelAndView model = new ModelAndView("/tcc/CadastroTcc");
		model.addObject("autores",new String[] {"Autor1","Autor2","Autor3"});
		model.addObject("orientadores",new String[] {"Orientador1","Orientador2","Orientador3"});
		model.addObject("cidades",new String[] {"Cidade1","Cidade2","Cidade3"});
		model.addObject("estados",new String[] {"AL","CE","PB"});
		model.addObject("tipos",Tipo_tcc.values());
		return model;
	}
	
	@RequestMapping(value="/novo",method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Tcc tcc, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(tcc);
		}
		//salvar no banco
		attributes.addFlashAttribute("mensagem", "Tcc salvo com sucesso!");
		return new ModelAndView("redirect:/tccs/novo");
	}
}
