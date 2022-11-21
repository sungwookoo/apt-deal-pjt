import {
  sidoNames,
  gugunNames,
  dongNames,
  buildingInfo,
  buildingDetail,
  interestArea,
  insertInterest,
  deleteInterest,
  dealAvg,
} from "@/api/house";

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

    // 건물 정보
    buildingList: [],
    buildingDetail: null,
    buildingDetailList: [],

    // 관심 지역 정보
    interestArea: [],

    // 동별 매매 평균
    avgDealList: [],
  },
  getters: {},
  mutations: {
    // 주소 정보
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

    // 건물 정보
    SET_BUILDING_LIST(state, buildingList) {
      state.buildingList = buildingList;
    },
    SET_BUILDING_DETAIL(state, buildingDetail) {
      state.buildingDetail = buildingDetail;
    },
    SET_BUILDING_DETAIL_LIST(state, buildingDetailList) {
      for (let building of buildingDetailList) {
        building["dealDate"] =
          `${String(building["dealYear"])}-` +
          `${String(building["dealMonth"]).padStart(2, "0")}-` +
          `${String(building["dealDay"]).padStart(2, "0")}`;
      }
      state.buildingDetailList = buildingDetailList;
    },

    // 관심 지역 정보
    SET_INTEREST_AREA(state, interestArea) {
      state.interestArea = interestArea;
    },

    // 동별 매매 평균
    SET_AVG_DEAL(state, avgDealList) {
      state.avgDealList = [];
      for (let avgDeal of avgDealList) {
        let key = Object.keys(avgDeal)[0];
        state.avgDealList.push({ dongName: key, deal: avgDeal[key] });
      }
    },
  },
  actions: {
    // 지역 코드
    async getSidoNames({ commit }) {
      await sidoNames(
        ({ status, data }) => {
          if (status == 200) {
            commit("SET_SIDO_NAMES", data);
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
        // eslint-disable-next-line no-fallthrough
        case "gugun":
          commit("INIT_DONG_NAMES");
      }
    },

    // 건물 정보
    async getBuildingInfo({ commit }, dongCode) {
      await buildingInfo(
        dongCode,
        ({ status, data }) => {
          if (status == 200) {
            commit("SET_BUILDING_LIST", data);
          } else if (status == 204) {
            commit("SET_BUILDING_LIST", []);
          }
        },
        (error) => {
          console.log(error);
          console.log("서버 오류 ==============");
          // eslint-disable-next-line prettier/prettier
        }
      );
    },
    async initBuildinginfo({ commit }) {
      await commit("SET_BUILDING_LIST", []);
      await commit("SET_BUILDING_DETAIL", null);
      await commit("SET_BUILDING_DETAIL_LIST", []);
    },
    async getBuildingDetailInfo({ commit }, payload) {
      await commit("SET_BUILDING_DETAIL", payload.detail);

      await buildingDetail(
        payload.aptCode,
        ({ status, data }) => {
          if (status == 200) {
            commit("SET_BUILDING_DETAIL_LIST", data);
          } else if (status == 204) {
            commit("SET_BUILDING_DETAIL_LIST", []);
          }
        },
        (error) => {
          console.log(error);
          console.log("서버 오류!!");
          // eslint-disable-next-line prettier/prettier
        }
      );
    },

    // 관심 지역
    async getInterestArea({ commit }, userid) {
      await interestArea(
        userid,
        ({ status, data }) => {
          console.log(data);
          if (status == 200) commit("SET_INTEREST_AREA", data);
          else commit("SET_INTEREST_AREA", []);
        },
        (error) => {
          console.log(error);
          // eslint-disable-next-line prettier/prettier
        }
      );
    },
    async createInterestArea({ dispatch }, info) {
      await insertInterest(
        info,
        async ({ status }) => {
          if (status == 200) await dispatch("getInterestArea", info.userId);
        },
        (error) => {
          console.log(error);
          console.log("서버 오류!!!");
          // eslint-disable-next-line prettier/prettier
        }
      );
    },
    async deleteInterestArea({ dispatch }, info) {
      await deleteInterest(
        info,
        async ({ status }) => {
          if (status == 200) {
            alert("관심 지역 삭제 완료!");
            dispatch("getInterestArea", info.userId);
          }
        },
        (error) => {
          console.log(error);
          // eslint-disable-next-line prettier/prettier
        }
      );
    },

    // 동별 매매 평균가
    async getAvgDeal({ commit }, dongCode) {
      await dealAvg(
        dongCode,
        ({ status, data }) => {
          if (status == 200) commit("SET_AVG_DEAL", data);
          else commit("SET_AVG_DEAL", []);
        },
        (error) => {
          console.log(error);
          // eslint-disable-next-line prettier/prettier
        }
      );
    },
    async initAvgDeal({ commit }) {
      await commit("SET_AVG_DEAL", []);
    },
  },
};
export default houseStore;
