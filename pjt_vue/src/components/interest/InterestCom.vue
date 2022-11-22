<template>
  <div>
    <b-row align-v="center" align-h="between">
      <b-col
        cols="3"
        align-self="center"
        style="font-size: 20px; font-weight: bold; text-align: left">
        <b-icon-star-fill></b-icon-star-fill> 관심지역
      </b-col>
      <b-col
        cols="3"
        align-self="center"
        id="if-admin"
        style="text-align: right">
        <b-button class="btn btn-dark" :to="{ name: 'Deal' }">
          등록하러 가기
        </b-button>
      </b-col>
    </b-row>
    <b-row
      class="mt-5"
      v-if="interestArea.length == 0"
      align-v="center"
      style="height: 460px">
      <b-col> 선택한 관심 지역이 없습니다. </b-col>
    </b-row>
    <div v-else class="mt-5">
      <b-card-group class="mt-3 justify-content-center" deck>
        <interest-row
          v-for="(area, index) in interestArea.slice(0, 2)"
          :key="area.dongCode + 'interest'"
          :area="area"
          :index="index + 1"></interest-row>
      </b-card-group>
      <b-card-group class="mt-3 justify-content-center" deck>
        <interest-row
          v-for="(area, index) in interestArea.slice(2, 4)"
          :key="area.dongCode + 'interest'"
          :area="area"
          :index="index + 3"></interest-row>
      </b-card-group>
      <b-card-group class="mt-3 justify-content-center" deck>
        <interest-row
          v-for="(area, index) in interestArea.slice(4, 6)"
          :key="area.dongCode + 'interest'"
          :area="area"
          :index="index + 5"></interest-row>
      </b-card-group>

      <b-card-group class="mt-3 justify-content-center" deck>
        <interest-row
          v-for="(area, index) in interestArea.slice(6, 8)"
          :key="area.dongCode + 'interest'"
          :area="area"
          :index="index + 7"></interest-row>
      </b-card-group>
      <b-card-group class="mt-3 justify-content-center" deck>
        <interest-row
          v-for="(area, index) in interestArea.slice(8)"
          :key="area.dongCode + 'interest'"
          :area="area"
          :index="index + 9"></interest-row>
      </b-card-group>
    </div>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
const houseStore = "houseStore";
const memberStore = "memberStore";
export default {
  components: {
    "interest-row": () =>
      import("@/components/interest/include/InterestRow.vue"),
  },

  async created() {
    await this.getInterestArea(this.userInfo.userId);
  },

  methods: {
    ...mapActions(houseStore, ["getInterestArea"]),
  },

  computed: {
    ...mapState(houseStore, ["interestArea"]),
    ...mapState(memberStore, ["userInfo"]),
  },
};
</script>

<style></style>
