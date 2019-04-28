<template>
    <div class="container-fluid">
      <Menu></Menu>
      <table class="table table-hover table-condensed table-striped table-bordered">
	    <thead>
            <tr>
                <th style="width: 5%">#</th>
                <th style="width: 25%">Titulo</th>
                <th style="width: 15%">Edicao</th>
                <th style="width: 15%">Editora</th>
                <th style="width: 10%">N° de Paginas</th>
                <th style="width: 10%">Data de Publicação</th>
                <th style="width: 10%">Editar</th>
                <th style="width: 10%">Deletar</th>
            </tr>
	    </thead>
	    <tbody>
			<tr v-for="revista in revistas" :key="revista.id">
				<td>{{revista.id}}</td>
				<td>{{revista.titulo}}</td>
				<td>{{revista.edicao}}</td>
				<td>{{revista.editora.nome}}</td>
				<td>{{revista.numpaginas}}</td>
				<td>{{revista.datapublicacao}}</td>
				<td><button type="button" class="btn btn-warning btn-editar" data-toggle="modal"
					data-target="#modal-revista-editar" v-on:click="(event) => { editarRevista(revista.id) } ">Editar</button></td>
				<td><button type="button" class="btn btn-danger btn-deletar" v-on:click="(event) => { exlcuirRevista(revista.id) } ">Deletar</button></td>
			</tr>		
	    </tbody>
	    <tfoot>
		<tr>
			<td colspan="8">Revistas cadastradas: <span id="quantidade-revistas">{{revistas.length}}</span></td>
		</tr>
		<tr>
			<td colspan="8">
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#modal-revista-salvar">Cadastrar Revista</button>
			</td>
		</tr>
	    </tfoot>
    </table>
    <div class="modal fade" id="modal-revista-salvar" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="form-revista" method="post">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Adicionar Revistas</h4>
				</div>
				<div class="modal-body">
					<label for="titulo">Titulo: </label>
					<input id="titulo" name="titulo" class="form-control" v-model="revista.titulo">
					
					<label for="edicao">Edição: </label>
					<input id="edicao" name="edicao" class="form-control" v-model="revista.edicao">
					
					<label for="editora">Editora: </label>
					<select id="editora" name="editora" class="form-control" v-model="revista.editora">
						<option v-for="editora in editoras" :key="editora.id" v-bind:value="editora">{{editora.nome}}</option>
					</select>
					
					<label for="numpaginas">N° de Paginas: </label>
					<input id="numpaginas" name="numpaginas" class="form-control" v-model="revista.numpaginas">
					
					<label for="datapublicacao">Data de Publicação: </label>
					<input id="datapublicacao" name="datapublicacao" class="form-control" type="date" v-model="revista.datapublicacao">
					
					<input id="id" name="id" value="" type="hidden">
					<input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarRevista()">Salvar Informações</button>
				</div>
			</form>
		    </div>
	    </div>
    </div>
    <div class="modal fade" id="modal-revista-editar" tabindex="-1" role="dialog" aria-hidden="true">
	  <div class="modal-dialog">
		<div class="modal-content">
			<form id="form-revista" method="post">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Alterar Revistas</h4>
				</div>
				<div class="modal-body">
					<label for="titulo">Titulo: </label>
					<input id="titulo" name="titulo" class="form-control" v-model="revistaAlterar.titulo">
					
					<label for="edicao">Edição: </label>
					<input id="edicao" name="edicao" class="form-control" v-model="revistaAlterar.edicao">
					
					<label for="editora">Editora: </label>
					<select id="editora" name="editora" class="form-control" v-model="revistaAlterar.editora">
						<option v-for="editora in editoras" :key="editora.id" v-bind:value="editora">{{editora.nome}}</option>
					</select>
					
					<label for="numpaginas">N° de Paginas: </label>
					<input id="numpaginas" name="numpaginas" class="form-control" v-model="revistaAlterar.numpaginas">
					
					<label for="datapublicacao">Data de Publicação: </label>
					<input id="datapublicacao" name="datapublicacao" class="form-control" type="date" v-model="revistaAlterar.datapublicacao">
					
					<input id="id" name="id" value="" type="hidden">
					<input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarAlteraçaoRevista()">Salvar Alterações</button>
				</div>
			</form>
		    </div>
	    </div>
    </div>
    </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Revistas",
  data() {
    return {
      revistas:[],
      revista:{
          id:"",
          titulo:"",
          edicao:"",
          editora:{
              id:"",
              nome:""
          },
          numpaginas:"",
          datapublicacao:""
       },
       revistaAlterar:{
          id:"",
          titulo:"",
          edicao:"",
          editora:{
              id:"",
              nome:""
          },
          numpaginas:"",
          datapublicacao:""
       },
      editoras:[]
    };
  },
  mounted() {
    axios({ method: "GET", url: "http://localhost:8090/revistas" }).then(
      result => {
        this.revistas = result.data;
      },
      error => {
        console.error(error);
      }
    ),
      axios({ method: "GET", url: "http://localhost:8090/editoras" }).then(
        result => {
          this.editoras = result.data;
        },
        error => {
          console.error(error);
        }
      );
  },
  methods: {
    carregarListaRevistas() {
      axios({ method: "GET", url: "http://localhost:8090/revistas" }).then(
        result => {
          this.revistas = result.data;
        },
        error => {
          console.error(error);
        }
      );
    },
    salvarRevista() {
      var modal = $("#modal-revista-salvar");
      modal.hide();
      axios({
        method: "POST",
        url: "http://localhost:8090/revistas",
        data: this.revista,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
          this.carregarListaRevistas();
          this.revista={
              id:"",
              titulo:"",
              edicao:"",
              editora:{
                  id:"",
                  nome:""
                  },
              numpaginas:"",
              datapublicacao:""
            }
        },
        error => {
          console.error(error);
        }
      );
    },
    exlcuirRevista(id) {
      axios({
        method: "DELETE",
        url: "http://localhost:8090/revistas/"+id
      }).then(
        result => {
          this.carregarListaRevistas();
        },
        error => {
          console.error(error);
        }
      );
    },
    editarRevista(id) {
      var modal = $("#modal-revista-editar");
      axios({ method: "GET", url: "http://localhost:8090/revistas/" + id }).then(
        result => {
          this.revistaAlterar = result.data;
        },
        error => {
          console.error(error);
        }
      );
      modal.show();
    },
    salvarAlteraçaoRevista() {
      var modal = $("#modal-revista-editar");
			axios({
        method: "PUT",
        url: "http://localhost:8090/revistas/" + this.revista.id,
        data: this.revistaAlterar,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
          this.carregarListaRevistas();
          modal.hide();
          this.revista={
              id:"",
              titulo:"",
              edicao:"",
              editora:{
                  id:"",
                  nome:""
                  },
              numpaginas:"",
              datapublicacao:""
            }
        },
        error => {
          console.error(error);
        }
      );
      modal.hide();
    }
  }
};

$(function() {
  var modalsalvar = $("#modal-revista-salvar");
  var titulo = modalsalvar.find('#titulo');
  var edicao = modalsalvar.find('#edicao');
  var editora = modalsalvar.find('#editora');
  var numpaginas = modalsalvar.find('#numpaginas');  
  var data = modalsalvar.find('#datapublicacao');  

  modalsalvar.on("shown.bs.modal", onModalShow);
  modalsalvar.on("hide.bs.modal", onModalClose);
  
  var modalalterar = $("#modal-revista-editar");
  modalalterar.on("shown.bs.modal", onModalShow);
  modalalterar.on("hide.bs.modal", onModalClose);

  function onModalShow() {
    titulo.focus();
  }

  function onModalClose() {
    titulo.val("");
    edicao.val("");
    editora.val("");
    numpaginas.val("");
    data.val("");
    this.revista={
        id:"",
        titulo:"",
        edicao:"",
        editora:{
            id:"",
            nome:""
            },
        numpaginas:"",
        datapublicacao:""
        }
    
  }
});
</script>