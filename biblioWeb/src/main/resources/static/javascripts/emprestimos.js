var listaLivros;
var listaJornais;
var listaAnais;
var listaRevistas;
var listaTccs;
var listaMidias;
var tipoDeItem = "";
var seletorLivro = $('#seletorLivro');
var seletorAnal = $('#seletorAnal');
var seletorJornal = $('#seletorJornal');
var seletorRevista = $('#seletorRevista');
var seletorTcc = $('#seletorTcc');
var seletorMidia = $('#seletorMidia');
seletorLivro.hide();
seletorAnal.hide();
seletorJornal.hide();
seletorRevista.hide();
seletorTcc.hide();
seletorMidia.hide();
function carregar(contexto){
	buscarLivrosAll(contexto+'livros/buscarAll');
	buscarJornaisAll(contexto+'jornais/buscarAll');
	buscarAnaisAll(contexto+'anais/buscarAll');
	buscarRevistasAll(contexto+'revistas/buscarAll');
	buscarTccsAll(contexto+'tccs/buscarAll');
	buscarMidiasAll(contexto+'midias/buscarAll');
}
	
	function buscarLivrosAll(url){
		
		$.ajax({
			url:url,
			method:'POST',
			contentType:'application/json',
			error:erroLivrosAll,
			success:buscaLivrosSucessso
		});
	}
	
	function erroLivrosAll(){
		console.log('Erro ao carregar os livros!');
	}

	function buscaLivrosSucessso(livros){
		listaLivros = livros;	
	}
	
	function buscarJornaisAll(url){
		
		$.ajax({
			url:url,
			method:'POST',
			contentType:'application/json',
			error:erroJornaisAll,
			success:buscaJornaisSucessso
		});
	}
	
	function erroJornaisAll(){
		console.log('Erro ao carregar os jornais!');
	}

	function buscaJornaisSucessso(jornais){
		listaJornais = jornais;	
	}
	
	function buscarAnaisAll(url){
		
		$.ajax({
			url:url,
			method:'POST',
			contentType:'application/json',
			error:erroAnaisAll,
			success:buscaAnaisSucessso
		});
	}
	
	function erroAnaisAll(){
		console.log('Erro ao carregar os anais!');
	}

	function buscaAnaisSucessso(anais){
		listaAnais = anais;	
	}
	
	function buscarRevistasAll(url){
		
		$.ajax({
			url:url,
			method:'POST',
			contentType:'application/json',
			error:erroRevistaAll,
			success:buscaRevistaSucessso
		});
	}
	
	function erroRevistaAll(){
		console.log('Erro ao carregar as revistas!');
	}

	function buscaRevistaSucessso(revistas){
		listaRevistas = revistas;	
	}
	
	function buscarTccsAll(url){
		
		$.ajax({
			url:url,
			method:'POST',
			contentType:'application/json',
			error:erroTccAll,
			success:buscaTccSucessso
		});
	}
	
	function erroTccAll(){
		console.log('Erro ao carregar os tccs!');
	}

	function buscaTccSucessso(tccs){
		listaTccs = tccs;	
	}
	
	function buscarMidiasAll(url){
		
		$.ajax({
			url:url,
			method:'POST',
			contentType:'application/json',
			error:erroMidiasAll,
			success:buscaMidiasSucessso
		});
	}
	
	function erroMidiasAll(){
		console.log('Erro ao carregar as midias!');
	}

	function buscaMidiasSucessso(midias){
		listaMidias = midias;	
	}
	

function selecionouCategoria(){
		
	var selecionado = document.getElementsByName("itemAcervo");
	for (var i = 0, length = selecionado.length; i < length; i++)
	{
		 if (selecionado[i].checked){	  
			 switch(selecionado[i].value){
			 	case 'livros':
			 		selecionouLivros();
			 		break;
			 	case 'jornais':
			 		selecionouJornais();
			 		break;
			 	case 'anais':
			 		selecionouAnais();
			 		break;
			 	case 'revistas':
			 		selecionouRevistas();
			 		break;
			 	case 'tccs':
			 		selecionouTccs();
			 		break;
			 	case 'midias':
			 		selecionouMidias();
			 		break;
			 }
		 }
	}
}
function selecionouLivros(){
	//console.log('Selecionou livros');
	seletorLivro.show();
	seletorAnal.hide();
	seletorJornal.hide();
	seletorRevista.hide();
	seletorTcc.hide();
	seletorMidia.hide();
	var combobox = $('#comboLivro');
	combobox.html('<option value="0">Selecione o item</option>');
	for(var i in listaLivros){
		combobox.append('<option value ='+listaLivros[i].id+'>'+listaLivros[i].titulo+'</option>');	
	}
	tipoDeItem = "Livro";
}
function selecionouJornais(){
	seletorLivro.hide();
	seletorAnal.hide();
	seletorJornal.show();
	seletorRevista.hide();
	seletorTcc.hide();
	seletorMidia.hide();
	//console.log('Selecionou jornias');
	var combobox =$('#comboJornal');
	combobox.html('<option value="0">Selecione o item</option>');
	for(var i in listaJornais){
		combobox.append('<option value ='+listaJornais[i].id+'>'+listaJornais[i].titulo+'</option>');	
	}
	tipoDeItem = "Jornal";
}
function selecionouAnais(){
	seletorLivro.hide();
	seletorAnal.show();
	seletorJornal.hide();
	seletorRevista.hide();
	seletorTcc.hide();
	seletorMidia.hide();
	//console.log('Selecionou anais');
	var combobox =$('#comboAnal');
	combobox.html('<option value="0">Selecione o item</option>');
	for(var i in listaAnais){
		combobox.append('<option value ='+listaAnais[i].id+'>'+listaAnais[i].titulo+'</option>');	
	}	
	tipoDeItem = "Anal";
}
function selecionouRevistas(){
	seletorLivro.hide();
	seletorAnal.hide();
	seletorJornal.hide();
	seletorRevista.show();
	seletorTcc.hide();
	seletorMidia.hide();
	//console.log('Selecionou revistas');
	var combobox =$('#comboRevista');
	combobox.html('<option value="0">Selecione o item</option>');
	for(var i in listaRevistas){
		combobox.append('<option value ='+listaRevistas[i].id+'>'+listaRevistas[i].titulo+'</option>');	
	}
	tipoDeItem = "Revista";
}
function selecionouTccs(){
	seletorLivro.hide();
	seletorAnal.hide();
	seletorJornal.hide();
	seletorRevista.hide();
	seletorTcc.show();
	seletorMidia.hide();
	//console.log('Selecionou tccs');
	var combobox =$('#comboTcc');
	combobox.html('<option value="0">Selecione o item</option>');
	for(var i in listaTccs){
		combobox.append('<option value ='+listaTccs[i].id+'>'+listaTccs[i].titulo+'</option>');	
	}
	tipoDeItem = "Tcc";
}
function selecionouMidias(){
	seletorLivro.hide();
	seletorAnal.hide();
	seletorJornal.hide();
	seletorRevista.hide();
	seletorTcc.hide();
	seletorMidia.show();
	//console.log('Selecionou midias');
	var combobox =$('#comboMidia');
	combobox.html('<option value="0">Selecione o item</option>');
	for(var i in listaMidias){
		combobox.append('<option value ='+listaMidias[i].id+'>'+listaMidias[i].titulo+'</option>');	
	}
	tipoDeItem = "Midia";
}
