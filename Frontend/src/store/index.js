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
    //userName: '',
    isLogin: '',
    articleDetail: null,
    articleComments: null,
    qnaList: null,
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
    CREATE_ARTICLE_DETAIL: function(state, item) {
      state.articleDetail = item
    },
    CREATE_ARTICLE_COMMENT: function(state, comments){
      state.articleComments = comments
    },
    CREATE_QNA_LIST: function(state, qnalist){
      state.qnaList = qnalist
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
    createArticleDetail: function({commit}, item) {
      commit("CREATE_ARTICLE_DETAIL", item)
    },
    createArticleComment: function({commit}, comments) {
      commit("CREATE_ARTICLE_COMMENT", comments)
    },
    createQnaList: function({commit}, qnalist){
      commit('CREATE_QNA_LIST', qnalist)
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
    getArticleDetail: function(state){
      return state.articleDetail
    },
    getArticleComments: function(state) {
      return state.articleComments
    },
    getQnaList: function(state){
      return state.qnaList
    }
  },

  modules: {
  }
})
