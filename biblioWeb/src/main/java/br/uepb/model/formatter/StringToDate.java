package br.uepb.model.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;

public class StringToDate implements Converter<String, Date>{
	private static Logger logger = Logger.getLogger(StringToDate.class);
		
	@Override
	public Date convert(String string) {
		String temp[] = string.split("-");
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		Date data = new Date();
		try {
			//System.out.println("Convertendo string em Date: "+temp[2]+"/"+temp[1]+"/"+temp[0]);
			data = formater.parse(temp[2]+"/"+temp[1]+"/"+temp[0]);
			//System.out.println("Data convertida: "+formater.format(data));
		}catch(Exception e){
			logger.error("Erro em converter a data",e);
		}
        return data;
	}

}

