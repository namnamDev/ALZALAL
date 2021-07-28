import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '@/App.vue'
import Login from '@/views/account/login.vue'
import Signup from '@/views/account/signup.vue'
import PageNotFound from '@/views/PageNotFound.vue'
import PasswordConfirm from '@/views/account/PasswordConfirm.vue'
import ProfilePage from '@/views/profile/ProfilePage.vue'
import UserModify from '@/views/account/modify.vue'
import followPage from '@/views/profile/followPage.vue'
import TargetProfilePage from '@/views/profile/targetProfilePage'
import debateList from '@/views/debate/debateList.vue'
import CreateArticle from '@/views/createArticle/CreateArticle.vue'
import Timeline from '@/views/article/Timeline.vue'
import QnA from '@/views/article/QnA.vue'
import introduce from '@/views/profile/introduce.vue'

Vue.use(VueRouter)


const routes = [
  {
    path: '/timeline',
    name: 'timeline',
    component: Timeline
  },
  {
    path: '/QnA',
    name: 'qna',
    component: QnA
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
    path: '/profilePage/introduce',
    name: 'introduce',
    component: introduce
  },
  {
    path: '/usermodify',
    name: 'usermodify',
    component: UserModify,
  },
  {
    path: '/debate',
    name: 'debate',
    component: debateList
  },
  {
    path: '/createArticle',
    name: 'createArticle',
    component: CreateArticle
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
