import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import Member from '@/components/Member'
import Profile from '@/components/Profile'

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: 'onAccountX',
  routes: [
    // {
    //   path: '/',
    //   redirect: '/home',
    //   name: 'Home',
    //   component: Home,
    //   children: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/ui/member',
      name: 'Member',
      component: Member
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile
    }
    //   ]
    // }
  ]
})
