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

import br.uepb.biblio.repository.Autores;
import br.uepb.biblio.repository.Cidades;
import br.uepb.biblio.repository.Orientadores;
import br.uepb.biblio.repository.Tccs;
import br.uepb.biblio.service.CrudTccService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Tcc;
import br.uepb.model.enums.Tipo_nivel;
import br.uepb.model.enums.Tipo_tcc;

@Controller
@RequestMapping("/tccs")
public class TccsController {

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
	
	@RequestMapping(value="/novo",method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Tcc tcc, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(tcc,null);
		}
		//salvar no banco
		// Convertendo a string da data do html em sql.Date
		@SuppressWarnings("deprecation")
		java.sql.Date dataSql = new java.sql.Date(Integer.parseInt(tcc.getData_string().substring(2, 4)) + 100,
				Integer.parseInt(tcc.getData_string().substring(5, 7)),
				Integer.parseInt(tcc.getData_string().substring(8, 10)));
		tcc.setData(dataSql);
		
		// Setando o autor do tcc 
		tcc.setAutor(autoresRepository.findOne(Integer.parseInt(tcc.getId_autor())));
		
		// Setando os orientador do tcc
		tcc.setOrientador(orientadoresRepository.findOne(Integer.parseInt(tcc.getId_orientador())));
		
		try {
			tccService.salvar(tcc);
		} catch (ItemDuplicadoException e) {
			result.rejectValue("titulo", e.getMessage(), e.getMessage());
			return(novo(tcc, null));
		}
	
		attributes.addFlashAttribute("mensagem", "Tcc salvo com sucesso!");
		return new ModelAndView("redirect:/tccs/novo");
	}
}
