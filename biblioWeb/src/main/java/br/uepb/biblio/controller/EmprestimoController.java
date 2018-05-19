package br.uepb.biblio.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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

import br.uepb.biblio.repository.Alunos;
import br.uepb.biblio.repository.Anais;
import br.uepb.biblio.repository.Emprestimos;
import br.uepb.biblio.repository.Jornais;
import br.uepb.biblio.repository.Livros;
import br.uepb.biblio.repository.Midias;
import br.uepb.biblio.repository.Revistas;
import br.uepb.biblio.repository.Tccs;
import br.uepb.biblio.service.CrudEmprestimosService;
import br.uepb.model.Emprestimo;



/**
 * Essa é a classe Controller da classe Emprestimo, e é responsável por fazer a ponte entre as views referentes a esse objeto e os Models, de acordo com as solicitações realizadas nas rotas.
 * @author EquipeACL
 *
 */
@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController{
	
	@Autowired
	private Livros livrosRepository;
	
	@Autowired
	private Jornais jornaisRepository;
	
	@Autowired
	private Revistas revistasRepository;
	
	@Autowired
	private Anais anaisRepository;
	
	@Autowired
	private Tccs tccsRepository;
	
	@Autowired
	private Midias midiasRepository;
	
	@Autowired
	private Alunos alunosRepository;
	
	@Autowired
	private CrudEmprestimosService emprestimosService;
	
	@Autowired
	private Emprestimos emprestimosRepository;
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota emprestimos/novo	
	 * @param emprestimo, que é o objeto a ser acessado
	 * @return model, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/novo")
	public ModelAndView novo(Emprestimo emprestimo,String busca,String filtro){
		ModelAndView model = new ModelAndView("emprestimo/CadastroEmprestimo");
		model.addObject("alunos",alunosRepository.findAll());
		if(busca!=null){
			if(filtro!=null && filtro.equals("data")){
				model.addObject("listaEmprestimos",emprestimosService.buscarPorData(busca));
			}else{
				model.addObject("listaEmprestimos",emprestimosService.buscarPorAluno(busca));
			}
		}else{
			model.addObject("listaEmprestimos", emprestimosRepository.findAll());
		}
		return model;
	}
	
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca,String filtro){
		ModelAndView model = new ModelAndView("emprestimo/PesquisaEmprestimo");
		if(busca!=null){
			if(filtro!=null && filtro.equals("data")){
				model.addObject("listaEmprestimos",emprestimosService.buscarPorData(busca));
			}else{
				model.addObject("listaEmprestimos",emprestimosService.buscarPorAluno(busca));
			}
		}else{
			model.addObject("listaEmprestimos", emprestimosRepository.findAll());
		}
		return model;
	}
	
	@RequestMapping(value="/pesquisar",method=RequestMethod.POST)
	public ModelAndView buscar(String busca,String filtro){
		ModelAndView model = new ModelAndView("emprestimo/PesquisaEmprestimo");
		if(busca!=null){
			if(filtro!=null && filtro.equals("data")){
				model.addObject("listaEmprestimos",emprestimosService.buscarPorData(busca));
			}else{
				model.addObject("listaEmprestimos",emprestimosService.buscarPorAluno(busca));
			}
		}else{
			model.addObject("listaEmprestimos", emprestimosRepository.findAll());
		}
		return model;
	}
	
	/**
	 * Esse é o método que irá acessar a rota emprestimos/novo, porém com uma requisição do tipo POST, que servirá para salvar o objeto passado por parâmetro no banco
	 * @param emprestimo, que é o objeto que será mapeado no formulário para salvar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/emprestimos/novo"), que renderiza a página no endereço emprestimos/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value="/novo",method=RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Emprestimo emprestimo,BindingResult result, RedirectAttributes attributes){
		if(result.hasErrors()){
			if(emprestimo.getAluno().getId()==0){
				result.reject("aluno"," Selecione um aluno");
			}
			if(emprestimo.getAnal().getId()==0 && emprestimo.getLivro().getId()==0 && emprestimo.getJornal().getId()==0
					&& emprestimo.getRevista().getId()==0 && emprestimo.getTcc().getId()==0 && emprestimo.getMidia().getId()==0){
				result.reject("item"," Selecione um item");
			}
			return novo(emprestimo,null,null);
		}		
		try{
			emprestimosService.salvar(emprestimo);
		}catch(Exception e){
			result.rejectValue("aluno", e.getMessage(),e.getMessage());
			return (novo(emprestimo,null,null));
		}
		attributes.addFlashAttribute("mensagem", " Emprestimo salvo com sucesso!");
		return new ModelAndView("redirect:/emprestimos/novo");
	}
	
	
	@RequestMapping(value = "/remover", method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Emprestimo emprestimo, RedirectAttributes attributes) {
		try {
			// vai tentar remover no banco
			System.out.println(">>>> chegou no controller");
			emprestimosService.remover(emprestimo.getId());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
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
