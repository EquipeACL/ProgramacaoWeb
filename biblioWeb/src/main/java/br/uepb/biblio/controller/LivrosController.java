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

import br.uepb.biblio.repository.AreasConhecimento;
import br.uepb.biblio.repository.Autores;
import br.uepb.biblio.repository.Editoras;
import br.uepb.biblio.repository.Livros;
import br.uepb.biblio.repository.Temas;
import br.uepb.biblio.service.CrudLivroService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Autor;
import br.uepb.model.Editora;
import br.uepb.model.Tema;
import br.uepb.model.acervo.Livro;

@Controller
public class LivrosController {

	@Autowired
	private Autores autoresRepository;
	
	@Autowired
	private Editoras editoras;
	
	@Autowired
	private AreasConhecimento areas;
	
	@Autowired
	private Temas temas;
	
	@Autowired
	private Livros livros;
	
	@Autowired
	private CrudLivroService livrosService;

	
	@RequestMapping("/livros/novo")
	public ModelAndView novo(Livro livro,String busca) {
		ModelAndView mv = new ModelAndView("livro/CadastroLivro");
		mv.addObject("editoras", editoras.findAll());
		mv.addObject("areas", areas.findAll());
		mv.addObject("temas",temas.findAll());
		mv.addObject("editoras",editoras.findAll());
		mv.addObject("autores",autoresRepository.findAll());
		if(busca!=null){
			mv.addObject("listaLivros",livrosService.buscarPorTitulo(busca));
		}else{
			mv.addObject("listaLivros",livros.findAll());
		}
		return mv;
	}
	
	@RequestMapping("/livros/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView mv = new ModelAndView("livro/PesquisaLivro");
		if(busca!=null){
			mv.addObject("listaLivros",livrosService.buscarPorTitulo(busca));
		}else{
			mv.addObject("listaLivros",livros.findAll());
		}
		return mv;
	}
	
	@RequestMapping(value = "/livros/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar (@Valid Livro livro, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(livro,null);
		}
		
		// Convertendo a string da data do html em sql.Date
		@SuppressWarnings("deprecation")
		java.sql.Date dataSql = new java.sql.Date(Integer.parseInt(livro.getString_data().substring(2, 4)) + 100,
						Integer.parseInt(livro.getString_data().substring(5, 7)),
						Integer.parseInt(livro.getString_data().substring(8, 10)));
		livro.setAnoPublicacao(dataSql);
		
		livro.setEditora(new Editora(editoras.findOne(Integer.parseInt(livro.getId_editora()))));
		
		livro.setTema(new Tema(temas.findOne(Integer.parseInt(livro.getId_tema()))));
		
		//Criando uma lista com os autores
		ArrayList<Autor> listaAutores = new ArrayList<Autor>();
		listaAutores.add(new Autor(autoresRepository.findOne(Integer.parseInt(livro.getId_autor()))));
		
		//atribuindo ao livro a lista de seus autores
		livro.setAutores(listaAutores);
		
		try {
			livrosService.salvar(livro);
		} catch (ItemDuplicadoException e) {
			result.rejectValue("titulo", e.getMessage(),e.getMessage());
			return (novo(livro,null));
		}
		
		attributes.addFlashAttribute("mensagem", "Livro salvo com sucesso!");
		return new ModelAndView("redirect:/livros/novo");
	}
	
}
