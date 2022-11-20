<template>
  <div>
    <b-container class="text-center justify-content-center">
      <b-row>
        <b-col id="map"></b-col>
      </b-row>
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
          </b-input-group>
        </b-col>
      </b-row>
      <b-row
        class="mt-2"
        style="max-height: 600px; height: 600px; border: solid">
        <b-col class="m-1" style="max-height: 100%; border: solid">
          <div><h2>매매 건물 정보</h2></div>
          <div
            v-if="buildingList.length > 0"
            style="height: 520px; overflow: scroll"
            class="no-scroll-board">
            <div
              v-for="(building, index) in buildingList"
              :key="building.aptCode + index"
              style="border: solid"
              @click="
                getbuildingDetail(
                  building.aptCode,
                  building.lat,
                  building.lng,
                  // eslint-disable-next-line prettier/prettier
                  building
                )
              ">
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
          </div>
          <div v-else>건물 정보가 없습니다.</div>
        </b-col>
        <b-col class="m-1" style="max-height: 100%; border: solid">
          <div><h2>선택 건물 상세 정보</h2></div>
          <div v-if="buildingDetail.length > 0">
            <div>건물명 : {{ selectBuilding.apartmentName }}</div>
            <div>건물 년도 : {{ selectBuilding.buildYear }}</div>
            <div>
              건물 주소 : {{ selectBuilding.lat + ", " + selectBuilding.lng }}
            </div>
            <div>총 거래 횟수 : {{ buildingDetail.length }}</div>
            <b-table
              sticky-header="400px"
              :items="buildingDetail"
              :fields="detailField"
              class="no-scroll-board"></b-table>
          </div>
          <div v-else>
            <div>선택한 건물이 없습니다.</div>
          </div>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
const houseStore = "houseStore";
export default {
  name: "KakaoMap",
  data() {
    return {
      sidoSelected: "",
      gugunSelected: "",
      dongSelected: "",
      markers: [],
      infowindow: null,
      detailField: [
        { key: "dealDate", label: "날짜" },
        { key: "dealAmount", label: "가격(만 원)" },
        { key: "area", label: "면적" },
      ],
      selectBuilding: {},
    };
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=ba902ad6bd46a14c91017e0c322d2115";
      document.head.appendChild(script);
    }
  },
  created() {
    this.getSido();
    this.initBuilding();
    this.initMarker();
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
    ]),
    // 주소 코드 얻기
    async getSido() {
      await this.getSidoNames();
    },
    async getGugun() {
      await this.getGugunNames(this.sidoSelected);
      this.gugunSelected = "";
    },
    async getDong() {
      await this.getDongNames(this.gugunSelected);
      this.dongSelected = "";
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

    // 건물 정보 얻기
    async getBuilding() {
      await this.getBuildingInfo(this.dongSelected);
    },
    async initBuilding() {
      await this.initBuildinginfo();
    },
    async getbuildingDetail(aptCode, lat, lng, building) {
      this.moveMarker(lat, lng);
      this.selectBuilding["apartmentName"] = building.apartmentName;
      this.selectBuilding["buildYear"] = building.buildYear;
      this.selectBuilding["lat"] = building.lat;
      this.selectBuilding["lng"] = building.lng;

      await this.getBuildingDetailInfo(aptCode);
    },

    // 마커 관련 함수
    makeMarker() {
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }

      const positions = this.buildingList.map(
        // eslint-disable-next-line prettier/prettier
        ({ lat, lng }) => new kakao.maps.LatLng(lat, lng)
      );

      if (positions.length > 0) {
        this.markers = positions.map(
          (position) =>
            new kakao.maps.Marker({
              map: this.map,
              position,
              // eslint-disable-next-line prettier/prettier
            })
        );

        const bounds = positions.reduce(
          (bounds, latlng) => bounds.extend(latlng),
          // eslint-disable-next-line prettier/prettier
          new kakao.maps.LatLngBounds()
        );

        this.map.setBounds(bounds);
      }
    },
    moveMarker(lat, lng) {
      // 이동할 위도 경도 위치를 생성합니다
      var moveLatLon = new kakao.maps.LatLng(lat, lng);

      // 지도 중심을 부드럽게 이동시킵니다
      // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
      this.map.setLevel(1);
      this.map.panTo(moveLatLon);
    },

    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 5,
      };

      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(container, options);
    },
    changeSize(size) {
      const container = document.getElementById("map");
      container.style.width = `${size}px`;
      container.style.height = `${size}px`;
      this.map.relayout();
    },
    displayInfoWindow() {
      if (this.infowindow && this.infowindow.getMap()) {
        //이미 생성한 인포윈도우가 있기 때문에 지도 중심좌표를 인포윈도우 좌표로 이동시킨다.
        this.map.setCenter(this.infowindow.getPosition());
        return;
      }

      var iwContent = '<div style="padding:5px;">Hello World!</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        iwPosition = new kakao.maps.LatLng(33.450701, 126.570667), //인포윈도우 표시 위치입니다
        iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

      this.infowindow = new kakao.maps.InfoWindow({
        map: this.map, // 인포윈도우가 표시될 지도
        position: iwPosition,
        content: iwContent,
        removable: iwRemoveable,
      });

      this.map.setCenter(iwPosition);
    },
  },
  computed: {
    ...mapState(houseStore, [
      "sidoOptions",
      "gugunOptions",
      "dongOptions",
      "buildingList",
      "buildingDetail",
    ]),
  },
  watch: {
    sidoSelected: function (value) {
      console.log("시도 코드 : " + value);
      if (value) {
        this.getGugun();
      } else {
        this.initSelected("sido");
      }
    },
    gugunSelected: function (value) {
      console.log("구군 코드 : " + value);
      if (value) {
        this.getDong();
      } else {
        this.initSelected("gugun");
      }
    },
    dongSelected: function (value) {
      console.log("동 코드 : " + value);
      if (value) {
        console.log("매물 표시");
        this.getBuilding();
      } else {
        // 건물 정보 초기화.
        this.initBuilding();
      }
    },
    buildingList: function (value) {
      if (value) {
        // 마커 생성!
        this.makeMarker();
      }
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#map {
  width: 100%;
  height: 500px;
}

.button-group {
  margin: 10px 0px;
}

.no-scroll-board::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}
</style>
