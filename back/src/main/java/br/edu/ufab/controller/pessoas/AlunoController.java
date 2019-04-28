package br.edu.ufab.controller.pessoas;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufab.exception.Exception;
import br.edu.ufab.model.entities.Curso;
import br.edu.ufab.model.entities.pessoas.Aluno;
import br.edu.ufab.model.enums.PeriodoDeIngresso;
import br.edu.ufab.model.repositories.CursoRepository;
import br.edu.ufab.model.repositories.pessoas.AlunoRepository;

/**
 * Classe responsavel por responder as requisições feitas para /alunos
 * 
 * @author EquipeACL 
 * 
 * 
 * */
@RestController
@RequestMapping("/alunos")
public class AlunoController {
	

	@Autowired private CursoRepository cursoRepository;
	@Autowired private AlunoRepository alunoRepository;
	
	@GetMapping
	public List<Aluno> listar() {
		return (List<Aluno>) alunoRepository.findAll();
	}
	
	@GetMapping("/periodos")
	public PeriodoDeIngresso[] listarPeriodos() {
		return PeriodoDeIngresso.values();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Aluno>> buscar(@PathVariable long id) {
		Optional<Aluno> aluno = alunoRepository.findById(id);
		if(aluno == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(aluno);
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<Aluno> salvar(@Valid @RequestBody Aluno aluno, BindingResult bindingResult) {
		
		if ( bindingResult.hasErrors() ) {
			throw new Exception();
		} else {
			aluno.gerarMatricula();
			Aluno retorno = alunoRepository.save(aluno);
			return ResponseEntity.ok(retorno);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<Aluno> deletar(@PathVariable long id) {
		Optional<Aluno> aluno = alunoRepository.findById(id);
		if(aluno == null){
			return ResponseEntity.notFound().build();
		}
		alunoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Aluno> atualizar(@PathVariable long id,@Valid @RequestBody Aluno aluno) {
		Optional<Aluno> existente = alunoRepository.findById(id);
		if(existente == null){
			return ResponseEntity.notFound().build();
		}
		if(!existente.get().getCurso().equals(aluno.getCurso())){
			aluno.gerarMatricula();
		}
		BeanUtils.copyProperties(aluno,existente,"id");
		BeanUtils.copyProperties(existente,aluno);
		aluno = alunoRepository.save(aluno);
		return ResponseEntity.ok(aluno);		
	}

}
