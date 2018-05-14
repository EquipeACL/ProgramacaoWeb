package br.uepb.biblio.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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

import br.uepb.biblio.repository.Jornais;
import br.uepb.biblio.service.CrudJornalService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Jornal;

@Controller
@RequestMapping("/jornais")
public class JornaisController {
	
	@Autowired
	private Jornais jornaisRepository;
	
	@Autowired
	private CrudJornalService jornaisService;
	
	
	@RequestMapping("/novo")
	public ModelAndView novo(Jornal jornal,String busca) {
		ModelAndView model = new ModelAndView("jornal/CadastroJornal");
		if(busca!=null){
			model.addObject("listaJornais", jornaisService.buscarPorTitulo(busca));
		}else{
			model.addObject("listaJornais", jornaisRepository.findAll());
		}
		return model;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView model = new ModelAndView("jornal/PesquisaJornal");
		if(busca!=null){
			model.addObject("listaJornais", jornaisService.buscarPorTitulo(busca));
		}else{
			model.addObject("listaJornais", jornaisRepository.findAll());
		}
		return model;
	}
	
	@RequestMapping("/editar")
	public ModelAndView editar(String id) {
		ModelAndView model = new ModelAndView("jornal/CadastroJornal");
		model.addObject("jornal",jornaisRepository.findOne(Integer.parseInt(id)));
		model.addObject("listaJornais", jornaisRepository.findAll());
		return model;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar (@Valid Jornal jornal, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(jornal,null);
		}
		//salvar no banco
		try {
			jornaisService.salvar(jornal);
		} catch (ItemDuplicadoException e) {
			result.rejectValue("titulo", e.getMessage(),e.getMessage());
			return (novo(jornal,null));
		}
		attributes.addFlashAttribute("mensagem", "Jornal salvo com sucesso!");
		return new ModelAndView("redirect:/jornais/novo");
	}
	
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	public ModelAndView atualizar(@Valid Jornal jornal, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(jornal,null);
		}
		//atualiza no banco
		try {
			jornaisService.atualizar(jornal);
		} catch (Exception e) {
			result.rejectValue("titulo", e.getMessage(),e.getMessage());
			return (novo(jornal,null));
		}
		attributes.addFlashAttribute("mensagem", "Jornal atualizado com sucesso!");
		return new ModelAndView("redirect:/jornais/novo");
	}
	
	@RequestMapping(value="/remover",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Jornal jornal,RedirectAttributes attributes){
		try {
			//vai tentar remover no banco
			jornaisService.remover(jornal.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
}
