<template>
  <div class="container-fluid">
      <table class="table table-hover table-condensed table-striped table-bordered">
				<thead>
					<tr>
						<th style="width: 10%">#</th>
						<th style="width: 60%">Nome</th>
						<th style="width: 15%">Editar</th>
						<th style="width: 15%">Deletar</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="autor in autores" :key="autor.id">
							<td>{{autor.id}}</td>
							<td>{{autor.nome}}</td>
							<td><button type="button" class="btn btn-warning btn-editar" data-toggle="modal"
								data-target="#modal-autor-editar" v-on:click="(event) => { editarAutor(autor.id) } ">Editar</button></td>
							<td><button type="button" class="btn btn-danger btn-deletar" v-on:click="(event) => { exlcuirAutor(autor.id) } ">Deletar</button></td>
					</tr>		
				</tbody>
				<tfoot>
					<tr>
						<td colspan="7">Autores cadastrados: <span id="quantidade-autores">{{autores.length}}</span></td>
					</tr>
					<tr>
						<td colspan="7">
							<button type="button" class="btn btn-primary" data-toggle="modal"
								data-target="#modal-autor-salvar">Cadastrar Autor</button>
						</td>
					</tr>	
				</tfoot>
	</table>
	<div class="modal fade" id="modal-autor-salvar" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="form-autor" method="post">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Informações do Autor</h4>
					</div>
					<div class="modal-body">
						<label for="nome" class="group-form">Nome: </label>
						<input id="nome" name="nome" v-model="autor.nome" class="form-control">
						
						
						<input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						<button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarAutor()">Salvar Informações</button>
					</div>
				</form>
			</div>
		</div>	
	</div>
	<div class="modal fade" id="modal-autor-editar" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="form-autor">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Alterar Autor</h4>
					</div>
					<div class="modal-body">
						<label for="nome" class="group-form">Nome: </label>
						<input id="nome" name="nome" v-model="autor.nome" class="form-control">
						
						<input id="id" name="id" v-model="autor.id" type="hidden">
						<input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						<button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarAlteraçaoAutor()">Salvar Alterações</button>
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
  name: "Autores",
  data() {
    return {
      autores: [],
      autor: {
				id:"",
				nome: ""
      }
    };
  },
  mounted() {
   axios({ method: "GET", url: "http://localhost:8090/autores" }).then(
      result => {
        this.autores = result.data;
      },
      error => {
        console.error(error);
      }
    );
  },
  methods: {
		carregarListaAutores(){
			axios({ method: "GET", url: "http://localhost:8090/autores" }).then(
      result => {
        this.autores = result.data;
      },
      error => {
        console.error(error);
      }
    );
		},
    salvarAutor() {
			var modal = $("#modal-autor-salvar");
  		var nome = $("#nome");
			nome.val("");
			modal.hide();
      axios({
        method: "POST",
        url: "http://localhost:8090/autores",
        data: this.autor,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
					this.carregarListaAutores()
				},
				error => {
					console.error(error);
				}
      );
		},
		exlcuirAutor(id){
			console.log("Excluir: "+id);
		  axios({
        method: "DELETE",
        url: "http://localhost:8090/autores/"+id
      }).then(				
				result => {
					this.carregarListaAutores()
				},
				error => {
					console.error(error);
				}
      );
		},
		editarAutor(id){
			var modal = $("#modal-autor-editar");
			axios({ method: "GET", url: "http://localhost:8090/autores/"+id }).then(
				result => {
				this.autor = result.data;			
				},
				error => {
					console.error(error);
				}
      );
			modal.show();
		},
		salvarAlteraçaoAutor(){
			var modal = $("#modal-autor-editar");
			var nome = modal.find('#nome');	
			console.log('>>> Autor.id:'+this.autor.id);
			console.log('>>> Autor.nome:'+this.autor.nome);
			axios({
        method: "PUT",
        url: "http://localhost:8090/autores/"+this.autor.id,
        data: this.autor,
        headers: { "content-type": "application/json" }
      }).then(
				result => {
					this.carregarListaAutores()
					this.autor.id = "";
					this.autor.nome = "";
				},
				error => {
					console.error(error);
				}
			);
			nome.val('');
			modal.hide();
		}
  }
};

$(function() {
  var modal = $("#modal-autor-salvar");
  var nome = modal.find("#nome");

  modal.on("shown.bs.modal", onModalShow);
  modal.on("hide.bs.modal", onModalClose);

  function onModalShow() {
    nome.focus();
  }

  function onModalClose() {
    nome.val("");
  }
});
</script>








