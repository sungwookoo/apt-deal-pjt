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
    ...mapActions(houseStore, ["getBuildingDetailInfo", "getBuildingAddress"]),
    async getbuildingDetail() {
      let geocoder = new window.kakao.maps.services.Geocoder();
      let coord = new window.kakao.maps.LatLng(
        this.building.lat,
        // eslint-disable-next-line prettier/prettier
        this.building.lng
      );

      let callback = (result, status) => {
        if (status === window.kakao.maps.services.Status.OK) {
          console.log(
            // eslint-disable-next-line prettier/prettier
            "카카오 주소 획득==========" + result[0]["address"].address_name
          );
          this.address = result[0]["address"].address_name;
        }
        // eslint-disable-next-line prettier/prettier
      };

      await geocoder.coord2Address(
        coord.getLng(),
        coord.getLat(),
        // eslint-disable-next-line prettier/prettier
        callback
      );
    },
  },

  watch: {
    address: async function (value) {
      if (value) {
        const payload = {
          aptCode: this.building.aptCode,
          detail: {
            apartmentName: this.building.apartmentName,
            buildYear: this.building.buildYear,
            address: this.address,
            lat: this.building.lat,
            lng: this.building.lng,
          },
        };

        await this.getBuildingDetailInfo(payload);
      }
    },
  },
};
</script>

<style></style>
