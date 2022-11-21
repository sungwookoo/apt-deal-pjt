<template>
  <div>
    <b-row>
      <b-col>
        <h2>관심지역</h2>
      </b-col>
    </b-row>
    <b-card-group class="mt-3 justify-content-center" deck>
      <interest-row
        v-for="area in interestArea.slice(0, 4)"
        :key="area.dongCode + 'interest'"
        :area="area"></interest-row>
    </b-card-group>
    <b-card-group class="mt-3 justify-content-center" deck>
      <interest-row
        v-for="area in interestArea.slice(4, 8)"
        :key="area.dongCode + 'interest'"
        :area="area"></interest-row>
    </b-card-group>
    <b-card-group class="mt-3 justify-content-center" deck>
      <interest-row
        v-for="area in interestArea.slice(8)"
        :key="area.dongCode + 'interest'"
        :area="area"></interest-row>
    </b-card-group>
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
