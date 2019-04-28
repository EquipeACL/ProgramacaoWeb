$(document).ready(function(){	
	
	var doc = new jsPDF();
	var specialElementHandlers = {
	    '#editor': function (element, renderer) {
	        return true;
	    }
	};

	$('#btn-relatorio').click(function () {   
	    doc.fromHTML($('#secao-dividas').html(), 15, 15, {
	        'width': 170,
	            'elementHandlers': specialElementHandlers
	    });
	    doc.save('dividas.pdf');
	});
	
});