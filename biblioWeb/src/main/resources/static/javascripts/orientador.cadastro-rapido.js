$(function() {
	
	var modal = $('#modalCadastroRapidoOrientador');
	var botaoSalvar = modal.find('.js-modal-cadastro-orientador-salvar-btn');
	var form = modal.find('form');
	form.on('submit', function(event) { event.preventDefault() });
	var url = form.attr('action');
	var inputNomeOrientador = $('#nomeOrientador');
	
	modal.on('shown.bs.modal', onModalShow);
	modal.on('hide.bs.modal', onModalClose);
	
	function onModalShow() {
		inputNomeOrientador.focus();
	}
	
	function onModalClose() {
		inputNomeOrientador.val('');
	}
});