package br.uepb.biblio.controller;


import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.uepb.biblio.repository.Anais;
//import br.uepb.biblio.repository.Anais;
import br.uepb.biblio.repository.Autores;
import br.uepb.biblio.repository.Cidades;
import br.uepb.biblio.service.CrudAnaisService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Autor;
import br.uepb.model.acervo.Anal;
import br.uepb.model.enums.Tipo_anal;



@Controller
@RequestMapping("/anais")
public class AnaisController {	
	
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
		ModelAndView mv = new ModelAndView("/anais/CadastroAnais");
		mv.addObject("tipos", Tipo_anal.values());
		mv.addObject("autores",autoresRepository.findAll());
		mv.addObject("cidades",cidadesRepository.findAll());
		if(busca!=null){
			mv.addObject("listaAnais",anaisService.buscarPorTitulo(busca));
		}else{
			mv.addObject("listaAnais",anaisRepository.findAll());
		}
		return mv;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView mv = new ModelAndView("/anais/PesquisaAnais");
		if(busca!=null){
			mv.addObject("listaAnais",anaisService.buscarPorTitulo(busca));
		}else{
			mv.addObject("listaAnais",anaisRepository.findAll());
		}
		return mv;
	}
	
	@RequestMapping(value="/novo",method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Anal anal, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(anal,null);
		}
		//Criando uma lista com os autores
		ArrayList<Autor> listaAutores = new ArrayList<Autor>();
		listaAutores.add(new Autor(autoresRepository.findOne(Integer.parseInt(anal.getId_autor()))));
		//atrubuindo ao anal a lista de seus autores
		anal.setAutores(listaAutores);
		
		//adicionando ao anal o objeto cidade referente ao id selecionado
		anal.setLocal(cidadesRepository.findOne(Integer.parseInt(anal.getId_cidade())));
		
		// Convertendo a string da data do html em sql.Date
		@SuppressWarnings("deprecation")
		java.sql.Date dataSql = new java.sql.Date(Integer.parseInt(anal.getData_string().substring(2, 4))+100,Integer.parseInt(anal.getData_string().substring(5, 7)),Integer.parseInt(anal.getData_string().substring(8, 10)));		System.out.println("Data: ");
		anal.setAnoPublicacao(dataSql);
		
		try {
			anaisService.salvar(anal);
		} catch (ItemDuplicadoException e) {
			result.rejectValue("titulo", e.getMessage(),e.getMessage());
			return (novo(anal,null));
		}
		
		attributes.addFlashAttribute("mensagem", "Anal salvo com sucesso!");
		return new ModelAndView("redirect:/anais/novo");
	}
}
