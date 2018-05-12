$(function() {
	
	var modal = $('#modalCadastroRapidoAutor');
	var botaoSalvar = modal.find('.js-modal-cadastro-autor-salvar-btn');
	var form = modal.find('form');
	form.on('submit', function(event) { event.preventDefault() });
	var url = form.attr('action');
	var inputNomeAutor = $('#nomeAutor');
	var containerMensagemErro = $('.js-mensagem-cadastro-rapido-autor');
	
	
	modal.on('shown.bs.modal', onModalShow);
	modal.on('hide.bs.modal', onModalClose);
	botaoSalvar.on('click',onBotaoSalvarClick);
	
	
	function onModalShow() {
		inputNomeAutor.focus();
	}
	
	function onModalClose() {
		inputNomeAutor.val('');
		form.find('.form-group').removeClass('has-error');
	}
	function onBotaoSalvarClick(){
		var nome = inputNomeAutor.val().trim();
		$.ajax({
			url:url,
			method:'POST',
			contentType:'application/json',
			data: JSON.stringify({nome:nome}),
			error:onErroSalvandoAutor,
			success:onAutorSalvo
		});
	}
	
	function onErroSalvandoAutor(obj){
		var mensagemErro = obj.responseText;
		containerMensagemErro.removeClass('hidden');
		containerMensagemErro.html('<span>'+mensagemErro +'</span>');
		form.find('.form-group').addClass('has-error');
		

	}
	function onAutorSalvo(autor){
		var comboAutor =$('#autor');
		comboAutor.append('<option value ='+autor.id+'>'+autor.nome+'</option>');
		comboAutor.val(autor.id);
		modal.modal('hide');
		
	}
});