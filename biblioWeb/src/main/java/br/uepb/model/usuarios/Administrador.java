package br.uepb.model.usuarios;

import java.util.ArrayList;

import br.uepb.interfaces.DAO_Dependencia;
import br.uepb.interfaces.IFDependencia;

/**
 * Essa classe � utilizada como modelo para um objeto do tipo Administrador.
 * A classe cont�m os respectivos getters and setters de seus atributos.
 * A classe Administrador extende a classe Usu�rio, que cont�m os atributos e m�todos comuns a todos os usu�rios do sistema.
 * A classe Administrador implementa as interfaces que cont�m m�todos para manter o usu�rio e manter o acervo.
 * A classe Administrador cont�m um m�todo �nico, que serve para remover um aluno do sistema.
 * @author EquipeACL
 */
public class Administrador extends Usuario {

	
	
	// - - - - - - - - - - - - - - - - - - - - - MANTER DEPENDENCIAS - - - - - - - - - - - - - - - - - - - - - - -
	
	
	public boolean create(DAO_Dependencia itemDAO, IFDependencia item) {
		if(item.validaDependencia()){
			return itemDAO.createItemDependencia(item);
		}
		return false;
	}

	public boolean update(DAO_Dependencia itemDAO, IFDependencia item) {
		if(item.validaDependencia()){
			return itemDAO.updateItemDependencia(item);
		}
		return false;
	}

	public boolean remove(DAO_Dependencia itemDAO, IFDependencia item) {
		if(item.validaDependencia()){
			return itemDAO.removeItemDependencia(item);
		}
		return false;
	}

	public ArrayList<IFDependencia> search(DAO_Dependencia itemDAO, String nome) {
		return itemDAO.searchItemDependencia(nome);
	}
	


}

