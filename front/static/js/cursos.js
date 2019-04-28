$(document).ready(function(){	
	
	aplicarListeners();
	
	aplicatListenerBtnSalvar();
	
});

var limparModal = function(){
	$('#id').val('');
	$('#nome').val('');
	$('#codigo').val('');
	$('#area').val('');
	$('#tipo').val('');
};

var aplicatListenerBtnSalvar = function(){
	$('#btn-salvar').on('click', function(){
		var url = 'curso';
		var dadosCursos = $('#form-curso :input[value!=""]').serialize();
		
		$.post(url, dadosCursos)
			.done(function(pagina){	
				$('#secao-cursos').html(pagina)
				aplicarListeners();
			})
			.fail(function(){
				alert('Erro ao salvar!');	
			})
			.always(function(){
				$('#modal-curso').modal('hide');
			});
	});
}

var aplicarListeners = function(){
	
	$('#modal-curso').on('hide.bs.modal', limparModal);
	
	$('.btn-editar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var url = 'curso/'+id;
		
		$.get(url)
			.success(function(curso){
				$('#id').val(curso.id);
				$('#nome').val(curso.nome);
				$('#codigo').val(curso.codigo);
				$('#area').val(curso.area);
				$('#tipo').val(curso.tipo);
				
				$('#modal-curso').modal('show');
			});
	});
	
	$('.btn-deletar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var cursos = $('#quantidade-cursos').text();
		var csrf = $('#csrf').val();
		
		$.ajax({
			url : "curso/"+id,
			type: 'DELETE',
			headers: {'X-CSRF-TOKEN': csrf},
		    success: function() {
		    	$('tr[data-id="'+id+'"]').remove();
		    	$('#quantidade-cursos').text(cursos - 1);
		    }
	    });
	});
}