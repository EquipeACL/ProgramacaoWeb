package br.uepb.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.apache.commons.beanutils.BeanUtils;

import br.uepb.validation.AtributoConfirmacao;

public class AtributoConfirmacaoValidator implements ConstraintValidator<AtributoConfirmacao, Object> {

	private String atributo;
	private String atributoConfirmacao;

	@Override
	public void initialize(AtributoConfirmacao constraintAnnotation) {

		this.atributo = constraintAnnotation.atributo();
		this.atributoConfirmacao = constraintAnnotation.atributoConfirmacao();

	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext context) {

		boolean valido = false;

		try {
			Object valorAtributo = BeanUtils.getProperty(object, this.atributo);
			Object valorAtributoConfirmacao = BeanUtils.getProperty(object, this.atributoConfirmacao);
			valido = ambosNulos(valorAtributo,valorAtributoConfirmacao) || saoIguais(valorAtributo,valorAtributoConfirmacao);	
			// Aula 18.4 11:48
		} catch (Exception e) {
			throw new RuntimeException("Erro recuperando valores dos atributos", e);
		}
		if(!valido) {
			context.disableDefaultConstraintViolation();
			String mensagem = context.getDefaultConstraintMessageTemplate();
			ConstraintViolationBuilder violationBuilder = context.buildConstraintViolationWithTemplate(mensagem);
			violationBuilder.addPropertyNode(atributoConfirmacao).addConstraintViolation();
		}

		return valido;
	}

	private boolean saoIguais(Object valorAtributo, Object valorAtributoConfirmacao) {
		return valorAtributo!= null && valorAtributo.equals(valorAtributoConfirmacao);
	}

	private boolean ambosNulos(Object valorAtributo, Object valorAtributoConfirmacao) {
		return valorAtributo == null & valorAtributoConfirmacao == null;
	}
}
