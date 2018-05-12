$(function() {
	
	var modal = $('#modalCadastroRapidoTema');
	var botaoSalvar = modal.find('.js-modal-cadastro-tema-salvar-btn');
	var form = modal.find('form');
	form.on('submit', function(event) { event.preventDefault() });
	var url = form.attr('action');
	var inputNomeTema = $('#nomeTema');
	var comboAreaConhecimento = $('#area');
	var containerMensagemErro = $('.js-mensagem-cadastro-rapido-tema');
	
	
	modal.on('shown.bs.modal', onModalShow);
	modal.on('hide.bs.modal', onModalClose);
	botaoSalvar.on('click',onBotaoSalvarClick);
	
	
	function onModalShow() {
		inputNomeTema.focus();
	}
	
	function onModalClose() {
		inputNomeTema.val('');
		form.find('.form-group').removeClass('has-error');
	}
	function onBotaoSalvarClick(){
		var nomeTema = inputNomeTema.val().trim();
		var areaId = comboAreaConhecimento.val();
		
		
		$.ajax({
			url:url,
			method:'POST',
			contentType:'application/json',
			data: JSON.stringify({nome:nomeTema,areaconhecimento_id:areaId}),
			error:onErroSalvandoTema,
			success:onTemaSalvo
		});
	}
	
	function onErroSalvandoTema(obj){
		var mensagemErro = obj.responseText;
		containerMensagemErro.removeClass('hidden');
		containerMensagemErro.html('<span>'+mensagemErro +'</span>');
		form.find('.form-group').addClass('has-error');
		

	}
	function onTemaSalvo(tema){
		
		var comboTema =$('#tema');
		comboTema.append('<option value ='+tema.id+'>'+tema.nome+'</option>');
		comboTema.val(tema.id);
		modal.modal('hide');
		
	}
});