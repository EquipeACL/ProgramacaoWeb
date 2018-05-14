package br.uepb.biblio.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.uepb.biblio.repository.Anais;
//import br.uepb.biblio.repository.Anais;
import br.uepb.biblio.repository.Autores;
import br.uepb.biblio.repository.Cidades;
import br.uepb.biblio.repository.Estados;
import br.uepb.biblio.service.CrudAnaisService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Autor;
import br.uepb.model.acervo.Anal;
import br.uepb.model.enums.Tipo_anal;



@Controller
@RequestMapping("/anais")
public class AnaisController {	
	
	@Autowired
	private Estados estadosRepository;
	
	@Autowired
	private Autores autoresRepository;
		
	@Autowired
	private Anais anaisRepository;
	
	@Autowired
	private CrudAnaisService anaisService;
	
	@Autowired
	private Cidades cidadesRepository;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Anal anal,String busca) {
		ModelAndView mv = new ModelAndView("anais/CadastroAnais");
		mv.addObject("tipos", Tipo_anal.values());
		mv.addObject("autores",autoresRepository.findAll());
		mv.addObject("cidades",cidadesRepository.findAll());
		mv.addObject("estados",estadosRepository.findAll());
		if(busca!=null){
			mv.addObject("listaAnais",anaisService.buscarPorTitulo(busca));
		}else{
			mv.addObject("listaAnais",anaisRepository.findAll());
		}
		return mv;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView mv = new ModelAndView("anais/PesquisaAnais");
		if(busca!=null){
			mv.addObject("listaAnais",anaisService.buscarPorTitulo(busca));
		}else{
			mv.addObject("listaAnais",anaisRepository.findAll());
		}
		return mv;
	}
	
	@RequestMapping("/editar")
	public ModelAndView editar(String id){
		ModelAndView model = new ModelAndView("anais/CadastroAnais");
		model.addObject("anal", anaisRepository.findOne(Integer.parseInt(id)));
		model.addObject("tipos", Tipo_anal.values());
		model.addObject("autores",autoresRepository.findAll());
		model.addObject("cidades",cidadesRepository.findAll());
		model.addObject("estados",estadosRepository.findAll());
		model.addObject("listaAnais",anaisRepository.findAll());
		return model;
	}
	
	@RequestMapping(value="/novo",method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Anal anal, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(anal,null);
		}
		//Criando uma lista com os autores
		ArrayList<Autor> listaAutores = new ArrayList<Autor>();
				listaAutores.add(new Autor(autoresRepository.findOne(Integer.parseInt(anal.getId_autor()))));
				
		//atribuindo ao livro a lista de seus autores
		anal.setAutores(listaAutores);
		
		try {
			anaisService.salvar(anal);
		} catch (ItemDuplicadoException e) {
			result.rejectValue("titulo", e.getMessage(),e.getMessage());
			return (novo(anal,null));
		}
		
		attributes.addFlashAttribute("mensagem", "Anal salvo com sucesso!");
		return new ModelAndView("redirect:/anais/novo");
	}
	
	@RequestMapping(value="/editar",method = RequestMethod.POST)
	public ModelAndView atualizar(@Valid Anal anal, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(anal,null);
		}
		//Criando uma lista com os autores
		ArrayList<Autor> listaAutores = new ArrayList<Autor>();
				listaAutores.add(new Autor(autoresRepository.findOne(Integer.parseInt(anal.getId_autor()))));
				
		//atribuindo ao livro a lista de seus autores
		anal.setAutores(listaAutores);
		
		try {
			anaisService.atualizar(anal);
		} catch (Exception e) {
			result.rejectValue("titulo", e.getMessage(),e.getMessage());
			return (novo(anal,null));
		}
		
		attributes.addFlashAttribute("mensagem", "Anal atualizado com sucesso!");
		return new ModelAndView("redirect:/anais/novo");
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
}
