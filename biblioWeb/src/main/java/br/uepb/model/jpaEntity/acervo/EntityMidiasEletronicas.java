package br.uepb.model.jpaEntity.acervo;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import br.uepb.interfaces.IFAcervo;
import br.uepb.model.acervo.MidiasEletronicas;
import br.uepb.model.enums.Tipo_midia;

/**
 * Classe utilizada para fazer o mapeamento da classe MidiasEletronicas para a base de dados.
 * A classe contém os respectivos getters and setters de seus atributos.
 * 
 * @author EquipeACL
 */
@Entity
@Table(name="midia")
public class EntityMidiasEletronicas extends EntityItemAcervo implements IFAcervo{
	
	@Enumerated(EnumType.STRING)
	private Tipo_midia tipo;
	
	/**
	 * Método construtor da classe
	 * Construtor vazio (utilizado para criar um objeto sem parametros definidos)
	 */
	public EntityMidiasEletronicas(){	
	}
	
	/**
	 * Método construtor da classe (utilizado para criar um objeto com par�metros definidos)
	 * @param titulo título da mídia eletronica
	 * @param tipo objeto Enum que define o tipo da midia eletronica
	 * @param data_gravacao data da gravação da  midia eletronica
	 */
	public EntityMidiasEletronicas(MidiasEletronicas midia) {
		if(midia!=null){
			setTitulo(midia.getTitulo());
			setTipo(midia.getTipo());
			setData_gravacao(midia.getData_gravacao());
		}
	}
	
	public Tipo_midia getTipo() {
		return tipo;
	}

	public void setTipo(Tipo_midia tipo) {
		this.tipo = tipo;
	}

	public Date getData_gravacao() {
		return getData();
	}

	public void setData_gravacao(Date data_gravacao) {
		setData(data_gravacao);
	}

	public boolean validaItem() {
		return true;
	}	

}
