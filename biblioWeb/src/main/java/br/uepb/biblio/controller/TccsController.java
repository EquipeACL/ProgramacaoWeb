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

import br.uepb.biblio.repository.Autores;
import br.uepb.biblio.repository.Cidades;
import br.uepb.biblio.repository.Estados;
import br.uepb.biblio.repository.Orientadores;
import br.uepb.biblio.repository.Tccs;
import br.uepb.biblio.service.CrudTccService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Tcc;
import br.uepb.model.enums.Tipo_nivel;
import br.uepb.model.enums.Tipo_tcc;
import br.uepb.model.jpaEntity.acervo.EntityTcc;

@Controller
@RequestMapping("/tccs")
public class TccsController {

	@Autowired
	private Estados estadosRepository;
	
	@Autowired
	private Autores autoresRepository;
	
	@Autowired
	private Orientadores orientadoresRepository;
	
	@Autowired
	private Tccs tccsRepository;
	
	@Autowired
	private Cidades cidadesRepository;
	
	@Autowired
	private CrudTccService tccService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Tcc tcc,String busca) {
		ModelAndView model = new ModelAndView("/tcc/CadastroTcc");
		model.addObject("autores",autoresRepository.findAll());
		model.addObject("orientadores",orientadoresRepository.findAll());
		model.addObject("cidades",cidadesRepository.findAll());
		model.addObject("formacoes",Tipo_nivel.values());
		model.addObject("tipos",Tipo_tcc.values());
		model.addObject("estados",estadosRepository.findAll());
		if(busca!=null){
			model.addObject("listaTcc",tccService.buscarPorTitulo(busca));
		}else{
			model.addObject("listaTcc",tccsRepository.findAll());
		}
		return model;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView model = new ModelAndView("/tcc/PesquisaTcc");
		model.addObject("autores",autoresRepository.findAll());
		model.addObject("orientadores",orientadoresRepository.findAll());
		model.addObject("formacoes",Tipo_nivel.values());
		model.addObject("tipos",Tipo_tcc.values());
		if(busca!=null){
			model.addObject("listaTcc",tccService.buscarPorTitulo(busca));
		}else{
			model.addObject("listaTcc",tccsRepository.findAll());
		}
		return model;
	}
	
	@RequestMapping("/editar")
	public ModelAndView editar(String id) {
		ModelAndView model = new ModelAndView("/tcc/CadastroTcc");
		model.addObject("tcc", tccsRepository.findOne(Integer.parseInt(id)));
		model.addObject("autores",autoresRepository.findAll());
		model.addObject("orientadores",orientadoresRepository.findAll());
		model.addObject("cidades",cidadesRepository.findAll());
		model.addObject("formacoes",Tipo_nivel.values());
		model.addObject("tipos",Tipo_tcc.values());
		model.addObject("estados",estadosRepository.findAll());
		model.addObject("listaTcc",tccsRepository.findAll());
		return model;
	}
	
	@RequestMapping(value="/remover",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Tcc tcc,RedirectAttributes attributes){
		try {
			//vai tentar remover no banco
			tccService.remover(tcc.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(value="/novo",method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Tcc tcc, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(tcc,null);
		}
				
		// Setando o autor do tcc 
		//tcc.setAutor(new Autor(autoresRepository.findOne(Integer.parseInt(tcc.getId_autor()))));
		
		// Setando os orientador do tcc
		//tcc.setOrientador(new Orientador(orientadoresRepository.findOne(Integer.parseInt(tcc.getId_orientador()))));
		
		try {
			tccService.salvar(tcc);
		} catch (ItemDuplicadoException e) {
			result.rejectValue("titulo", e.getMessage(), e.getMessage());
			return(novo(tcc, null));
		}
	
		attributes.addFlashAttribute("mensagem", "Tcc salvo com sucesso!");
		return new ModelAndView("redirect:/tccs/novo");
	}
	
	@RequestMapping(value="/editar",method = RequestMethod.POST)
	public ModelAndView atualizar(@Valid Tcc tcc, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(tcc,null);
		}
		//atualiza no banco		
		try {
			tccService.atualizar(tcc);
		} catch (ItemDuplicadoException e) {
			result.rejectValue("titulo", e.getMessage(), e.getMessage());
			return(novo(tcc, null));
		}
	
		attributes.addFlashAttribute("mensagem", "Tcc atualizado com sucesso!");
		return new ModelAndView("redirect:/tccs/novo");
	}
	
	@RequestMapping(value="/buscarAll",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> buscarAll(){
		
		List<EntityTcc> retorno = tccsRepository.findAll();
		
		return ResponseEntity.ok(retorno);
	} 
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
}
