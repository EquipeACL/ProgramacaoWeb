package br.uepb.model.formatter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import br.uepb.model.Grupo;

public class GrupoConverter implements Converter <String,Grupo> {
	
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
