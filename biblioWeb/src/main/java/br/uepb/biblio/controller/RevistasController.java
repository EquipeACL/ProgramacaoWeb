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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.uepb.biblio.repository.Editoras;
import br.uepb.biblio.repository.Revistas;
import br.uepb.biblio.service.CrudRevistaService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.acervo.Revista;
import br.uepb.model.jpaEntity.acervo.EntityRevista;

/**
 * Essa é a classe Controller da classe Revista, e é responsável por fazer a ponte entre as views referentes a esse objeto e os Models, de acordo com as solicitações realizadas nas rotas.
 * @author EquipeACL
 *
 */
@Controller
@RequestMapping("/revistas")
public class RevistasController {
	
	@Autowired
	private Revistas revistaRepository;
	
	@Autowired
	private Editoras editoraRepository;
	
	@Autowired
	private CrudRevistaService revistaService;
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota revistas/novo	
	 * @param revista, que é o objeto a ser acessado
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Revista no banco de dados.
	 * @return model, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/novo")
	public ModelAndView novo(Revista revista, String busca,String filtro) {
		ModelAndView model = new ModelAndView("/revista/CadastroRevista");
		model.addObject("listaEditoras",editoraRepository.findAll());
		if(busca!=null){
			if(filtro!=null && filtro.equals("editora")){
				model.addObject("listaRevistas", revistaService.buscarPorEditora(busca));
			}else{
				model.addObject("listaRevistas", revistaService.buscarPorTitulo(busca));
			}
		}else{
			model.addObject("listaRevistas", revistaRepository.findAll());
		}
		return model;
	}
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota revistas/pesquisar	
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Revista no banco de dados.
	 * @return model, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca,String filtro) {
		ModelAndView model = new ModelAndView("/revista/PesquisaRevista");
		if(busca!=null){
			if(filtro!=null && filtro.equals("editora")){
				model.addObject("listaRevistas", revistaService.buscarPorEditora(busca));
			}else{
				model.addObject("listaRevistas", revistaService.buscarPorTitulo(busca));
			}
		}else{
			model.addObject("listaRevistas", revistaRepository.findAll());
		}
		return model;
	}
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota revistas/editar	
	 * @param id, que é o id do objeto que vai ser editado no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/editar")
	public ModelAndView editar(String id) {
		ModelAndView model = new ModelAndView("/revista/CadastroRevista");
		model.addObject("revista",revistaRepository.findOne(Integer.parseInt(id)));
		model.addObject("listaEditoras",editoraRepository.findAll());
		model.addObject("listaRevistas", revistaRepository.findAll());
		return model;
	}
	
	/**
	 * Esse é o método que irá acessar a rota revistas/novo, porém com uma requisição do tipo POST, que servirá para salvar o objeto passado por parâmetro no banco
	 * @param revista, que é o objeto que será mapeado no formulário para salvar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/revistas/novo"), que renderiza a página no endereço revistas/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value="/novo",method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Revista revista, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(revista,null,null);
		}
		//salvar no banco		
		
		try {
			revistaService.salvar(revista);
		} catch (ItemDuplicadoException e) {
			result.rejectValue("titulo", e.getMessage(), e.getMessage());
			return(novo(revista, null,null));
		}
		attributes.addFlashAttribute("mensagem", "Revista salva com sucesso!");	
		return new ModelAndView("redirect:/revistas/novo");
	}
	
	/**
	 * Esse é o método que irá acessar a rota revistas/editar, porém com uma requisição do tipo POST, que servirá para alterar o objeto passado por parâmetro no banco
	 * @param revista, que é o objeto que será mapeado no formulário para alterar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/revistas/novo"), que renderiza a página no endereço revistas/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value="/editar",method = RequestMethod.POST)
	public ModelAndView atualizar(@Valid Revista revista, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(revista,null,null);
		}
		//atualiza no banco		
		try {
			revistaService.atualizar(revista);
		} catch (ItemDuplicadoException e) {
			result.rejectValue("titulo", e.getMessage(), e.getMessage());
			return(novo(revista, null,null));
		}
		attributes.addFlashAttribute("mensagem", "Revista atualizada com sucesso!");	
		return new ModelAndView("redirect:/revistas/novo");
		
		
	}
	
	/**
	 * Esse é o método que irá acessar a rota revistas/remover, porém com uma requisição do tipo DELETE, que servirá para remover o objeto passado por parâmetro no banco
	 * @param revista, que é o objeto que será mapeado no formulário para remover informações no banco de dados.
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return ResponseEntity.ok().build(); , que é a confirmação da remoção
	 */
	@RequestMapping(value="/remover",method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Revista revista,RedirectAttributes attributes){
		try {
			//vai tentar remover no banco
			revistaService.remover(revista.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Esse é o método que irá acessar a rota revistas/buscarAll, porém com uma requisição do tipo POST, que servirá para buscar todos os objetos do tipo Revista no banco de dados
	 * @return ResponseEntity.ok(retorno) que é a confirmação da busca realizada no banco.
	 */
	@RequestMapping(value="/buscarAll",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> buscarAll(){
		
		List<EntityRevista> retorno = revistaRepository.findAll();
		
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
