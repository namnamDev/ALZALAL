const webSocketVuex = {
  state: {
    socketConnection: null,
  },

  mutations: {
    // state에 있는 변수에 값을 수정하거나 저장하기위해
    CREATE_SOCKETCONNECTION: function(state, params) {
      state.socketConnection = params;
    },
  },

  actions: {
    // 요청해서 받아온값 mutation으로
    createSocketConnection: function({ commit }, params) {
      commit("CREATE_SOCKETCONNECTION", params);
    },
  },

  getters: {
    // state에 있는 정보를 가져오는 역할
    getSocketConnection: function(state) {
      return state.socketConnection;
    },
  },
};

export default webSocketVuex;
