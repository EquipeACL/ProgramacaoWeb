
$(function(){
	//Método pra formatar o input em numero decimal
	var reais = $('.js-decimal');
	reais.maskMoney({thousands:'.', decimal:','}); //

	//Método pra formatar o input em numero inteiro
	var plain = $('.js-plain');
	plain.maskMoney({precision:0,thousands:"."}); //
});