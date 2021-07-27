import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '@/App.vue'
import Login from '@/views/account/login.vue'
import Signup from '@/views/account/signup.vue'
import PageNotFound from '@/views/PageNotFound.vue'
import PasswordConfirm from '@/views/account/PasswordConfirm.vue'
import ProfilePage from '@/views/profile/ProfilePage.vue'
import UserModify from '@/views/account/modify.vue'
import Feed from '@/views/feed/Feed.vue'
import followPage from '@/views/profile/followPage.vue'
import TargetProfilePage from '@/views/profile/targetProfilePage'
//import store from '../store'

Vue.use(VueRouter)


const routes = [
  {
    path: '/feed',
    name: 'feed',
    component: Feed
  },
  {
    path: '/login',
    name: 'login',
    component: Login
  },
  {
    path: '/signup',
    name: 'signup',
    component: Signup
  },
  {
    path: '/passwordConfirm',
    name: 'passwordConfirm',
    component: PasswordConfirm,
  },
  {
    path: '/profilePage',
    name: 'profilePage',
    component: ProfilePage,
  },
  {
    path: '/targetProfilePage',
    name: 'targetProfilePage',
    component: TargetProfilePage
  },
  {
    path: '/profilePage/followPage',
    name : 'followPage',
    component: followPage
  },
  {
    path: '/usermodify',
    name: 'usermodify',
    component: UserModify,
  },
  {
    path: '*',
    name: 'PageNotFound',
    component: PageNotFound
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
