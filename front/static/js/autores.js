$(document).ready(function(){
	
	aplicarListeners();
	
	aplicatListenerBtnSalvar();
	
});

var limparModal = function(){
	$('#id').val('');
	$('#nome').val('');
};

var aplicatListenerBtnSalvar = function(){
	$('#btn-salvar').on('click', function(){
		var url = 'autor';
		var dadosAutores= $('#form-autor :input[value!=""]').serialize();
		
		$.post(url, dadosAutores)
			.done(function(pagina){	
				$('#secao-autores').html(pagina)
				aplicarListeners();
			})
			.fail(function(){
				alert('Erro ao salvar!');	
			})
			.always(function(){
				$('#modal-autor').modal('hide');
			});
	});
}

var aplicarListeners = function(){
	
	$('#modal-autor').on('hide.bs.modal', limparModal);
	
	$('.btn-editar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var url = 'autor/'+id;
		
		$.get(url)
			.success(function(autor){
				$('#id').val(autor.id);
				$('#nome').val(autor.nome);
				
				$('#modal-autor').modal('show');
			});
	});
	
	$('.btn-deletar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var autores = $('#quantidade-autores').text();
		var csrf = $('#csrf').val();
		
		$.ajax({
			url : "autor/"+id,
			type: 'DELETE',
			headers: {'X-CSRF-TOKEN': csrf},
		    success: function() {
		    	$('tr[data-id="'+id+'"]').remove();
		    	$('#quantidade-autores').text(autores - 1);
		    }
	    });
	});
}