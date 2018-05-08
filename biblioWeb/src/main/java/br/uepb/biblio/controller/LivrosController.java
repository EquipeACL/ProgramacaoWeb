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
import br.uepb.biblio.repository.Editoras;
import br.uepb.biblio.repository.Temas;
import br.uepb.model.acervo.Livro;

@Controller
public class LivrosController {

	@Autowired
	private Editoras editoras;
	
	@Autowired
	private AreasConhecimento areas;
	
	@Autowired
	private Temas temas;
	
	/*@Autowired
	private Livros livros;
	
	@Autowired
	private CadastroLivroService livrosService;*/

	
	@RequestMapping("/livros/novo")
	public ModelAndView novo(Livro livro,String busca) {
		ModelAndView mv = new ModelAndView("livro/CadastroLivro");
		mv.addObject("editoras", editoras.findAll());
		mv.addObject("areas", areas.findAll());
		mv.addObject("temas",temas.findAll());
		mv.addObject("editoras",editoras.findAll());
		if(busca!=null){
			//mv.addObject("listaLivros",livrosService.buscarPorTitulo(busca));
		}else{
			//mv.addObject("listaLivros",livros.findAll());
		}
		return mv;
	}
	
	@RequestMapping("/livros/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView mv = new ModelAndView("livro/PesquisaLivro");
		if(busca!=null){
			//mv.addObject("listaLivros",livrosService.buscarPorTitulo(busca));
		}else{
			//mv.addObject("listaLivros",livros.findAll());
		}
		return mv;
	}
	
	@RequestMapping(value = "/livros/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar (@Valid Livro livro, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(livro,null);
		}
		
		// Convertendo a string da data do html em sql.Date
				java.sql.Date dataSql = new java.sql.Date(Integer.parseInt(livro.getString_data().substring(2, 4)) + 100,
						Integer.parseInt(livro.getString_data().substring(5, 7)),
						Integer.parseInt(livro.getString_data().substring(8, 10)));
		livro.setAnoPublicacao(dataSql);
		
		livro.setEditora(editoras.findOne(Integer.parseInt(livro.getId_editora())));
		
		livro.setTema(temas.findOne(Integer.parseInt(livro.getId_tema())));
		
		//FALTA ADICIONAR OS AUTORES
		
		//livrosService.save(livro);
		
		attributes.addFlashAttribute("mensagem", "Livro salvo com sucesso!");
		return new ModelAndView("redirect:/livros/novo");
	}
	
}
