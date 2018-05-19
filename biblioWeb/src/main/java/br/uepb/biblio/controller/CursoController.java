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
import br.uepb.biblio.repository.Cursos;
import br.uepb.biblio.service.CadastroCursosService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Curso;
import br.uepb.model.enums.Tipo_curso;

/**
 * Essa é a classe Controller da classe Curso, e é responsável por fazer a ponte entre as views referentes a esse objeto e os Models, de acordo com as solicitações realizadas nas rotas.
 * @author EquipeACL
 *
 */
@Controller
@RequestMapping("/cursos")
public class CursoController {
	
	@Autowired
	private Cursos cursosRepository;
	
	@Autowired
	private AreasConhecimento areasRepository;
	
	@Autowired
	private CadastroCursosService cursosService;

	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota cursos/novo	
	 * @param curso, que é o objeto a ser acessado
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Curso no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/novo")
	public ModelAndView novo(Curso curso,String busca,String filtro){
		ModelAndView model = new ModelAndView("curso/CadastroCurso");
		model.addObject("tiposCursos", Tipo_curso.values());
		model.addObject("areas", areasRepository.findAll());
		if(busca!=null){
			if(filtro!=null && filtro.equals("tipo")){
				model.addObject("listaCurso",cursosService.buscarPorTipo(busca));
			}else{
				model.addObject("listaCurso",cursosService.buscarPorNome(busca));
			}
		}else{
			model.addObject("listaCurso",cursosRepository.findAll());
		}
		return model;
	}
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota cursos/pesquisar	
	 * @param busca, que é a string que serve como parâmetro para a busca de um objeto do tipo Anal no banco de dados.
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/pesquisar")
	public ModelAndView pesquisar(String busca){
		ModelAndView model = new ModelAndView("curso/PesquisaCurso");
		if(busca!=null){
			model.addObject("listaCurso",cursosService.buscarPorNome(busca));
		}else{
			model.addObject("listaCurso",cursosRepository.findAll());
		}
		return model;
	}
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota cursos/editar	
	 * @param id, que é o id do objeto que vai ser editado no banco de dados.
	 * @return model, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/editar")
	public ModelAndView editar(String id){
		ModelAndView model = new ModelAndView("curso/CadastroCurso");
		model.addObject("curso",cursosRepository.findOne(Integer.parseInt(id)));
		model.addObject("tiposCursos", Tipo_curso.values());
		model.addObject("areas", areasRepository.findAll());
		model.addObject("listaCurso",cursosRepository.findAll());
		return model;
	}
	
	/**
	 * Esse é o método que irá acessar a rota cursos/novo, porém com uma requisição do tipo POST, que servirá para salvar o objeto passado por parâmetro no banco
	 * @param curso, que é o objeto que será mapeado no formulário para salvar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/cursos/novo"), que renderiza a página no endereço cursos/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value="/novo",method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Curso curso, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(curso,null,null);
		}
		
		try{
			cursosService.salvar(curso);
		}catch(ItemDuplicadoException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(curso,null,null));
		}
		attributes.addFlashAttribute("mensagem", " Curso salvo com sucesso!");
		return new ModelAndView("redirect:/cursos/novo");
	}
	
	/**
	 * Esse é o método que irá acessar a rota cursos/editar, porém com uma requisição do tipo POST, que servirá para alterar o objeto passado por parâmetro no banco
	 * @param curso, que é o objeto que será mapeado no formulário para alterar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/cursos/novo"), que renderiza a página no endereço cursos/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value="/editar",method = RequestMethod.POST)
	public ModelAndView atualizar(@Valid Curso curso, BindingResult result, Model model, RedirectAttributes attributes) {
		if(result.hasErrors()) {
			return novo(curso,null,null);
		}		
		try{
			cursosService.atualizar(curso);
		}catch(ItemDuplicadoException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(curso,null,null));
		}
		attributes.addFlashAttribute("mensagem", " Curso atualizado com sucesso!");
		return new ModelAndView("redirect:/cursos/novo");
	}
	
	/**
	 * Esse é o método que irá acessar a rota cursos/remover, porém com uma requisição do tipo POST, que servirá para remover o objeto passado por parâmetro no banco
	 * @param curso, que é o objeto que será mapeado no formulário para remover informações no banco de dados.
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return ResponseEntity.ok().build(); , que é a confirmação da remoção
	 */	
	@RequestMapping(value="/remover",method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> remover(@RequestBody Curso curso,RedirectAttributes attributes){
		try {
			//vai tentar remover no banco
			cursosService.remover(curso.getId());
		}
		catch(Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().build();
	}
	
}

