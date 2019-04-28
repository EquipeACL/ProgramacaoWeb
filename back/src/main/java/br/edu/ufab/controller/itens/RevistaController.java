package br.edu.ufab.controller.itens;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.edu.ufab.exception.Exception;
import br.edu.ufab.model.entities.Editora;
import br.edu.ufab.model.entities.itens.Revista;
import br.edu.ufab.model.repositories.EditoraRepository;
import br.edu.ufab.model.repositories.itens.RevistaRepository;

/**
 * Classe responsavel por responder as requisições feitas para /revistas
 * 
 * @author EquipeACL
 * 
 * 
 * */
@RestController
@RequestMapping("/revistas")
public class RevistaController {
	
	@Autowired private EditoraRepository editoraRepository;
	@Autowired private RevistaRepository revistaRepository;
	
	@GetMapping
	public List<Revista> listar() {
		return (List<Revista>) revistaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Revista>> buscar(@PathVariable long id) {
		Optional<Revista> revista = revistaRepository.findById(id);
		if(revista == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(revista);
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<Revista> salvar(@Valid @RequestBody Revista revista, BindingResult bindingResult) {
		
		if ( bindingResult.hasErrors() ) {
			throw new Exception();
		} else {
			Revista retorno = revistaRepository.save(revista);
			return ResponseEntity.ok(retorno);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<Void> deletar(@PathVariable long id) {
		Optional<Revista> revista = revistaRepository.findById(id);
		if(revista == null){
			return ResponseEntity.notFound().build();
		}
		revistaRepository.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<Revista> atualizar(@PathVariable long id, @Valid @RequestBody Revista revista) {
		Optional<Revista> existente = revistaRepository.findById(id);
		if(existente == null){
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(revista,existente,"id");
		BeanUtils.copyProperties(existente,revista);
		revista = revistaRepository.save(revista);
		return ResponseEntity.ok(revista);		
	}

}
