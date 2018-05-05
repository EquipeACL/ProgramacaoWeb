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
	
	@RequestMapping(value="/novo",method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Curso curso, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(curso,null);
		}
		
		//setando a area do cohecimento de acordo com id selecionando
		curso.setArea(areasRepository.findOne(Integer.parseInt(curso.getArea_conhecimento_id())));
		curso.setTipo(Tipo_curso.valueOf(curso.getTipo().getDescricao()));
		try{
			cursosService.salvar(curso);
		}catch(ItemDuplicadoException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(curso,null));
		}
		attributes.addFlashAttribute("mensagem", " Curso salvo com sucesso!");
		return new ModelAndView("redirect:/cursos/novo");
	}
}
