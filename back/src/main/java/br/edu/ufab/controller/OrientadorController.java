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
import br.edu.ufab.model.entities.Orientador;
import br.edu.ufab.model.repositories.OrientadorRepository;
/**
 * Classe responsavel por responder as requisições feitas para /orientadores
 * 
 * @author EquipeACL 
 * 
 * 
 * */
@RestController
@RequestMapping("/orientadores")
public class OrientadorController {

	@Autowired private OrientadorRepository orientadorRepository;
	
	@GetMapping
	public List<Orientador> listar() {
		return (List<Orientador>) orientadorRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Orientador>> buscar(@PathVariable long id) {
		Optional<Orientador> orientador = orientadorRepository.findById(id);
		if(orientador == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(orientador);
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<Orientador> salvar(@Valid @RequestBody Orientador orientador, BindingResult bindingResult) {
		
		if ( bindingResult.hasErrors() ) {
			throw new Exception();
		} else {
			Orientador retorno = orientadorRepository.save(orientador);
			return ResponseEntity.ok(retorno);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<Void> deletar(@PathVariable long id) {
		Optional<Orientador> orientador = orientadorRepository.findById(id);
		if(orientador == null){
			return ResponseEntity.notFound().build();
		}		
		orientadorRepository.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<Orientador> atualizar(@PathVariable long id, @Valid @RequestBody Orientador orientador, BindingResult bindingResult) {
		Optional<Orientador> existente = orientadorRepository.findById(id);
		if(existente == null){
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(orientador, existente,"id");
		BeanUtils.copyProperties(existente,orientador);
		orientador = orientadorRepository.save(orientador);
		return ResponseEntity.ok(orientador);
	}
}
