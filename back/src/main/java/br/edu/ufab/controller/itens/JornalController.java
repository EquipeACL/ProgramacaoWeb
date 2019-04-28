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
import br.edu.ufab.model.entities.itens.Jornal;
import br.edu.ufab.model.repositories.EditoraRepository;
import br.edu.ufab.model.repositories.itens.JornalRepository;
import br.edu.ufab.propertyeditors.EditoraPropertyEditor;
/**
 * Classe responsavel por responder as requisições feitas para /jornais
 * 
 * @author EquipeACL 
 * 
 * 
 * */
@RestController
@RequestMapping("/jornais")
public class JornalController {

	@Autowired private EditoraPropertyEditor editoraPropertyEditor;
	@Autowired private EditoraRepository editoraRepository;
	@Autowired private JornalRepository jornalRepository;
	
	@GetMapping
	public List<Jornal> listar() {
		return (List<Jornal>) jornalRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Jornal>> buscar(@PathVariable long id) {
		Optional<Jornal> jornal = jornalRepository.findById(id);
		if(jornal == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(jornal);
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<Jornal> salvar(@Valid @RequestBody Jornal jornal, BindingResult bindingResult) {
		
		if ( bindingResult.hasErrors() ) {
			throw new Exception();
		} else {
			Jornal retorno = jornalRepository.save(jornal);
			return ResponseEntity.ok(retorno);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<Void> deletar(@PathVariable long id) {
		Optional<Jornal> jornal = jornalRepository.findById(id);
		if(jornal == null){
			return ResponseEntity.notFound().build();
		}
		jornalRepository.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<Jornal> atualizar(@PathVariable long id, @Valid @RequestBody Jornal jornal) {
		Optional<Jornal> existente = jornalRepository.findById(id);
		if(existente == null){
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(jornal,existente,"id");
		BeanUtils.copyProperties(existente,jornal);
		jornal = jornalRepository.save(jornal);
		return ResponseEntity.ok(jornal);		
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.registerCustomEditor(Editora.class, editoraPropertyEditor);
	}
}
