package br.uepb.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.apache.commons.beanutils.BeanUtils;

import br.uepb.validation.AtributoConfirmacao;

/**
 * Essa é a classe responsável por conter os métodos referentes a Validação do Atributo de Confirmação
 * @author EquipeACL
 *
 */
public class AtributoConfirmacaoValidator implements ConstraintValidator<AtributoConfirmacao, Object> {

	private String atributo;
	private String atributoConfirmacao;

	/**
	 * Esse é o metodo de inicialização do AtributoConfirmação
	 * @param constraintAnnotaton, que é o parâmetro oriundo da interface
	 */
	@Override
	public void initialize(AtributoConfirmacao constraintAnnotation) {

		this.atributo = constraintAnnotation.atributo();
		this.atributoConfirmacao = constraintAnnotation.atributoConfirmacao();

	}

	/**
	 * Esse é o método responsável por validar um objeto dado um contexto
	 * @param object, que é o objeto a ser validado
	 * @param context, que é o contexto de validação do objeto
	 * @return true or false, dependendo do sucesso ou falha da validação.
	 */
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

	/**
	 * Método para verificar se dois objetos são iguais
	 * @param valorAtributo, objeto a ser comparado
	 * @param valorAtributoConfirmacao, objeto ou ser comparado
	 * @return true or false, dependendo do sucesso ou falha da validaçãoo
	 */
	private boolean saoIguais(Object valorAtributo, Object valorAtributoConfirmacao) {
		return valorAtributo!= null && valorAtributo.equals(valorAtributoConfirmacao);
	}

	/**
	 * Método para verificar se dois objetos são nulos
	 * @param valorAtributo, objeto a ser comparado
	 * @param valorAtributoConfirmacao, objeto ou ser comparado
	 * @return true or false, dependendo do sucesso ou falha da validaçãoo
	 */
	private boolean ambosNulos(Object valorAtributo, Object valorAtributoConfirmacao) {
		return valorAtributo == null & valorAtributoConfirmacao == null;
	}
}
