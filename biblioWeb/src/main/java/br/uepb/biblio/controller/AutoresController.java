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

import br.uepb.biblio.repository.Autores;
import br.uepb.biblio.service.CadastroAutorService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Autor;

/**
 * Essa é a classe Controller da classe Autor, e é responsável por fazer a ponte entre as views referentes a esse objeto e os Models, de acordo com as solicitações realizadas nas rotas.
 * @author EquipeACL
 *
 */
@Controller
@RequestMapping("/autores")
public class AutoresController {

	@Autowired
	private Autores autoresRepository;
	
	@Autowired
	private CadastroAutorService cadastroAutorService;
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota autores/novo	
	 * @param autor, que é o objeto a ser acessado
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Autor no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/novo")
	public ModelAndView novo(Autor autor,String busca) {
		ModelAndView mv = new ModelAndView("autor/CadastroAutor");
		if(busca!=null){
			mv.addObject("listaAutores", cadastroAutorService.buscarPorNome(busca));
		}else{
			mv.addObject("listaAutores", autoresRepository.findAll());
		}
		return mv;
	}
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota autores/pesquisar	
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Autor no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView mv = new ModelAndView("autor/PesquisaAutor");
		if(busca!=null){
			mv.addObject("listaAutores", cadastroAutorService.buscarPorNome(busca));
		}else{
			mv.addObject("listaAutores", autoresRepository.findAll());
		}
		return mv;
	}	
	
	/**
	 * Esse é o método que irá acessar a rota autores/novo, porém com uma requisição do tipo POST, que servirá para salvar o objeto passado por parâmetro no banco
	 * @param autor, que é o objeto que será mapeado no formulário para salvar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/autores/novo"), que renderiza a página no endereço autores/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar (@Valid Autor autor, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(autor,null);
		}
		try {
			cadastroAutorService.salvar(autor);
		}
		catch(ItemDuplicadoException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(autor,null));
		}
		
		attributes.addFlashAttribute("mensagem", "Autor salvo com sucesso!");
		return new ModelAndView("redirect:/autores/novo");
	}
	
	/**
	 * Esse método é responsável por salvar um objeto do tipo Autor de maneira rápida, nas views que tem o objeto como parâmetro.
	 * @param autor, que é o objeto a ser salvo no banco
	 * @param result, que serve para exibir na view se houve erro ou sucesso durante o processo de salvar
	 * @return ResponseEntity.ok(retorno), que é a confirmação da inserção
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody Autor autor,BindingResult result){
		
		//Se deu erro ele vai retornar a msg padrão definida lá no @NotBlank ou de outra anotação se houver
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
		}
		Autor retorno = new Autor();
		try {
			//vai tentar salvar no banco
			retorno = new Autor(cadastroAutorService.salvar(autor));
		}
		catch(ItemDuplicadoException e) {
			//se ja tiver nome cadastrado vai lançar essa exceção
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		//se tiver tudo ok, vem pra cá
		return ResponseEntity.ok(retorno);
	}

	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota autores/editar	
	 * @param id, que é o id do objeto que vai ser editado no banco de dados.
	 * @return model, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/editar")
	public ModelAndView editar(String id) {
		ModelAndView model = new ModelAndView("autor/CadastroAutor");
		model.addObject("autor", autoresRepository.findOne(Integer.parseInt(id)));
		model.addObject("listaAutores", autoresRepository.findAll());
		return model;
	}

	/**
	 * Esse é o método que irá acessar a rota autores/editar, porém com uma requisição do tipo POST, que servirá para alterar o objeto passado por parâmetro no banco
	 * @param autor, que é o objeto que será mapeado no formulário para alterar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/autores/novo"), que renderiza a página no endereço autores/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	public ModelAndView editar(@Valid Autor autor, BindingResult result, Model model,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return novo(autor, null);
		}
		try {
			cadastroAutorService.atualizar(autor);
		} catch (Exception e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return (novo(autor, null));
		}
		attributes.addFlashAttribute("mensagem", "Autor atualizado com sucesso!");
		return new ModelAndView("redirect:/autores/novo");
	}

	/**
	 * Esse é o método que irá acessar a rota autores/remover, porém com uma requisição do tipo POST, que servirá para remover o objeto passado por parâmetro no banco
	 * @param autor, que é o objeto que será mapeado no formulário para remover informações no banco de dados.
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return ResponseEntity.ok().build(); , que é a confirmação da remoção
	 */
	@RequestMapping(value = "/remover", method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Autor autor, RedirectAttributes attributes) {
		try {
			// vai tentar remover no banco
			cadastroAutorService.remover(autor.getId());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}

}
