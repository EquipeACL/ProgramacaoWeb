package br.uepb.biblio.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.uepb.biblio.repository.Editoras;
import br.uepb.biblio.repository.Revistas;
import br.uepb.biblio.service.CrudRevistaService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Revista;
import br.uepb.model.jpaEntity.acervo.EntityRevista;

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
	
	@RequestMapping("/editar")
	public ModelAndView editar(String id) {
		ModelAndView model = new ModelAndView("/revista/CadastroRevista");
		model.addObject("revista",revistaRepository.findOne(Integer.parseInt(id)));
		model.addObject("listaEditoras",editoraRepository.findAll());
		model.addObject("listaRevistas", revistaRepository.findAll());
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
	
	@RequestMapping(value="/editar",method = RequestMethod.POST)
	public ModelAndView atualizar(@Valid Revista revista, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(revista,null);
		}
		//atualiza no banco		
		try {
			revistaService.atualizar(revista);
		} catch (ItemDuplicadoException e) {
			result.rejectValue("titulo", e.getMessage(), e.getMessage());
			return(novo(revista, null));
		}
		attributes.addFlashAttribute("mensagem", "Revista atualizada com sucesso!");	
		return new ModelAndView("redirect:/revistas/novo");
		
		
	}
	
	@RequestMapping(value="/remover",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Revista revista,RedirectAttributes attributes){
		try {
			//vai tentar remover no banco
			revistaService.remover(revista.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value="/buscarAll",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> buscarAll(){
		
		List<EntityRevista> retorno = revistaRepository.findAll();
		
		return ResponseEntity.ok(retorno);
	} 
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
}
