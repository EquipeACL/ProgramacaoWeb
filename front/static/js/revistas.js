$(document).ready(function(){	
	
	aplicarListeners();
	
	aplicatListenerBtnSalvar();
	
});

var limparModal = function(){
	$('#id').val('');
	$('#titulo').val('');
	$('#edicao').val('');
	$('#editora option').attr('selected', false);
	$('#numpaginas').val('');
	$('#datapublicacao').val('');
};

var aplicatListenerBtnSalvar = function(){
	$('#btn-salvar').on('click', function(){
		var url = 'revista';
		var dadosRevistas = $('#form-revista :input[value!=""]').serialize();
		
		$.post(url, dadosRevistas)
			.done(function(pagina){	
				$('#secao-revistas').html(pagina)
				aplicarListeners();
			})
			.fail(function(){
				alert('Erro ao salvar!');	
			})
			.always(function(){
				$('#modal-revista').modal('hide');
			});
	});
}

var aplicarListeners = function(){
	
	$('#modal-revista').on('hide.bs.modal', limparModal);
	
	$('.btn-editar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var url = 'revista/'+id;
		
		$.get(url)
			.success(function(revista){
				$('#id').val(revista.id);
				$('#titulo').val(revista.titulo);
				$('#edicao').val(revista.edicao);

				var id = revista.editora.id;
				$('#editora option[value='+id+']').attr('selected', true);
				
				$('#numpaginas').val(revista.numpaginas);
				$('#datapublicacao').val(revista.datapublicacao);
				
				$('#modal-revista').modal('show');
			});
	});
	
	$('.btn-deletar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var revistas = $('#quantidade-revistas').text();
		var csrf = $('#csrf').val();
		
		$.ajax({
			url : "revista/"+id,
			type: 'DELETE',
			headers: {'X-CSRF-TOKEN': csrf},
		    success: function() {
		    	$('tr[data-id="'+id+'"]').remove();
		    	$('#quantidade-revistas').text(revistas - 1);
		    }
	    });
	});
}