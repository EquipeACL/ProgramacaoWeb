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
	
	@RequestMapping("/novo")
	ModelAndView novo(Aluno aluno) {
		ModelAndView mv = new ModelAndView("aluno/CadastroAluno");
		mv.addObject("cursos",cursos.findAll());
		mv.addObject("niveis", Tipo_nivel.values());
		mv.addObject("grupos", cadastroGrupoService.buscaAluno());
		return mv;
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastro(@Valid Aluno aluno, BindingResult result,RedirectAttributes attributes, Model model ) {
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
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
	    binder.registerCustomEditor(Date.class, editor);
	}
}
