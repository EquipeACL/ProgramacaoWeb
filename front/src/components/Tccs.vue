<template>
    <div class="container-fluid">
        <table class="table table-hover table-condensed table-striped table-bordered">
            <thead>
                <tr>
                    <td style="width: 5%">#</td>
                    <td style="width: 20%">Titulo</td>
                    <td style="width: 15%">Local</td>
                    <td style="width: 15%">Autores</td>
                    <td style="width: 10%">Tipo</td>
                    <td style="width: 15%">Orientadores</td>
                    <td style="width: 10%">Editar</td>
                    <td style="width: 10%">Deletar</td>
                </tr>
	        </thead>
            <tbody>
                <tr v-for="tcc in tccs" :key="tcc.id">
                	<td>{{tcc.id}}</td>
				    <td>{{tcc.titulo}}</td>
				    <td>{{tcc.local}}</td>
				    <td>
					    <select multiple class="form-control">
                            <option v-for="autor in tcc.autores" :key="autor.id">{{autor.nome}}</option>
                        </select>
				    </td>
				    <td>{{tcc.tipo}}</td>
				    <td>
                        <select multiple class="form-control">
                            <option v-for="orientador in tcc.orientadores" :key="orientador.id">{{orientador.nome}}</option>
                        </select>
				    </td>
				    <td><button type="button" class="btn btn-warning btn-editar" data-toggle="modal"
					data-target="#modal-tcc-editar" v-on:click="(event) => { editarTcc(tcc.id) } ">Editar</button></td>
				    <td><button type="button" class="btn btn-danger btn-deletar" v-on:click="(event) => { exlcuirTcc(tcc.id) } ">Deletar</button></td>
			    </tr>
	    </tbody>
	    <tfoot>
		    <tr>
			    <td colspan="8">TCCs cadastrados: <span id="quantidade-tccs">{{tccs.length}}</span></td>
		    </tr>
		    <tr>
			    <td colspan="8">
				    <button type="button" class="btn btn-primary" data-toggle="modal"
					data-target="#modal-tcc-salvar">Cadastrar TCC</button>
			    </td>
		    </tr>
	    </tfoot>
    </table>
    <div class="modal fade" id="modal-tcc-salvar" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="form-tcc" method="post">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">Adicionar TCC</h4>
                    </div>
                    <div class="modal-body">
                        <label for="titulo">Titulo: </label>
                        <input id="titulo" name="titulo" class="form-control" v-model="tcc.titulo">
                    
                        <label for="local">Local: </label>
                        <input id="local" name="local" class="form-control" v-model="tcc.local">
                        
                        <label for="autores">Autores: </label>
                        <select id="autores" name="autores" class="form-control" multiple="multiple" v-model="tcc.autores">
                            <option v-for="autor in autores" :key="autor.id" v-bind:value="autor">{{autor.nome}}</option>                           
                        </select>
                        
                        <label for="tipo">Tipo: </label>
                        <select id="tipo" name="tipo" class="form-control" v-model="tcc.tipo">
                            <option v-for="tipo in tipos" :key="tipo">{{tipo}}</option>
                        </select>
                        
                        <label for="orientadores">Orientadores: </label>
                        <select id="orientadores" name="orientadores" class="form-control" multiple="multiple" v-model="tcc.orientadores">
                            <option v-for="orientador in orientadores" :key="orientador.id" v-bind:value="orientador">{{orientador.nome}}</option>
                        </select>
                        
                        <input id="id" name="id" value="" type="hidden">
                        <input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        <button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarTcc()">Salvar Informações</button>
                    </div>
			    </form>
		    </div>
	    </div>
    </div>
    <div class="modal fade" id="modal-tcc-editar" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<form id="form-tcc" method="post">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal"
                            aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                        <h4 class="modal-title">Alterar TCC</h4>
                    </div>
                    <div class="modal-body">
                        <label for="titulo">Titulo: </label>
                        <input id="titulo" name="titulo" class="form-control" v-model="tcc.titulo">
                    
                        <label for="local">Local: </label>
                        <input id="local" name="local" class="form-control" v-model="tcc.local">
                        
                        <label for="autores">Autores: </label>
                        <select id="autores" name="autores" class="form-control" multiple="multiple" v-model="tcc.autores">
                            <option v-for="autor in autores" :key="autor.id" v-bind:value="autor">{{autor.nome}}</option>                           
                        </select>
                        
                        <label for="tipo">Tipo: </label>
                        <select id="tipo" name="tipo" class="form-control" v-model="tcc.tipo">
                            <option v-for="tipo in tipos" :key="tipo">{{tipo}}</option>
                        </select>
                        
                        <label for="orientadores">Orientadores: </label>
                        <select id="orientadores" name="orientadores" class="form-control" multiple="multiple" v-model="tcc.orientadores">
                            <option v-for="orientador in orientadores" :key="orientador.id" v-bind:value="orientador">{{orientador.nome}}</option>
                        </select>
                        
                        <input id="id" name="id" value="" type="hidden">
                        <input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                        <button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarAlteraçaoTcc()">Salvar Informações</button>
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
  name: "Tccs",
  data() {
    return {
      tccs: [],
      tipos: [],
      autores: [],
      orientadores: [],
      tcc: {
        id: "",
        titulo: "",
        local: "",
        autores: [],
        tipo: "",
        orientadores: []
      }
    };
  },
  mounted() {
    axios({ method: "GET", url: "http://localhost:8090/tccs" }).then(
      result => {
        this.tccs = result.data;
      },
      error => {
        console.error(error);
      }
    ),
      axios({ method: "GET", url: "http://localhost:8090/autores" }).then(
        result => {
          this.autores = result.data;
        },
        error => {
          console.error(error);
        }
      ),
      axios({ method: "GET", url: "http://localhost:8090/orientadores" }).then(
        result => {
          this.orientadores = result.data;
        },
        error => {
          console.error(error);
        }
      ),
      axios({ method: "GET", url: "http://localhost:8090/tccs/tipos" }).then(
        result => {
          this.tipos = result.data;
        },
        error => {
          console.error(error);
        }
      );
  },
  methods: {
    carregarListaTccs() {
      axios({ method: "GET", url: "http://localhost:8090/tccs" }).then(
        result => {
          this.tccs = result.data;
        },
        error => {
          console.error(error);
        }
      );
    },
    salvarTcc() {
      var modal = $("#modal-tcc-salvar");
      modal.hide();
      axios({
        method: "POST",
        url: "http://localhost:8090/tccs",
        data: this.tcc,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
          this.carregarListaTccs();
          tcc = {
            id: "",
            titulo: "",
            local: "",
            autores: [],
            tipo: "",
            orientadores: []
          };
        },
        error => {
          console.error(error);
        }
      );
    },
    exlcuirTcc(id) {
      axios({
        method: "DELETE",
        url: "http://localhost:8090/tccs/" + id
      }).then(
        result => {
          this.carregarListaTccs();
        },
        error => {
          console.error(error);
        }
      );
    },
    editarTcc(id) {
      var modal = $("#modal-tcc-editar");
      axios({ method: "GET", url: "http://localhost:8090/tccs/" + id }).then(
        result => {
          this.tcc = result.data;
        },
        error => {
          console.error(error);
        }
      );
      modal.show();
    },
    salvarAlteraçaoTcc() {
      var modal = $("#modal-tcc-editar");
      axios({
        method: "PUT",
        url: "http://localhost:8090/tccs/" + this.tcc.id,
        data: this.tcc,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
          this.carregarListaTccs();
          modal.hide();
          this.tcc.id = "";
          this.tcc.titulo = "";
          this.tcc.local = "";
          this.tcc.autores = [];
          this.tcc.orientadores = [];
          this.tcc.tipo = "";
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
  var modalsalvar = $("#modal-tcc-salvar");
  var titulo = modalsalvar.find("#titulo");
  var local = modalsalvar.find("#local");
  var autores = modalsalvar.find("#autores");
  var tipo = modalsalvar.find("#tipo");
  var orientadores = modalsalvar.find("#orientadores");

  modalsalvar.on("shown.bs.modal", onModalShow);
  modalsalvar.on("hide.bs.modal", onModalClose);

  var modalalterar = $("#modal-tcc-editar");
  modalalterar.on("shown.bs.modal", onModalShow);
  modalalterar.on("hide.bs.modal", onModalClose);

  function onModalShow() {
    titulo.focus();
  }

  function onModalClose() {
    titulo.val("");
    local.val("");
    autores.val("");
    tipo.val("");
    orientadores.val("");
    this.tcc = {
      id: "",
      titulo: "",
      local: "",
      autores: [],
      tipo: "",
      orientadores: []
    };
  }
});
</script>


