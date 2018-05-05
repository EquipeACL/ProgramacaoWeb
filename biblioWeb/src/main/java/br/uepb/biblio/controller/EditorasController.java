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
import br.uepb.biblio.service.CadastroEditoraService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Editora;

@Controller
public class EditorasController {


	@Autowired
	private Editoras editorasRepoditory;
	
	@Autowired
	private CadastroEditoraService cadastroEditoraService;
	
	@RequestMapping("/editoras/novo")
	ModelAndView novo(Editora editora,String busca) {
		ModelAndView mv = new ModelAndView("editora/CadastroEditora");
		if(busca!=null){
			mv.addObject("listaEditora", cadastroEditoraService.buscarPorNome(busca));
		}else{
			mv.addObject("listaEditora", editorasRepoditory.findAll());
		}
		return mv;
	}
	
	@RequestMapping("/editoras/pesquisar")
	ModelAndView pesquisar(String busca) {
		ModelAndView mv = new ModelAndView("editora/PesquisaEditora");
		if(busca!=null){
			mv.addObject("listaEditora", cadastroEditoraService.buscarPorNome(busca));
		}else{
			mv.addObject("listaEditora", editorasRepoditory.findAll());
		}
		return mv;
	}
	
	@RequestMapping(value = "/editoras/novo", method = RequestMethod.POST)
	ModelAndView cadastro(@Valid Editora editora, BindingResult result, Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return (novo(editora,null));
		}
		try {
			cadastroEditoraService.salvar(editora);
		}catch(ItemDuplicadoException e) {
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(editora,null));
		}
		
		attributes.addFlashAttribute("mensagem", "Editora salva com sucesso!");
		return new ModelAndView("redirect:/editoras/novo");

	}
}
