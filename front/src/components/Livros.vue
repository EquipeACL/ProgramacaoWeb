<template>
    <div class="container-fluid">
        <Menu></Menu> 
        <div class="table-responsive">
        <table class="table table-hover table-condensed table-striped table-bordered">
            <thead>
                <tr>
                    <th style="width: 5%">ISBN</th>
                    <th style="width: 20%">Titulo</th>
                    <th style="width: 5%">Edicao</th>
                    <th style="width: 10%">Editora</th>
                    <th style="width: 10%">N° de Paginas</th>
                    <th style="width: 25%">Lista de Autores</th>
                    <th style="width: 10%">Editar</th>
                    <th style="width: 10%">Deletar</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="livro in livros" :key="livro.id">
                    <td>{{livro.isbn}}</td>
                    <td>{{livro.titulo}}</td>
                    <td>{{livro.edicao}}</td>
                    <td>{{livro.editora.nome}}</td>
                    <td>{{livro.numpaginas}}</td>
                    <td>                   
                        <select class="form-control" multiple>
                            <option v-for="autor in livro.autores" :key="autor.id">{{autor.nome}}</option>
                        </select>
                    </td>
                    <td><button type="button" class="btn btn-warning btn-editar" data-toggle="modal"
                            data-target="#modal-livro-editar" v-on:click="(event) => { editarLivro(livro.id) } ">Editar</button></td>
                    <td><button type="button" class="btn btn-danger btn-deletar" v-on:click="(event) => { excluirLivro(livro.id) } ">Deletar</button></td>
                </tr>                
            </tbody>
            <tfoot>
                <tr>
                    <td colspan="9">Livros cadastrados: <span id="quantidade-livros">{{livros.length}}</span></td>
                </tr>
                <tr>
                    <td colspan="9">
                        <button type="button" class="btn btn-primary" data-toggle="modal"
                            data-target="#modal-livro-salvar">Cadastrar Livro</button>
                    </td>
                </tr>
            </tfoot>
        </table>
        </div>
        <div class="modal fade" id="modal-livro-salvar" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form id="form-livro" method="post">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Adicionar Livro</h4>
                        </div>
                        <div class="modal-body">
                            
                            <label for="isbn">ISBN: </label>
                            <input id="isbn" name="isbn" class="form-control" v-model="livro.isbn">

                            <label for="titulo">Titulo: </label>
                            <input id="titulo" name="titulo" class="form-control" v-model="livro.titulo">
                            
                            <label for="edicao">Edição: </label>
                            <input id="edicao" name="edicao" class="form-control" v-model="livro.edicao">
                            
                            <label for="editora">Editora: </label>
                            <select id="editora" name="editora" class="form-control" v-model="livro.editora">
                                <option v-for="editora in editoras" :key="editora.id" v-bind:value="editora">{{editora.nome}}</option>
                            </select>
                            
                            <label for="numpaginas">N° de Paginas: </label>
                            <input id="numpaginas" name="numpaginas" class="form-control" v-model="livro.numpaginas">
                            
                            <label for="autores">Autores: </label>
                            <select id="autores" name="autores" class="form-control" multiple="multiple" v-model="livro.autores">
                                <option v-for="autor in autores" :key="autor.id" v-bind:value="autor">{{autor.nome}}</option>
                            </select>
                            
                            <input id="id" name="id" value="" type="hidden">
                            <input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarLivro()">Salvar Informações</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
         <div class="modal fade" id="modal-livro-editar" tabindex="-1" role="dialog" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <form id="form-livro" method="post">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"
                                aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                            <h4 class="modal-title">Alterar Livro</h4>
                        </div>
                        <div class="modal-body">
                            
                            <label for="isbn">ISBN: </label>
                            <input id="isbn" name="isbn" class="form-control" v-model="livroAlterar.isbn">

                            <label for="titulo">Titulo: </label>
                            <input id="titulo" name="titulo" class="form-control" v-model="livroAlterar.titulo">
                            
                            <label for="edicao">Edição: </label>
                            <input id="edicao" name="edicao" class="form-control" v-model="livroAlterar.edicao">
                            
                            <label for="editora">Editora: </label>
                            <select id="editora" name="editora" class="form-control" v-model="livroAlterar.editora">
                                <option v-for="editora in editoras" :key="editora.id" v-bind:value="editora">{{editora.nome}}</option>
                            </select>
                            
                            <label for="numpaginas">N° de Paginas: </label>
                            <input id="numpaginas" name="numpaginas" class="form-control" v-model="livroAlterar.numpaginas">
                            
                            <label for="autores">Autores: </label>
                            <select id="autores" name="autores" class="form-control" multiple="multiple" v-model="livroAlterar.autores">
                                <option v-for="autor in autores" :key="autor.id" v-bind:value="autor">{{autor.nome}}</option>
                            </select>
                            
                            <input id="id" name="id" value="" type="hidden">
                            <input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
                            <button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarAlteraçaoLivro()">Salvar Alterações</button>
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
  name: "Livros",
  data() {
    return {
      livros: [],
      editoras:[],
      autores: [],
      livro: {
        id:"",
        isbn:"",
        titulo:"",
        edicao:"",
        editora:{
            id:"",
            nome:""
        },
        autores:[],
        numpaginas:""
        },
        livroAlterar: {
            id:"",
            isbn:"",
            titulo:"",
            edicao:"",
            editora:{
                id:"",
                nome:""
            },
            autores:[],
            numpaginas:""
        }
    };
  },
  mounted() {
    axios({ method: "GET", url: "http://localhost:8090/livros" }).then(
      result => {
        this.livros = result.data;
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
    carregarListaLivros() {
      axios({ method: "GET", url: "http://localhost:8090/livros" }).then(
        result => {
          this.livros = result.data;
        },
        error => {
          console.error(error);
        }
      );
    },
    salvarLivro() {
      var modal = $("#modal-livro-salvar");
      modal.hide();
      axios({
        method: "POST",
        url: "http://localhost:8090/livros",
        data: this.livro,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
          this.carregarListaLivros();
          this.livro={
            id:"",
            isbn:"",
            titulo:"",
            edicao:"",
            editora:{
                id:"",
                nome:""
            },
            autores:[],
            numpaginas:""
            }
        },
        error => {
          console.error(error);
        }
      );
    },
    excluirLivro(id) {
      axios({
        method: "DELETE",
        url: "http://localhost:8090/livros/"+id
      }).then(
        result => {
          this.carregarListaLivros();
        },
        error => {
          console.error(error);
        }
      );
    },
    editarLivro(id) {
      var modal = $("#modal-livro-editar");
      axios({ method: "GET", url: "http://localhost:8090/livros/" + id }).then(
        result => {
          this.livroAlterar = result.data;
        },
        error => {
          console.error(error);
        }
      );
      modal.show();
    },
    salvarAlteraçaoLivro() {
      var modal = $("#modal-livro-editar");
		axios({
        method: "PUT",
        url: "http://localhost:8090/livros/" + this.livroAlterar.id,
        data: this.livroAlterar,
        headers: { "content-type": "application/json" }
      }).then(
        result => {
			this.carregarListaLivros();
			modal.hide();
            this.livroAlterar= {
                id:"",
                isbn:"",
                titulo:"",
                edicao:"",
                editora:{
                    id:"",
                    nome:""
                },
                autores:[],
                numpaginas:""
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
  var modalsalvar = $("#modal-livro-salvar");
  var isbn = modalsalvar.find('#isbn');
  var titulo = modalsalvar.find('#titulo');
  var edicao = modalsalvar.find('#edicao');
  var autores = modalsalvar.find('#autores');
  var editora = modalsalvar.find('#editora');
  var numpaginas = modalsalvar.find('#numpaginas');

  modalsalvar.on("shown.bs.modal", onModalShow);
  modalsalvar.on("hide.bs.modal", onModalClose);
  
  var modalalterar = $("#modal-livro-editar");
  modalalterar.on("shown.bs.modal", onModalShow);
  modalalterar.on("hide.bs.modal", onModalClose);

  function onModalShow() {
    isbn.focus();
  }

  function onModalClose() {
      isbn.val('');
      titulo.val('');
      edicao.val('');
      editora.val('');
      autores.val('');
      numpaginas.val('');
      this.livro={
          id:"",
          isbn:"",
          titulo:"",
          edicao:"",
          editora:{
              id:"",
              nome:""
          },
          autores:[],
          numpaginas:""
        }

  }
});
</script>