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
	$('#orientadores option').attr('selected', false);
};

var aplicatListenerBtnSalvar = function(){
	$('#btn-salvar').on('click', function(){
		var url = 'tcc';
		var dadosTCCs = $('#form-tcc :input[value!=""]').serialize();
		
		$.post(url, dadosTCCs)
			.done(function(pagina){
				$('#secao-tccs').html(pagina)
				aplicarListeners();
			})
			.fail(function(){
				alert('Erro ao salvar!');
			})
			.always(function(){
				$('#modal-tcc').modal('hide');
			});
	});
}

var aplicarListeners = function(){
	
	$('#modal-tcc').on('hide.bs.modal', limparModal);
	
	$('.btn-editar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var url = 'tcc/'+id;
		
		$.get(url)
			.success(function(tcc){
				$('#id').val(tcc.id);
				$('#titulo').val(tcc.titulo);
				$('#local').val(tcc.local);
				
				tcc.autores.forEach(function(autor){
					var idautor = autor.id;
					$('#autores option[value='+idautor+']').attr('selected', true);
				});
				
				$('#tipo').val(tcc.tipo);
				
				tcc.orientadores.forEach(function(orientador){
					var idorientador = orientador.id;
					$('#orientadores option[value='+idorientador+']').attr('selected', true);
				});
				
				$('#modal-tcc').modal('show');
			});;
	});
	
	$('.btn-deletar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var tccs = $('#quantidade-tccs').text();
		var csrf = $('#csrf').val();
		
		$.ajax({
			url : "tcc/"+id,
			type: 'DELETE',
			headers: {'X-CSRF-TOKEN': csrf},
		    success: function() {
		    	$('tr[data-id="'+id+'"]').remove();
		    	$('#quantidade-tccs').text(tccs - 1);
		    }
	    });
	});	
}