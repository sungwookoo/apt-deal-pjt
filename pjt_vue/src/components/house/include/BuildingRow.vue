<template>
  <div style="border: solid" @click="getbuildingDetail">
    <div>
      {{
        building.apartmentName +
        "(" +
        building.lat.slice(0, 6) +
        ", " +
        building.lng.slice(0, 6) +
        ")"
      }}
    </div>
    <div>건축 년도 : {{ building.buildYear }}</div>
    <div>최대 매매 가격 : {{ building.dealAmount }}</div>
    <div>최근 매매 년도 : {{ building.dealYear }}</div>
    <div>최대 매매 면적 : {{ building.area }}</div>
  </div>
  <!-- 
    https://bootstrap-vue.org/play
    <div style="text-align:left; border:solid; padding:10px; width:60%;">
      <div style="text-align:center; font-weight:bold; font-size:24px">아파트 이름</div>
      <div>건축 년도 :</div>
      <div>최대 매매 가격 :</div>
      <div>최근 매매 년도 :</div>
      <div>최대 매매 면적 :</div>
    </div>
  -->
</template>

<script>
import { mapActions } from "vuex";
const houseStore = "houseStore";

export default {
  props: {
    building: Object,
  },

  data() {
    return {
      address: "",
    };
  },

  methods: {
    ...mapActions(houseStore, ["getBuildingDetailInfo"]),
    async getbuildingDetail() {
      const payload = {
        aptCode: this.building.aptCode,
        detail: {
          apartmentName: this.building.apartmentName,
          buildYear: this.building.buildYear,
          address: this.building.address,
          lat: this.building.lat,
          lng: this.building.lng,
        },
      };

      await this.getBuildingDetailInfo(payload);
    },
  },
};
</script>

<style></style>
