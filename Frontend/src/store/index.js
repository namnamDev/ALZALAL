import Vue from "vue";
import Vuex from "vuex";
import createPersistedState from "vuex-persistedstate";
import webSocketVuex from "./webSocket";
import mainVuex from "./mainVuex";

Vue.use(Vuex);

export default new Vuex.Store({
  plugins: [
    createPersistedState({
      storage: window.sessionStorage,
      //소켓정보는 저장되지 않게 하려고 모듈을 나눔
      paths: ["mainVuex"],
    }),
  ],
  state: {},

  mutations: {
    // state에 있는 변수에 값을 수정하거나 저장하기위해
  },

  actions: {
    // 요청해서 받아온값 mutation으로
  },

  getters: {
    // state에 있는 정보를 가져오는 역할
  },

  modules: { webSocketVuex, mainVuex },
});
