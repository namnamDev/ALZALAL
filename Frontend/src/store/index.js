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
  },
  mutations: {
    // state에 있는 변수에 값을 수정하거나 저장하기위해
    CREATE_USER_EMAIL: function(state, payload) {
     //console.log("mut"+email)
      state.userEmail = payload.email
      state.isLogin = payload.isLogin
    },
    LOGOUT: function(state){
      state.isLogin = false
    }

  },
  actions: {
    // 요청해서 받아온값 mutation으로
    login: function({commit}, payload) {
      //console.log("act"+email)
      commit("CREATE_USER_EMAIL", payload)
    },
    logout: function({commit}){
      commit("LOGOUT")
    } 

  },
  getters: {
    // state에 있는 정보를 가져오는 역할
    getEmail: function(state){
      //return state.userEmail;
      //console.log('asdf')
      //console.log("gett"+state.userEmail)
      return state.userEmail
    },
    isLogin: function(state) {
      return state.isLogin
    }

  },
  modules: {
  }
})
