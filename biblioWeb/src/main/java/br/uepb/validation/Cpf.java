//package br.uepb.validation;
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//import javax.validation.Constraint;
//import javax.validation.Payload;
//
//@Constraint(validatedBy = CpfConstraintValidator.class)
//@Target({ElementType.METHOD,ElementType.FIELD})
//@Retention(RetentionPolicy.RUNTIME)
//public @interface Cpf {
//
//	public String message () default "Cpf Inválido";
//	
//	
//	public Class <?>[] groups() default{};
//	
//	public Class <? extends Payload>[] payload() default {};
//}
