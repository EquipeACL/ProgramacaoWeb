// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import Menu from './components/Menu'
import CadastrarAluno from './components/CadastrarAluno'
Vue.config.productionTip = false
Vue.component('Menu',Menu)
Vue.component('CadastrarAluno',CadastrarAluno)

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
