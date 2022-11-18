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
      <b-row align-h="center">
        <b-col style="border: solid">
          <div class="button-group">
            <button @click="changeSize(0)">Hide</button>
            <button @click="changeSize(800)">show</button>
            <button @click="displayMarker(markerPositions1)">
              marker set 1
            </button>
            <button @click="displayMarker(markerPositions2)">
              marker set 2
            </button>
            <button @click="displayMarker([])">marker set 3 (empty)</button>
            <button @click="displayInfoWindow">infowindow</button>
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
      markerPositions1: [
        [33.452278, 126.567803],
        [33.452671, 126.574792],
        [33.451744, 126.572441],
      ],
      markerPositions2: [
        [37.499590490909185, 127.0263723554437],
        [37.499427948430814, 127.02794423197847],
        [37.498553760499505, 127.02882598822454],
        [37.497625593121384, 127.02935713582038],
        [37.49629291770947, 127.02587362608637],
        [37.49754540521486, 127.02546694890695],
        [37.49646391248451, 127.02675574250912],
      ],
      markers: [],
      infowindow: null,
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
  },
  methods: {
    ...mapActions(houseStore, [
      "getSidoNames",
      "getGugunNames",
      "getDongNames",
      "initOptions",
    ]),
    // 코드 얻기
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
          break;
        case "gugun":
          this.dongSelected = "";
          break;
      }
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
    displayMarker(markerPositions) {
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }

      const positions = markerPositions.map(
        // eslint-disable-next-line prettier/prettier
        (position) => new kakao.maps.LatLng(...position)
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
    ...mapState(houseStore, ["sidoOptions", "gugunOptions", "dongOptions"]),
    changeSido() {
      console.log("시도 코드 : " + this.sidoSelected);
      if (this.sidoSelected) {
        this.getGugun();
      } else {
        this.initSelected("sido");
      }
      return this.sidoSelected;
    },
    changeGugun() {
      console.log("구군 코드 : " + this.gugunSelected);
      if (this.gugunSelected) {
        this.getDong();
      } else {
        this.initSelected("gugun");
      }
      return this.gugunSelected;
    },
    changeDong() {
      console.log("동 코드 : " + this.dongSelected);
      if (this.dongSelected) {
        console.log("매물 표시");
      }
      return this.dongSelected;
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

button {
  margin: 0 3px;
}
</style>
