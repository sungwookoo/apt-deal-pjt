import { sidoNames, gugunNames, dongNames } from "@/api/house";

const houseStore = {
  namespaced: true,
  state: {
    // 셀렉트 박스.
    // 시도 코드 얻기.
    sidoOptions: [{ dongCode: "", sidoName: "시/도 선택" }],
    // 구군 코드 얻기.
    gugunOptions: [{ dongCode: "", gugunName: "구/군 선택" }],
    // 동 코드 얻기.
    dongOptions: [{ dongCode: "", dongName: "동 선택" }],
  },
  getters: {},
  mutations: {
    SET_SIDO_NAMES(state, sidoOptions) {
      state.sidoOptions = [{ dongCode: "", sidoName: "시/도 선택" }];
      state.sidoOptions = state.sidoOptions.concat(sidoOptions);
    },
    SET_GUGUN_NAMES(state, gugunOptions) {
      state.gugunOptions = [{ dongCode: "", gugunName: "구/군 선택" }];
      state.gugunOptions = state.gugunOptions.concat(gugunOptions);
    },
    SET_DONG_NAMES(state, dongOptions) {
      state.dongOptions = [{ dongCode: "", dongName: "동 선택" }];
      state.dongOptions = state.dongOptions.concat(dongOptions);
    },
    INIT_GUGUN_NAMES(state) {
      state.gugunOptions = [{ dongCode: "", gugunName: "구/군 선택" }];
    },
    INIT_DONG_NAMES(state) {
      state.dongOptions = [{ dongCode: "", dongName: "동 선택" }];
    },
  },
  actions: {
    async getSidoNames({ commit }) {
      await sidoNames(
        ({ status, data }) => {
          if (status == 200) {
            commit("SET_SIDO_NAMES", data);
            console.log(data);
          }
        },
        (error) => {
          console.log(error);
          console.log("서버 오류!!!!");
          // eslint-disable-next-line prettier/prettier
        }
      );
    },
    async getGugunNames({ commit }, sidoCode) {
      await gugunNames(
        sidoCode,
        ({ status, data }) => {
          if (status == 200) {
            commit("SET_GUGUN_NAMES", data);
          }
        },
        (error) => {
          console.log(error);
          console.log("서버 오류!!!");
          // eslint-disable-next-line prettier/prettier
        }
      );
    },
    async getDongNames({ commit }, gugunCode) {
      await dongNames(
        gugunCode,
        ({ status, data }) => {
          if (status == 200) {
            commit("SET_DONG_NAMES", data);
          }
        },
        (error) => {
          console.log(error);
          console.log("서버 오류!!!");
          // eslint-disable-next-line prettier/prettier
        }
      );
    },
    async initOptions({ commit }, name) {
      switch (name) {
        case "sido":
          commit("INIT_GUGUN_NAMES");
          break;
        case "gugun":
          commit("INIT_DONG_NAMES");
      }
    },
  },
};
export default houseStore;
