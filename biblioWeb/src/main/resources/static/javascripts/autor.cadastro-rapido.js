$(function() {
	
	var modal = $('#modalCadastroRapidoAutor');
	var botaoSalvar = modal.find('.js-modal-cadastro-autor-salvar-btn');
	var form = modal.find('form');
	form.on('submit', function(event) { event.preventDefault() });
	var url = form.attr('action');
	var inputNomeAutor = $('#nome');
	
	modal.on('shown.bs.modal', onModalShow);
	modal.on('hide.bs.modal', onModalClose);
	
	function onModalShow() {
		inputNomeAutor.focus();
	}
	
	function onModalClose() {
		inputNomeAutor.val('');
	}
});