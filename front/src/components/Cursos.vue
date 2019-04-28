/* eslint-disable */
<template>
    <div class="container-fluid">
        <Menu></Menu>
        <table class="table table-hover table-condensed table-striped table-bordered">
            <thead>
                <tr>
                    <th style="width: 5%">#</th>
                    <th style="width: 25%">Nome</th>
                    <th style="width: 10%">Codigo</th>
                    <th style="width: 20%">Area</th>
                    <th style="width: 20%">Tipo</th>
                    <th style="width: 10%">Editar</th>
                    <th style="width: 10%">Deletar</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="curso in cursos" :key="curso.id">
                    <td>{{curso.id}}</td>
                    <td>{{curso.nome}}</td>
                    <td>{{curso.codigo}}</td>
                    <td>{{curso.area}}</td>                    
                    <td>{{curso.tipo}}</td>
                    <td><button type="button" class="btn btn-warning btn-editar" data-toggle="modal"
                            data-target="#modal-curso-editar" v-on:click="(event) => { editarCurso(curso.id) } ">Editar</button></td>
                    <td><button type="button" class="btn btn-danger btn-deletar" v-on:click="(event) => { exlcuirCurso(curso.id) } ">Deletar</button></td>
                    </tr>
                
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="7">Cursos cadastrados: <span id="quantidade-cursos">{{cursos.length}}</span></td>
                </tr>
                <tr>
                    <td colspan="7">
                        <button type="button" class="btn btn-primary" data-toggle="modal"
                            data-target="#modal-curso-salvar">Cadastrar Curso</button>
                    </td>
                </tr>
            </tfoot>
        </table>
        <div class="modal fade" id="modal-curso-salvar" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form id="form-curso" method="post">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Adicionar Curso</h4>
                        </div>
                        <div class="modal-body">
                            <label for="nome">Nome: </label>
                            <input id="nome" name="nome" class="form-control" v-model="curso.nome">
                            
                            <label for="codigo">Código: </label>
                            <input id="codigo" name="codigo" class="form-control" v-model="curso.codigo">
                            
                            <label for="area">Area: </label>
                            <select id="area" name="area" class="form-control" v-model="curso.area">
                                <option v-for="area in areas" :key="area" v-bind:value="area">{{area}}</option>
                            </select>
                            
                            <label for="tipo">Tipo: </label>
                            <select id="tipo" name="tipo" class="form-control" v-model="curso.tipo">
                                <option v-for="tipo in tipos" :key="tipo" v-bind:value="tipo">{{tipo}} </option>
                            </select>
                            
                            <input id="id" name="id" value="" type="hidden">
                            <input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarCurso()">Salvar Informações</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <div class="modal fade" id="modal-curso-editar" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form id="form-curso" method="post">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Alterar Curso</h4>
                        </div>
                        <div class="modal-body">
                            <label for="nome">Nome: </label>
                            <input id="nome" name="nome" class="form-control" v-model="curso.nome">
                            
                            <label for="codigo">Código: </label>
                            <input id="codigo" name="codigo" class="form-control" v-model="curso.codigo">
                            
                            <label for="area">Area: </label>
                            <select id="area" name="area" class="form-control" v-model="curso.area">
                                <option v-for="area in areas" :key="area" v-bind:value="area">{{area}}</option>
                            </select>
                            
                            <label for="tipo">Tipo: </label>
                            <select id="tipo" name="tipo" class="form-control" v-model="curso.tipo">
                                <option v-for="tipo in tipos" :key="tipo" v-bind:value="tipo">{{tipo}} </option>
                            </select>
                            
                            <input id="id" name="id" value="" type="hidden">
                            <input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarAlteraçaoCurso()">Salvar Informações</button>
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
  name: "Cursos",
  data() {
    return {
      cursos:[],
      curso:{
          id:"",
          nome:"",
          codigo:"",
          area:"",
          tipo:""
      },
      tipos:[],
      areas:[]
    };
  },
  mounted() {
    axios({ method: "GET", url: "http://localhost:8090/cursos" }).then(
      result => {
        this.cursos = result.data;
      },
      error => {
        console.error(error);
      }
    ),
      axios({ method: "GET", url: "http://localhost:8090/cursos/tipos" }).then(
        result => {
          this.tipos = result.data;
        },
        error => {
          console.error(error);
        }
      ),
      axios({ method: "GET", url: "http://localhost:8090/cursos/areas" }).then(
        result => {
          this.areas = result.data;
        },
        error => {
          console.error(error);
        }
      );
  },
  methods: {
    carregarListaCursos() {
      axios({ method: "GET", url: "http://localhost:8090/cursos" }).then(
        result => {
          this.cursos = result.data;
        },
        error => {
          console.error(error);
        }
      );
    },
    salvarCurso() {
      var modal = $("#modal-curso-salvar");
      modal.hide();
      axios({
        method: "POST",
        url: "http://localhost:8090/cursos",
        data: this.curso,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
          this.carregarListaCursos();
          this.curso={
            id:"",
            nome:"",
            codigo:"",
            area:"",
            tipo:""
           }
        },
        error => {
          console.error(error);
        }
      );
    },
    exlcuirCurso(id) {
      axios({
        method: "DELETE",
        url: "http://localhost:8090/cursos/"+id
      }).then(
        result => {
          this.carregarListaCursos();
        },
        error => {
          console.error(error);
        }
      );
    },
    editarCurso(id) {
      var modal = $("#modal-curso-editar");
      axios({ method: "GET", url: "http://localhost:8090/cursos/" + id }).then(
        result => {
          this.curso = result.data;
        },
        error => {
          console.error(error);
        }
      );
      modal.show();
    },
    salvarAlteraçaoCurso() {
      var modal = $("#modal-curso-editar");
			axios({
        method: "PUT",
        url: "http://localhost:8090/cursos/" + this.curso.id,
        data: this.curso,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
          this.carregarListaCursos();
          modal.hide();
          this.curso={
            id:"",
            nome:"",
            codigo:"",
            area:"",
            tipo:""
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
  var modalsalvar = $("#modal-curso-salvar");
  var nome = modalsalvar.find('#nome');
  var codigo = modalsalvar.find('#codigo');
  var area = modalsalvar.find('#area');
  var tipo = modalsalvar.find('#tipo');  

  modalsalvar.on("shown.bs.modal", onModalShow);
  modalsalvar.on("hide.bs.modal", onModalClose);
  
  var modalalterar = $("#modal-curso-editar");
  modalalterar.on("shown.bs.modal", onModalShow);
  modalalterar.on("hide.bs.modal", onModalClose);

  function onModalShow() {
    nome.focus();
  }

  function onModalClose() {
    nome.val("");
    codigo.val("");
    area.val("");
    tipo.val("");
    this.curso={
        id:"",
        nome:"",
        codigo:"",
        area:"",
        tipo:""
    }
    
  }
});
</script>