package br.uepb.biblio.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.uepb.biblio.repository.Alunos;
import br.uepb.biblio.repository.Cursos;
import br.uepb.biblio.repository.Grupos;
import br.uepb.biblio.service.CadastroAlunoService;
import br.uepb.biblio.service.CadastroGrupoService;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.biblio.service.exception.SenhaObrigatoriaUsuarioException;
import br.uepb.model.Curso;
import br.uepb.model.enums.Tipo_nivel;
import br.uepb.model.usuarios.Aluno;

/**
 * Essa é a classe Controller da classe Aluno, e é responsável por fazer a ponte entre as views referentes a esse objeto e os Models, de acordo com as solicitações realizadas nas rotas.
 * @author EquipeACL
 *
 */

@Controller
@RequestMapping("/alunos")
public class AlunosController {
	@Autowired
	private CadastroAlunoService cadastroAlunoService;
	
	@Autowired
	private CadastroGrupoService cadastroGrupoService;
	
	@Autowired
	private Cursos cursos;
	
	@Autowired
	private Alunos alunos;
	
	@Autowired
	private Grupos grupos;
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota alunos/novo	
	 * @param aluno, que é o objeto a ser acessado
	 * @return mv, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/novo")
	ModelAndView novo(Aluno aluno) {
		ModelAndView mv = new ModelAndView("aluno/CadastroAluno");
		mv.addObject("cursos",cursos.findAll());
		mv.addObject("niveis", Tipo_nivel.values());
		mv.addObject("grupos", cadastroGrupoService.buscaAluno());
		return mv;
	}
	
	/**
	 * Esse é o método que irá acessar a rota alunos/novo, porém com uma requisição do tipo POST, que servirá para salvar o objeto passado por parâmetro no banco
	 * @param aluno, que é o objeto que será mapeado no formulário para salvar informações no banco de dados.
	 * @param result, que serve para mapear se houve erros de preenchimento do formulário 
	 * @param attributes, que serve para fornecer avisos na view (sucesso ou erro)
	 * @return new ModelAndView("redirect:/alunos/novo"), que renderiza a página no endereço alunos/novo (caso haja sucesso na inserção) 
	 */
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastro(@Valid Aluno aluno, BindingResult result,RedirectAttributes attributes, Model model) {
		if(result.hasErrors()) {
			return novo(aluno);
		}

		try {

			aluno.setCurso(new Curso (cursos.findOne(aluno.getCurso().getId())));
			System.out.println(aluno.getCurso().getSigla());
			aluno.gerarMatricula();
			System.out.println(aluno.getMatricula());
			cadastroAlunoService.salvar(aluno);
		}
		catch (ItemDuplicadoException e){
			result.rejectValue("cpf", e.getMessage(),e.getMessage());
			return (novo(aluno));
		}
		catch(SenhaObrigatoriaUsuarioException e){
			result.rejectValue("senha", e.getMessage(),e.getMessage());
		}
		attributes.addFlashAttribute("mensagem", "Aluno salvo com sucesso!");
		
		return new ModelAndView("redirect:/alunos/novo");
		
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
