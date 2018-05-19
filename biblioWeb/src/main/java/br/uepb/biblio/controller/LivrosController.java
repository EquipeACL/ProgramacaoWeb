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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.uepb.biblio.repository.AreasConhecimento;
import br.uepb.biblio.repository.Autores;
import br.uepb.biblio.repository.Editoras;
import br.uepb.biblio.repository.Livros;
import br.uepb.biblio.repository.Temas;
import br.uepb.biblio.service.CrudLivroService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Autor;
import br.uepb.model.acervo.Livro;
import br.uepb.model.jpaEntity.acervo.EntityLivro;

/**
 * Essa é a classe Controller da classe Livro, e é responsável por fazer a ponte entre as views referentes a esse objeto e os Models, de acordo com as solicitações realizadas nas rotas.
 * @author EquipeACL
 *
 */
@Controller
@RequestMapping("/livros")
public class LivrosController {

	@Autowired
	private Autores autoresRepository;
	
	@Autowired
	private Editoras editoras;
	
	@Autowired
	private AreasConhecimento areas;
	
	@Autowired
	private Temas temas;
	
	@Autowired
	private Livros livros;
	
	@Autowired
	private CrudLivroService livrosService;

	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota livros/novo	
	 * @param livro, que é o objeto a ser acessado
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Livro no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/novo")
	public ModelAndView novo(Livro livro,String busca,String filtro) {
		ModelAndView mv = new ModelAndView("livro/CadastroLivro");
		mv.addObject("editoras", editoras.findAll());
		mv.addObject("areas", areas.findAll());
		mv.addObject("temas",temas.findAll());
		mv.addObject("editoras",editoras.findAll());
		mv.addObject("autores",autoresRepository.findAll());
		if(busca!=null){
			if(filtro != null && filtro.equals("autor")){
				mv.addObject("listaLivros",livrosService.buscarPorAutor(busca));
			}else{
				mv.addObject("listaLivros",livrosService.buscarPorTitulo(busca));
			}
		}else{
			mv.addObject("listaLivros",livros.findAll());
		}
		return mv;
	}
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota livros/pesquisar	
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Livro no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca,String filtro) {
		ModelAndView mv = new ModelAndView("livro/PesquisaLivro");
		if(busca!=null){
			if(filtro != null && filtro.equals("autor")){
				mv.addObject("listaLivros",livrosService.buscarPorAutor(busca));
			}else{
				mv.addObject("listaLivros",livrosService.buscarPorTitulo(busca));
			}
		}else{
			mv.addObject("listaLivros",livros.findAll());
		}
		return mv;
	}
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota livros/editar	
	 * @param id, que é o id do objeto que vai ser editado no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/editar")
	public ModelAndView editar(String id) {
		ModelAndView mv = new ModelAndView("livro/CadastroLivro");
		mv.addObject("livro", livros.findOne(Integer.parseInt(id)));
		mv.addObject("editoras", editoras.findAll());
		mv.addObject("areas", areas.findAll());
		mv.addObject("temas",temas.findAll());
		mv.addObject("editoras",editoras.findAll());
		mv.addObject("autores",autoresRepository.findAll());
		mv.addObject("listaLivros",livros.findAll());
		return mv;
	}
	
	/**
	 * Esse é o método que irá acessar a rota livros/novo, porém com uma requisição do tipo POST, que servirá para salvar o objeto passado por parâmetro no banco
	 * @param livro, que é o objeto que será mapeado no formulário para salvar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/livros/novo"), que renderiza a página no endereço livros/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar (@Valid Livro livro, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(livro,null,null);
		}
		
		//Criando uma lista com os autores
		ArrayList<Autor> listaAutores = new ArrayList<Autor>();
		listaAutores.add(new Autor(autoresRepository.findOne(Integer.parseInt(livro.getId_autor()))));
		
		//atribuindo ao livro a lista de seus autores
		livro.setAutores(listaAutores);
		
		try {
			livrosService.salvar(livro);
		} catch (ItemDuplicadoException e) {
			result.rejectValue("titulo", e.getMessage(),e.getMessage());
			return (novo(livro,null,null));
		}
		
		attributes.addFlashAttribute("mensagem", "Livro salvo com sucesso!");
		return new ModelAndView("redirect:/livros/novo");
	}
	
	/**
	 * Esse é o método que irá acessar a rota livros/editar, porém com uma requisição do tipo POST, que servirá para alterar o objeto passado por parâmetro no banco
	 * @param livro, que é o objeto que será mapeado no formulário para alterar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/livros/novo"), que renderiza a página no endereço livros/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value = "/editar", method = RequestMethod.POST)
	public ModelAndView atualizar (@Valid Livro livro, BindingResult result, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(livro,null,null);
		}
		
		//Criando uma lista com os autores
		ArrayList<Autor> listaAutores = new ArrayList<Autor>();
		listaAutores.add(new Autor(autoresRepository.findOne(Integer.parseInt(livro.getId_autor()))));
		
		//atribuindo ao livro a lista de seus autores
		livro.setAutores(listaAutores);
		
		try {
			livrosService.atualizar(livro);
		} catch (ItemDuplicadoException e) {
			result.rejectValue("titulo", e.getMessage(),e.getMessage());
			return (novo(livro,null,null));
		}
		
		attributes.addFlashAttribute("mensagem", "Livro atualizado com sucesso!");
		return new ModelAndView("redirect:/livros/novo");
	}
	
	/**
	 * Esse é o método que irá acessar a rota livros/remover, porém com uma requisição do tipo DELETE, que servirá para remover o objeto passado por parâmetro no banco
	 * @param livro, que é o objeto que será mapeado no formulário para remover informações no banco de dados.
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return ResponseEntity.ok().build(); , que é a confirmação da remoção
	 */	
	@RequestMapping(value="/remover",method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Livro livro,RedirectAttributes attributes){
		try {
			//vai tentar remover no banco
			livrosService.remover(livro.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
	/**
	 * Esse é o método que irá acessar a rota livros/buscarAll, porém com uma requisição do tipo POST, que servirá para buscar todos os objetos do tipo Livro no banco de dados
	 * @return ResponseEntity.ok(retorno) que é a confirmação da busca realizada no banco.
	 */
	@RequestMapping(value="/buscarAll",method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> buscarAll(){
		
		List<EntityLivro> retorno = livros.findAll();
		
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
