$(function() {
	
	var modal = $('#modalCadastroRapidoOrientador');
	var botaoSalvar = modal.find('.js-modal-cadastro-orientador-salvar-btn');
	var form = modal.find('form');
	form.on('submit', function(event) { event.preventDefault() });
	var url = form.attr('action');
	var inputNomeOrientador = $('#nomeOrientador');
	var comboFormacaoOrientador = $('#formacaoOrientador');
	var containerMensagemErro = $('.js-mensagem-cadastro-rapido-orientador');
	
	
	modal.on('shown.bs.modal', onModalShow);
	modal.on('hide.bs.modal', onModalClose);
	botaoSalvar.on('click',onBotaoSalvarClick);
	
	
	function onModalShow() {
		inputNomeOrientador.focus();
	}
	
	function onModalClose() {
		inputNomeOrientador.val('');
		form.find('.form-group').removeClass('has-error');
	}
	function onBotaoSalvarClick(){
		var nome = inputNomeOrientador.val().trim();
		var formacao = comboFormacaoOrientador.val();
		
		//console.log("nomeOrientador: ",)
		$.ajax({
			url:url,
			method:'POST',
			contentType:'application/json',
			data: JSON.stringify({nome:nome,formacao:formacao}),
			error:onErroSalvandoOrientador,
			success:onOrientadorSalvo
		});
	}
	
	function onErroSalvandoOrientador(obj){
		var mensagemErro = obj.responseText;
		containerMensagemErro.removeClass('hidden');
		containerMensagemErro.html('<span>'+mensagemErro +'</span>');
		form.find('.form-group').addClass('has-error');
		

	}
	function onOrientadorSalvo(orientador){
		
		var comboOrientador =$('#orientador');
		comboOrientador.append('<option value ='+orientador.id+'>'+orientador.nome+'</option>');
		comboOrientador.val(orientador.id);
		modal.modal('hide');
		
	}
});