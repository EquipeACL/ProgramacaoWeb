<template>
    
        <nav class="navbar navbar-default">
            <div class="container-fluid">
                <div class="navbar-header">
                <a class="navbar-brand" href="">Biblioteca UFAB</a>
                </div>
            <ul class="nav navbar-nav">
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Pessoas <span class="caret"></span></a>
                <ul class="dropdown-menu">
                    <li>
                        <router-link to="/alunos">
                            <a>Aluno</a>
                        </router-link>
                    </li>
                </ul>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Itens <span class="caret"></span></a>
                <ul class="dropdown-menu">
                <li>
                    <router-link to="/anais">
                        <a>Anais</a>
                    </router-link>
                </li>
                <li>
                    <router-link to="/jornais">
                        <a>Jornais</a>
                    </router-link>
                </li>
                <li>
                    <router-link to="/livros">
                        <a>Livros</a>
                    </router-link>
                </li>
                <li>
                    <router-link to="/midias">
                        <a>Midias Eletronicas</a>
                    </router-link>
                </li>
                <li>
                    <router-link to="/revistas">
                        <a>Revistas</a>
                    </router-link>
                </li>
                <li>
                    <router-link to="/tccs">
                        <a>TCC</a>
                    </router-link>
                </li>
                </ul>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">Outros <span class="caret"></span></a>
                <ul class="dropdown-menu">
                <li>
                    <router-link to="/autores">
                        <a>Autores</a>
                    </router-link>
                </li>
                <li>
                    <router-link to="/cursos">
                        <a>Cursos</a>
                    </router-link>
                </li>
                <li>
                    <router-link to="/editoras">
                        <a>Editoras</a>
                    </router-link>
                </li>
                <li>
                    <router-link to="/orientadores">
                        <a>Orientadores</a>
                    </router-link>
                </li>
                </ul>
            </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form action="${path}/sair" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <button id="btn-sair" type="submit" class="btn btn-default  glyphicon glyphicon-log-in"> Sair</button>
                    </form>
                </li>
            </ul>
        </div>
        </nav>
    
</template>

<script>
export default {
  name: "Menu"
};
</script>