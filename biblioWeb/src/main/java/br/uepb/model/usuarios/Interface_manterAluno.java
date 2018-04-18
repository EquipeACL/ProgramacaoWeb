package br.uepb.model.usuarios;

import java.util.ArrayList;

/**
 * Essa � a interface que cont�m as assinaturas dos m�todos para criar, atualizar e buscar um objeto do tipo Aluno
 * @author EquipeACL
 *
 */
public interface Interface_manterAluno {
	
	public boolean createAluno(Aluno aluno);
	public boolean updateAluno(Aluno aluno);
	public ArrayList<Aluno> searchAluno(Aluno aluno);

}
