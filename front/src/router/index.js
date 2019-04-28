import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Editoras from '@/components/Editoras'
import Orientadores from '@/components/Orientadores'
import Jornais from '@/components/Jornais'
import Autores from '@/components/Autores'
import Anais from '@/components/Anais'
import Tccs from '@/components/Tccs'
import Midias from '@/components/Midias'
import Cursos from '@/components/Cursos'
import Revistas from '@/components/Revistas'
import Livros from '@/components/Livros'
import Alunos from '@/components/Alunos'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/editoras',
      name: 'Editoras',
      component: Editoras
    },
    {
      path: '/orientadores',
      name: 'Orientadores',
      component: Orientadores
    },
    {
      path: '/jornais',
      name: 'Jornais',
      component: Jornais
    },
    {
      path: '/autores',
      name: 'Autores',
      component: Autores
    },
    {
      path: '/anais',
      name: 'Anais',
      component: Anais
    },
    {
      path: '/tccs',
      name: 'Tccs',
      component: Tccs
    },
    {
      path: '/midias',
      name: 'Midias',
      component: Midias
    },
    {
      path: '/cursos',
      name: 'Cursos',
      component: Cursos
    },
    {
      path: '/revistas',
      name: 'Revistas',
      component: Revistas
    },
    {
      path: '/livros',
      name: 'Livros',
      component: Livros
    },
    {
      path: '/alunos',
      name: 'Alunos',
      component: Alunos
    }
  ]
})
