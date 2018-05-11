package br.uepb.biblio.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import br.uepb.biblio.repository.AreasConhecimento;
import br.uepb.biblio.service.CadastroAreaConhecimento;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.AreaConhecimento;
import br.uepb.model.Autor;

@Controller
@RequestMapping("/areasconhecimento")
public class AreaConhecimentoController {
	@Autowired
	private CadastroAreaConhecimento cadastroAreaConhecimento;
	
	@Autowired
	private AreasConhecimento repositoryAreas;
	
	@RequestMapping("/novo")
	public ModelAndView novo(AreaConhecimento areaConhecimento,String busca){
		ModelAndView model = new ModelAndView("areaConhecimento/CadastroAreaConhecimento");
		
		if(busca!=null){
			model.addObject("listaAreaConhecimento",cadastroAreaConhecimento.buscarPorNome(busca));
		}else{
			model.addObject("listaAreaConhecimento",repositoryAreas.findAll());
		}
		return model;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca){
		ModelAndView model = new ModelAndView("areaConhecimento/PesquisaAreaConhecimento");
		
		if(busca!=null){
			model.addObject("listaAreaConhecimento",cadastroAreaConhecimento.buscarPorNome(busca));
		}else{
			model.addObject("listaAreaConhecimento",repositoryAreas.findAll());
		}
		return model;
	}
	
	@RequestMapping("/editar")
	public ModelAndView editar(String id){
		ModelAndView model = new ModelAndView("areaConhecimento/CadastroAreaConhecimento");
		model.addObject("areaConhecimento",repositoryAreas.findOne(Integer.parseInt(id)));
		model.addObject("listaAreaConhecimento",repositoryAreas.findAll());
		return model;
	}
	
	@RequestMapping(value="/editar",method=RequestMethod.POST)
	public ModelAndView editar(@Valid AreaConhecimento areaConhecimento, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(areaConhecimento,null);
		}		
		try{
			cadastroAreaConhecimento.atualizar(areaConhecimento);
		}catch(Exception e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(areaConhecimento,null));
		}
		attributes.addFlashAttribute("mensagem", "Area de Conhecimento atualizada com sucesso!");
		return new ModelAndView("redirect:/areasconhecimento/novo");
	}
	
	@RequestMapping(value="/novo",method=RequestMethod.POST)
	public ModelAndView cadastrar(@Valid AreaConhecimento areaConhecimento, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(areaConhecimento,null);
		}		
		try{
			cadastroAreaConhecimento.salvar(areaConhecimento);
		}catch(ItemDuplicadoException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(areaConhecimento,null));
		}
		attributes.addFlashAttribute("mensagem", "Area de Conhecimento salva com sucesso!");
		return new ModelAndView("redirect:/areasconhecimento/novo");
	}
	
	@RequestMapping(value="/remover",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody AreaConhecimento area,RedirectAttributes attributes){
		try {
			//vai tentar remover no banco
			cadastroAreaConhecimento.remover(area.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody AreaConhecimento area,BindingResult result){
		
		//Se deu erro ele vai retornar a msg padrão definida lá no @NotBlank ou de outra anotação se houver
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
		}
		try {
			//vai tentar salvar no banco
			cadastroAreaConhecimento.salvar(area);
		}
		catch(ItemDuplicadoException e) {
			//se ja tiver nome cadastrado vai lançar essa exceção
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		//se tiver tudo ok, vem pra cá
		return ResponseEntity.ok(area);
	}
}
