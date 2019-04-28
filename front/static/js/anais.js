$(document).ready(function(){
	
	aplicarListeners();
	
	aplicatListenerBtnSalvar();
	
});

var limparModal = function(){
	$('#id').val('');
	$('#titulo').val('');
	$('#local').val('');
	$('#autores option').attr('selected', false);
	$('#tipo').val('');
	$('#nomecongreco').val('');
};

var aplicatListenerBtnSalvar = function(){
	$('#btn-salvar').on('click', function(){
		var url = 'anais';
		var dadosAnais = $('#form-anais :input[value!=""]').serialize();
		
		$.post(url, dadosAnais)
			.done(function(pagina){
				$('#secao-anais').html(pagina)
				aplicarListeners();
			})
			.fail(function(){
				alert('Erro ao salvar!');
			})
			.always(function(){
				$('#modal-anais').modal('hide');
			});
	});
}

var aplicarListeners = function(){
	
	$('#modal-anais').on('hide.bs.modal', limparModal);
	
	$('.btn-editar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var url = 'anais/'+id;
		
		$.get(url)
			.success(function(anais){
				$('#id').val(anais.id);
				$('#titulo').val(anais.titulo);
				$('#local').val(anais.local);
				
				anais.autores.forEach(function(autor){
					var id = autor.id;
					$('#autores option[value='+id+']').attr('selected', true);
				});
				
				$('#tipo').val(anais.tipo);
				$('#nomecongreco').val(anais.nomecongreco);
				
				$('#modal-anais').modal('show');
			});;
	});
	
	$('.btn-deletar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var anais = $('#quantidade-anais').text();
		var csrf = $('#csrf').val();
		
		$.ajax({
			url : "anais/"+id,
			type: 'DELETE',
			headers: {'X-CSRF-TOKEN': csrf},
		    success: function() {
		    	$('tr[data-id="'+id+'"]').remove();
		    	$('#quantidade-anais').text(anais - 1);
		    }
	    });
	});	
}