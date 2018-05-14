package br.uepb.model.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;

public class DateToString implements Converter<Date, String>{
	private static Logger logger = Logger.getLogger(StringToDate.class);
	
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
