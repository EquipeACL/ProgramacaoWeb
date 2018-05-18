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

@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController{
	
	/*@Autowired
	private Alunos alunosRepository;*/
	
	/*@Autowired
	private CrudEmprestimos emprestimosService;*/
	
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
	
	@RequestMapping(value="/novo",method=RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Emprestimo emprestimo, BindingResult result, Model model, RedirectAttributes attributes){
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
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
	
}
