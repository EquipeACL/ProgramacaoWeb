package br.uepb.biblio.controller;

import java.util.List;

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

import br.uepb.biblio.repository.AreasConhecimento;
import br.uepb.biblio.repository.Temas;
import br.uepb.biblio.service.CadastroTemaService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.AreaConhecimento;
import br.uepb.model.Tema;
import br.uepb.model.jpaEntity.EntityTema;

@Controller
@RequestMapping("/temas")
public class TemaController{
	
	@Autowired
	private AreasConhecimento areasRepository;

	@Autowired
	private Temas temas;
	
	@Autowired
	private CadastroTemaService temaService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Tema tema,String busca){
		ModelAndView model = new ModelAndView("tema/CadastroTema");
		model.addObject("areas", areasRepository.findAll());
		if(busca!=null){
			model.addObject("temas",temaService.buscarPorNome(busca));
		}else{
			model.addObject("temas",temas.findAll());
		}
		return model;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca){
		ModelAndView model = new ModelAndView("tema/PesquisaTema");
		model.addObject("temas",temas.findAll());
		if(busca!=null){
			model.addObject("temas",temaService.buscarPorNome(busca));
		}else{
			model.addObject("temas",temas.findAll());
		}
		return model;
	}
	
	@RequestMapping("/editar")
	public ModelAndView editar(String id){
		ModelAndView model = new ModelAndView("tema/CadastroTema");
		model.addObject("tema",temas.findOne(Integer.parseInt(id)));
		model.addObject("temas",temas.findAll());
		model.addObject("areas", areasRepository.findAll());
		return model;
	}
	
	@RequestMapping(value="/editar",method=RequestMethod.POST)
	public ModelAndView editar(@Valid Tema tema, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(tema,null);
		}		
		try{
			temaService.atualizar(tema);
		}catch(Exception e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(tema,null));
		}
		attributes.addFlashAttribute("mensagem", " Tema atualizado com sucesso!");
		return new ModelAndView("redirect:/temas/novo");
	}
	
	@RequestMapping(value="/novo",method=RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Tema tema,BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(tema,null);
		}
			
		try{
			temaService.salvar(tema);
		}catch(ItemDuplicadoException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(tema,null));
		}		
		attributes.addFlashAttribute("mensagem"," Tema cadastrado com sucesso!");
		return new ModelAndView("redirect:/temas/novo");
	}
	
	@RequestMapping(value="/remover",method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Tema tema){
		try {
			//vai tentar remover no banco
			temaService.remover(tema.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		//se tiver tudo ok, vem pra cá
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody Tema tema,BindingResult result){
		
		//Se deu erro ele vai retornar a msg padrão definida lá no @NotBlank ou de outra anotação se houver
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
		}
		//tema.setArea(new AreaConhecimento(areasRepository.findOne(Integer.parseInt(tema.getAreaConhecimento_id()))));
		Tema retorno = new Tema();
		try {
			//vai tentar salvar no banco
			retorno = new Tema(temaService.salvar(tema));
		}
		catch(ItemDuplicadoException e) {
			//se ja tiver nome cadastrado vai lançar essa exceção
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		//se tiver tudo ok, vem pra cá
		return ResponseEntity.ok(retorno);
	}
	
	@RequestMapping(value="/buscar",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> buscar(@RequestBody AreaConhecimento area){
		
		List<EntityTema> retorno = temaService.buscarPorArea(area.getId());
		
		return ResponseEntity.ok(retorno);
	} 

}
