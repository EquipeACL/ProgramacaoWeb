$(document).ready(function(){	
	
	aplicarListeners();
	
	aplicatListenerBtnSalvar();
	
});

var limparModal = function(){
	$('#id').val('');
	$('#cpf').val('');
	$('#rg').val('');
	$('#nome').val('');
	$('#naturalidade').val('');
	$('#endereco').val('');
	$('#telefone').val('');
	$('#datanascimento').val('');
	$('#senha').val('');
	$('#email').val('');
	$('#matricula').val('');
	$('#nomemae').val('');
	$('#curso option').attr('selected', false);
	$('#ano').val('');
};

var aplicatListenerBtnSalvar = function(){
	$('#btn-salvar').on('click', function(){
		var url = 'aluno';
		var dadosAluno = $('#form-aluno :input[value!=""]').serialize();
		
		$.post(url, dadosAluno)
			.done(function(pagina){
				$('#secao-alunos').html(pagina)
				aplicarListeners();
			})
			.fail(function(){
				alert('Erro ao salvar!');	
			})
			.always(function(){
				$('#modal-aluno').modal('hide');
			});
	});
}

var aplicarListeners = function(){
	
	$('#modal-aluno').on('hide.bs.modal', limparModal);
	
	$('.btn-editar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var url = 'aluno/'+id;
		
		$.get(url)
			.success(function(aluno){
				$('#id').val(aluno.id);
				$('#cpf').val(aluno.cpf);
				$('#rg').val(aluno.rg);
				$('#nome').val(aluno.nome);
				$('#naturalidade').val(aluno.naturalidade);
				$('#endereco').val(aluno.endereco);
				$('#telefone').val(aluno.telefone);
				$('#datanascimento').val(aluno.datanascimento);
				$('#senha').val(aluno.senha);
				$('#email').val(aluno.email);
				$('#matricula').val(aluno.matricula);
				$('#nomemae').val(aluno.nomemae);
				
				var id = aluno.curso.id;
				$('#curso option[value='+id+']').attr('selected', true);
				
				$('#ano').val(aluno.ano);
				
				$('#modal-aluno').modal('show');
			});
	});
	
	$('.btn-deletar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var alunos = $('#quantidade-alunos').text();
		var csrf = $('#csrf').val();
		
		$.ajax({
			url : "aluno/"+id,
			type: 'DELETE',
			headers: {'X-CSRF-TOKEN': csrf},
		    success: function() {
		    	$('tr[data-id="'+id+'"]').remove();
		    	$('#quantidade-alunos').text(alunos - 1);
		    }
	    });
	});
}