package br.uepb.biblio.controller;


import javax.servlet.http.HttpServletRequest;
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
import br.uepb.biblio.service.CadastroAreaConhecimento;
import br.uepb.biblio.service.exception.NomeAreaConhecimentoJaCadastradaException;
import br.uepb.model.AreaConhecimento;

@Controller
@RequestMapping("/areasconhecimento")
public class AreaConhecimentoController {
	@Autowired
	private CadastroAreaConhecimento cadastroAreaConhecimento;
	
	@Autowired
	private AreasConhecimento repositoryAreas;
	
	@RequestMapping("/novo")
	public ModelAndView novo(AreaConhecimento areaConhecimento){
		ModelAndView model = new ModelAndView("areaConhecimento/CadastroAreaConhecimento");
		model.addObject("listaAreaConhecimento",repositoryAreas.findAll());
		return model;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(){
		ModelAndView model = new ModelAndView("areaConhecimento/PesquisaAreaConhecimento");
		model.addObject("listaAreaConhecimento",repositoryAreas.findAll());
		return model;
	}
	
	@RequestMapping(value="/pesquisar",method=RequestMethod.POST)
	public ModelAndView pesquisar(String busca, HttpServletRequest request){
		ModelAndView model = new ModelAndView("areaConhecimento/PesquisaAreaConhecimento");
		if(request.getServletPath().equals("/areasconhecimento/pesquisar")){
			model.addObject("listaAreaConhecimento",cadastroAreaConhecimento.buscarPorNome(busca));
		}else if(request.getServletPath().equals("/areasconhecimento/novo")){
			model = new ModelAndView("areaConhecimento/CadastroAreaConhecimento");
			model.addObject("listaAreaConhecimento",cadastroAreaConhecimento.buscarPorNome(busca));
		}		
		return model;
	}
	
	@RequestMapping(value="/novo",method=RequestMethod.POST)
	public ModelAndView cadastrar(@Valid AreaConhecimento areaConhecimento, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(areaConhecimento);
		}
		
		try{
			cadastroAreaConhecimento.salvar(areaConhecimento);
		}catch(NomeAreaConhecimentoJaCadastradaException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(areaConhecimento));
		}
		attributes.addFlashAttribute("mensagem", "Area de Conhecimento salva com sucesso!");
		return new ModelAndView("redirect:/areasconhecimento/novo");
	}
}
