package br.uepb.biblio.controller;

import java.util.List;

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
import br.uepb.biblio.repository.Temas;
import br.uepb.biblio.service.CadastroTemaService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.AreaConhecimento;
import br.uepb.model.Tema;
import br.uepb.model.jpaEntity.EntityTema;

/**
 * Essa é a classe Controller da classe Tema, e é responsável por fazer a ponte entre as views referentes a esse objeto e os Models, de acordo com as solicitações realizadas nas rotas.
 * @author EquipeACL
 *
 */

@Controller
@RequestMapping("/temas")
public class TemaController{
	
	@Autowired
	private AreasConhecimento areasRepository;

	@Autowired
	private Temas temas;
	
	@Autowired
	private CadastroTemaService temaService;
	
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota temas/novo	
	 * @param tema, que é o objeto a ser acessado
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Tema no banco de dados.
	 * @return model, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/novo")
	public ModelAndView novo(Tema tema,String busca){
		ModelAndView model = new ModelAndView("tema/CadastroTema");
		model.addObject("areas", areasRepository.findAll());
		if(busca!=null){
			model.addObject("temas",temaService.buscarPorNome(busca));
		}else{
			model.addObject("temas",temas.findAll());
		}
		return model;
	}
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota temas/pesquisar	
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Tema no banco de dados.
	 * @return model, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca){
		ModelAndView model = new ModelAndView("tema/PesquisaTema");
		model.addObject("temas",temas.findAll());
		if(busca!=null){
			model.addObject("temas",temaService.buscarPorNome(busca));
		}else{
			model.addObject("temas",temas.findAll());
		}
		return model;
	}
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota temas/editar	
	 * @param id, que é o id do objeto que vai ser editado no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/editar")
	public ModelAndView editar(String id){
		ModelAndView model = new ModelAndView("tema/CadastroTema");
		model.addObject("tema",temas.findOne(Integer.parseInt(id)));
		model.addObject("temas",temas.findAll());
		model.addObject("areas", areasRepository.findAll());
		return model;
	}
	
	/**
	 * Esse é o método que irá acessar a rota temas/editar, porém com uma requisição do tipo POST, que servirá para alterar o objeto passado por parâmetro no banco
	 * @param tema, que é o objeto que será mapeado no formulário para alterar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/temas/novo"), que renderiza a página no endereço temas/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value="/editar",method=RequestMethod.POST)
	public ModelAndView editar(@Valid Tema tema, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(tema,null);
		}		
		try{
			temaService.atualizar(tema);
		}catch(Exception e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(tema,null));
		}
		attributes.addFlashAttribute("mensagem", " Tema atualizado com sucesso!");
		return new ModelAndView("redirect:/temas/novo");
	}
	
	
	/**
	 * Esse é o método que irá acessar a rota temas/novo, porém com uma requisição do tipo POST, que servirá para salvar o objeto passado por parâmetro no banco
	 * @param tema, que é o objeto que será mapeado no formulário para salvar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/temas/novo"), que renderiza a página no endereço temas/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value="/novo",method=RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Tema tema,BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(tema,null);
		}
			
		try{
			temaService.salvar(tema);
		}catch(ItemDuplicadoException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(tema,null));
		}		
		attributes.addFlashAttribute("mensagem"," Tema cadastrado com sucesso!");
		return new ModelAndView("redirect:/temas/novo");
	}

	/**
	 * Esse é o método que irá acessar a rota temas/remover, porém com uma requisição do tipo DELETE, que servirá para remover o objeto passado por parâmetro no banco
	 * @param tema, que é o objeto que será mapeado no formulário para remover informações no banco de dados.
	 * @return ResponseEntity.ok().build(); , que é a confirmação da remoção
	 */
	@RequestMapping(value="/remover",method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Tema tema){
		try {
			//vai tentar remover no banco
			temaService.remover(tema.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		//se tiver tudo ok, vem pra cá
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Esse método é responsável por salvar um objeto do tipo Tema de maneira rápida, nas views que tem o objeto como parâmetro.
	 * @param tema, que é o objeto a ser salvo no banco
	 * @param result, que serve para exibir na view se houve erro ou sucesso durante o processo de salvar
	 * @return ResponseEntity.ok(retorno), que é a confirmação da inserção
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody Tema tema,BindingResult result){
		
		//Se deu erro ele vai retornar a msg padrão definida lá no @NotBlank ou de outra anotação se houver
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
		}
		//tema.setArea(new AreaConhecimento(areasRepository.findOne(Integer.parseInt(tema.getAreaConhecimento_id()))));
		Tema retorno = new Tema();
		try {
			//vai tentar salvar no banco
			retorno = new Tema(temaService.salvar(tema));
		}
		catch(ItemDuplicadoException e) {
			//se ja tiver nome cadastrado vai lançar essa exceção
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		//se tiver tudo ok, vem pra cá
		return ResponseEntity.ok(retorno);
	}
	
	@RequestMapping(value="/buscar",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> buscar(@RequestBody AreaConhecimento area){
		
		List<EntityTema> retorno = temaService.buscarPorArea(area.getId());
		
		return ResponseEntity.ok(retorno);
	} 

}
