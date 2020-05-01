import Vue from 'vue'
import Router from 'vue-router'
const App = () => import(/* webpackChunkName: "chunk-base" */ '@/App.vue')
const Member = () => import(/* webpackChunkName: "chunk-base" */ '@/components/Member.vue')
const Account = () => import(/* webpackChunkName: "chunk-base" */ '@/components/Account.vue')
const Subject = () => import(/* webpackChunkName: "chunk-base" */ '@/components/Subject.vue')
const Journal = () => import(/* webpackChunkName: "chunk-base" */ '@/components/Journal.vue')
const Report = () => import(/* webpackChunkName: "chunk-base" */ '@/components/Report.vue')
const Profile = () => import(/* webpackChunkName: "chunk-base" */ '@/components/Profile.vue')

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
      component: App,
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
      name: 'Member',
      component: Member
    },
    {
      path: '/account',
      name: 'Account',
      component: Account
    },
    {
      path: '/subject',
      name: 'Subject',
      component: Subject
    },
    {
      path: '/journal',
      name: 'Journal',
      component: Journal
    },
    {
      path: '/report',
      name: 'Report',
      component: Report
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile
    }
  ]
})
