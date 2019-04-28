<template>
<div class="container-fluid">
    <table
	class="table table-hover table-condensed table-striped table-bordered">
	<thead>
		<tr>
			<td style="width: 5%">#</td>
			<td style="width: 25%">Titulo</td>
			<td style="width: 15%">Edicao</td>
			<td style="width: 15%">Editora</td>
			<td style="width: 10%">N° de Paginas</td>
			<td style="width: 10%">Data</td>
			<td style="width: 10%">Editar</td>
			<td style="width: 10%">Deletar</td>
		</tr>
	</thead>
	<tbody>
	    <tr v-for="jornal in jornais" :key="jornal.id">
			<td>{{jornal.id}}</td>
			<td>{{jornal.titulo}}</td>
			<td>{{jornal.edicao}}</td>
			<td>{{jornal.editora.nome}}</td>
			<td>{{jornal.numpaginas}}</td>
			<td>{{jornal.data}}</td>
			<td><button type="button" class="btn btn-warning btn-editar"  data-toggle="modal"
								data-target="#modal-jornal-editar" v-on:click="(event) => { editarjornal(jornal.id) } ">Editar</button></td>
			<td><button type="button" class="btn btn-danger btn-deletar" v-on:click="(event) => { exlcuirJornal(jornal.id) } ">Deletar</button></td>
		</tr>
		
	</tbody>
	<tfoot>
		<tr>
			<td colspan="8">Jornais cadastrados: <span id="quantidade-jornais">{{jornais.length}}</span></td>
		</tr>
		<tr>
			<td colspan="8">
				<button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#modal-jornal-salvar">Cadastrar Jornal</button>
			</td>
		</tr>
	</tfoot>
</table>
<div class="modal fade" id="modal-jornal-salvar" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="form-jornal" method="post">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Adicionar Jornal</h4>
				</div>
				<div class="modal-body">
					<label for="titulo">Titulo: </label>
					<input id="titulo" name="titulo" v-model="jornal.titulo" class="form-control">
					
					<label for="edicao">Edição: </label>
					<input id="edicao" name="edicao" class="form-control" v-model="jornal.edicao">
					
					<label for="editora">Editora: </label>
					<select id="editora" name="editora" class="form-control" v-model="jornal.editora.id">
						<option v-for="edi in editoras" :key="edi.id" v-bind:value="edi.id">{{edi.nome}}</option>
					</select>
					
					<label for="numpaginas">N° de Paginas: </label>
					<input id="numpaginas" name="numpaginas" class="form-control" v-model="jornal.numpaginas">
					
					<label for="data">Data: </label>
					<input id="data" name="data" class="form-control" type="date" v-model="jornal.data">
					
					<input id="id" name="id" value="" type="hidden">
					<input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarJornal()">Salvar Informações</button>
				</div>
			</form>
		</div>
	</div>
</div>
<div class="modal fade" id="modal-jornal-editar" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="form-jornal" method="post">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Alterar Jornal</h4>
				</div>
				<div class="modal-body">
					<label for="titulo">Titulo: </label>
					<input id="titulo" name="titulo" v-model="jornal.titulo" class="form-control">
					
					<label for="edicao">Edição: </label>
					<input id="edicao" name="edicao" class="form-control" v-model="jornal.edicao">
					
					<label for="editora">Editora: </label>
					<select id="editora" name="editora" class="form-control" v-model="jornal.editora.id">
						<option v-for="edi in editoras" :key="edi.id" v-bind:value="edi.id">{{edi.nome}}</option>
					</select>
					
					<label for="numpaginas">N° de Paginas: </label>
					<input id="numpaginas" name="numpaginas" class="form-control" v-model="jornal.numpaginas">
					
					<label for="data">Data: </label>
					<input id="data" name="data" class="form-control" type="date" v-model="jornal.data">
					
					<input id="id" name="id" value="" type="hidden">
					<input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarAlteraçaoJornal()">Salvar Informações</button>
				</div>
			</form>
			</div>
		</div>	
	</div>
</div>
</template>

/* eslint-disable */
<script>
import axios from "axios";
export default {
  name: "Jornais",
  data() {
    return {
      jornais: [],
      editoras: [],
      jornal: {
        id: "",
        titulo: "",
        edicao: "",
        editora: {
          id: "",
          nome: ""
        },
        numpaginas: "",
        data: ""
      }
    };
  },
  mounted() {
    axios({ method: "GET", url: "http://localhost:8090/jornais" }).then(
      result => {
        this.jornais = result.data;
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
    carregarListaJornais() {
      axios({ method: "GET", url: "http://localhost:8090/jornais" }).then(
        result => {
          this.jornais = result.data;
        },
        error => {
          console.error(error);
        }
      );
    },
    salvarJornal() {
      var modal = $("#modal-jornal-salvar");
      modal.hide();
      axios({
        method: "POST",
        url: "http://localhost:8090/jornais",
        data: this.jornal,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
          this.carregarListaJornais();
        },
        error => {
          console.error(error);
        }
      );
    },
    exlcuirJornal(id) {
      console.log("Excluir: " + id);
      axios({
        method: "DELETE",
        url: "http://localhost:8090/jornais/"+id
      }).then(
        result => {
          this.carregarListaJornais();
        },
        error => {
          console.error(error);
        }
      );
    },
    editarjornal(id) {
      var modal = $("#modal-jornal-editar");
      axios({ method: "GET", url: "http://localhost:8090/jornais/" + id }).then(
        result => {
          this.jornal = result.data;
        },
        error => {
          console.error(error);
        }
      );
      modal.show();
    },
    salvarAlteraçaoJornal() {
      var modal = $("#modal-jornal-editar");
			axios({
        method: "PUT",
        url: "http://localhost:8090/jornais/" + this.jornal.id,
        data: this.jornal,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
					this.carregarListaJornais();
					modal.hide();
					this.jornal.id = "";
					this.jornal.titulo = "";
					this.jornal.edicao = "";
					this.jornal.editora = {
						id: "",
						nome:""
					};
					this.jornal.numpaginas = "";
					this.jornal.data = "";
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
  var modal = $("#modal-jornal-salvar");
  var titulo = modal.find("#titulo");
  var edicao = modal.find("#edicao");
  var numpaginas = modal.find("#numpaginas");
  var editora = modal.find("#editora");
  var data = modal.find("#data");

  modal.on("shown.bs.modal", onModalShow);
  modal.on("hide.bs.modal", onModalClose);

  function onModalShow() {
    titulo.focus();
  }

  function onModalClose() {
    titulo.val("");
    edicao.val("");
    numpaginas.val("");
    editora.val("");
    data.val("");
  }
});
</script>

<style>
	#btn-sair{
		margin-top: 7px;
		margin-right: 15px;
	}
</style>