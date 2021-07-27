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
  },
  mutations: {
    // state에 있는 변수에 값을 수정하거나 저장하기위해
    CREATE_USER_EMAIL: function(state, email) {
     //console.log("mut"+email)
      state.userEmail = email
    },

  },
  actions: {
    // 요청해서 받아온값 mutation으로
    login: function({commit}, email) {
      //console.log("act"+email)
      commit("CREATE_USER_EMAIL", email)
    }, 

  },
  getters: {
    // state에 있는 정보를 가져오는 역할
    getEmail: function(state){
      //return state.userEmail;
      //console.log('asdf')
      //console.log("gett"+state.userEmail)
      return state.userEmail
    },

  },
  modules: {
  }
})
