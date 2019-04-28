<template>
    <div class="container-fluid">
        <Menu></Menu>
        <table class="table table-hover table-condensed table-striped table-bordered">
            <thead>
                <tr>
                    <th style="width: 5%">#</th>
                    <th style="width: 40%">Titulo</th>
                    <th style="width: 15%">Tipo</th>
                    <th style="width: 20%">Data</th>
                    <th style="width: 10%">Editar</th>
                    <th style="width: 10%">Deletar</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="midia in midias" :key="midia.id">
                    <td>{{midia.id}}</td>
                    <td>{{midia.titulo}}</td>
                    <td>{{midia.tipo}}</td>
                    <td>{{midia.data}}</td>
                    <td><button type="button" class="btn btn-warning btn-editar" data-toggle="modal"
                    data-target="#modal-midia-editar" v-on:click="(event) => { editarMidia(midia.id) } ">Editar</button></td>
                    <td><button type="button" class="btn btn-danger btn-deletar" v-on:click="(event) => { exlcuirMidia(midia.id) } ">Deletar</button></td>
                </tr>
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="6">Midias Eletronicas Cadastradas: <span id="quantidade-midias">{{midias.length}}</span></td>
                </tr>
                <tr>
                    <td colspan="6">
                        <button type="button" class="btn btn-primary" data-toggle="modal"
                            data-target="#modal-midia-salvar">Cadastrar Midia</button>
                    </td>
                </tr>
            </tfoot>
        </table>
        <div class="modal fade" id="modal-midia-salvar" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form id="form-midia" method="post">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Informações de Midias</h4>
                        </div>
                        <div class="modal-body">
                            <label for="nome">Titulo: </label>
                            <input id="titulo" name="titulo" class="form-control" v-model="midia.titulo">
                            
                            <label for="tipo">Tipo: </label>
                            <select id="tipo" name="tipo" class="form-control" v-model="midia.tipo">
                                <option v-for="tipo in tipos" :key="tipo">{{tipo}}</option>
                            </select>
                            <label for="data">Data: </label>
                            <input id="data" name="data" class="form-control" type="date" v-model="midia.data">
                            
                            <input id="id" name="id" value="" type="hidden">
                            <input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarMidia()">Salvar Informações</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="modal fade" id="modal-midia-editar" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <form id="form-midia" method="post">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Alterar Midia</h4>
                        </div>
                        <div class="modal-body">
                            <label for="titulo">Titulo: </label>
                            <input id="titulo" name="titulo" class="form-control" v-model="midia.titulo">                    
                            
                            <label for="tipo">Tipo: </label>
                            <select id="tipo" name="tipo" class="form-control" v-model="midia.tipo">
                                <option v-for="tipo in tipos" :key="tipo">{{tipo}}</option>
                            </select>

                            <label for="data">Data: </label>
					        <input id="data" name="data" class="form-control" type="date" v-model="midia.data">
                            
                            <input id="id" name="id" value="" type="hidden">
                            <input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarAlteraçaoMidia()">Salvar Informações</button>
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
  name: "Midias",
  data() {
    return {
      midias: [],
      tipos: [],
      midia: {
        id: "",
        titulo: "",
        data:"",
        tipo: ""
      }
    };
  },
  mounted() {
    axios({ method: "GET", url: "http://localhost:8090/midias" }).then(
      result => {
        this.midias = result.data;
      },
      error => {
        console.error(error);
      }
    ),
      
      axios({ method: "GET", url: "http://localhost:8090/midias/tipos" }).then(
        result => {
          this.tipos = result.data;
        },
        error => {
          console.error(error);
        }
      );
  },
  methods: {
    carregarListaMidias() {
      axios({ method: "GET", url: "http://localhost:8090/midias" }).then(
      result => {
        this.midias = result.data;
      },
      error => {
        console.error(error);
      }
    );
    },
    salvarMidia() {
    console.log(this.midia)
      var modal = $("#modal-midia-salvar");
      modal.hide();
      
      axios({
        method: "POST",
        url: "http://localhost:8090/midias",
        data: this.midia,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
          this.carregarListaMidias();
        },
        error => {
          console.error(error);
        }
      );
    },
    exlcuirMidia(id) {
      axios({
        method: "DELETE",
        url: "http://localhost:8090/midias/" + id
      }).then(
        result => {
          this.carregarListaMidias();
        },
        error => {
          console.error(error);
        }
      );
    },
    editarMidia(id) {
      var modal = $("#modal-midia-editar");
      axios({ method: "GET", url: "http://localhost:8090/midias/" + id }).then(
        result => {
          this.midia = result.data;
        },
        error => {
          console.error(error);
        }
      );
      modal.show();
    },
    salvarAlteraçaoMidia() {
      var modal = $("#modal-midia-editar");
      axios({
        method: "PUT",
        url: "http://localhost:8090/midias/" + this.midia.id,
        data: this.midia,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
          this.carregarListaMidias();
          modal.hide();
          this.midia.id = "";
          this.midia.titulo = "";
          this.midia.data = "";
          this.midia.tipo = "";
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
  var modal = $("#modal-midia-salvar");
  var titulo = modal.find("#titulo");
  var data = modal.find("#data");
  var tipo = modal.find("#tipo");
  
  modal.on("shown.bs.modal", onModalShow);
  modal.on("hide.bs.modal", onModalClose);

  function onModalShow() {
    titulo.focus();
  }

  function onModalClose() {
    titulo.val("");
    data.val("");
    tipo.val("");
  }
});
</script>


