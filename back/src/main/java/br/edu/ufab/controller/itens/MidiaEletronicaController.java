package br.edu.ufab.controller.itens;

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
import br.edu.ufab.model.entities.itens.MidiaEletronica;
import br.edu.ufab.model.repositories.itens.MidiaEletronicaRepository;
/**
 * Classe responsavel por responder as requisições feitas para /midias
 * 
 * @author EquipeACL
 * 
 * 
 * */
@RestController
@RequestMapping("/midias")
public class MidiaEletronicaController {
	
	@Autowired private MidiaEletronicaRepository midiaEletronicaRepository;
	
	@GetMapping
	public List<MidiaEletronica> listar() {
		return (List<MidiaEletronica>) midiaEletronicaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Optional<MidiaEletronica>> buscar(@PathVariable long id) {
		Optional<MidiaEletronica> midia = midiaEletronicaRepository.findById(id);
		if(midia == null){
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(midia);
	}
	
	@PostMapping
	public @ResponseBody ResponseEntity<MidiaEletronica> salvar(@Valid @RequestBody MidiaEletronica midia, BindingResult bindingResult) {
		
		if ( bindingResult.hasErrors() ) {
			throw new Exception();
		} else {
			MidiaEletronica retorno = midiaEletronicaRepository.save(midia);
			return ResponseEntity.ok(retorno);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public @ResponseBody ResponseEntity<Void> deletar(@PathVariable long id) {
		Optional<MidiaEletronica> midia = midiaEletronicaRepository.findById(id);
		if(midia == null){
			return ResponseEntity.notFound().build();
		}
		midiaEletronicaRepository.deleteById(id);		
		return ResponseEntity.noContent().build();
		
	}
	
	@PutMapping("/{id}")
	public @ResponseBody ResponseEntity<MidiaEletronica> atualizar(@PathVariable long id,@Valid @RequestBody MidiaEletronica midia) {
		Optional<MidiaEletronica> existente = midiaEletronicaRepository.findById(id);
		if(existente == null){
			return ResponseEntity.notFound().build();
		}
		BeanUtils.copyProperties(midia,existente,"id");
		BeanUtils.copyProperties(existente,midia);
		midia = midiaEletronicaRepository.save(midia);
		return ResponseEntity.ok(midia);
	}
}

