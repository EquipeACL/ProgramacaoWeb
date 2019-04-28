$(document).ready(function(){
	
	aplicarListeners();
	
	aplicatListenerBtnSalvar();
	
});

var limparModal = function(){
	$('#id').val('');
	$('#titulo').val('');
	$('#tipo').val('');
	$('#data').val('');
};

var aplicatListenerBtnSalvar = function(){
	$('#btn-salvar').on('click', function(){
		var url = 'midia';
		var dadosMidias= $('#form-midia :input[value!=""]').serialize();
		
		$.post(url, dadosMidias)
			.done(function(pagina){	
				$('#secao-midias').html(pagina)
				aplicarListeners();
			})
			.fail(function(){
				alert('Erro ao salvar!');	
			})
			.always(function(){
				$('#modal-midia').modal('hide');
			});
	});
}

var aplicarListeners = function(){
	
	$('#modal-midia').on('hide.bs.modal', limparModal);
	
	$('.btn-editar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var url = 'midia/'+id;
		
		$.get(url)
			.success(function(midia){
				$('#id').val(midia.id);
				$('#titulo').val(midia.titulo);
				$('#tipo').val(midia.tipo);
				$('#data').val(midia.data);
				
				$('#modal-midia').modal('show');
			});
	});
	
	$('.btn-deletar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var midias = $('#quantidade-midias').text();
		var csrf = $('#csrf').val();
		
		$.ajax({
			url : "midia/"+id,
			type: 'DELETE',
			headers: {'X-CSRF-TOKEN': csrf},
		    success: function() {
		    	$('tr[data-id="'+id+'"]').remove();
		    	$('#quantidade-midias').text(midias - 1);
		    }
	    });
	});
}