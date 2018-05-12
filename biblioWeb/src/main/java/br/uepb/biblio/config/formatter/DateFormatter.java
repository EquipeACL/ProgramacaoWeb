package br.uepb.biblio.config.formatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.expression.ParseException;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Service;
@Service
public class DateFormatter implements Formatter<Date> {
	private static final Logger logger = LogManager.getLogger(DateFormatter.class);
    @Autowired
    private MessageSource messageSource;


    public DateFormatter() {
        super();
    }

    public java.sql.Date parse(final String text, final Locale locale) throws ParseException {
        final SimpleDateFormat dateFormat = createDateFormat(locale);
        try {
        	java.util.Date data = dateFormat.parse(text);
			return new java.sql.Date(data.getTime());
		} catch (java.text.ParseException e) {
			logger.error("Erro na conversão da data",e);
		}
        return null;
    }

    public String print(final Date object, final Locale locale) {
        final SimpleDateFormat dateFormat = createDateFormat(locale);
        return dateFormat.format(object);
    }

    private SimpleDateFormat createDateFormat(final Locale locale) {
        final String format = this.messageSource.getMessage("dd/MM/YYYY", null, locale);
        final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        dateFormat.setLenient(false);
        return dateFormat;
    }

}