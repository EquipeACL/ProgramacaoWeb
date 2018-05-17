var Brewer = Brewer ||{}

Brewer.MaskMoney = (function(){
	function MaskMoney(){
		this.decimal =  $('.js-decimal');
		this.plain = $('.js-plain');
	}
	MaskMoney.prototype.enable = function(){
		this.decimal.maskMoney({thousands:'.', decimal:','}); //
		this.plain.maskMoney({precision:0,thousands:"."});
	}
	return MaskMoney;
}());

Brewer.MaskPhoneNumber = (function() {
	
	function MaskPhoneNumber() {
		this.inputPhoneNumber = $('.js-phone-number');
	}
	
	MaskPhoneNumber.prototype.enable = function() {
		var maskBehavior = function (val) {
		  return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
		};
		
		var options = {
		  onKeyPress: function(val, e, field, options) {
		      field.mask(maskBehavior.apply({}, arguments), options);
		    }
		};
		
		this.inputPhoneNumber.mask(maskBehavior, options);
	}
	
	return MaskPhoneNumber;
	
}());

Brewer.MaskCpf = (function(){

	function MaskCpf(){
		this.inputCpf = $('.js-cpf');
	}
	MaskCpf.prototype.enable = function(){
		this.inputCpf.mask('000.000.000-00', {reverse: true});
		
	}
	
	return MaskCpf;
}());

$(function(){

		var maskMoney = new Brewer.MaskMoney();
		maskMoney.enable();
		
		var maskPhoneNumber = new Brewer.MaskPhoneNumber();
		maskPhoneNumber.enable();
		
		var maskCpf = new Brewer.MaskCpf();
		maskCpf.enable();
		
		
});