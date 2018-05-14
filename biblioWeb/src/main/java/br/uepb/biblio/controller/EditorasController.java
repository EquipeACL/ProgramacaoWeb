package br.uepb.biblio.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.uepb.biblio.repository.Editoras;
import br.uepb.biblio.service.CadastroEditoraService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Editora;

@Controller
@RequestMapping("/editoras")
public class EditorasController {


	@Autowired
	private Editoras editorasRepository;
	
	@Autowired
	private CadastroEditoraService cadastroEditoraService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Editora editora,String busca) {
		ModelAndView mv = new ModelAndView("editora/CadastroEditora");
		if(busca!=null){
			mv.addObject("listaEditora", cadastroEditoraService.buscarPorNome(busca));
		}else{
			mv.addObject("listaEditora", editorasRepository.findAll());
		}
		return mv;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView mv = new ModelAndView("editora/PesquisaEditora");
		if(busca!=null){
			mv.addObject("listaEditora", cadastroEditoraService.buscarPorNome(busca));
		}else{
			mv.addObject("listaEditora", editorasRepository.findAll());
		}
		return mv;
	}
	
	@RequestMapping("/editar")
	public ModelAndView editar(String id){
		ModelAndView model = new ModelAndView("editora/CadastroEditora");
		model.addObject("editora",editorasRepository.findOne(Integer.parseInt(id)));
		model.addObject("listaEditora",editorasRepository.findAll());
		return model;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
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
	
	@RequestMapping(value="/editar",method=RequestMethod.POST)
	public ModelAndView editar(@Valid Editora editora, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(editora,null);
		}		
		try{
			cadastroEditoraService.atualizar(editora);
		}catch(Exception e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(editora,null));
		}
		attributes.addFlashAttribute("mensagem", "Editora atualizada com sucesso!");
		return new ModelAndView("redirect:/editoras/novo");
	}
	
	@RequestMapping(value="/remover",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Editora editora){
		try {
			//vai tentar remover no banco
			cadastroEditoraService.remover(editora.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		//se tiver tudo ok, vem pra cá
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody Editora editora,BindingResult result){
		
		//Se deu erro ele vai retornar a msg padrão definida lá no @NotBlank ou de outra anotação se houver
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
		}
		Editora retorno = new Editora();
		try {
			//vai tentar salvar no banco
			retorno = new Editora(cadastroEditoraService.salvar(editora));
		}
		catch(ItemDuplicadoException e) {
			//se ja tiver nome cadastrado vai lançar essa exceção
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		//se tiver tudo ok, vem pra cá
		return ResponseEntity.ok(retorno);
	}
}
