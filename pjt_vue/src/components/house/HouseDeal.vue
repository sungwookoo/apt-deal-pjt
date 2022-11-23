<template>
  <div style="width: 20%; min-width: 20%; max-width: 20%">
    <b-row
      style="
        margin-left: 15px;
        height: 75%;
        width: 20%;
        min-height: 75%;
        max-height: 75%;
        height: 75%;
        border: solid;
        position: absolute;
      ">
      <b-col class="m-1" style="width: 100%; max-height: 100%">
        <div><h2>건물 정보</h2></div>

        <div
          v-if="buildingList.length > 0"
          style="height: 90%; overflow: scroll"
          class="no-scroll-board">
          <building-row
            v-b-toggle.buildingDetail
            class="mt-1"
            v-for="(building, index) in buildingList"
            :key="building.aptCode + index"
            :building="building">
          </building-row>
        </div>
        <div v-else>건물 정보가 없습니다.</div>
        <b-sidebar id="buildingDetail" title="상세 정보" width="20%" shadow>
          <b-row class="px-3 py-2">
            <b-col class="m-1" style="max-height: 100%; border: solid">
              <div v-if="buildingDetail">
                <building-detail></building-detail>
              </div>
              <div v-else>건물을 선택해 주세요.</div>
            </b-col>
          </b-row>
        </b-sidebar>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import { mapState } from "vuex";
const houseStore = "houseStore";
export default {
  components: {
    "building-row": () => import("@/components/house/include/BuildingRow.vue"),
    "building-detail": () =>
      import("@/components/house/include/BuildingDetail.vue"),
  },

  computed: {
    ...mapState(houseStore, ["buildingList", "buildingDetail"]),
  },
};
</script>

<style scoped>
.no-scroll-board::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}
</style>
