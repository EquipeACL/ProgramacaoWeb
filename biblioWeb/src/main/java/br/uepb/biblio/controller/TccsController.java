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

/**
 * Essa é a classe Controller da classe TCC, e é responsável por fazer a ponte entre as views referentes a esse objeto e os Models, de acordo com as solicitações realizadas nas rotas.
 * @author EquipeACL
 *
 */
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
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota tccs/novo	
	 * @param tcc, que é o objeto a ser acessado
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Tcc no banco de dados.
	 * @return model, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
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
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota tccs/pesquisar	
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Tcc no banco de dados.
	 * @return model que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
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
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota tccs/editar	
	 * @param id, que é o id do objeto que vai ser editado no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
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
	
	/**
	 * Esse é o método que irá acessar a rota tccs/remover, porém com uma requisição do tipo DELETE, que servirá para remover o objeto passado por parâmetro no banco
	 * @param tcc, que é o objeto que será mapeado no formulário para remover informações no banco de dados.
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return ResponseEntity.ok().build(); , que é a confirmação da remoção
	 */	
	@RequestMapping(value="/remover",method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
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
	
	/**
	 * Esse é o método que irá acessar a rota tccs/novo, porém com uma requisição do tipo POST, que servirá para salvar o objeto passado por parâmetro no banco
	 * @param tcc, que é o objeto que será mapeado no formulário para salvar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/tccs/novo"), que renderiza a página no endereço tccs/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value="/novo",method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Tcc tcc, BindingResult result, RedirectAttributes attributes) {
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
	
	/**
	 * Esse é o método que irá acessar a rota tccs/editar, porém com uma requisição do tipo POST, que servirá para alterar o objeto passado por parâmetro no banco
	 * @param tcc, que é o objeto que será mapeado no formulário para alterar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/tccs/novo"), que renderiza a página no endereço tccs/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value="/editar",method = RequestMethod.POST)
	public ModelAndView atualizar(@Valid Tcc tcc, BindingResult result, RedirectAttributes attributes) {
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
	
	/**
	 * Esse é o método que irá acessar a rota tccs/buscarAll, porém com uma requisição do tipo POST, que servirá para buscar todos os objetos do tipo Anais no banco de dados
	 * @return ResponseEntity.ok(retorno) que é a confirmação da busca realizada no banco.
	 */
	@RequestMapping(value="/buscarAll",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> buscarAll(){
		
		List<EntityTcc> retorno = tccsRepository.findAll();
		
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
