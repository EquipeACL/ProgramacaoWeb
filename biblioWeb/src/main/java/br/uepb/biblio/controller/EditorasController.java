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

import br.uepb.biblio.repository.Editoras;
import br.uepb.biblio.service.CadastroEditoraService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Editora;

/**
 * Essa é a classe Controller da classe Editora, e é responsável por fazer a ponte entre as views referentes a esse objeto e os Models, de acordo com as solicitações realizadas nas rotas.
 * @author EquipeACL
 *
 */
@Controller
@RequestMapping("/editoras")
public class EditorasController {


	@Autowired
	private Editoras editorasRepository;
	
	@Autowired
	private CadastroEditoraService cadastroEditoraService;
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota editoras/novo	
	 * @param editora, que é o objeto a ser acessado
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Editora no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/novo")
	public ModelAndView novo(Editora editora,String busca) {
		ModelAndView mv = new ModelAndView("editora/CadastroEditora");
		if(busca!=null){
			mv.addObject("listaEditora", cadastroEditoraService.buscarPorNome(busca));
		}else{
			mv.addObject("listaEditora", editorasRepository.findAll());
		}
		return mv;
	}
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota editoras/pesquisar	
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Editora no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView mv = new ModelAndView("editora/PesquisaEditora");
		if(busca!=null){
			mv.addObject("listaEditora", cadastroEditoraService.buscarPorNome(busca));
		}else{
			mv.addObject("listaEditora", editorasRepository.findAll());
		}
		return mv;
	}
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota editoras/editar	
	 * @param id, que é o id do objeto que vai ser editado no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/editar")
	public ModelAndView editar(String id){
		ModelAndView model = new ModelAndView("editora/CadastroEditora");
		model.addObject("editora",editorasRepository.findOne(Integer.parseInt(id)));
		model.addObject("listaEditora",editorasRepository.findAll());
		return model;
	}
	
	/**
	 * Esse é o método que irá acessar a rota editoras/novo, porém com uma requisição do tipo POST, que servirá para salvar o objeto passado por parâmetro no banco
	 * @param editora, que é o objeto que será mapeado no formulário para salvar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/editoras/novo"), que renderiza a página no endereço editoras/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	ModelAndView cadastro(@Valid Editora editora, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return (novo(editora,null));
		}
		try {
			cadastroEditoraService.salvar(editora);
		}catch(ItemDuplicadoException e) {
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(editora,null));
		}
		
		attributes.addFlashAttribute("mensagem", "Editora salva com sucesso!");
		return new ModelAndView("redirect:/editoras/novo");

	}
	/**
	 * Esse é o método que irá acessar a rota editoras/editar, porém com uma requisição do tipo POST, que servirá para alterar o objeto passado por parâmetro no banco
	 * @param editora, que é o objeto que será mapeado no formulário para alterar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/editoras/novo"), que renderiza a página no endereço editoras/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value="/editar",method=RequestMethod.POST)
	public ModelAndView editar(@Valid Editora editora, BindingResult result, Model model, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(editora,null);
		}		
		try{
			cadastroEditoraService.atualizar(editora);
		}catch(Exception e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(editora,null));
		}
		attributes.addFlashAttribute("mensagem", "Editora atualizada com sucesso!");
		return new ModelAndView("redirect:/editoras/novo");
	}
	
	/**
	 * Esse é o método que irá acessar a rota editoras/remover, porém com uma requisição do tipo DELETE, que servirá para remover o objeto passado por parâmetro no banco
	 * @param editora, que é o objeto que será mapeado no formulário para remover informações no banco de dados.
	 * @return ResponseEntity.ok().build(); , que é a confirmação da remoção
	 */
	@RequestMapping(value="/remover",method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Editora editora){
		try {
			//vai tentar remover no banco
			cadastroEditoraService.remover(editora.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		//se tiver tudo ok, vem pra cá
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Esse método é responsável por salvar um objeto do tipo Editora de maneira rápida, nas views que tem o objeto como parâmetro.
	 * @param editora, que é o objeto a ser salvo no banco
	 * @param result, que serve para exibir na view se houve erro ou sucesso durante o processo de salvar
	 * @return ResponseEntity.ok(retorno), que é a confirmação da inserção
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> salvar(@RequestBody Editora editora,BindingResult result){
		
		//Se deu erro ele vai retornar a msg padrão definida lá no @NotBlank ou de outra anotação se houver
		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
		}
		Editora retorno = new Editora();
		try {
			//vai tentar salvar no banco
			retorno = new Editora(cadastroEditoraService.salvar(editora));
		}
		catch(ItemDuplicadoException e) {
			//se ja tiver nome cadastrado vai lançar essa exceção
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		//se tiver tudo ok, vem pra cá
		return ResponseEntity.ok(retorno);
	}
}
