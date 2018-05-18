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

import br.uepb.biblio.repository.Orientadores;
import br.uepb.biblio.service.CadastroOrientadorService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Orientador;
import br.uepb.model.enums.Tipo_nivel;

@Controller
@RequestMapping("/orientadores")
public class OrientadoresController {

	@Autowired
	private Orientadores orientadoresRepository;
	
	@Autowired
	CadastroOrientadorService cadastroOrientadorService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Orientador orientador,String busca) {
		ModelAndView mv = new ModelAndView("/orientador/CadastroOrientador");
		mv.addObject("formacoes", Tipo_nivel.values());
		if(busca!=null){
			mv.addObject("listaOrientador", cadastroOrientadorService.buscarPorNome(busca));
		}else{
			mv.addObject("listaOrientador", orientadoresRepository.findAll());
		}
		return mv;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView mv = new ModelAndView("/orientador/PesquisaOrientador");
		mv.addObject("formacoes", Tipo_nivel.values());
		if(busca!=null){
			mv.addObject("listaOrientador", cadastroOrientadorService.buscarPorNome(busca));
		}else{
			mv.addObject("listaOrientador", orientadoresRepository.findAll());
		}
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar (@Valid Orientador orientador, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(orientador,null);
		}
		//
		try {
			cadastroOrientadorService.salvar(orientador);
		}
		catch(ItemDuplicadoException e) {
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return novo(orientador,null);
		}
		
		attributes.addFlashAttribute("mensagem", "Orientador salvo com sucesso!");
		return new ModelAndView("redirect:/orientadores/novo");
	}
	
	@RequestMapping("/editar")
	public ModelAndView editar(String id){
		ModelAndView model = new ModelAndView("orientador/CadastroOrientador");
		model.addObject("orientador",orientadoresRepository.findOne(Integer.parseInt(id)));
		model.addObject("listaOrientador",orientadoresRepository.findAll());
		model.addObject("formacoes", Tipo_nivel.values());
		return model;
	}
	
	@RequestMapping(value="/editar",method=RequestMethod.POST)
	public ModelAndView editar(@Valid Orientador orientador, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(orientador,null);
		}		
		try{
			cadastroOrientadorService.atualizar(orientador);
		}catch(Exception e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(orientador,null));
		}
		attributes.addFlashAttribute("mensagem", " Orientador atualizado com sucesso!");
		return new ModelAndView("redirect:/orientadores/novo");
	}
	
	@RequestMapping(value="/remover",method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Orientador orientador,RedirectAttributes attributes){
		try {
			//vai tentar remover no banco
			cadastroOrientadorService.remover(orientador.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	@RequestMapping(method = RequestMethod.POST,consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody  ResponseEntity<?> salvar(@RequestBody Orientador orientador, BindingResult result){

		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
		}
		Orientador retorno = new Orientador();
		try {
			retorno = new Orientador(cadastroOrientadorService.salvar(orientador));
		}
		catch(ItemDuplicadoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(retorno);
		
	}
}
