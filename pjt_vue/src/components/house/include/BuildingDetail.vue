<template>
  <div>
    <div
      id="roadview"
      style="
        width: 100%;
        height: 300px;
        min-height: 300px;
        max-height: 300px;
      "></div>
    <div style="font-weight: bold; font-size: 17px">
      {{ buildingDetail.apartmentName }}
    </div>
    <div class="text-left">건축 년도 : {{ buildingDetail.buildYear }}</div>
    <div class="text-left">주소 : {{ buildingDetail.address }}</div>
    <div class="text-left">총 거래 횟수 : {{ buildingDetailList.length }}</div>
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

  methods: {
    getRoadView() {
      var roadviewContainer = window.document.getElementById("roadview"); //로드뷰를 표시할 div
      var roadview = new window.kakao.maps.Roadview(roadviewContainer); //로드뷰 객체
      var roadviewClient = new window.kakao.maps.RoadviewClient(); //좌표로부터 로드뷰 파노ID를 가져올 로드뷰 helper객체

      var position = new window.kakao.maps.LatLng(
        this.buildingDetail.lat,
        // eslint-disable-next-line prettier/prettier
        this.buildingDetail.lng
      );

      // 특정 위치의 좌표와 가까운 로드뷰의 panoId를 추출하여 로드뷰를 띄운다.
      roadviewClient.getNearestPanoId(position, 50, function (panoId) {
        roadview.setPanoId(panoId, position); //panoId와 중심좌표를 통해 로드뷰 실행
      });
    },
  },

  mounted() {
    this.getRoadView();
  },

  computed: {
    ...mapState(houseStore, ["buildingDetail", "buildingDetailList"]),
  },

  watch: {
    buildingDetail: function (after, before) {
      console.log("건물 상세 정보========");
      console.log(before);
      console.log(after);
      console.log("=============");
      this.getRoadView();
    },
    immediate: true,
  },
};
</script>

<style scoped>
.no-scroll-board::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}
</style>
