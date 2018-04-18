package br.uepb.model.usuarios;

import java.util.ArrayList;

/**
 * Essa � a interface que cont�m as assinaturas dos m�todos para criar, atualizar, buscar e remover um objeto do tipo Funcion�rio
 * @author Cleonice
 *
 */
public interface Interface_manterFuncionario {
	
	public boolean createFuncionario(Funcionario f);
	public boolean updateFuncionario(Funcionario f);
	public boolean removeFuncionario(Funcionario f);
	public ArrayList<Funcionario> searchFuncionario(Funcionario f);

}
