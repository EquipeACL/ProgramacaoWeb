package br.uepb.biblio.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.uepb.biblio.repository.AreasConhecimento;
import br.uepb.biblio.service.CadastroAreaConhecimento;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.AreaConhecimento;

@Controller
@RequestMapping("/areasconhecimento")
public class AreaConhecimentoController {
	@Autowired
	private CadastroAreaConhecimento cadastroAreaConhecimento;
	
	@Autowired
	private AreasConhecimento repositoryAreas;
	
	@RequestMapping("/novo")
	public ModelAndView novo(AreaConhecimento areaConhecimento,String busca){
		ModelAndView model = new ModelAndView("areaConhecimento/CadastroAreaConhecimento");
		
		if(busca!=null){
			model.addObject("listaAreaConhecimento",cadastroAreaConhecimento.buscarPorNome(busca));
		}else{
			model.addObject("listaAreaConhecimento",repositoryAreas.findAll());
		}
		return model;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca){
		ModelAndView model = new ModelAndView("areaConhecimento/PesquisaAreaConhecimento");
		
		if(busca!=null){
			model.addObject("listaAreaConhecimento",cadastroAreaConhecimento.buscarPorNome(busca));
		}else{
			model.addObject("listaAreaConhecimento",repositoryAreas.findAll());
		}
		return model;
	}
	
	@RequestMapping("/editar")
	public ModelAndView editar(String id){
		ModelAndView model = new ModelAndView("areaConhecimento/CadastroAreaConhecimento");
		model.addObject("areaConhecimento",repositoryAreas.findOne(Integer.parseInt(id)));
		return model;
	}
	
	@RequestMapping(value="/editar",method=RequestMethod.POST)
	public ModelAndView editar(@Valid AreaConhecimento areaConhecimento, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(areaConhecimento,null);
		}		
		try{
			cadastroAreaConhecimento.atualizar(areaConhecimento);
		}catch(Exception e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(areaConhecimento,null));
		}
		attributes.addFlashAttribute("mensagem", "Area de Conhecimento atualizada com sucesso!");
		return new ModelAndView("redirect:/areasconhecimento/novo");
	}
	
	@RequestMapping(value="/novo",method=RequestMethod.POST)
	public ModelAndView cadastrar(@Valid AreaConhecimento areaConhecimento, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(areaConhecimento,null);
		}		
		try{
			cadastroAreaConhecimento.salvar(areaConhecimento);
		}catch(ItemDuplicadoException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(areaConhecimento,null));
		}
		attributes.addFlashAttribute("mensagem", "Area de Conhecimento salva com sucesso!");
		return new ModelAndView("redirect:/areasconhecimento/novo");
	}
}
