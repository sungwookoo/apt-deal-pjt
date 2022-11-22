<template>
  <b-card
    v-if="!isClick"
    class="mr-4 ml-4 mb-2"
    border-variant="dark"
    header-bg-variant="dark"
    header-text-variant="white"
    header-class="header-font"
    :header="'관심 지역 # ' + index"
    style="max-width: 35%; height: 100px"
    @click="cardClick">
    <b-card-text class="mt-0">{{ address }}</b-card-text>
  </b-card>
  <b-card
    v-else
    class="mr-4 ml-4 mb-2"
    border-variant="primary"
    header-bg-variant="primary"
    header-text-variant="white"
    header-class="header-font"
    :header="area.dongCode"
    style="max-width: 35%; height: 100px"
    @click="cardClick">
    <b-card-text>
      <b-button
        size="sm"
        class="mr-1"
        variant="outline-primary"
        style="width: 100px"
        @click="moveDealPage">
        이동
      </b-button>
      <b-button
        size="sm"
        class="ml-1"
        variant="outline-danger"
        style="width: 100px"
        @click="deleteInterest">
        삭제
      </b-button>
    </b-card-text>
  </b-card>
</template>

<script>
import { mapActions } from "vuex";
const houseStore = "houseStore";
export default {
  props: {
    area: Object,
    index: Number,
  },
  data() {
    return {
      isClick: false,
    };
  },
  methods: {
    ...mapActions(houseStore, ["deleteInterestArea"]),
    cardClick() {
      this.isClick = !this.isClick;
    },
    async deleteInterest() {
      const info = {
        userId: this.area.userId,
        code: this.area.dongCode,
      };
      await this.deleteInterestArea(info);
    },
    moveDealPage() {
      this.$router.push({ name: "Deal", params: { code: this.area.dongCode } });
    },
  },
  computed: {
    address() {
      return (
        this.area.sidoName +
        " " +
        this.area.gugunName +
        " " +
        this.area.dongName
      );
    },
  },
};
</script>

<style scoped>
.header-font {
  font-weight: bold;
}
.card-body {
  padding-top: 10px;
}
</style>
