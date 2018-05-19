$(function() {
	
	var modal = $('#modalCadastroRapidoEditora');
	var botaoSalvar = modal.find('.js-modal-cadastro-editora-salvar-btn');
	var form = modal.find('form');
	form.on('submit', function(event) { event.preventDefault() });
	var url = form.attr('action');
	var inputNomeEditora = $('#nomeEditora');
	var containerMensagemErro = $('.js-mensagem-cadastro-rapido-editora');
	
	modal.on('shown.bs.modal', onModalShow);
	modal.on('hide.bs.modal', onModalClose);
	botaoSalvar.on('click',onBotaoSalvarClick);
	
	function onModalShow() {
		inputNomeEditora.focus();
	}
	
	function onModalClose() {
		inputNomeEditora.val('');
		form.find('form-group').removeClass('.has-error');
	}
	
	function onBotaoSalvarClick(){
		var nome = inputNomeEditora.val().trim();
		console.log("URLEditora: "+url);
		$.ajax({
			url:url,
			method:'POST',
			contentType:'application/json',
			data: JSON.stringify({nome:nome}),
			error:onErroSalvandoEditora,
			success:onEditoraSalvo
		});
	}
	
	function onErroSalvandoEditora(obj){
		var mensagemErro = obj.responseText;
		containerMensagemErro.removeClass('hidden');
		containerMensagemErro.html('<span>'+mensagemErro +'</span>');
		form.find('.form-group').addClass('has-error');
		

	}
	function onEditoraSalvo(editora){
		
		var comboEditora =$('#editora');
		comboEditora.append('<option value ='+editora.id+'>'+editora.nome+'</option>');
		comboEditora.val(editora.id);
		modal.modal('hide');
		
	}
});