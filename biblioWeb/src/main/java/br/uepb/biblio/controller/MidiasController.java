package br.uepb.biblio.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.uepb.model.acervo.Midias_Eletronicas;
import br.uepb.model.enums.Tipo_midia;

@Controller
@RequestMapping("/midias")
public class MidiasController {
	/*
	@Autowired
	private Midias midiasRepository;
	
	@Autowired
	private CadastroMidiasService midiasService;*/
	
	@RequestMapping("/novo")
	public ModelAndView novo(Midias_Eletronicas midia, String busca) {
		ModelAndView model = new ModelAndView("midia/CadastroMidia");
		model.addObject("tipos", Tipo_midia.values());
		if(busca!=null){
			//model.addObject("listaMidias",midiasService.buscarPorNome(busca));
		}else{
			//model.addObject("listaMidias",midiasRepository.findAll());
		}
		return model;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView model = new ModelAndView("midia/PesquisaMidia");
		model.addObject("tipos", Tipo_midia.values());
		if(busca!=null){
			//model.addObject("listaMidias",midiasService.buscarPorNome(busca));
		}else{
			//model.addObject("listaMidias",midiasRepository.findAll());
		}
		return model;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar (@Valid Midias_Eletronicas midia, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(midia,null);
		}
		//salvar no banco
		
		attributes.addFlashAttribute("mensagem", "Midia salva com sucesso!");
		return new ModelAndView("redirect:/midias/novo");
	}

}
