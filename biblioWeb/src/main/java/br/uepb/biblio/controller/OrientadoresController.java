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

import br.uepb.biblio.repository.Orientadores;
import br.uepb.biblio.service.CadastroOrientadorService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Orientador;
import br.uepb.model.enums.Tipo_nivel;

/**
 * Essa é a classe Controller da classe Orientador, e é responsável por fazer a ponte entre as views referentes a esse objeto e os Models, de acordo com as solicitações realizadas nas rotas.
 * @author EquipeACL
 *
 */
@Controller
@RequestMapping("/orientadores")
public class OrientadoresController {

	@Autowired
	private Orientadores orientadoresRepository;
	
	@Autowired
	CadastroOrientadorService cadastroOrientadorService;
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota orientadores/novo	
	 * @param orientador, que é o objeto a ser acessado
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Orientadores no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/novo")
	public ModelAndView novo(Orientador orientador,String busca) {
		ModelAndView mv = new ModelAndView("/orientador/CadastroOrientador");
		mv.addObject("formacoes", Tipo_nivel.values());
		if(busca!=null){
			mv.addObject("listaOrientador", cadastroOrientadorService.buscarPorNome(busca));
		}else{
			mv.addObject("listaOrientador", orientadoresRepository.findAll());
		}
		return mv;
	}
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota orientadores/pesquisar	
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Orientador no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca) {
		ModelAndView mv = new ModelAndView("/orientador/PesquisaOrientador");
		mv.addObject("formacoes", Tipo_nivel.values());
		if(busca!=null){
			mv.addObject("listaOrientador", cadastroOrientadorService.buscarPorNome(busca));
		}else{
			mv.addObject("listaOrientador", orientadoresRepository.findAll());
		}
		return mv;
	}
	
	/**
	 * Esse é o método que irá acessar a rota orientadores/novo, porém com uma requisição do tipo POST, que servirá para salvar o objeto passado por parâmetro no banco
	 * @param orientador, que é o objeto que será mapeado no formulário para salvar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/orientadores/novo"), que renderiza a página no endereço orientadores/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar (@Valid Orientador orientador, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(orientador,null);
		}
		//
		try {
			cadastroOrientadorService.salvar(orientador);
		}
		catch(ItemDuplicadoException e) {
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return novo(orientador,null);
		}
		
		attributes.addFlashAttribute("mensagem", "Orientador salvo com sucesso!");
		return new ModelAndView("redirect:/orientadores/novo");
	}
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota orientadores/editar	
	 * @param id, que é o id do objeto que vai ser editado no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/editar")
	public ModelAndView editar(String id){
		ModelAndView model = new ModelAndView("orientador/CadastroOrientador");
		model.addObject("orientador",orientadoresRepository.findOne(Integer.parseInt(id)));
		model.addObject("listaOrientador",orientadoresRepository.findAll());
		model.addObject("formacoes", Tipo_nivel.values());
		return model;
	}
	
	/**
	 * Esse é o método que irá acessar a rota orientadores/editar, porém com uma requisição do tipo POST, que servirá para alterar o objeto passado por parâmetro no banco
	 * @param orientador, que é o objeto que será mapeado no formulário para alterar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/orientadores/novo"), que renderiza a página no endereço orientadores/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value="/editar",method=RequestMethod.POST)
	public ModelAndView editar(@Valid Orientador orientador, BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			return novo(orientador,null);
		}		
		try{
			cadastroOrientadorService.atualizar(orientador);
		}catch(Exception e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(orientador,null));
		}
		attributes.addFlashAttribute("mensagem", " Orientador atualizado com sucesso!");
		return new ModelAndView("redirect:/orientadores/novo");
	}
	
	/**
	 * Esse é o método que irá acessar a rota orientadores/remover, porém com uma requisição do tipo DELETE, que servirá para remover o objeto passado por parâmetro no banco
	 * @param orientador, que é o objeto que será mapeado no formulário para remover informações no banco de dados.
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return ResponseEntity.ok().build(); , que é a confirmação da remoção
	 */
	@RequestMapping(value="/remover",method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Orientador orientador,RedirectAttributes attributes){
		try {
			//vai tentar remover no banco
			cadastroOrientadorService.remover(orientador.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Esse método é responsável por salvar um objeto do tipo Orientador de maneira rápida, nas views que tem o objeto como parâmetro.
	 * @param orientador, que é o objeto a ser salvo no banco
	 * @param result, que serve para exibir na view se houve erro ou sucesso durante o processo de salvar
	 * @return ResponseEntity.ok(retorno), que é a confirmação da inserção
	 */
	@RequestMapping(method = RequestMethod.POST,consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody  ResponseEntity<?> salvar(@RequestBody Orientador orientador, BindingResult result){

		if(result.hasErrors()) {
			return ResponseEntity.badRequest().body(result.getFieldError().getDefaultMessage());
		}
		Orientador retorno = new Orientador();
		try {
			retorno = new Orientador(cadastroOrientadorService.salvar(orientador));
		}
		catch(ItemDuplicadoException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok(retorno);
		
	}
}
