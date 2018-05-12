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

import br.uepb.biblio.repository.Editoras;
import br.uepb.biblio.repository.Revistas;
import br.uepb.biblio.service.CrudRevistaService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Revista;

@Controller
@RequestMapping("/revistas")
public class RevistasController {
	
	@Autowired
	private Revistas revistaRepository;
	
	@Autowired
	private Editoras editoraRepository;
	
	@Autowired
	private CrudRevistaService revistaService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Revista revista, String busca) {
		ModelAndView model = new ModelAndView("/revista/CadastroRevista");
		model.addObject("listaEditoras",editoraRepository.findAll());
		if(busca!=null){
			model.addObject("listaRevistas", revistaService.buscarPorTitulo(busca));
		}else{
			model.addObject("listaRevistas", revistaRepository.findAll());
		}
		return model;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView model = new ModelAndView("/revista/PesquisaRevista");
		if(busca!=null){
			model.addObject("listaRevistas", revistaService.buscarPorTitulo(busca));
		}else{
			model.addObject("listaRevistas", revistaRepository.findAll());
		}
		return model;
	}
	
	@RequestMapping(value="/novo",method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Revista revista, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(revista,null);
		}
		//salvar no banco		
		
		try {
			revistaService.salvar(revista);
		} catch (ItemDuplicadoException e) {
			result.rejectValue("titulo", e.getMessage(), e.getMessage());
			return(novo(revista, null));
		}
		attributes.addFlashAttribute("mensagem", "Revista salva com sucesso!");	
		return new ModelAndView("redirect:/revistas/novo");
		
		
	}
}
