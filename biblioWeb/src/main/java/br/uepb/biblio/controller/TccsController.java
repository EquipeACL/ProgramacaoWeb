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

import br.uepb.biblio.repository.Autores;
import br.uepb.biblio.repository.Orientadores;
import br.uepb.model.acervo.Tcc;
import br.uepb.model.enums.Tipo_nivel_aluno;
import br.uepb.model.enums.Tipo_tcc;

@Controller
@RequestMapping("/tccs")
public class TccsController {

	@Autowired
	Autores autores;
	@Autowired
	Orientadores orientadores;
	
	
	@RequestMapping("/novo")
	public ModelAndView novo(Tcc tcc) {
		ModelAndView model = new ModelAndView("/tcc/CadastroTcc");
		model.addObject("autores",autores.findAll());
		model.addObject("orientadores",orientadores.findAll());
		model.addObject("cidades",new String[] {"Cidade1","Cidade2","Cidade3"});
		model.addObject("estados",new String[] {"AL","CE","PB"});
		model.addObject("formacoes",Tipo_nivel_aluno.values());
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
