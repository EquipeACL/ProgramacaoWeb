package br.uepb.model.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;

/**
 * Essa classe é responsável por conter o método que forece a conversão de data para String
 * @author EquipeACL
 *
 */
public class DateToString implements Converter<Date, String>{
	private static Logger logger = Logger.getLogger(StringToDate.class);
	
	/**
	 * Método responsável por realizar a conversão de data para String
	 * @param data, que é a data a ser convertida
	 * @return dataString, que é a string com a data formatada
	 */
	@Override
	public String convert(Date data) {
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		String dataString = "";
		try {
			//System.out.println("Convertendo Date em string: "+data.toString());
			dataString = formater.format(data);
			//System.out.println("String convertida: "+dataString);
		}catch(Exception e){
			logger.error("Erro em converter a data",e);
		}
        return dataString;
	}

}
