//package br.uepb.biblio.service;
//
//import java.util.List;
//import java.util.Optional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import br.uepb.biblio.repository.Alunos;
//import br.uepb.biblio.service.exception.ItemDuplicadoException;
//import br.uepb.model.usuarios.Aluno;
//@Service
//public class CadastroAlunoService {
//
//	@Autowired
//	private Alunos alunos;
//	
//	@PersistenceContext
//    private EntityManager manager;
//	
//	@Transactional
//	public void salvar (Aluno aluno) {
//		Optional <Aluno> alunoOptional = alunos.findByNomeIgnoreCase(aluno.getNome());
//		if(alunoOptional.isPresent()){
//			throw new ItemDuplicadoException(" Aluno(a) já Cadastrado!");
//		}
//		alunos.save(aluno);
//	}
//	
//	@Transactional
//	public List<Aluno> buscarPorNome (String busca) {
//		return manager.createQuery("select a from Aluno a where a.nomeCompleto like '%"+busca+"%'",Aluno.class).getResultList();
//	}
//}
