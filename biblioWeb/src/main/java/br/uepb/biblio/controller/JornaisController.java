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
}
