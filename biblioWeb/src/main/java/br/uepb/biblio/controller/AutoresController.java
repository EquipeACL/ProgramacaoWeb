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

import br.uepb.biblio.repository.Autores;
import br.uepb.biblio.service.CadastroAutorService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Autor;

@Controller
@RequestMapping("/autores")
public class AutoresController {

	@Autowired
	private Autores autoresRepository;
	
	@Autowired
	private CadastroAutorService cadastroAutorService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Autor autor,String busca) {
		ModelAndView mv = new ModelAndView("autor/CadastroAutor");
		if(busca!=null){
			mv.addObject("listaAutores", cadastroAutorService.buscarPorNome(busca));
		}else{
			mv.addObject("listaAutores", autoresRepository.findAll());
		}
		return mv;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView mv = new ModelAndView("autor/PesquisaAutor");
		if(busca!=null){
			mv.addObject("listaAutores", cadastroAutorService.buscarPorNome(busca));
		}else{
			mv.addObject("listaAutores", autoresRepository.findAll());
		}
		return mv;
	}	
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar (@Valid Autor autor, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(autor,null);
		}
		try {
			cadastroAutorService.salvar(autor);
		}
		catch(ItemDuplicadoException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(autor,null));
		}
		
		attributes.addFlashAttribute("mensagem", "Autor salvo com sucesso!");
		return new ModelAndView("redirect:/autores/novo");
	}
	
	@RequestMapping(value = "/autores", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody Autor autor,BindingResult result){
		
		//Se deu erro ele vai retornar a msg padrão definida lá no @NotBlank ou de outra anotação se houver
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
		}
		try {
			//vai tentar salvar no banco
			cadastroAutorService.salvar(autor);
		}
		catch(ItemDuplicadoException e) {
			//se ja tiver nome cadastrado vai lançar essa exceção
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		//se tiver tudo ok, vem pra cá
		return ResponseEntity.ok(autor);
	}
	
}
