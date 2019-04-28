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
import br.edu.ufab.model.entities.Orientador;
import br.edu.ufab.model.entities.itens.TCC;
import br.edu.ufab.model.enums.TipoDeTCC;
import br.edu.ufab.model.repositories.AutorRepository;
import br.edu.ufab.model.repositories.OrientadorRepository;
import br.edu.ufab.model.repositories.itens.TCCRepository;
import br.edu.ufab.propertyeditors.AutorPropertyEditor;
import br.edu.ufab.propertyeditors.OrientadorPropertyEditor;
/**
 * Classe responsavel por responder as requisições feitas para /tccs
 * 
 * @author EquipeACL
 * 
 * 
 * */
@RestController
@RequestMapping("/tccs")
public class TCCController {
	
	@Autowired private AutorPropertyEditor autorPropertyEditor;
	@Autowired private AutorRepository autorRepository;
	@Autowired private OrientadorPropertyEditor orientadorPropertyEditor;
	@Autowired private OrientadorRepository oriendadorRepository;
	@Autowired private TCCRepository tccRepository;
	
	@GetMapping
	public List<TCC> listar() {
		return (List<TCC>) tccRepository.findAll();
	}
	
	@GetMapping("/tipos")
	public TipoDeTCC[] listarTipos() {
		return TipoDeTCC.values();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<TCC>> buscar(@PathVariable long id) {
		Optional<TCC> tcc = tccRepository.findById(id);
		if(tcc == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(tcc);
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<TCC> salvar(@Valid @RequestBody TCC tcc, BindingResult bindingResult) {
		
		if ( bindingResult.hasErrors() ) {
			throw new Exception();
		} else {
			TCC retorno = tccRepository.save(tcc);
			return ResponseEntity.ok(retorno);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<TCC> deletar(@PathVariable long id) {
		Optional<TCC> tcc = tccRepository.findById(id);
		if(tcc == null){
			return ResponseEntity.notFound().build();
		}
		tccRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<TCC> atualizar(@PathVariable long id, @Valid @RequestBody TCC tcc) {
		Optional<TCC> existente = tccRepository.findById(id);
		if(existente == null){
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(tcc,existente,"id");
		BeanUtils.copyProperties(existente,tcc);
		tcc = tccRepository.save(tcc);
		return ResponseEntity.ok(tcc);
		
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder){
		webDataBinder.registerCustomEditor(Autor.class, autorPropertyEditor);
		webDataBinder.registerCustomEditor(Orientador.class, orientadorPropertyEditor);
	}
}
