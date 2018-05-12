$(function() {
	
	var modal = $('#modalCadastroRapidoAreaConhecimento');
	var botaoSalvar = modal.find('.js-modal-cadastro-areaConhecimento-salvar-btn');
	var form = modal.find('form');
	form.on('submit', function(event) { event.preventDefault() });
	var url = form.attr('action');
	var inputNomeArea = $('#nomeArea');
	var containerMessagemErro = $('.js-mensagem-cadastro-rapido-areaConhecimento');
	
	modal.on('shown.bs.modal', onModalShow);
	modal.on('hide.bs.modal', onModalClose);
	botaoSalvar.on('click',onBotaoSalvarClick);
	
	function onModalShow() {
		inputNomeArea.focus();
	}
	
	function onModalClose() {
		inputNomeArea.val('');
		form.find('form-group').removeClass('has-error');
	}
	function onBotaoSalvarClick(){
		var nome = inputNomeArea.val().trim();
		$.ajax({
			url:url,
			method:'POST',
			contentType:'application/json',
			data: JSON.stringify({nome:nome}),
			error:onErroSalvandoArea,
			success:onAreaSalvo
		});
	}
	
	function onErroSalvandoArea(obj){
		var mensagemErro = obj.responseText;
		containerMensagemErro.removeClass('hidden');
		containerMensagemErro.html('<span>'+mensagemErro +'</span>');
		form.find('.form-group').addClass('has-error');
		

	}
	function onAreaSalvo(area){
		var comboArea =$('#area');		
		comboArea.append('<option value ='+area.id+'>'+area.nome+'</option>');
		comboArea.val(area.id);
		modal.modal('hide');
		
	}
});