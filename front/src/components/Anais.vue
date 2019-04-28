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
                    <td style="width: 15%">Nome do Congresso</td>
                    <td style="width: 10%">Editar</td>
                    <td style="width: 10%">Deletar</td>
                </tr>
            </thead>
            <tbody>
                <tr v-for="anal in anais" :key="anal.id">
                    <td>{{anal.id}}</td>
                    <td>{{anal.titulo}}</td>
                    <td>{{anal.local}}</td>
                    <td>
                        <select multiple class="form-control">
                            <option v-for="autor in anal.autores" :key="autor.id">{{autor.nome}}</option>
                        </select>
                    </td>
                    <td>{{anal.tipo}}</td>
                    <td>{{anal.nomecongreco}}</td>
                    <td><button type="button" class="btn btn-warning btn-editar" data-toggle="modal"
                            data-target="#modal-anais-editar" v-on:click="(event) => { editarAnal(anal.id) } ">Editar</button></td>
                    <td><button type="button" class="btn btn-danger btn-deletar" v-on:click="(event) => { exlcuirAnal(anal.id) } ">Deletar</button></td>
                </tr>                
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="8">Anais cadastrados: <span id="quantidade-anais">{{anais.length}}</span></td>
                </tr>
                <tr>
                    <td colspan="8">
                        <button type="button" class="btn btn-primary" data-toggle="modal"
                            data-target="#modal-anais-salvar">Cadastrar Anais</button>
                    </td>
                </tr>
            </tfoot>
        </table>
        <div class="modal fade" id="modal-anais-salvar" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form id="form-anais">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Adicionar Anal de Congresso</h4>
                        </div>
                        <div class="modal-body">
                            <label for="titulo">Titulo: </label>
                            <input id="titulo" name="titulo" class="form-control" v-model="anal.titulo">
                        
                            <label for="local">Local: </label>
                            <input id="local" name="local" class="form-control" v-model="anal.local">
                            
                            <label for="autores">Autores: </label>
                            <select id="autores" name="autores" class="form-control" multiple="multiple" v-model="anal.autores">
                                <option v-for="autor in autores" :key="autor.id" v-bind:value="autor">{{autor.nome}}</option>
                            </select>
                            
                            <label for="tipo">Tipo: </label>
                            <select id="tipo" name="tipo" class="form-control" v-model="anal.tipo">
                                <option v-for="tipo in tipos" :key="tipo.id" >{{tipo}}</option>
                            </select>
                            
                            <label for="nomecongreco">Nome do Congresso: </label>
                            <input id="nomecongreco" name="nomecongreco" class="form-control" v-model="anal.nomecongreco">
                            
                            <input id="id" name="id" value="" type="hidden">
                            <input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarAnal()">Salvar Informações</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="modal fade" id="modal-anais-editar" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form id="form-anais">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Alterar Anal de Congresso</h4>
                        </div>
                        <div class="modal-body">
                            <label for="titulo">Titulo: </label>
                            <input id="titulo" name="titulo" class="form-control" v-model="anal.titulo">
                        
                            <label for="local">Local: </label>
                            <input id="local" name="local" class="form-control" v-model="anal.local">
                            
                            <label for="autores">Autores: </label>
                            <select id="autores" name="autores" class="form-control" multiple="multiple" v-model="anal.autores">
                                <option v-for="autor in autores" :key="autor.id" v-bind:value="autor">{{autor.nome}}</option>
                            </select>
                            
                            <label for="tipo">Tipo: </label>
                            <select id="tipo" name="tipo" class="form-control" v-model="anal.tipo">
                                <option v-for="tipo in tipos" :key="tipo.id" >{{tipo}}</option>
                            </select>
                            
                            <label for="nomecongreco">Nome do Congresso: </label>
                            <input id="nomecongreco" name="nomecongreco" class="form-control" v-model="anal.nomecongreco">
                            
                            <input id="id" name="id" value="" type="hidden">
                            <input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarAlteraçaoAnal()">Salvar Alterações</button>
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
  name: "Anais",
  data() {
    return {
      anais: [],
      tipos:[],
      autores: [],
      anal: {
        id:"",
        titulo:"",
        local:"",
        autores:[],
        tipo:"",
        nomecongreco:""
        }
    };
  },
  mounted() {
    axios({ method: "GET", url: "http://localhost:8090/anais" }).then(
      result => {
        this.anais = result.data;
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
      axios({ method: "GET", url: "http://localhost:8090/anais/tipos" }).then(
        result => {
          this.tipos = result.data;
        },
        error => {
          console.error(error);
        }
      );
  },
  methods: {
    carregarListaAnais() {
      axios({ method: "GET", url: "http://localhost:8090/anais" }).then(
        result => {
          this.anais = result.data;
        },
        error => {
          console.error(error);
        }
      );
    },
    salvarAnal() {
      var modal = $("#modal-anais-salvar");
      modal.hide();
      axios({
        method: "POST",
        url: "http://localhost:8090/anais",
        data: this.anal,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
          this.carregarListaAnais();
          anal = {
              id:"",
              titulo:"",
              local:"",
              autores:[],
              tipo:"",
              nomecongreco:""
            }
        },
        error => {
          console.error(error);
        }
      );
    },
    exlcuirAnal(id) {
      axios({
        method: "DELETE",
        url: "http://localhost:8090/anais/"+id
      }).then(
        result => {
          this.carregarListaAnais();
        },
        error => {
          console.error(error);
        }
      );
    },
    editarAnal(id) {
      var modal = $("#modal-anais-editar");
      axios({ method: "GET", url: "http://localhost:8090/anais/" + id }).then(
        result => {
          this.anal = result.data;
        },
        error => {
          console.error(error);
        }
      );
      modal.show();
    },
    salvarAlteraçaoAnal() {
      var modal = $("#modal-anais-editar");
			axios({
        method: "PUT",
        url: "http://localhost:8090/anais/" + this.anal.id,
        data: this.anal,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
					this.carregarListaAnais();
					modal.hide();
                    this.anal.id =""
                    this.anal.titulo = ""
                    this.anal.local = ""
                    this.anal.autores = []
                    this.anal.tipo = ""
                    this.anal.nomecongreco = ""
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
  var modalsalvar = $("#modal-anais-salvar");
  var titulo = modalsalvar.find('#titulo');
  var local = modalsalvar.find('#local');
  var autores = modalsalvar.find('#autores');
  var tipo = modalsalvar.find('#tipo');
  var nomecongreco = modalsalvar.find('#nomecongreco');

  modalsalvar.on("shown.bs.modal", onModalShow);
  modalsalvar.on("hide.bs.modal", onModalClose);
  
  var modalalterar = $("#modal-anais-editar");
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
    nomecongreco.val("");
    this.anal = {
              id:"",
              titulo:"",
              local:"",
              autores:[],
              tipo:"",
              nomecongreco:""
            }
    
  }
});
</script>


