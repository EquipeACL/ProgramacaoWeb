package br.uepb.biblio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.uepb.biblio.service.CidadesService;
import br.uepb.model.Estado;
import br.uepb.model.jpaEntity.EntityCidade;
@Controller
@RequestMapping("/cidades")
public class CidadesController {
	
	@Autowired
	private CidadesService cidadesService;
	
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> buscar(@RequestBody Estado estado){
		
		List<EntityCidade> retorno = cidadesService.buscarPorUf(estado.getSigla());
		
		System.out.println("Tamanho da lista: "+retorno.size());
		
		return ResponseEntity.ok(retorno);
	} 

}
