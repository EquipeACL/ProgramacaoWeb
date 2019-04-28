import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Editoras from '@/components/Editoras'
import Orientadores from '@/components/Orientadores'
import Jornais from '@/components/Jornais'
import Autores from '@/components/Autores'
import Anais from '@/components/Anais'
import Tccs from '@/components/Tccs'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
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
    }
  ]
})
