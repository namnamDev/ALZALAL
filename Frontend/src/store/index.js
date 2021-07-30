import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex)

export default new Vuex.Store({
  plugins: [
    createPersistedState(),
  ],
  state: {
    userEmail: '',
    userName: '',
    isLogin: '',
    content:'',
  },
  mutations: {
    // state에 있는 변수에 값을 수정하거나 저장하기위해
    CREATE_USER_EMAIL: function(state, payload) {
      state.userEmail = payload.email
      state.isLogin = payload.isLogin
    },
    LOGOUT: function(state){
      state.isLogin = false
    },
    CREATE_CONTENT: function(state,content){
      state.content = content
    }

  },
  actions: {
    // 요청해서 받아온값 mutation으로
    login: function({commit}, payload) {
      commit("CREATE_USER_EMAIL", payload)
    }, 
    logout: function({commit}){
      commit("LOGOUT")
    },
    create_content: function({commit}, content){
      commit("CREATE_CONTENT", content)
    }

  },
  getters: {
    // state에 있는 정보를 가져오는 역할
    getEmail: function(state){
      return state.userEmail
    },
    isLogin: function(state){
      return state.isLogin
    },
    getContent: function(state){
      return state.content
    }

  },
  modules: {
  }
})
