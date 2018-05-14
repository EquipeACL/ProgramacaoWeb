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

import br.uepb.biblio.repository.AreasConhecimento;
import br.uepb.biblio.repository.Cursos;
import br.uepb.biblio.service.CadastroCursosService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Curso;
import br.uepb.model.enums.Tipo_curso;

@Controller
@RequestMapping("/cursos")
public class CursoController {
	
	@Autowired
	private Cursos cursosRepository;
	
	@Autowired
	private AreasConhecimento areasRepository;
	
	@Autowired
	private CadastroCursosService cursosService;

	@RequestMapping("/novo")
	public ModelAndView novo(Curso curso,String busca){
		ModelAndView model = new ModelAndView("curso/CadastroCurso");
		model.addObject("tiposCursos", Tipo_curso.values());
		model.addObject("areas", areasRepository.findAll());
		if(busca!=null){
			model.addObject("listaCurso",cursosService.buscarPorNome(busca));
		}else{
			model.addObject("listaCurso",cursosRepository.findAll());
		}
		return model;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca){
		ModelAndView model = new ModelAndView("curso/PesquisaCurso");
		if(busca!=null){
			model.addObject("listaCurso",cursosService.buscarPorNome(busca));
		}else{
			model.addObject("listaCurso",cursosRepository.findAll());
		}
		return model;
	}
	
	@RequestMapping("/editar")
	public ModelAndView editar(String id){
		ModelAndView model = new ModelAndView("curso/CadastroCurso");
		model.addObject("curso",cursosRepository.findOne(Integer.parseInt(id)));
		model.addObject("tiposCursos", Tipo_curso.values());
		model.addObject("areas", areasRepository.findAll());
		model.addObject("listaCurso",cursosRepository.findAll());
		return model;
	}
	
	@RequestMapping(value="/novo",method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Curso curso, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(curso,null);
		}
		
		try{
			cursosService.salvar(curso);
		}catch(ItemDuplicadoException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(curso,null));
		}
		attributes.addFlashAttribute("mensagem", " Curso salvo com sucesso!");
		return new ModelAndView("redirect:/cursos/novo");
	}
	
	@RequestMapping(value="/editar",method = RequestMethod.POST)
	public ModelAndView atualizar(@Valid Curso curso, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(curso,null);
		}		
		try{
			cursosService.atualizar(curso);
		}catch(ItemDuplicadoException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(curso,null));
		}
		attributes.addFlashAttribute("mensagem", " Curso atualizado com sucesso!");
		return new ModelAndView("redirect:/cursos/novo");
	}
	
	@RequestMapping(value="/remover",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Curso curso,RedirectAttributes attributes){
		try {
			//vai tentar remover no banco
			cursosService.remover(curso.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
}

