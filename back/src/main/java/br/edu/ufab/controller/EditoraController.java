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
import br.edu.ufab.model.entities.Editora;
import br.edu.ufab.model.repositories.EditoraRepository;

/**
 * Classe por responder as requisições feitas para /editoras
 * 
 * @author EquipeACL
 * 
 * 
 * */

@RestController
@RequestMapping("/editoras")
public class EditoraController {

	@Autowired private EditoraRepository editoraRepository;
	
	@GetMapping
	public List<Editora> listar() {
		return (List<Editora>) editoraRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Editora>> buscar(@PathVariable long id) {
		Optional<Editora> editora = editoraRepository.findById(id);
		if(editora == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(editora);
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<Editora> salvar(@Valid @RequestBody Editora editora, BindingResult bindingResult) {
		
		if ( bindingResult.hasErrors() ) {
			throw new Exception();
		} else {
			Editora retorno = editoraRepository.save(editora);
			return ResponseEntity.ok(retorno);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<Void> deletar(@PathVariable long id) {
		Optional<Editora> editora = editoraRepository.findById(id);
		if(editora == null){
			return ResponseEntity.notFound().build();
		}
		editoraRepository.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<Editora> atualizar(@PathVariable long id, @Valid @RequestBody Editora editora) {
		Optional<Editora> existente = editoraRepository.findById(id);
		if(existente == null){
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(editora,existente,"id");
		BeanUtils.copyProperties(existente,editora);
		editora = editoraRepository.save(editora);
		return ResponseEntity.ok(editora);
	}
	
}
