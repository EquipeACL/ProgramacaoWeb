package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Alunos;
import br.uepb.biblio.repository.Funcionarios;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.biblio.service.exception.LoginDuplicadoException;
import br.uepb.model.jpaEntity.usuarios.EntityAluno;
import br.uepb.model.usuarios.Funcionario;
/**
 * Essa é a classe de Serviço do Funcionario, que contém os métodos responsáveis pelo CRUD desse objeto no banco de dados.
 * @author EquipeACL
 *
 */
@Service
public class CadastroFuncionarioService {
	private static Logger logger = Logger.getLogger(CadastroFuncionarioService.class);
	@Autowired
	Funcionarios funcionarios;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	Alunos alunos;
	
	@PersistenceContext
    private EntityManager manager;
	/**
	 * Esse é o método responsável por salvar um objeto no banco de dados
	 * @param funcionario, que é o objeto que irá ser salvo no banco de dados.
	 */
	@Transactional
	public Funcionario salvar (Funcionario funcionario) {
		Optional <Funcionario> funcionarioOptional = funcionarios.findByNomeIgnoreCase(funcionario.getNome());
		if(funcionarioOptional.isPresent()){
			throw new ItemDuplicadoException(" Funcionário(a) já Cadastrado!");
		}
		
		funcionarioOptional = funcionarios.findByLoginIgnoreCase(funcionario.getLogin());
		if(funcionarioOptional.isPresent()) {
			throw new LoginDuplicadoException(" Já há um usuário cadastrado com este Login!");
		}
		
		Optional <EntityAluno> alunoOptional = alunos.findByMatriculaIgnoreCase(funcionario.getLogin());
		if(funcionarioOptional.isPresent()) {
			throw new LoginDuplicadoException(" Já há um Aluno cadastrado com este Login!");
		}	

		funcionario.setSenha(this.passwordEncoder.encode(funcionario.getSenha()));
		funcionario.setConfirmacaoSenha(funcionario.getSenha());
		try {
			Funcionario retorno =  funcionarios.saveAndFlush(funcionario);
			logger.info("Usuario cadastrado com sucesso.");
			return retorno;
		} catch (Exception e) {
			logger.error("Erro ao cadastrar usuario",e);
			return null;
		}
	}
	
	/**
	 * Esse é o método responsável por fazer uma busca por nome no banco de dados
	 * @param busca, que é a String que contém o parâmetro de busca por Funcionario no banco de dados
	 * @return List<Funcionario> contendo o(s) objeto(s) referentes à busca
	 */
	@Transactional
	public List<Funcionario> buscarPorNome (String busca) {
		return manager.createQuery("select a from Funcionario a where a.nome like '%"+busca+"%'",Funcionario.class).getResultList();
	}
	
	@Transactional
	public boolean atualizar (Funcionario funcionario) {
		try {
			funcionario.setSenha(this.passwordEncoder.encode(funcionario.getSenha()));
			funcionario.setConfirmacaoSenha(funcionario.getSenha());
			funcionarios.save(funcionario);
			logger.info("Usuario atualizado com sucesso.");
			return true;
		} catch (Exception e) {
			logger.error("Erro ao atualizar usuario.",e);
			return false;
		}
	}
	
	@Transactional
	public boolean remover (int id) {
		try {
			funcionarios.delete(id);
			logger.info("Usuario removido com sucesso.");
			return true;
		} catch (Exception e) {
			logger.error("Erro ao remover usuario.",e);
			return false;
		}
	}
	
	/**
	 * Esse é o método responsável por fornecer as permissões a um objeto do tipo Funcionario
	 * @param funcionario, que é o objeto cujo irá ter as permissões atribuídas a si
	 * @return List<String> contendo as permissões referentes ao objeto passado por parâmetro
	 */
	@Transactional
	public List <String> permissoes(Funcionario funcionario){
		return manager.createQuery("select distinct p.nome from Funcionario u inner join u.grupos g inner join g.permissoes p where u = :funcionario",String.class)
				.setParameter("funcionario", funcionario)
				.getResultList();
	}
	
}
