import Vue from 'vue'
import VueRouter from 'vue-router'
// import Home from '@/App.vue'
import Login from '@/views/account/login.vue'
import Signup from '@/views/account/signup.vue'
import PageNotFound from '@/views/PageNotFound.vue'
import PasswordConfirm from '@/views/account/PasswordConfirm.vue'
import ProfilePage from '@/views/profile/ProfilePage.vue'
import CreateArticle from '@/views/createArticle/CreateArticle.vue'
import Markdown4 from '@/views/createArticle/markdown4.vue'
import Viewer from '@/views/createArticle/viewer.vue'
import Timeline from '@/views/article/Timeline.vue'
import QnA from '@/views/article/QnA.vue'


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
    component: PasswordConfirm
  },
  {
    path: '/profilePage',
    name: 'profilePage',
    component: ProfilePage
  },
  {
    path: '/createArticle',
    name: 'createArticle',
    component: CreateArticle
  },
  {
    path: '/markdown4',
    name: 'Markdown4',
    component: Markdown4
  },
  {
    path: '/viewer',
    name: 'Viewer',
    component: Viewer
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
