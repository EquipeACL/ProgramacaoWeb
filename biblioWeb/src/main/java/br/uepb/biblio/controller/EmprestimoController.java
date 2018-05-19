package br.uepb.biblio.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

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

import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.Emprestimo;
import br.uepb.model.usuarios.Aluno;



/**
 * Essa é a classe Controller da classe Emprestimo, e é responsável por fazer a ponte entre as views referentes a esse objeto e os Models, de acordo com as solicitações realizadas nas rotas.
 * @author EquipeACL
 *
 */
@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController{
	
	/*@Autowired
	private Alunos alunosRepository;*/
	
	/*@Autowired
	private CrudEmprestimos emprestimosService;*/
	
	/**
	 * Esse método é responsável por adicionar os parâmetros que vão ser exibidos na view renderizada ao acessar a rota emprestimos/novo	
	 * @param emprestimo, que é o objeto a ser acessado
	 * @return model, que é um objeto ModelAndView que contém os parâmetros que foram adicionados para exibir na view.
	 */
	@RequestMapping("/novo")
	public ModelAndView novo(Emprestimo emprestimo){
		ModelAndView model = new ModelAndView("emprestimo/CadastroEmprestimo");
		//model.addObject("alunos",alunosRepository.findAll());
		List<Aluno> alunos = new ArrayList<Aluno>();
		Aluno a = new Aluno();
		a.setNome("Adalcino Jr");
		a.setId(1);
		alunos.add(a);
		a = new Aluno();
		a.setNome("Caio Cesar");
		a.setId(2);
		alunos.add(a);
		a = new Aluno();
		a.setNome("Lucas Cosmo");
		a.setId(3);
		alunos.add(a);
		model.addObject("alunos",alunos);
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
	public ModelAndView cadastrar(@Valid Emprestimo emprestimo, BindingResult result, RedirectAttributes attributes){
		System.out.println("Quantidade de itens: "+emprestimo.getEmprestimos().size());
		if(result.hasErrors()){
			if(emprestimo.getAluno().getId()==0){
				result.reject("aluno","Selecione um aluno");
			}			
			return novo(emprestimo);
		}		
		try{
			//emprestimosService.salvar(emprestimo);
		}catch(ItemDuplicadoException e){
			result.rejectValue("nome", e.getMessage(),e.getMessage());
			return (novo(emprestimo));
		}
		attributes.addFlashAttribute("mensagem", " Emprestimo salvo com sucesso!");
		return new ModelAndView("redirect:/emprestimos/novo");
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
