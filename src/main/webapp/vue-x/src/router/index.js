import Vue from 'vue'
import Router from 'vue-router'
const App = () => import(/* webpackChunkName: "chunk-base" */ '../App.vue')
const Member = () => import(/* webpackChunkName: "chunk-base" */ '../components/Member.vue')
const Profile = () => import(/* webpackChunkName: "chunk-base" */ '../components/Profile.vue')

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: 'onAccountX',
  routes: [
    /*
    {
      path: '/',
      name: 'Root',
      component: Auth
    },
    */
    {
      path: '/',
      redirect: '/home',
      name: 'App',
      children: [
        {
          path: '/home',
          name: 'Home',
          component: App
        }
      ]
    },
    {
      path: '/member',
      name: 'member',
      component: Member
    },
    {
      path: '/profile',
      name: 'profile',
      component: Profile
    }
  ]
})
