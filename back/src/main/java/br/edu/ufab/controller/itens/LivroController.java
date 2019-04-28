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
import br.edu.ufab.model.entities.Autor;
import br.edu.ufab.model.entities.Editora;
import br.edu.ufab.model.entities.itens.Livro;
import br.edu.ufab.model.repositories.AutorRepository;
import br.edu.ufab.model.repositories.EditoraRepository;
import br.edu.ufab.model.repositories.itens.LivroRepository;
import br.edu.ufab.propertyeditors.AutorPropertyEditor;
import br.edu.ufab.propertyeditors.EditoraPropertyEditor;

/**
 * Classe responsavel por responder as requisições feitas para /livros
 * 
 * @author EquipeACL
 * 
 * 
 * */
@RestController
@RequestMapping("/livros")
public class LivroController {

	@Autowired private EditoraPropertyEditor editoraPropertyEditor;
	@Autowired private EditoraRepository editoraRepository;
	@Autowired private AutorPropertyEditor autorPropertyEditor;
	@Autowired private AutorRepository autorRepository;
	@Autowired private LivroRepository livroRepository;
	
	@GetMapping
	public List<Livro> listar() {
		return (List<Livro>) livroRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Livro>> buscar(@PathVariable long id) {
		Optional<Livro> livro = livroRepository.findById(id);
		if(livro == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(livro);
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<Livro> salvar(@Valid @RequestBody Livro livro, BindingResult bindingResult) {
		
		if ( bindingResult.hasErrors() ) {
			throw new Exception();
		} else {
			Livro retorno = livroRepository.save(livro);
			return ResponseEntity.ok(retorno);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<Void> deletar(@PathVariable long id) {
		Optional<Livro> livro = livroRepository.findById(id);
		if(livro == null){
			return ResponseEntity.notFound().build();
		}
		livroRepository.deleteById(id);
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<Livro> atualizar(@PathVariable long id,@Valid @RequestBody Livro livro) {
		Optional<Livro> existente = livroRepository.findById(id);
		if(existente == null){
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(livro,existente,"id");
		BeanUtils.copyProperties(existente,livro);
		livro = livroRepository.save(livro);
		return ResponseEntity.ok(livro);
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.registerCustomEditor(Editora.class, editoraPropertyEditor);
		webDataBinder.registerCustomEditor(Autor.class, autorPropertyEditor);
	}
}
