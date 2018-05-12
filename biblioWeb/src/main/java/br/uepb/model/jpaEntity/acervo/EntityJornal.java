package br.uepb.model.jpaEntity.acervo;

import javax.persistence.Entity;
import javax.persistence.Table;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.acervo.Jornal;

/**
 * Classe utilizada utilizada para fazer o mapeamento da classe Jornal para a base de dados. 
 * A classe contém os respectivos getters and setters de seus atributos.
 * 
 * @author EquipeACL
 */
@Entity
@Table(name="jornal")
public class EntityJornal extends EntityItemAcervo implements IFAcervo{
	
	/**
	 * Método construtor da classe
	 * Construtor vazio (utilizado para criar um objeto do tipo Jornal sem parametros definidos)
	 */
	public EntityJornal() {
		
	}
	
	/**
	 * Método construtor da classe(utilizado para criar um objeto do tipo EntityJornal com par�metros definidos)
	 * @param titulo titulo do jornal
	 * @param data  data do jornal
	 * @param edicao  edicao do jornal
	 */
	public EntityJornal(Jornal jornal) {
		if(jornal!=null){
			setTitulo(jornal.getTitulo());
			setData(jornal.getData());
			setEdicao(jornal.getEdicao());
		}
		
	}

	public boolean validaItem() {
		return true;
	}


}
