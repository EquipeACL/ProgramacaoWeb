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
import br.uepb.biblio.service.CadastroAreaConhecimento;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.AreaConhecimento;

/**
 * Essa é a classe Controller da classe Area de Conhecimento, e é responsável por fazer a ponte entre as views referentes a esse objeto e os Models, de acordo com as solicitações realizadas nas rotas.
 * @author EquipeACL
 *
 */
@Controller
@RequestMapping("/areasconhecimento")
public class AreaConhecimentoController {
	@Autowired
	private CadastroAreaConhecimento cadastroAreaConhecimento;
	
	@Autowired
	private AreasConhecimento repositoryAreas;
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota areasConhecimento/novo	
	 * @param areaConhecimento, que é o objeto a ser acessado
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo AreaConhecimento no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/novo")
	public ModelAndView novo(AreaConhecimento areaConhecimento,String busca){
		ModelAndView model = new ModelAndView("areaConhecimento/CadastroAreaConhecimento");
		
		if(busca!=null){
			model.addObject("listaAreaConhecimento",cadastroAreaConhecimento.buscarPorNome(busca));
		}else{
			model.addObject("listaAreaConhecimento",repositoryAreas.findAll());
		}
		return model;
	}
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota areasConhecimento/pesquisar	
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo AreaConhecimento no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca){
		ModelAndView model = new ModelAndView("areaConhecimento/PesquisaAreaConhecimento");
		
		if(busca!=null){
			model.addObject("listaAreaConhecimento",cadastroAreaConhecimento.buscarPorNome(busca));
		}else{
			model.addObject("listaAreaConhecimento",repositoryAreas.findAll());
		}
		return model;
	}
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota areasConhecimento/editar	
	 * @param id, que é o id do objeto que vai ser editado no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/editar")
	public ModelAndView editar(String id){
		ModelAndView model = new ModelAndView("areaConhecimento/CadastroAreaConhecimento");
		model.addObject("areaConhecimento",repositoryAreas.findOne(Integer.parseInt(id)));
		model.addObject("listaAreaConhecimento",repositoryAreas.findAll());
		return model;
	}
	
	/**
	 * Esse é o método que irá acessar a rota areaConhecimento/editar, porém com uma requisição do tipo POST, que servirá para alterar o objeto passado por parâmetro no banco
	 * @param areaConhecimento, que é o objeto que será mapeado no formulário para alterar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/areasConhecimento/novo"), que renderiza a página no endereço areasConhecimento/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value="/editar",method=RequestMethod.POST)
	public ModelAndView editar(@Valid AreaConhecimento areaConhecimento, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(areaConhecimento,null);
		}		
		try{
			cadastroAreaConhecimento.atualizar(areaConhecimento);
		}catch(Exception e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(areaConhecimento,null));
		}
		attributes.addFlashAttribute("mensagem", "Area de Conhecimento atualizada com sucesso!");
		return new ModelAndView("redirect:/areasconhecimento/novo");
	}
	
	/**
	 * Esse é o método que irá acessar a rota areasConhecimento/novo, porém com uma requisição do tipo POST, que servirá para salvar o objeto passado por parâmetro no banco
	 * @param areaConhecimento, que é o objeto que será mapeado no formulário para salvar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/areasConhecimento/novo"), que renderiza a página no endereço areasConhecimento/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value="/novo",method=RequestMethod.POST)
	public ModelAndView cadastrar(@Valid AreaConhecimento areaConhecimento, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(areaConhecimento,null);
		}		
		try{
			cadastroAreaConhecimento.salvar(areaConhecimento);
		}catch(ItemDuplicadoException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(areaConhecimento,null));
		}
		attributes.addFlashAttribute("mensagem", "Area de Conhecimento salva com sucesso!");
		return new ModelAndView("redirect:/areasconhecimento/novo");
	}
	
	/**
	 * Esse é o método que irá acessar a rota areasConhecimento/remover, porém com uma requisição do tipo POST, que servirá para remover o objeto passado por parâmetro no banco
	 * @param area, que é o objeto que será mapeado no formulário para remover informações no banco de dados.
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return ResponseEntity.ok().build(); , que é a confirmação da remoção
	 */
	@RequestMapping(value="/remover",method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody AreaConhecimento area,RedirectAttributes attributes){
		try {
			//vai tentar remover no banco
			cadastroAreaConhecimento.remover(area.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Esse método é responsável por salvar um objeto do tipo AreaConhecimento de maneira rápida, nas views que tem o objeto como parâmetro.
	 * @param area, que é o objeto a ser salvo no banco
	 * @param result, que serve para exibir na view se houve erro ou sucesso durante o processo de salvar
	 * @return ResponseEntity.ok(retorno), que é a confirmação da inserção
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody AreaConhecimento area,BindingResult result){
		
		//Se deu erro ele vai retornar a msg padrão definida lá no @NotBlank ou de outra anotação se houver
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
		}
		AreaConhecimento retorno = new AreaConhecimento();
		try {
			//vai tentar salvar no banco
			retorno = new AreaConhecimento(cadastroAreaConhecimento.salvar(area));
		}
		catch(ItemDuplicadoException e) {
			//se ja tiver nome cadastrado vai lançar essa exceção
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		//se tiver tudo ok, vem pra cá
		return ResponseEntity.ok(retorno);
	}
}
