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

import br.uepb.biblio.repository.Midias;
import br.uepb.biblio.service.CrudMidiasEletronicasService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.MidiasEletronicas;
import br.uepb.model.enums.Tipo_midia;
import br.uepb.model.jpaEntity.acervo.EntityMidiasEletronicas;

@Controller
@RequestMapping("/midias")
public class MidiasController {
	
	@Autowired
	private Midias midiasRepository;
	
	@Autowired
	private CrudMidiasEletronicasService midiasService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(MidiasEletronicas midia, String busca) {
		ModelAndView model = new ModelAndView("midia/CadastroMidia");
		model.addObject("tipos", Tipo_midia.values());
		if(busca!=null){
			model.addObject("listaMidias",midiasService.buscarPorTitulo(busca));
		}else{
			model.addObject("listaMidias",midiasRepository.findAll());
		}
		return model;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView model = new ModelAndView("midia/PesquisaMidia");
		model.addObject("tipos", Tipo_midia.values());
		if(busca!=null){
			model.addObject("listaMidias",midiasService.buscarPorTitulo(busca));
		}else{
			model.addObject("listaMidias",midiasRepository.findAll());
		}
		return model;
	}
	
	@RequestMapping("/editar")
	public ModelAndView editar(String id) {
		ModelAndView model = new ModelAndView("midia/CadastroMidia");
		model.addObject("midiasEletronicas",midiasRepository.findOne(Integer.parseInt(id)));
		model.addObject("tipos", Tipo_midia.values());
		model.addObject("listaMidias",midiasRepository.findAll());
		return model;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar (@Valid MidiasEletronicas midia, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(midia,null);
		}
		//salvar no banco
		try {
			midiasService.salvar(midia);
		} catch (ItemDuplicadoException e) {
			result.rejectValue("titulo", e.getMessage(), e.getMessage());
			return (novo(midia, null));
		}
		attributes.addFlashAttribute("mensagem", "Midia salva com sucesso!");
		return new ModelAndView("redirect:/midias/novo");
	}
	
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	public ModelAndView atualizar (@Valid MidiasEletronicas midia, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(midia,null);
		}
		//atualiza no banco
		try {
			midiasService.atualizar(midia);
		} catch (ItemDuplicadoException e) {
			result.rejectValue("titulo", e.getMessage(), e.getMessage());
			return (novo(midia, null));
		}
		attributes.addFlashAttribute("mensagem", "Midia atualizada com sucesso!");
		return new ModelAndView("redirect:/midias/novo");
	}
	
	@RequestMapping(value = "/remover", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody MidiasEletronicas midia, RedirectAttributes attributes) {
		try {
			// vai tentar remover no banco
			midiasService.remover(midia.getId());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value="/buscarAll",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> buscarAll(){
		
		List<EntityMidiasEletronicas> retorno = midiasRepository.findAll();
		
		return ResponseEntity.ok(retorno);
	} 
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}

}
