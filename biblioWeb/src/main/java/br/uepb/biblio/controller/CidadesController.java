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

/**
 * Essa é a classe Controller da classe Cidades, e é responsável por fazer a ponte entre as views referentes a esse objeto e os Models, de acordo com as solicitações realizadas nas rotas.
 * @author EquipeACL
 *
 */
@Controller
@RequestMapping("/cidades")
public class CidadesController {
	
	@Autowired
	private CidadesService cidadesService;
	
	/**
	 * Esse método é responsável por fazer a busca das cidades cadastradas no banco, pelo parâmetro UF
	 * @param estado, que é o estado referente às cidades que se deseja fazer a busca
	 * @return ResponseEntity.ok(retorno), que é a confirmação da busca
	 */
	@RequestMapping(method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public @ResponseBody ResponseEntity<?> buscar(@RequestBody Estado estado){
		
		List<EntityCidade> retorno = cidadesService.buscarPorUf(estado.getSigla());
		
		return ResponseEntity.ok(retorno);
	} 

}
