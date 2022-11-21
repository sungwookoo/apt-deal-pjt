<template>
  <b-card
    v-if="!isClick"
    border-variant="primary"
    header-bg-variant="primary"
    header-text-variant="white"
    header-class="header-font"
    :header="area.dongCode"
    style="max-width: 22%; height: 130px"
    @click="cardClick">
    <b-card-text class="mt-0">{{ address }}</b-card-text>
  </b-card>
  <b-card
    v-else
    border-variant="primary"
    header-bg-variant="primary"
    header-text-variant="white"
    header-class="header-font"
    :header="area.dongCode"
    style="max-width: 22%; height: 130px"
    @click="cardClick">
    <b-card-text>
      <b-button class="mr-1" variant="outline-primary" @click="moveDealPage">
        이동
      </b-button>
      <b-button class="ml-1" variant="outline-danger" @click="deleteInterest">
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
</style>
