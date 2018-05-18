$(function() {
	
	var modal = $('#modalRemoverAutor');
	var botaoSim = modal.find('.js-remover-autor-sim');
	var idRemover = modal.find("#idRemover");
	var urlRemover = modal.find("#urlRemover");
	
	botaoSim.on('click',remover);
	
	function remover(id,url){
		var id = idRemover.val();
		var url = urlRemover.val();
		$.ajax({
			url:url,
			method:'DELETE',
			contentType:'application/json',
			data: JSON.stringify({id:id}),
			error:erroRemover,
			success:removidoSucesso
		});
	}
	
	function erroRemover(){
		//console.log('Erro ao remover!');
	}

	function removidoSucesso(){
		var id = idRemover.val();
		var linha =$('#autor'+id);
		linha.remove();
		modal.modal('hide');
		var aviso = $('.alert');
		aviso.remove();
	}
	
});

function setValorRemover(id,url){
	$("#idRemover").val(id);
	$("#urlRemover").val(url);
}


