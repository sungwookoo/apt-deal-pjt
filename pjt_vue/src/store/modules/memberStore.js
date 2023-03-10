/* eslint-disable prettier/prettier */
import jwtDecode from "jwt-decode";
import router from "@/router";
import {
  login,
  snsLogin,
  snsConnect,
  findById,
  tokenRegeneration,
  logout,
  searchUserList,
  getUserDetail,
  createUser,
  findPassword,
  updateUser,
  deleteUser,
  snsUnconnect,
} from "@/api/member";

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    isValidToken: false,
    userInfo: null,
    userDetailInfo: null,
    userList: null,
    detailUserId: "",
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
    checkToken: function (state) {
      return state.isValidToken;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_IS_VALID_TOKEN: (state, isValidToken) => {
      state.isValidToken = isValidToken;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.userInfo = userInfo;
    },
    SET_USER_DETAIL_INFO: (state, userDetailInfo) => {
      state.userDetailInfo = userDetailInfo;
    },
    SET_USER_LIST: (state, userList) => {
      state.userList = userList;
    },
    SET_DETAIL_USERID: (state, userid) => {
      console.log("mutation 설정 : " + userid);
      state.detailUserId = userid;
    },
    INIT_DETAIL_USERID: (state) => {
      state.detailUserId = "";
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["access-token"];
            let refreshToken = data["refresh-token"];
            // console.log("login success token created!!!! >> ", accessToken, refreshToken);
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            commit("SET_IS_VALID_TOKEN", true);
            sessionStorage.setItem("access-token", accessToken);
            sessionStorage.setItem("refresh-token", refreshToken);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userSnsConfirm({ commit }, payload) {
      await snsLogin(
        payload.userId,
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["access-token"];
            let refreshToken = data["refresh-token"];
            // console.log("login success token created!!!! >> ", accessToken, refreshToken);
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            commit("SET_IS_VALID_TOKEN", true);
            sessionStorage.setItem("access-token", accessToken);
            sessionStorage.setItem("refresh-token", refreshToken);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
            commit("SET_IS_VALID_TOKEN", false);
            payload.failCallback();
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userSnsConnect({ dispatch, state }, payload) {
      await snsConnect(
        payload.info,
        ({ data }) => {
          if (data.message === "success") {
            dispatch("setDetailUser", state.userInfo.userId);
            router.push({ name: "UserDetail" });
          } else {
            payload.failCallback();
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userSnsUnconnect(context, userId) {
      await snsUnconnect(
        userId,
        ({ status }) => {
          if (status == 200) {
            console.log("연동 해제");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getUserInfo({ commit, dispatch }, token) {
      let decodeToken = jwtDecode(token);
      // console.log("2. getUserInfo() decodeToken :: ", decodeToken);
      await findById(
        decodeToken.userid,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_USER_INFO", data.userInfo);
            // console.log("3. getUserInfo data >> ", data);
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        async (error) => {
          console.log(
            "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
            error.response.status
          );
          commit("SET_IS_VALID_TOKEN", false);
          await dispatch("tokenRegeneration");
        }
      );
    },
    async tokenRegeneration({ commit, state }) {
      console.log(
        "토큰 재발급 >> 기존 토큰 정보 : {}",
        sessionStorage.getItem("access-token")
      );
      await tokenRegeneration(
        JSON.stringify(state.userInfo),
        ({ data }) => {
          if (data.message === "success") {
            let accessToken = data["access-token"];
            console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
            sessionStorage.setItem("access-token", accessToken);
            commit("SET_IS_VALID_TOKEN", true);
          }
        },
        async (error) => {
          // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
          if (error.response.status === 401) {
            console.log("갱신 실패");
            // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
            await logout(
              state.userInfo.userid,
              ({ data }) => {
                if (data.message === "success") {
                  console.log("리프레시 토큰 제거 성공");
                } else {
                  console.log("리프레시 토큰 제거 실패");
                }
                alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
                commit("SET_IS_LOGIN", false);
                commit("SET_USER_INFO", null);
                commit("SET_IS_VALID_TOKEN", false);
                router.push({ name: "login" });
              },
              (error) => {
                console.log(error);
                commit("SET_IS_LOGIN", false);
                commit("SET_USER_INFO", null);
              }
            );
          }
        }
      );
    },
    async userLogout({ commit }, userid) {
      await logout(
        userid,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_IS_LOGIN", false);
            commit("SET_USER_INFO", null);
            commit("SET_IS_VALID_TOKEN", false);
          } else {
            console.log("유저 정보 없음!!!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getUserList({ commit }, userid) {
      await searchUserList(
        userid,
        ({ data }) => {
          if (data.message === "success") {
            commit("SET_USER_LIST", data.userList);
          } else {
            console.log("유저 목록 없음!!!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async getUserDetailInfo({ commit, dispatch }, userid) {
      console.log(userid);
      await getUserDetail(
        userid,
        ({ data, status }) => {
          if (status == 200) {
            console.log(data);
            commit("SET_USER_DETAIL_INFO", data);
          } else {
            dispatch("initDetailUser");
            router.push({ name: "Home" });
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userJoin(context, payload) {
      await createUser(
        payload.user,
        ({ status }) => {
          if (status == 200) {
            payload.callback();
          }
        },
        (error) => {
          console.log(error);
          alert("회원가입 실패.");
        }
      );
    },
    async pwdFind(context, payload) {
      await findPassword(
        payload.user,
        ({ status, data }) => {
          if (status == 200) {
            payload.successCallback(data);
          } else {
            payload.failCallback();
          }
        },
        (error) => {
          console.log(error);
          alert("서버 오류 발생!");
        }
      );
    },
    async userModify(context, user) {
      await updateUser(
        user,
        ({ status }) => {
          if (status == 200) {
            console.log("유저 정보 수정 완료. =============");
          } else {
            console.log("유저 정보 수정 실패. =============");
          }
        },
        (error) => {
          console.log(error);
          console.log("서버 오류 발생!===============");
        }
      );
    },
    async removeUser({ state, dispatch }, userId) {
      // 로그아웃 실행(본인 계정 삭제라면)
      if (state.isLogin && state.userInfo.userId == userId) {
        await dispatch("userLogout", userId);
      }

      // 회원 삭제 실행
      await deleteUser(
        userId,
        ({ status }) => {
          if (status == 200) {
            console.log(`유저 ${userId} 정보 삭제 완료.===========`);
          } else {
            console.log(`유저 ${userId} 정보 삭제 실패.===========`);
          }
        },
        (error) => {
          console.log(error);
          console.log("서버 오류!!!!!!!");
        }
      );
    },
    async setDetailUser({ commit }, userid) {
      console.log(userid + "============유저 설정");
      await commit("SET_DETAIL_USERID", userid);
    },

    async initDetailUser({ commit }) {
      await commit("INIT_DETAIL_USERID");
    },
  },
};

export default memberStore;
