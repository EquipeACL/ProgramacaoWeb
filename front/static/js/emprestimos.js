$(document).ready(function(){	
	
	aplicarListeners();
	
	aplicatListenerBtnSalvar();
	
});

var aplicatListenerBtnSalvar = function(){
	$('#btn-salvar').on('click', function(){
		var url = 'emprestimo';
		var dadosEmprestimos = $('#form-emprestimo').serialize();
		
		$.post(url, dadosEmprestimos)
			.done(function(pagina){	
				$('#secao-emprestimos').html(pagina)
				aplicarListeners();
			})
			.fail(function(){
				alert('Erro ao salvar!');	
			})
			.always(function(){
				$('#modal-emprestimo').modal('hide');
			});
	});
}

var aplicarListeners = function(){
	
	$("#anais").hide();
    $("#anais-l").hide();
    $("#jornal").hide();
    $("#jornal-l").hide();
    $("#livro").hide();
    $("#livro-l").hide();
    $("#midia").hide();
    $("#midia-l").hide();
    $("#revista").hide();
    $("#revista-l").hide();
    $("#tcc").hide();
    $("#tcc-l").hide();
	
	$('select[name="emprestimo"]').on('change', function(){
	    var emprestimo = this.value;
	    
	    if (emprestimo === 'anais') {
	        $("#anais").show();
	        $("#anais-l").show();
	        $("#jornal").hide();
	        $("#jornal-l").hide();
	        $("#livro").hide();
	        $("#livro-l").hide();
	        $("#midia").hide();
	        $("#midia-l").hide();
	        $("#revista").hide();
	        $("#revista-l").hide();
	        $("#tcc").hide();
	        $("#tcc-l").hide();
	        
	    } else if(emprestimo === 'jornal'){
	    	$("#anais").hide();
	    	$("#anais-l").hide();
	        $("#jornal").show();
	        $("#jornal-l").show();
	        $("#livro").hide();
	        $("#livro-l").hide();
	        $("#midia").hide();
	        $("#midia-l").hide();
	        $("#revista").hide();
	        $("#revista-l").hide();
	        $("#tcc").hide();
	        $("#tcc-l").hide();
	        
	    } else if(emprestimo === 'livro'){
	    	$("#anais").hide();
	    	$("#anais-l").hide();
	        $("#jornal").hide();
	        $("#jornal-l").hide();
	        $("#livro").show();
	        $("#livro-l").show();
	        $("#midia").hide();
	        $("#midia-l").hide();
	        $("#revista").hide();
	        $("#revista-l").hide();
	        $("#tcc").hide();
	        $("#tcc-l").hide();
	        
	    } else if(emprestimo === 'midia'){
	    	$("#anais").hide();
	    	$("#anais-l").hide();
	        $("#jornal").hide();
	        $("#jornal-l").hide();
	        $("#livro").hide();
	        $("#livro-l").hide();
	        $("#midia").show();
	        $("#midia-l").show();
	        $("#revista").hide();
	        $("#revista-l").hide();
	        $("#tcc").hide();
	        $("#tcc-l").hide();
	        
	    } else if(emprestimo === 'revista'){
	    	$("#anais").hide();
	    	$("#anais-l").hide();
	        $("#jornal").hide();
	        $("#jornal-l").hide();
	        $("#livro").hide();
	        $("#livro-l").hide();
	        $("#midia").hide();
	        $("#midia-l").hide();
	        $("#revista").show();
	        $("#revista-l").show();
	        $("#tcc").hide();
	        $("#tcc-l").hide();
	        
	    } else {
	    	$("#anais").hide();
	    	$("#anais-l").hide();
	        $("#jornal").hide();
	        $("#jornal-l").hide();
	        $("#livro").hide();
	        $("#livro-l").hide();
	        $("#midia").hide();
	        $("#midia-l").hide();
	        $("#revista").hide();
	        $("#revista-l").hide();
	        $("#tcc").show();
	        $("#tcc-l").show();
	    }
	});
	
	$('#dataemprestimo')[0].valueAsDate = new Date();
	
	$('#dataemprestimo').change(function() {
	      var date = this.valueAsDate;
	      date.setDate(date.getDate() + 15);
	      $('#datadevolucao')[0].valueAsDate = date;
	    });

	$('#dataemprestimo').change();
	
	$('.btn-devolver').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var emprestimos = $('#quantidade-emprestimo').text();
		var csrf = $('#csrf').val();
		
		$.ajax({
			url : "emprestimo/"+id,
			type: 'DELETE',
			headers: {'X-CSRF-TOKEN': csrf},
		    success: function() {
		    	$('tr[data-id="'+id+'"]').remove();
		    	$('#quantidade-emprestimo').text(emprestimos - 1);
		    }
	    });
	});
	
	$('.btn-renovar').on('click', function(){
		var id = $(this).parents('tr').data('id');
		var url = 'emprestimo/'+id;
		
		$.get(url)
			.success(function(emprestimo){
				$('#id').val(emprestimo.id);
				$('#aluno').val(emprestimo.aluno);
				$('#anais').val(emprestimo.anais);
				$('#jornal').val(emprestimo.jornal);
				$('#livro').val(emprestimo.livro);
				$('#midia').val(emprestimo.midia);
				$('#revista').val(emprestimo.revista);
				$('#tcc').val(emprestimo.tcc);
				
				$('#dataemprestimo')[0].valueAsDate = new Date();
				
				$('#dataemprestimo').change(function() {
				      var date = this.valueAsDate;
				      date.setDate(date.getDate() + 15);
				      $('#datadevolucao')[0].valueAsDate = date;
				    });

				$('#dataemprestimo').change();
				
				$('#modal-renovar').modal('show');
			});
	});
	
	var doc = new jsPDF();
	var specialElementHandlers = {
	    '#editor': function (element, renderer) {
	        return true;
	    }
	};

	$('#btn-relatorio').click(function () {   
	    doc.fromHTML($('#secao-emprestimos').html(), 15, 15, {
	        'width': 170,
	            'elementHandlers': specialElementHandlers
	    });
	    doc.save('emprestimos.pdf');
	});
}