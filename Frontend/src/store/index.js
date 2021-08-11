import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex)

export default new Vuex.Store({
  plugins: [
    createPersistedState({ storage: window.sessionStorage }),
  ],
  state: {
    userEmail: '',
    //userName: '',
    isLogin: '',
    articleDetail: null,
    articleComments: null,
    qnaList: null,
    searchArticle: null,
    helpmeDetail: null,
    helpComments: null,
    notify: false,
    notificationList: [],
    params: null,
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
    CREATE_HELPME_DETAIL: function(state, item) {
      state.helpmeDetail = item
    },
    CREATE_ARTICLE_COMMENT: function(state, comments){
      state.articleComments = comments
    },
    CREATE_QNA_LIST: function(state, qnalist){
      state.qnaList = qnalist
    },
    CREATE_SEARCH_ARTICLE: function(state, data){
      state.searchArticle = data
    },
    CREATE_HELPME_COMMENTS: function(state, item){
      state.helpComments = item
    },
    DELETE_ARTICLE_COMMENT(state){
      state.articleComments = []
    },
    DELETE_COMMENT: function(state, index){
      state.articleComments.splice(index,1)
    },
    CREATE_NOTIFY:function(state){
      state.notify = true
    },
    DELETE_NOTIFY: function(state){
      state.notify = false
    },
    CREATE_NOTIFICATION_LIST: function(state,notificationList){
      state.notificationList = state.notificationList.concat(notificationList)
    },
    DELETE_NOTIFICATION_LIST: function(state){
      state.notificationList = []
    },
    CREATE_SEARCH_PARAMS: function(state, params){
      state.params = params
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
    },
    createSearchArticle: function({commit},data){
      commit('CREATE_SEARCH_ARTICLE', data)
    },
    createHelpmeDetail: function({commit}, item) {
      commit("CREATE_HELPME_DETAIL", item)
    },
    createHelpmeComment: function({commit}, item){
      commit("CREATE_HELPME_COMMENTS",item)
    },
    deleteArticleComment: function({commit}){
      commit('DELETE_ARTICLE_COMMENT')
    },
    deleteComment: function(context, comment){
      const index = context.state.articleComments.indexOf(comment)
      context.commit('DELETE_COMMENT',index)
    },
    createNotify: function({commit}){
      commit("CREATE_NOTIFY")
    },
    deleteNotify: function({commit}){
      commit("DELETE_NOTIFY")
    },
    createNoticationList: function({commit}, notificationList){
      commit("CREATE_NOTIFICATION_LIST",notificationList)
    },
    deleteNotificationList:function({commit}){
      commit("DELETE_NOTIFICATION_LIST")
    },
    createSearchParams: function({commit}, params){
      commit("CREATE_SEARCH_PARAMS", params)
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
    getHelpmeDetail: function(state){
      return state.helpmeDetail
    },
    getArticleComments: function(state) {
      return state.articleComments
    },
    getQnaList: function(state){
      return state.qnaList
    },
    getSearchArticle: function(state){
      return state.searchArticle
    },
    getHelpmeComments: function(state){
      return state.helpComments
    },
    getNotify: function(state){
      return state.notify
    },
    getNotificationList: function(state){
      return state.notificationList
    },
    getSearchParams: function(state) {
      return state.params
    }
  },

  modules: {
  }
})
