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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufab.exception.Exception;
import br.edu.ufab.model.entities.Autor;
import br.edu.ufab.model.repositories.AutorRepository;
/**
 * Classe responsavel por responder as requisições feitas para /autores
 * 
 * @author EquipeACL
 * 
 * 
 * */
@RestController
@RequestMapping("/autores")
public class AutorController {

	@Autowired private AutorRepository autorRepository;
	
	
	@GetMapping
	public List<Autor> listar() {
		return (List<Autor>) autorRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Autor>> buscar(@PathVariable long id) {
		Optional<Autor> autor = autorRepository.findById(id);
		if(autor == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(autor);
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<Autor> salvar(@Valid @RequestBody Autor autor, BindingResult bindingResult) {
		
		if ( bindingResult.hasErrors() ) {
			throw new Exception();
		} else {
			Autor retorno = autorRepository.save(autor);
			return ResponseEntity.ok(retorno);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<Void> deletar(@PathVariable long id) {
		Optional<Autor> autor = autorRepository.findById(id);
		if(autor == null){
			return ResponseEntity.notFound().build();
		}
		autorRepository.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<Autor> atualizar(@PathVariable long id, @Valid @RequestBody Autor autor, BindingResult bindingResult) {
		Optional<Autor> existente = autorRepository.findById(id);
		if(existente == null){
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(autor,existente,"id");
		BeanUtils.copyProperties(existente,autor);
		autor = autorRepository.save(autor);
		return ResponseEntity.ok(autor);
	}
}
