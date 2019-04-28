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
	$('#data').val('');
};

var aplicatListenerBtnSalvar = function(){
	$('#btn-salvar').on('click', function(){
		var url = 'jornal';
		var dadosJornais = $('#form-jornal :input[value!=""]').serialize();
		
		$.post(url, dadosJornais)
			.done(function(pagina){	
				$('#secao-jonais').html(pagina)
				aplicarListeners();
			})
			.fail(function(){
				alert('Erro ao salvar!');	
			})
			.always(function(){
				$('#modal-jornal').modal('hide');
			});
	});
}

var aplicarListeners = function(){
	
	$('#modal-jornal').on('hide.bs.modal', limparModal);
	
	$('.btn-editar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var url = 'jornal/'+id;
		
		$.get(url)
			.success(function(jornal){
				$('#id').val(jornal.id);
				$('#titulo').val(jornal.titulo);
				$('#edicao').val(jornal.edicao);
				
				var id = jornal.editora.id;
				$('#editora option[value='+id+']').attr('selected', true);
				
				$('#numpaginas').val(jornal.numpaginas);
				$('#data').val(jornal.data);
				
				$('#modal-jornal').modal('show');
			});
	});
	
	$('.btn-deletar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var jornais = $('#quantidade-jornais').text();
		var csrf = $('#csrf').val();
		
		$.ajax({
			url : "jornal/"+id,
			type: 'DELETE',
			headers: {'X-CSRF-TOKEN': csrf},
		    success: function() {
		    	$('tr[data-id="'+id+'"]').remove();
		    	$('#quantidade-jornais').text(jornais - 1);
		    }
	    });
	});
}