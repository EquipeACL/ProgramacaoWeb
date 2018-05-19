package br.uepb.model.formatter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.uepb.model.Grupo;

/**
 * Essa classe é responsável por conter o método que atribui um código a um grupo
 * @author EquipeACL
 *
 */
public class GrupoConverter implements Converter <String,Grupo> {
	
	/**
	 * Método responsável por atribuir um código a um grupo
	 * @param codigo, que é o código a ser atribuido a um grupo
	 * @return grupo or null, dependendo da validação do código
	 */
	@Override
	public Grupo convert(String codigo) {
		if(!StringUtils.isEmpty(codigo)) {
			Grupo grupo = new Grupo();
			grupo.setId(Integer.valueOf(codigo));
			return grupo;
		}
		return null;
	}

}
