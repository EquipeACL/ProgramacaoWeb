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
	$('#isbn').val('');
	$('#autores option').attr('selected', false);
};

var aplicatListenerBtnSalvar = function(){
	$('#btn-salvar').on('click', function(){
		var url = 'livro';
		var dadosLivros = $('#form-livro :input[value!=""]').serialize();
		
		$.post(url, dadosLivros)
			.done(function(pagina){	
				$('#secao-livros').html(pagina)
				aplicarListeners();
			})
			.fail(function(){
				alert('Erro ao salvar!');	
			})
			.always(function(){
				$('#modal-livro').modal('hide');
			});
	});
}

var aplicarListeners = function(){
	
	$('#modal-livro').on('hide.bs.modal', limparModal);
	
	$('.btn-editar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var url = 'livro/'+id;
		
		$.get(url)
			.success(function(livro){
				$('#id').val(livro.id);
				$('#titulo').val(livro.titulo);
				$('#edicao').val(livro.edicao);
				
				var idlivro = livro.editora.id;
				$('#editora option[value='+idlivro+']').attr('selected', true);
				
				$('#numpaginas').val(livro.numpaginas);
				$('#isbn').val(livro.isbn);
				
				livro.autores.forEach(function(autor){
					var idautor = autor.id;
					$('#autores option[value='+idautor+']').attr('selected', true);
				});
				
				$('#modal-livro').modal('show');
			});
	});
	
	$('.btn-deletar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var livros = $('#quantidade-livros').text();
		var csrf = $('#csrf').val();
		
		$.ajax({
			url : "livro/"+id,
			type: 'DELETE',
			headers: {'X-CSRF-TOKEN': csrf},
		    success: function() {
		    	$('tr[data-id="'+id+'"]').remove();
		    	$('#quantidade-livros').text(livros - 1);
		    }
	    });
	});
}