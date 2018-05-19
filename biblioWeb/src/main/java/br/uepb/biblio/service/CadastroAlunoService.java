package br.uepb.biblio.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.uepb.biblio.repository.Alunos;
import br.uepb.biblio.service.exception.ItemDuplicadoException;
import br.uepb.model.jpaEntity.usuarios.EntityAluno;
import br.uepb.model.usuarios.Aluno;
/**
 * Essa é a classe de Serviço do Aluno, que contém os métodos responsáveis pelo CRUD desse objeto no banco de dados.
 * @author EquipeACL
 *
 */
@Service
public class CadastroAlunoService {

	private static Logger logger = Logger.getLogger(CadastroAlunoService.class);
	@Autowired
	private Alunos alunos;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PersistenceContext
    private EntityManager manager;
	
	/**
	 * Esse é o método responsável por salvar um objeto no banco de dados
	 * @param aluno, que é o objeto que irá ser salvo no banco de dados.
	 */
	@Transactional
	public EntityAluno salvar (Aluno aluno) {
		EntityAluno entAluno = new EntityAluno(aluno);
		
		Optional <EntityAluno> alunoOptional = alunos.findByCpfIgnoreCase(aluno.getCpf());
		
		if(alunoOptional.isPresent()){
			throw new ItemDuplicadoException(" Aluno(a) já Cadastrado!");
		}
		
		
		entAluno.setSenha(this.passwordEncoder.encode(entAluno.getSenha()));
		entAluno.setConfirmacaoSenha(entAluno.getSenha());
		try {
			return alunos.saveAndFlush(entAluno);
		} catch (Exception e) {
			logger.error("Erro ao cadastrar aluno.",e);
			return null;
		}
	}
	
	/**
	 * Esse é o método responsável por fazer uma busca por nome no banco de dados
	 * @param busca, que é a String que contém o parâmetro de busca por Aluno no banco de dados
	 * @return List<Aluno> contendo o(s) objeto(s) referentes à busca
	 */
	@Transactional
	public List<EntityAluno> buscarPorNome (String busca) {
		return manager.createQuery("select a from EntityAluno a where a.nome like '%"+busca+"%'",EntityAluno.class).getResultList();
	}

	
	@Transactional
	public boolean atualizar(Aluno aluno) {
		
		EntityAluno entAluno = new EntityAluno(aluno);
		
		entAluno.setSenha(this.passwordEncoder.encode(entAluno.getSenha()));
		entAluno.setConfirmacaoSenha(entAluno.getSenha());
		try {
			alunos.save(entAluno); 
			logger.info("Aluno atualizado com sucesso.");
			return true;
		} catch (Exception e) {
			logger.error("Erro ao atualizar aluno.",e);
			return false;
		}
	}
	
	@Transactional
	public boolean remover(int id) {
		if(id>0) {
			try {
				alunos.delete(id);
				logger.info("Aluno removido com sucesso.");
				return true;
			} catch (Exception e) {
				logger.error("Erro ao remover aluno.",e);
				
			}
		}
		return false;
	}
	
	/**
	 * Esse é o método responsável por fornecer as permissões a um objeto do tipo Aluno
	 * @param aluno, que é o objeto cujo irá ter as permissões atribuídas a si
	 * @return List<String> contendo as permissões referentes ao objeto passado por parâmetro
	 */
	public List<String> permissoes(EntityAluno aluno) {
		return manager.createQuery("select distinct p.nome from EntityAluno a inner join a.grupos g inner join g.permissoes p where a = :aluno",String.class)
				.setParameter("aluno", aluno)
				.getResultList();
	}
}
