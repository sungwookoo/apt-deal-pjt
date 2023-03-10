import Vue from "vue";
import Vuex from "vuex";
import http from "@/api/http-common";
import persistedState from "vuex-persistedstate";

// module import
import memberStore from "@/store/modules/memberStore";
import houseStore from "@/store/modules/houseStore";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    // 게시판
    boardList: [],
    board: {},
    searchNotice: [],
    searchQna: [],
  },
  getters: {
    // 게시판(공지사항, QnA)
    boardList(state) {
      return state.boardList;
    },
    board(state) {
      return state.board;
    },
    searchNotice(state) {
      return state.searchNotice;
    },
    searchQna(state) {
      return state.searchQna;
    },
    // 유저
    userInfo2(state) {
      return state.userInfo2;
    },
  },
  mutations: {
    // 게시판(공지사항, QnA)
    BOARDLIST(state, payload) {
      state.boardList = payload.boardList;
    },
    BOARD(state, payload) {
      state.board = payload.board;
    },
    SEARCHBOARD(state, payload) {
      const type = 0;
      let count = 0;
      for (let i = 0; i < payload.searchBoard.length; i++) {
        if (payload.searchBoard[i].type != type) break;
        count = count + 1;
      }
      state.searchNotice = payload.searchBoard.slice(0, count);
      state.searchQna = payload.searchBoard.slice(count);
    },
  },
  actions: {
    // 게시판 함수(공지사항, QnA)
    getBoardList(context, type) {
      http.get(`/board?type=${type}`).then((response) => {
        console.log(response);
        switch (response.status) {
          case 200:
            context.commit({
              type: "BOARDLIST",
              boardList: response.data,
            });
            break;
          case 204:
            context.commit({
              type: "BOARDLIST",
              boardList: [],
            });
            break;
          case 500:
            alert("서버 오류!");
            break;
        }
      });
    },
    createBoard(context, payload) {
      http.post(`/board`, payload.board).then((response) => {
        console.log(response);
        switch (response.status) {
          case 200:
            payload.callback();
            break;
          case 400:
            alert("잘못된 요청입니다.");
            break;
          case 500:
            alert("서버 오류!");
            break;
        }
      });
    },
    modifyBoard(context, payload) {
      http
        .put(`/board/${payload.board.boardNo}`, payload.board)
        .then((response) => {
          console.log(response);
          switch (response.status) {
            case 200:
              payload.callback();
              break;
            case 400:
              alert("잘못된 요청입니다.");
              break;
            case 500:
              alert("서버 오류!");
              break;
          }
        });
    },
    getBoard(context, boardNo) {
      http.get(`/board/${boardNo}`).then((response) => {
        console.log(response);
        context.commit({
          type: "BOARD",
          board: response.data,
        });
      });
    },
    getBoardSearch(context, payload) {
      http
        .get(
          // eslint-disable-next-line prettier/prettier
          `/board?type=${payload.type}&searchParam=${payload.searchParam}&searchType=${payload.selected}`
        )
        .then((response) => {
          console.log(response);
          switch (response.status) {
            case 200:
              context.commit({
                type: "BOARDLIST",
                boardList: response.data,
              });
              break;
            case 204:
              this.boardList = context.commit({
                type: "BOARDLIST",
                boardList: [],
              });
              break;
            case 400:
              alert("잘못된 요청입니다.");
              break;
            case 500:
              alert("서버 오류!");
              break;
          }
        });
    },
    deleteBoard(context, payload) {
      if (confirm("삭제하시겠습니까?")) {
        http.delete(`/board/${payload.boardNo}`).then((response) => {
          let msg = "삭제 처리시 문제가 발생했습니다.";
          if (response.status == "200") {
            msg = "삭제가 완료되었습니다.";
          }
          alert(msg);

          payload.callback();
        });
      }
    },
    getSearchBoard(context, keyword) {
      http.get(`/board/search?keyword=${keyword}`).then((response) => {
        console.log(response);
        context.commit({
          type: "SEARCHBOARD",
          searchBoard: response.data,
        });
      });
    },
  },
  modules: {
    memberStore,
    houseStore,
  },
  plugins: [
    persistedState({
      storage: sessionStorage,
    }),
  ],
});
