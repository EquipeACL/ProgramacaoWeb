package br.uepb.biblio.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Essa é a classe Controller da classe Seguranca, e é responsável por fazer a ponte entre as views referentes a esse objeto e os Models, de acordo com as solicitações realizadas nas rotas.
 * @author EquipeACL
 *
 */
@Controller
public class SegurancaController {

	/**
	 * Esse é o método responsável pela autenticação do login do usuário.
	 * @param user, que são os dados do login do usuário
	 * @return "redirect:/tccs/novo", que redireciona o usuário para a página da biblioteca em caso do login estar correto
	 */
	@GetMapping("/login")
	public String login(@AuthenticationPrincipal User user){
		if(user!= null) {
			return "redirect:/index";
		}
		return "Login";
	}
	

	/**
	 * Esse método é responsável por retornar um erro caso o login do usuário esteja incorreto.
	 * @return 403, renderiza a página de erro 403
	 */
	@GetMapping("/403")
	public String acessoNegado() {
		return "403";
	}
	

	/**
	 * Esse método é responsável por renderizar a pagina inicial do sistema
	 * @return index, que é a pagina inicial da biblioteca
	 */
	@GetMapping("/index")
	
	public String paginaInicial() {
		return "index";
	}
	
	
}
