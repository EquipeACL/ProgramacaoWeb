/* eslint-disable */
<template>
    <div class="container-fluid">
        <section id="login" class="panel panel-primary">
			<div class="alert alert-warning" v-if="erro_login || erro_senha">
					Usuario e/ou senha incorretos!
			</div>
            
            <div class="alert alert-info" v-if="saiu">
				Você saiu do sistema!
			</div>			
		
			<div class="panel-heading">
				<h4 class="text-center">Biblioteca UFAB - Login</h4>
			</div>
				
			<div class="panel-body">
				<label for="usuario">Usuário</label>
				<input id="usuario" name="usuario" class="form-control" placeholder="usuario">
					
				<label for="senha">Senha</label>
				<input type="password" id="senha" name="senha" class="form-control" placeholder="*******">
				<br>
				<label>Não possui cadastro? </label><button class="btn btn-link" data-toggle="modal" data-target="#modal-aluno">Clique aqui</button>
			</div>
				
			<div class="panel-footer">
				<button id="btn-login" class="btn btn-primary">Entrar</button>
			</div>
				
				<input type="hidden" id="_csrf" name="_csrf" value="${_csrf.token}">
		</section>
		<CadastrarAluno></CadastrarAluno>
    </div>
</template>

<script>

import axios from "axios";
export default {
  name: "Login",
  data() {
    return {
	  erro_login : false,
	  erro_senha : false,
	  saiu : false
      }
    }
};

</script>

<style>
#login{
    width: 400px;
    margin-left: auto;
    margin-right: auto;
    margin-top: 100px;
}
			
#btn-login{
    width: 100%;
}
</style>

