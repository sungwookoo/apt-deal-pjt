<template>
  <div>
    <b-row class="m-3" align-h="center">
      <b-col cols="8">
        <b-input-group>
          <b-form-select
            v-model="sidoSelected"
            :options="sidoOptions"
            value-field="dongCode"
            text-field="sidoName"></b-form-select>
          <b-form-select
            class="ml-3"
            v-model="gugunSelected"
            :options="gugunOptions"
            value-field="dongCode"
            text-field="gugunName"></b-form-select>
          <b-form-select
            class="ml-3"
            v-model="dongSelected"
            :options="dongOptions"
            value-field="dongCode"
            text-field="dongName"></b-form-select>
          <b-form-group
            v-if="isLogin"
            class="ml-3"
            :state="!interest"
            :invalid-feedback="feedback">
            <b-button
              variant="outline-success"
              @click="validate"
              :disabled="interest">
              <b-icon-star-fill style="color: #ffd400"></b-icon-star-fill>
              관심 지역
            </b-button>
          </b-form-group>
        </b-input-group>
      </b-col>
    </b-row>
    <b-row class="mt-2" style="max-height: 600px; height: 600px; border: solid">
      <b-col class="m-1" style="max-height: 100%; border: solid">
        <div><h2>건물 정보</h2></div>
        <div
          v-if="buildingList.length > 0"
          style="height: 520px; overflow: scroll"
          class="no-scroll-board">
          <building-row
            class="mt-1"
            v-for="(building, index) in buildingList"
            :key="building.aptCode + index"
            :building="building">
          </building-row>
        </div>
        <div v-else>건물 정보가 없습니다.</div>
      </b-col>
      <b-col class="m-1" style="max-height: 100%; border: solid">
        <div><h2>상세 정보</h2></div>
        <div v-if="buildingDetail">
          <building-detail></building-detail>
        </div>
        <div v-else>건물을 선택해 주세요.</div>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
const houseStore = "houseStore";
const memberStore = "memberStore";
export default {
  components: {
    "building-row": () => import("@/components/house/include/BuildingRow.vue"),
    "building-detail": () =>
      import("@/components/house/include/BuildingDetail.vue"),
  },

  data() {
    return {
      sidoSelected: "",
      gugunSelected: "",
      dongSelected: "",
      interest: false,
      isFromInterest: false,
      feedback: "",
    };
  },

  created() {
    let param = this.$route.params.code;
    if (param) {
      this.isFromInterest = true;
      this.sidoSelected = param.substring(0, 2).padEnd(10, "0");
      this.gugunSelected = param.substring(0, 5).padEnd(10, "0");
      this.dongSelected = param;
      console.log("================");
      console.log(this.dongSelected);
      console.log(this.gugunSelected);
      console.log(this.sidoSelected);
      console.log("================");
    } else {
      this.getSido();
      this.initBuilding();
    }
    if (this.isLogin) {
      this.getInterestArea(this.userInfo.userId);
    }
  },

  methods: {
    ...mapActions(houseStore, [
      "getSidoNames",
      "getGugunNames",
      "getDongNames",
      "initOptions",
      "getBuildingInfo",
      "initBuildinginfo",
      "getBuildingDetailInfo",
      "getInterestArea",
      "createInterestArea",
    ]),

    // 주소 코드 얻기
    async getSido() {
      await this.getSidoNames();
    },
    async getGugun() {
      await this.getGugunNames(this.sidoSelected);
      if (!this.isFromInterest) this.gugunSelected = "";
    },
    async getDong() {
      await this.getDongNames(this.gugunSelected);
      if (!this.isFromInterest) this.dongSelected = "";
    },
    async initSelected(name) {
      await this.initOptions(name);
      switch (name) {
        case "sido":
          this.gugunSelected = "";
        // eslint-disable-next-line no-fallthrough
        case "gugun":
          this.dongSelected = "";
      }
    },

    async validate() {
      if (this.dongSelected == "") {
        alert("동을 선택해 주세요.");
      } else {
        const info = {
          userId: this.userInfo.userId,
          code: this.dongSelected,
        };
        await this.createInterestArea(info);
        this.interest = true;
      }
    },

    // 건물 정보 얻기
    async getBuilding() {
      await this.getBuildingInfo(this.dongSelected);
      this.isFromInterest = false;
      this.$route.params.code = null;
    },
    async initBuilding() {
      await this.initBuildinginfo();
    },
  },

  computed: {
    ...mapState(houseStore, [
      "sidoOptions",
      "gugunOptions",
      "dongOptions",
      "buildingList",
      "buildingDetail",
      "interestArea",
    ]),
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },

  watch: {
    sidoSelected: function (value) {
      console.log("시도 코드 : " + value);
      if (value) {
        this.getGugun();
      } else {
        if (!this.isFromInterest) this.initSelected("sido");
      }
    },
    gugunSelected: function (value) {
      console.log("구군 코드 : " + value);
      if (value) {
        this.getDong();
      } else {
        if (!this.isFromInterest) this.initSelected("gugun");
      }
    },
    dongSelected: function (value) {
      console.log("동 코드 : " + value);
      this.interest = false;
      if (value) {
        console.log("매물 표시");
        if (this.interestArea.length < 10) {
          for (let { dongCode } of this.interestArea) {
            if (String(dongCode) == value) {
              console.log("확인!");
              this.interest = true;
              this.feedback = "이미 추가된 지역입니다";
              break;
            }
          }
        } else {
          this.interest = true;
          this.feedback = "관심 지역 상한입니다";
        }
        this.getBuilding();
      } else {
        // 건물 정보 초기화.
        this.initBuilding();
      }
    },
  },
};
</script>

<style scoped>
.no-scroll-board::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}
</style>
