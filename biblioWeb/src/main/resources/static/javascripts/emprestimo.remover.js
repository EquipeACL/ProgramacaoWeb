$(function() {
	
	var modal = $('#modalRemoverEmprestimo');
	var botaoSim = modal.find('.js-remover-emprestimo-sim');
	var idRemover = modal.find("#idEmprestimoRemover");
	var urlRemover = modal.find("#urlEmprestimoRemover");
	
	botaoSim.on('click',remover);
	
	function remover(id,url){
		console.log('Vai remover');
		var id = idRemover.val();
		var url = urlRemover.val();
		console.log(url);
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
		console.log('Erro ao remover!');
	}

	function removidoSucesso(){
		var id = idRemover.val();
		var linha =$('#emprestimo'+id);
		linha.remove();
		modal.modal('hide');
		var aviso = $('.alert');
		aviso.remove();
	}
	
});

function setValorRemover(id,url){
	$("#idEmprestimoRemover").val(id);
	$("#urlEmprestimoRemover").val(url);
}


