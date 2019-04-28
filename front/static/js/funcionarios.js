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
	$('#usuario').val('');
	$('#nivel').val('');
};

var aplicatListenerBtnSalvar = function(){
	$('#btn-salvar').on('click', function(){
		var url = 'funcionario';
		var dadosFuncionarios = $('#form-funcionario :input[value!=""]').serialize();
		
		$.post(url, dadosFuncionarios)
			.done(function(pagina){	
				$('#secao-funcionarios').html(pagina)
				aplicarListeners();
			})
			.fail(function(){
				alert('Erro ao salvar!');	
			})
			.always(function(){
				$('#modal-funcionario').modal('hide');
			});
	});
}

var aplicarListeners = function(){
	
	$('#modal-funcionario').on('hide.bs.modal', limparModal);
	
	$('.btn-editar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var url = 'funcionario/'+id;
		
		$.get(url)
			.success(function(funcionario){
				$('#id').val(funcionario.id);
				$('#cpf').val(funcionario.cpf);
				$('#rg').val(funcionario.rg);
				$('#nome').val(funcionario.nome);
				$('#naturalidade').val(funcionario.naturalidade);
				$('#endereco').val(funcionario.endereco);
				$('#telefone').val(funcionario.telefone);
				$('#datanascimento').val(funcionario.datanascimento);
				$('#senha').val(funcionario.senha);
				$('#email').val(funcionario.email);
				$('#usuario').val(funcionario.usuario);
				$('#nivel').val(funcionario.nivel);
				
				$('#modal-funcionario').modal('show');
			});
	});
	
	$('.btn-deletar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var funcionarios = $('#quantidade-funcionarios').text();
		var csrf = $('#csrf').val();
		
		$.ajax({
			url : "funcionario/"+id,
			type: 'DELETE',
			headers: {'X-CSRF-TOKEN': csrf},
		    success: function() {
		    	$('tr[data-id="'+id+'"]').remove();
		    	$('#quantidade-funcionarios').text(funcionarios - 1);
		    }
	    });
	});
}