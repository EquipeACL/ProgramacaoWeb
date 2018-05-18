function selecionouEstado(sigla,url){
	$.ajax({
		url:url,
		method:'POST',
		contentType:'application/json',
		data: JSON.stringify({sigla:sigla}),
		error:erroEstado,
		success:selecionarCidade
	});
	
	function selecionarCidade(cidades){
		var comboCidade = $("#cidade");
		comboCidade.html('<option value ='+0+'>Selecione a Cidade</option>');
		
		for ( var i in cidades) {
			comboCidade.append('<option value ='+cidades[i].id+'>'+cidades[i].nome+'</option>');			
		}
		
	}
	function erroEstado(){
		console.log('Erro em carregar as cidades!')
	}
	
}
function selecionouArea(area,url){
	$.ajax({
		url:url,
		method:'POST',
		contentType:'application/json',
		data: JSON.stringify({id:area}),
		error:erroArea,
		success:selecionarTemas
	});
	
	function selecionarTemas(temas){
		var comboTema = $("#tema");
		comboTema.html('<option value ='+0+'>Selecione o tema</option>');
		
		for ( var i in temas) {
			comboTema.append('<option value ='+temas[i].id+'>'+temas[i].nome+'</option>');			
		}
		
	}
	function erroArea(){
		console.log('Erro em carregar os temas!')
	}
	
}



$('.form').find('input, textarea').on('keyup blur focus', function (e) {
  
  var $this = $(this),
      label = $this.prev('label');

	  if (e.type === 'keyup') {
			if ($this.val() === '') {
          label.removeClass('active highlight');
        } else {
          label.addClass('active highlight');
        }
    } else if (e.type === 'blur') {
    	if( $this.val() === '' ) {
    		label.removeClass('active highlight'); 
			} else {
		    label.removeClass('highlight');   
			}   
    } else if (e.type === 'focus') {
      
      if( $this.val() === '' ) {
    		label.removeClass('highlight'); 
			} 
      else if( $this.val() !== '' ) {
		    label.addClass('highlight');
			}
    }

});

$('.tab a').on('click', function (e) {
  
  e.preventDefault();
  
  $(this).parent().addClass('active');
  $(this).parent().siblings().removeClass('active');
  
  target = $(this).attr('href');

  $('.tab-content > div').not(target).hide();
  
  $(target).fadeIn(600);
  
});