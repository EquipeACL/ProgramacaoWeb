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
					<tr v-for="orientador in orientadores" :key="orientador.id">
							<td>{{orientador.id}}</td>
							<td>{{orientador.nome}}</td>
							<td><button type="button" class="btn btn-warning btn-editar" data-toggle="modal"
								data-target="#modal-orientador-editar" v-on:click="(event) => { editarOrientador(orientador.id) } ">Editar</button></td>
							<td><button type="button" class="btn btn-danger btn-deletar" v-on:click="(event) => { excluirOrientador(orientador.id) } ">Deletar</button></td>
					</tr>		
				</tbody>
				<tfoot>
					<tr>
						<td colspan="7">Orientadores cadastrados: <span id="quantidade-orientadores">{{orientadores.length}}</span></td>
					</tr>
					<tr>
						<td colspan="7">
							<button type="button" class="btn btn-primary" data-toggle="modal"
								data-target="#modal-orientador-salvar">Cadastrar Orientador</button>
						</td>
					</tr>	
				</tfoot>
	</table>
	<div class="modal fade" id="modal-orientador-salvar" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="form-orientador" method="post">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Informações do Orientador</h4>
					</div>
					<div class="modal-body">
						<label for="nome">Nome: </label>
						<input id="nome" name="nome" v-model="orientador.nome" class="form-control">
						
						
						<input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						<button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarOrientador()">Salvar Informações</button>
					</div>
				</form>
			</div>
		</div>	
	</div>
	<div class="modal fade" id="modal-orientador-editar" tabindex="-1" role="dialog" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<form id="form-orientador" method="post">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Alterar Orientador</h4>
					</div>
					<div class="modal-body">
						<label for="nome">Nome: </label>
						<input id="nome" name="nome" v-model="orientador.nome" class="form-control">
						
						<input id="id" name="id" v-model="orientador.id" type="hidden">
						<input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
						<button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarAlteraçaoOrientador()">Salvar Alterações</button>
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
  name: "Orientadores",
  data() {
    return {
      orientadores: [],
      orientador: {
				id:"",
				nome: ""
      }
    };
  },
  mounted() {
   axios({ method: "GET", url: "http://localhost:8090/orientadores" }).then(
      result => {
        this.orientadores = result.data;
      },
      error => {
        console.error(error);
      }
    );
  },
  methods: {
		carregarListaOrientadores(){
			axios({ method: "GET", url: "http://localhost:8090/orientadores" }).then(
      result => {
        this.orientadores = result.data;
      },
      error => {
        console.error(error);
      }
    );
		},
    salvarOrientador() {
			var modal = $("#modal-orientador-salvar");
  		var nome = $("#nome");
			nome.val("");
			modal.hide();
      axios({
        method: "POST",
        url: "http://localhost:8090/orientadores",
        data: this.orientador,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
					this.carregarListaOrientadores()
				},
				error => {
					console.error(error);
				}
      );
		},
		excluirOrientador(id){
			console.log("Excluir: "+id);
		  axios({
        method: "DELETE",
        url: "http://localhost:8090/orientadores/"+id
      }).then(				
				result => {
					this.carregarListaOrientadores()
				},
				error => {
					console.error(error);
				}
      );
		},
		editarOrientador(id){
			var modal = $("#modal-orientador-editar");
			axios({ method: "GET", url: "http://localhost:8090/orientadores/"+id }).then(
				result => {
				this.orientador.id = result.data.id;
				this.orientador.nome = result.data.nome;				
				},
				error => {
					console.error(error);
				}
      );
			modal.show();
		},
		salvarAlteraçaoOrientador(){
			var modal = $("#modal-orientador-editar");
			var nome = modal.find('#nome');	
			console.log('>>> Orientador.id:'+this.orientador.id);
			console.log('>>> Orientador.nome:'+this.orientador.nome);
			axios({
        method: "PUT",
        url: "http://localhost:8090/orientadores/"+this.orientador.id,
        data: this.orientador,
        headers: { "content-type": "application/json" }
      }).then(
				result => {
					this.carregarListaOrientadores()
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
  var modal = $("#modal-orientador-salvar");
  var nome = $("#nome");

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








