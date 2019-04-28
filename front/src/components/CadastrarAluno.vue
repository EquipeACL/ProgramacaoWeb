<template>
    <div class="container-fluid">
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
    </div>
</template>

<script>
import axios from "axios";
export default {
  name: "CadastrarAluno",
  data() {
    return {
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
              
            },
            error => {
			    console.error(error);
			}
      );
	}
  }
};

$(function() {
  var modal = $("#modal-aluno");
  var nome = modal.find("#nome");
  var cpf = modal.find("#cpf");
  var rg = modal.find("#rg");
  var naturalidade = modal.find("#naturalidade");
  var endereco = modal.find("#endereco");
  var telefone = modal.find("#telefone");
  var data = modal.find("#datanascimento");
  var senha = modal.find("#senha");
  var email = modal.find("#email");
  var nomemae = modal.find("#nomemae");
  var curso = modal.find("#curso");
  var ano = modal.find("#ano");
  var periodo = modal.find("#periodo");


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
    this.aluno={
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