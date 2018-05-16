package br.uepb.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.uepb.validation.AtributoConfirmacao;

public class AtributoConfirmacaoValidator implements ConstraintValidator<AtributoConfirmacao,Object>{

	private String atributo;
	private String atributoConfirmacao;
	
	@Override
	public void initialize(AtributoConfirmacao constraintAnnotation) {
		
		this.atributo = constraintAnnotation.atributo();
		this.atributoConfirmacao = constraintAnnotation.atributoConfirmacao();
		
	}

	@Override
	public boolean isValid(Object object, ConstraintValidatorContext arg1) {
		Object valorAtributo;
		
		//Aula 18.4 11:48 
		
		return false;
	}
}
