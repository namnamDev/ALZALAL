import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userEmail: '',
    userName: '',
  },
  mutations: {
    // state에 있는 변수에 값을 수정하거나 저장하기위해
    CREATE_USER_EMAIL: function(state, email) {
      state.userEmail = email
    }
  },
  actions: {
    // 요청해서 받아온값 mutation으로
    login: function({commit}, email) {
      commit("CREATE_USER_EMAIL", email)
    }
  },
  getters: {
    // state에 있는 정보를 가져오는 역할
  },
  modules: {
  }
})
