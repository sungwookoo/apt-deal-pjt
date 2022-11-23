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
      map: null,
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

        for (let index in this.markers) {
          let overlay = new kakao.maps.CustomOverlay({
            map: this.map,
            position: this.markers[index].getPosition(),
          });

          let content = document.createElement("div");
          content.className = "overlay-box";

          let info = document.createElement("div");
          info.className = "overlay-info";

          let title = document.createElement("div");
          title.className = "overlay-title";
          title.appendChild(
            // eslint-disable-next-line prettier/prettier
            document.createTextNode(this.buildingList[index].apartmentName)
          );

          let close = document.createElement("div");
          close.className = "overlay-close";
          close.onclick = function () {
            overlay.setMap(null);
          };

          title.appendChild(close);

          let body = document.createElement("div");
          body.className = "overlay-body";

          let img = document.createElement("div");
          img.className = "overlay-img";
          // let image = document.createElement("img");
          // image.setAttribute(":src", "@/assets/house.svg");
          // image.width = "73";
          // image.height = "70";
          // img.appendChild(image);

          let desc = document.createElement("div");
          desc.className = "overlay-desc";
          let ellipsis = document.createElement("div");
          ellipsis.className = "overlay-ellipsis";
          ellipsis.appendChild(
            // eslint-disable-next-line prettier/prettier
            document.createTextNode(this.buildingList[index].address)
          );

          desc.appendChild(ellipsis);

          body.appendChild(img);
          body.appendChild(desc);

          info.appendChild(title);
          info.appendChild(body);

          content.appendChild(info);

          kakao.maps.event.addListener(this.markers[index], "click", () => {
            overlay.setMap(this.map);
            // eslint-disable-next-line prettier/prettier
          });

          overlay.setContent(content);
          overlay.setMap(null);
        }
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

<style>
.overlay-box {
  position: absolute;
  left: 0;
  bottom: 40px;
  width: 288px;
  height: 132px;
  margin-left: -144px;
  text-align: left;
  overflow: hidden;
  font-size: 12px;
  font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
  line-height: 1.5;
}
.overlay-box * {
  padding: 0;
  margin: 0;
}
.overlay-box .overlay-info {
  width: 286px;
  height: 120px;
  border-radius: 5px;
  border-bottom: 2px solid #ccc;
  border-right: 1px solid #ccc;
  overflow: hidden;
  background: #fff;
}
.overlay-box .overlay-info:nth-child(1) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.overlay-info .overlay-title {
  padding: 5px 0 0 10px;
  height: 30px;
  background: #eee;
  border-bottom: 1px solid #ddd;
  font-size: 18px;
  font-weight: bold;
}
.overlay-info .overlay-close {
  position: absolute;
  top: 10px;
  right: 10px;
  color: #888;
  width: 17px;
  height: 17px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}
.overlay-info .overlay-close:hover {
  cursor: pointer;
}
.overlay-info .overlay-body {
  position: relative;
  overflow: hidden;
}
.overlay-info .overlay-desc {
  position: relative;
  margin: 13px 0 0 90px;
  height: 75px;
}
.overlay-desc .overlay-ellipsis {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.overlay-desc .overlay-jibun {
  font-size: 11px;
  color: #888;
  margin-top: -2px;
}
.overlay-info .overlay-img {
  position: absolute;
  top: 6px;
  left: 5px;
  width: 73px;
  height: 71px;
  border: 1px solid #ddd;
  color: #888;
  overflow: hidden;
}
.overlay-img {
  background: url(@/assets/house.svg) no-repeat;
  background-position: center;
}
.overlay-info:after {
  content: "";
  position: absolute;
  margin-left: -12px;
  left: 50%;
  bottom: 0;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.overlay-info .overlay-link {
  color: #5085bb;
}
</style>
