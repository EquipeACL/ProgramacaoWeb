<template>
  <div class="container-fluid">
		<Menu></Menu>
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
					<tr v-for="editora in editoras" :key="editora.id">
							<td>{{editora.id}}</td>
							<td>{{editora.nome}}</td>
							<td><button type="button" class="btn btn-warning btn-editar" data-toggle="modal"
								data-target="#modal-editora-editar" v-on:click="(event) => { editarEditora(editora.id) } ">Editar</button></td>
							<td><button type="button" class="btn btn-danger btn-deletar" v-on:click="(event) => { exlcuirEditora(editora.id) } ">Deletar</button></td>
					</tr>		
				</tbody>
				<tfoot>
					<tr>
						<td colspan="7">Editoras cadastradas: <span id="quantidade-editoras">{{editoras.length}}</span></td>
					</tr>
					<tr>
						<td colspan="7">
							<button type="button" class="btn btn-primary" data-toggle="modal"
								data-target="#modal-editora-salvar">Cadastrar Editora</button>
						</td>
					</tr>	
				</tfoot>
	</table>
	<div class="modal fade" id="modal-editora-salvar" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="form-editora" method="post">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Informações da Editora</h4>
					</div>
					<div class="modal-body">
						<label for="nome" class="group-form">Nome: </label>
						<input id="nome" name="nome" v-model="editora.nome" class="form-control">
						
						
						<input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						<button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarEditora()">Salvar Informações</button>
					</div>
				</form>
			</div>
		</div>	
	</div>
	<div class="modal fade" id="modal-editora-editar" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="form-editora">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Alterar Editora</h4>
					</div>
					<div class="modal-body">
						<label for="nome" class="group-form">Nome: </label>
						<input id="nome" name="nome" v-model="editora.nome" class="form-control">
						
						<input id="id" name="id" v-model="editora.id" type="hidden">
						<input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						<button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarAlteraçaoEditora()">Salvar Alterações</button>
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
  name: "Editoras",
  data() {
    return {
      editoras: [],
      editora: {
				id:"",
				nome: ""
      }
    };
  },
  mounted() {
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
		carregarListaEditoras(){
			axios({ method: "GET", url: "http://localhost:8090/editoras" }).then(
      result => {
        this.editoras = result.data;
      },
      error => {
        console.error(error);
      }
    );
		},
    salvarEditora() {
			var modal = $("#modal-editora-salvar");
  		var nome = $("#nome");
			nome.val("");
			modal.hide();
      axios({
        method: "POST",
        url: "http://localhost:8090/editoras",
        data: this.editora,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
					this.carregarListaEditoras()
				},
				error => {
					console.error(error);
				}
      );
		},
		exlcuirEditora(id){
			console.log("Excluir: "+id);
		  axios({
        method: "DELETE",
        url: "http://localhost:8090/editoras/"+id
      }).then(				
				result => {
					this.carregarListaEditoras()
				},
				error => {
					console.error(error);
				}
      );
		},
		editarEditora(id){
			var modal = $("#modal-editora-editar");
			axios({ method: "GET", url: "http://localhost:8090/editoras/"+id }).then(
				result => {
				this.editora = result.data;			
				},
				error => {
					console.error(error);
				}
      );
			modal.show();
		},
		salvarAlteraçaoEditora(){
			var modal = $("#modal-editora-editar");
			var nome = modal.find('#nome');	
			console.log('>>> Editora.id:'+this.editora.id);
			console.log('>>> Editora.nome:'+this.editora.nome);
			axios({
        method: "PUT",
        url: "http://localhost:8090/editoras/"+this.editora.id,
        data: this.editora,
        headers: { "content-type": "application/json" }
      }).then(
				result => {
					this.carregarListaEditoras()
					this.editora.id = "";
					this.editora.nome = "";
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
  var modalsalvar = $("#modal-editora-salvar");
  var nome = modalsalvar.find("#nome");

  modalsalvar.on("shown.bs.modal", onModalShow);
	modalsalvar.on("hide.bs.modal", onModalClose);
	
	var modalalterar = $("#modal-editora-editar");
  
  modalalterar.on("shown.bs.modal", onModalShow);
  modalalterar.on("hide.bs.modal", onModalClose);

  function onModalShow() {
    nome.focus();
  }

  function onModalClose() {
		nome.val("");
		this.editora = {
			id:"",
			nome:""
		}
  }
});
</script>








