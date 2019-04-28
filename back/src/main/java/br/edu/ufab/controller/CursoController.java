package br.edu.ufab.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufab.exception.Exception;
import br.edu.ufab.model.entities.Curso;
import br.edu.ufab.model.enums.AreaDeCurso;
import br.edu.ufab.model.enums.TipoDeCurso;
import br.edu.ufab.model.repositories.CursoRepository;
/**
 * Classe responsavel por responder as requisições feitas para /cursos
 * 
 * @author EquipeACL 
 * 
 * 
 * */
@RestController
@RequestMapping("/cursos")
public class CursoController {

	@Autowired private CursoRepository cursoRepository;
	
	@GetMapping
	public List<Curso> listar() {
		return (List<Curso>) cursoRepository.findAll();
	}
	
	@GetMapping("/tipos")
	public TipoDeCurso[] listarTipos() {
		return TipoDeCurso.values();
	}
	
	@GetMapping("/areas")
	public AreaDeCurso[] listarAreas() {
		return AreaDeCurso.values();
	}
	
	@GetMapping("/{id}")
	public @ResponseBody ResponseEntity<Optional<Curso>> buscar(@PathVariable long id) {
		Optional<Curso> curso = cursoRepository.findById(id);
		if(curso == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(curso);
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<Curso> salvar(@Valid @RequestBody Curso curso, BindingResult bindingResult) {
		
		if ( bindingResult.hasErrors() ) {
			throw new Exception();
		} else {
			Curso retorno = cursoRepository.save(curso);
			return ResponseEntity.ok(retorno);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<Void> deletar(@PathVariable long id) {
		Optional<Curso> curso = cursoRepository.findById(id);
		if(curso == null){
			return ResponseEntity.notFound().build();
		}
		cursoRepository.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Curso> atualizar(@PathVariable long id,@Valid @RequestBody Curso curso, BindingResult bindingResult) {
		Optional<Curso> existente = cursoRepository.findById(id);
		if(existente == null){
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(curso,existente,"id");
		BeanUtils.copyProperties(existente,curso);
		curso = cursoRepository.save(curso);
		return ResponseEntity.ok(curso);
	}
}
