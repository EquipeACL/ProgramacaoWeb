<template>
    <div class="container-fluid">
    <Menu></Menu> 
    <div class="table-responsive">
    <table class="table table-hover table-condensed table-striped table-bordered">
        <thead>
            <tr>
                <th style="width: 5%">#</th>
                <th style="width: 5%">CPF</th>
                <th style="width: 5%">RG</th>
                <th style="width: 10%">Nome</th>
                <th style="width: 5%">Naturaliade</th>
                <th style="width: 10%">Endereco</th>
                <th style="width: 5%">Telefone</th>
                <th style="width: 5%">Nascimento</th>
                <th style="width: 5%">Senha</th>
                <th style="width: 5%">Email</th>
                <th style="width: 5%">Matricula</th>
                <th style="width: 10%">Nome da Mãe</th>
                <th style="width: 5%">Curso</th>
                <th style="width: 5%">Ano</th>
                <th style="width: 5%">Periodo</th>
                <th style="width: 5%">Editar</th>
                <th style="width: 5%">Deletar</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="aluno in alunos" :key="aluno.id">
                <td>{{aluno.id}}</td>
                <td>{{aluno.cpf}}</td>
                <td>{{aluno.rg}}</td>
                <td>{{aluno.nome}}</td>
                <td>{{aluno.naturalidade}}</td>
                <td>{{aluno.endereco}}</td>
                <td>{{aluno.telefone}}</td>
                <td>{{aluno.datanascimento}}</td>
                <td>{{aluno.senha}}</td>
                <td>{{aluno.email}}</td>
                <td>{{aluno.matricula}}</td>
                <td>{{aluno.nomemae}}</td>
                <td>{{aluno.curso.nome}}</td>
                <td>{{aluno.ano}}</td>
                <td>{{aluno.periodo}}</td>
                <td><button type="button" class="btn btn-warning btn-editar" data-toggle="modal" data-target="#modal-aluno-editar" v-on:click="editarAluno(aluno.id)">Editar</button></td>
                <td><button type="button" class="btn btn-danger btn-deletar" v-on:click="excluirAluno(aluno.id)">Deletar</button></td>
            </tr>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="17">Alunos cadastrados: <span id="quantidade-alunos">{{alunos.length}}</span></td>
            </tr>
            <tr>
                <td colspan="17">
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#modal-aluno">Cadastrar Aluno</button>
                </td>
            </tr>
        </tfoot>
    </table>
    </div>
    <div class="modal fade" id="modal-aluno" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Adicionar Aluno</h4>
				</div>
				<div class="modal-body">
					<label for="cpf">CPF: </label>
					<input id="cpf" name="cpf" class="form-control" v-model="aluno.cpf">
					
					<label for="rg">RG: </label>
					<input id="rg" name="rg" class="form-control" v-model="aluno.rg">
					
					<label for="nome">Nome: </label>
					<input id="nome" name="nome" class="form-control" v-model="aluno.nome">
					
					<label for="naturalidade">Naturalidade: </label>
					<input id="naturalidade" name="naturalidade" class="form-control" v-model="aluno.naturalidade">
					
					<label for="endereco">Endereço: </label>
					<input id="endereco" name="endereco" class="form-control" v-model="aluno.endereco">
					
					<label for="telefone">Telefone: </label>
					<input id="telefone" name="telefone" class="form-control" v-model="aluno.telefone">
					
					<label for="datanascimento">Data de Nascimento: </label>
					<input id="datanascimento" name="datanascimento" class="form-control" type="date" v-model="aluno.datanascimento">
					
					<label for="senha">Senha: </label>
					<input id="senha" name="senha" class="form-control" type="password" v-model="aluno.senha">
					
					<label for="email">Email: </label>
					<input id="email" name="email" class="form-control" type="email" placeholder="example@mail.com" v-model="aluno.email">
					
					<label for="nomemae">Nome da Mãe: </label>
					<input id="nomemae" name="nomemae" class="form-control" v-model="aluno.nomemae">
					
					<label for="curso">Curso: </label>
					<select id="curso" name="curso" class="form-control" v-model="aluno.curso">
						<option v-for="curso in cursos" :key="curso.id" v-bind:value="curso">{{curso.nome}}</option>
					</select>
					
					<label for="ano">Ano: </label>
					<input id="ano" name="ano" class="form-control" v-model="aluno.ano">
					
					<label for="periodo">Periodo: </label>
					<select id="periodo" name="periodo" class="form-control" v-model="aluno.periodo">
						<option v-for="periodo in periodos" :key="periodo" v-bind:value="periodo">{{periodo}}</option>
					</select>
					
					<input id="id" name="id" value="" type="hidden">
					<input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarAluno()">Salvar Informações</button>
                </div>
                
                </div>
            </div>
        </div>
    <div class="modal fade" id="modal-aluno-editar" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">			
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title">Alterar Aluno</h4>
				</div>
				<div class="modal-body">
					<label for="cpfA">CPF: </label>
					<input id="cpfA" name="cpfA" class="form-control" v-model="alunoAlterar.cpf">
					
					<label for="rgA">RG: </label>
					<input id="rgA" name="rgA" class="form-control" v-model="alunoAlterar.rg">
					
					<label for="nomeA">Nome: </label>
					<input id="nomeA" name="nomeA" class="form-control" v-model="alunoAlterar.nome">
					
					<label for="naturalidadeA">Naturalidade: </label>
					<input id="naturalidadeA" name="naturalidadeA" class="form-control" v-model="alunoAlterar.naturalidade">
					
					<label for="enderecoA">Endereço: </label>
					<input id="enderecoA" name="enderecoA" class="form-control" v-model="alunoAlterar.endereco">
					
					<label for="telefoneA">Telefone: </label>
					<input id="telefoneA" name="telefoneA" class="form-control" v-model="alunoAlterar.telefone">
					
					<label for="datanascimentoA">Data de Nascimento: </label>
					<input id="datanascimentoA" name="datanascimentoA" class="form-control" type="date" v-model="alunoAlterar.datanascimento">
					
					<label for="senhaA">Senha: </label>
					<input id="senhaA" name="senhaA" class="form-control" type="password" v-model="alunoAlterar.senha">
					
					<label for="emailA">Email: </label>
					<input id="emailA" name="emailA" class="form-control" type="email" placeholder="example@mail.com" v-model="alunoAlterar.email">
					
					<label for="nomemaeA">Nome da Mãe: </label>
					<input id="nomemaeA" name="nomemaeA" class="form-control" v-model="alunoAlterar.nomemae">
					
					<label for="cursoA">Curso: </label>
					<select id="cursoA" name="cursoA" class="form-control" v-model="alunoAlterar.curso">
						<option v-for="curso in cursos" :key="curso.id" v-bind:value="curso">{{curso.nome}}</option>
					</select>
					
					<label for="anoA">Ano: </label>
					<input id="anoA" name="anoA" class="form-control" v-model="alunoAlterar.ano">
					
					<label for="periodoA">Periodo: </label>
					<select id="periodoA" name="periodoA" class="form-control" v-model="alunoAlterar.periodo">
						<option v-for="periodo in periodos" :key="periodo" v-bind:value="periodo">{{periodo}}</option>
					</select>
					
					<input id="id" name="id" value="" type="hidden">
					<input id="csrf" name="_csrf" type="hidden" value="${_csrf.token}">
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Cancelar</button>
					<button id="btn-salvar" type="button" class="btn btn-primary" v-on:click="salvarAlteracaoAluno()">Salvar Alterações</button>
                </div>
                
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Alunos",
  data() {
    return {
      alunos:[],
      cursos: [],
      periodos:[],
      aluno: {
          id:"",
          nome: "",
          cpf:"",
          rg:"",
          naturalidade:"",
          endereco:"",
          telefone:"",
          data:"",
          senha:"",
          email:"",
          nomemae:"",
          curso:"",
          ano:"",
          periodo:""
      },
      alunoAlterar: {
          id:"",
          nome: "",
          cpf:"",
          rg:"",
          naturalidade:"",
          endereco:"",
          telefone:"",
          data:"",
          senha:"",
          email:"",
          nomemae:"",
          curso:"",
          ano:"",
          periodo:""
      }
    };
  },
  mounted() {
   axios({ method: "GET", url: "http://localhost:8090/alunos/periodos" }).then(
      result => {
        this.periodos = result.data;
      },
      error => {
        console.error(error);
      }
    ),
    axios({ method: "GET", url: "http://localhost:8090/alunos" }).then(
      result => {
        this.alunos = result.data;
      },
      error => {
        console.error(error);
      }
    ),
    axios({ method: "GET", url: "http://localhost:8090/cursos" }).then(
      result => {
        this.cursos = result.data;
      },
      error => {
        console.error(error);
      }
    );
  },
  methods: {
    carregarListaAlunos(){
        axios({ method: "GET", url: "http://localhost:8090/alunos" }).then(
      result => {
        this.alunos = result.data;
      },
      error => {
        console.error(error);
      }
    );
    },
    salvarAluno() {
		var modal = $("#modal-aluno");
  		modal.hide();
        axios({
            method: "POST",
            url: "http://localhost:8090/alunos",
            data: this.aluno,
            headers: { "content-type": "application/json" }
        }).then(
            result => {
              this.carregarListaAlunos();
            },
            error => {
			    console.error(error);
			}
      );
	},
    editarAluno(id){
        axios({ method: "GET", url: "http://localhost:8090/alunos/"+id }).then(
            result => {
                this.alunoAlterar = result.data;
            },
            error => {
                console.error(error);
            }
    );
    },
	salvarAlteracaoAluno() {
        var modal = $("#modal-aluno-editar");
        modal.hide();
        axios({
            method: "PUT",
            url: "http://localhost:8090/alunos/"+this.alunoAlterar.id,
            data: this.alunoAlterar,
            headers: { "content-type": "application/json" }
        }).then(
            result => {
                this.carregarListaAlunos();
			},
            error => {
			    console.error(error);
			}
      );
    },
    excluirAluno(id){
        axios({ method: "DELETE", url: "http://localhost:8090/alunos/"+id }).then(
            result => {
                 this.carregarListaAlunos();
            },
            error => {
                console.error(error);
            }
    );
    }
  }
};

$(function() {
  var modal = $("#modal-aluno-editar");
  var nome = modal.find("#nomeA");
  var cpf = modal.find("#cpfA");
  var rg = modal.find("#rgA");
  var naturalidade = modal.find("#naturalidadeA");
  var endereco = modal.find("#enderecoA");
  var telefone = modal.find("#telefoneA");
  var data = modal.find("#datanascimentoA");
  var senha = modal.find("#senhaA");
  var email = modal.find("#emailA");
  var nomemae = modal.find("#nomemaeA");
  var curso = modal.find("#cursoA");
  var ano = modal.find("#anoA");
  var periodo = modal.find("#periodoA");


  modal.on("shown.bs.modal", onModalShow);
  modal.on("hide.bs.modal", onModalClose);

  function onModalShow() {
    cpf.focus();
  }

  function onModalClose() {
    nome.val("");
    cpf.val("");
    rg.val("");
    naturalidade.val("");
    endereco.val("");
    telefone.val("");
    data.val("");
    senha.val("");
    email.val("");
    nomemae.val("");
    curso.val("");
    ano.val("");
    periodo.val("");
    this.alunoAlterar={
          id:"",
          nome: "",
          cpf:"",
          rg:"",
          naturalidade:"",
          endereco:"",
          telefone:"",
          data:"",
          senha:"",
          email:"",
          nomemae:"",
          curso:"",
          ano:"",
          periodo:""
      }

  }
});
</script>