package br.uepb.biblio.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import br.uepb.model.jpaEntity.acervo.EntityAnal;


/**
 * Essa é a classe Controller da classe Anais, e é responsável por fazer a ponte entre as views referentes a esse objeto e os Models, de acordo com as solicitações realizadas nas rotas.
 * @author EquipeACL
 *
 */
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
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota anais/novo	
	 * @param anal, que é o objeto a ser acessado
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Anal no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/novo")
	public ModelAndView novo(Anal anal,String busca,String filtro) {
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
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota anais/pesquisar	
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Anal no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca, String filtro) {
		ModelAndView mv = new ModelAndView("anais/PesquisaAnais");
		if(busca!=null){
			mv.addObject("listaAnais",anaisService.buscarPorTitulo(busca));
		}else{
			mv.addObject("listaAnais",anaisRepository.findAll());
		}
		return mv;
	}
	

	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota anais/editar	
	 * @param id, que é o id do objeto que vai ser editado no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
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
	
	/**
	 * Esse é o método que irá acessar a rota anais/novo, porém com uma requisição do tipo POST, que servirá para salvar o objeto passado por parâmetro no banco
	 * @param anal, que é o objeto que será mapeado no formulário para salvar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/anais/novo"), que renderiza a página no endereço anais/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value="/novo",method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Anal anal, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(anal,null,null);
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
			return (novo(anal,null,null));
		}
		
		attributes.addFlashAttribute("mensagem", "Anal salvo com sucesso!");
		return new ModelAndView("redirect:/anais/novo");
	}
	
	/**
	 * Esse é o método que irá acessar a rota anais/editar, porém com uma requisição do tipo POST, que servirá para alterar o objeto passado por parâmetro no banco
	 * @param anal, que é o objeto que será mapeado no formulário para alterar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/anais/novo"), que renderiza a página no endereço anais/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value="/editar",method = RequestMethod.POST)
	public ModelAndView atualizar(@Valid Anal anal, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(anal,null,null);
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
			return (novo(anal,null,null));
		}
		
		attributes.addFlashAttribute("mensagem", "Anal atualizado com sucesso!");
		return new ModelAndView("redirect:/anais/novo");
	}
	
	/**
	 * Esse é o método que irá acessar a rota anais/remover, porém com uma requisição do tipo POST, que servirá para remover o objeto passado por parâmetro no banco
	 * @param anal, que é o objeto que será mapeado no formulário para remover informações no banco de dados.
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return ResponseEntity.ok().build(); , que é a confirmação da remoção
	 */	
	@RequestMapping(value="/remover",method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Anal anal,RedirectAttributes attributes){
		try {
			//vai tentar remover no banco
			anaisService.remover(anal.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Esse é o método que irá acessar a rota anais/buscarAll, porém com uma requisição do tipo POST, que servirá para buscar todos os objetos do tipo Anais no banco de dados
	 * @return ResponseEntity.ok(retorno) que é a confirmação da busca realizada no banco.
	 */
	@RequestMapping(value="/buscarAll",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> buscarAll(){
		
		List<EntityAnal> retorno = anaisRepository.findAll();
		
		return ResponseEntity.ok(retorno);
	} 
	
	/**
	 * Esse método é responsável por formatar a data de acordo com o padrão do banco de dados.
	 * @param binder, que é o objeto que será formatado de acordo com o padrão definido.
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
}
