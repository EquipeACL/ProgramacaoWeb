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

import br.uepb.biblio.repository.Cidades;
import br.uepb.model.acervo.Anais;
import br.uepb.model.enums.Tipo_anal;



@Controller
@RequestMapping("/anais")
public class AnaisController {
	
	/* 
	@Autowired
	private AnaisRepository anaisRepository;
	
	@Autowired
	private CadastroAnaisService anaisService;*/
	
	@Autowired
	private Cidades cidades;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Anais anal,String busca) {
		ModelAndView mv = new ModelAndView("/anais/CadastroAnais");
		mv.addObject("tipos", Tipo_anal.values());
		mv.addObject("autores",new String[] {"Autor1","Autor2","Autor3"});
		mv.addObject("cidades",cidades.findAll());
		mv.addObject("estados",new String[] {"AL","CE","PB"});
		if(busca!=null){
			//mv.addObject("listaAnais",anaisService.buscarPorTitulo(busca));
		}else{
			//mv.addObject("listasAnais",anaisRepository.findAll());
		}
		return mv;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView mv = new ModelAndView("/anais/PesquisaAnais");
		mv.addObject("tipos", Tipo_anal.values());
		mv.addObject("autores",new String[] {"Autor1","Autor2","Autor3"});
		mv.addObject("cidades",cidades.findAll());
		mv.addObject("estados",new String[] {"AL","CE","PB"});
		if(busca!=null){
			//mv.addObject("listaAnais",anaisService.buscarPorTitulo(busca));
		}else{
			//mv.addObject("listasAnais",anaisRepository.findAll());
		}
		return mv;
	}
	
	@RequestMapping(value="/novo",method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Anais anal, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(anal,null);
		}
		//salvar no banco
		attributes.addFlashAttribute("mensagem", "Anal salvo com sucesso!");
		return new ModelAndView("redirect:/anais/novo");
	}
}
