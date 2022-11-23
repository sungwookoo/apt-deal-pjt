<template>
  <b-row>
    <b-col id="map"></b-col>
  </b-row>
</template>

<script>
import { mapState } from "vuex";
const houseStore = "houseStore";
export default {
  data() {
    return {
      markers: [],
    };
  },

  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${process.env.VUE_APP_KAKAO_MAP_APPKEY}&libraries=services`;
      document.head.appendChild(script);
    }
  },

  created() {
    this.initMarker();
  },

  methods: {
    // 지도 초기화.
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

    // 마커 생성.
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
  },

  computed: {
    ...mapState(houseStore, ["buildingList", "buildingDetail"]),
  },

  watch: {
    buildingList: function (value) {
      if (value) {
        // 마커 생성!
        this.makeMarker();
      }
    },
    buildingDetail: function (value) {
      if (value) {
        this.moveMarker(this.buildingDetail.lat, this.buildingDetail.lng);
      } else {
        this.moveMarker(33.450701, 126.570667);
        this.map.setLevel(5);
      }
    },
  },
};
</script>

<style scoped>
#map {
  width: 100%;
  height: 500px;
}
</style>
