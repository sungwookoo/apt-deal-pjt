<template>
  <div>
    <div>건물명 : {{ buildingDetail.apartmentName }}</div>
    <div>건물 년도 : {{ buildingDetail.buildYear }}</div>
    <div>건물 주소 : {{ buildingDetail.address }}</div>
    <div>총 거래 횟수 : {{ buildingDetailList.length }}</div>
    <b-table
      sticky-header="400px"
      :items="buildingDetailList"
      :fields="detailField"
      class="no-scroll-board"></b-table>
  </div>
</template>

<script>
import { mapState } from "vuex";
const houseStore = "houseStore";
export default {
  data() {
    return {
      detailField: [
        { key: "dealDate", label: "날짜" },
        { key: "dealAmount", label: "가격(만 원)" },
        { key: "area", label: "면적" },
      ],
    };
  },

  computed: {
    ...mapState(houseStore, ["buildingDetail", "buildingDetailList"]),
  },
};
</script>

<style scoped>
.no-scroll-board::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}
</style>
