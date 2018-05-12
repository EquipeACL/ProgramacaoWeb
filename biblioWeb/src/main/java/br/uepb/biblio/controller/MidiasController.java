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

import br.uepb.biblio.repository.Midias;
import br.uepb.biblio.service.CrudMidiasEletronicasService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.MidiasEletronicas;
import br.uepb.model.enums.Tipo_midia;

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
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar (@Valid MidiasEletronicas midia, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(midia,null);
		}
		//salvar no banco
		// Convertendo a string da data do html em sql.Date
		java.sql.Date dataSql = new java.sql.Date(Integer.parseInt(midia.getData_string().substring(2, 4)) + 100,
				Integer.parseInt(midia.getData_string().substring(5, 7)),
				Integer.parseInt(midia.getData_string().substring(8, 10)));
		midia.setData(dataSql);
		try {
			midiasService.salvar(midia);
		} catch (ItemDuplicadoException e) {
			result.rejectValue("titulo", e.getMessage(), e.getMessage());
			return (novo(midia, null));
		}
		attributes.addFlashAttribute("mensagem", "Midia salva com sucesso!");
		return new ModelAndView("redirect:/midias/novo");
	}

}
