<template>
  <div>
    <b-row align-v="center" align-h="between">
      <b-col
        cols="5"
        align-self="center"
        style="font-size: 20px; font-weight: bold; text-align: left">
        <b-icon-calculator></b-icon-calculator> 매매평균
      </b-col>
    </b-row>
    <br />
    <b-row class="m-1" align-h="center">
      <b-col cols="10">
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
          <!-- <b-form-select
            class="ml-3"
            v-model="dongSelected"
            :options="dongOptions"
            value-field="dongCode"
            text-field="dongName"></b-form-select> -->
        </b-input-group>
      </b-col>
    </b-row>
    <b-row style="height: 500px; overflow: scroll" class="no-scroll-board">
      <b-col v-if="avgDealList.length">
        <b-table-simple hover responsive>
          <b-thead head-variant="dark">
            <b-tr>
              <b-th style="width: 100px">동</b-th>
              <b-th style="width: 600px">매매평균가</b-th>
            </b-tr>
          </b-thead>
          <b-tbody>
            <deal-row
              v-for="deal in avgDealList"
              :key="deal.dongName"
              :deal="deal">
            </deal-row>
          </b-tbody>
        </b-table-simple>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import { mapActions, mapState } from "vuex";
const houseStore = "houseStore";
export default {
  components: {
    "deal-row": () => import("@/components/home/include/AvgDealRow.vue"),
  },
  data() {
    return {
      sidoSelected: "",
      gugunSelected: "",
    };
  },

  async created() {
    await this.initOptions();
    await this.initAvgDeal();
    await this.getSido();
  },

  methods: {
    ...mapActions(houseStore, [
      "getSidoNames",
      "getGugunNames",
      "initOptions",
      "getAvgDeal",
      "initAvgDeal",
    ]),

    // 주소 코드 얻기
    async getSido() {
      await this.getSidoNames();
    },
    async getGugun() {
      await this.getGugunNames(this.sidoSelected);
      this.gugunSelected = "";
    },
    async initSelected(name) {
      await this.initOptions(name);
      switch (name) {
        case "sido":
          this.gugunSelected = "";
        // eslint-disable-next-line no-fallthrough
      }
    },
  },

  computed: {
    ...mapState(houseStore, ["sidoOptions", "gugunOptions", "avgDealList"]),
  },

  watch: {
    sidoSelected: async function (value) {
      if (value) {
        await this.getGugun();
      } else {
        await this.initSelected("sido");
      }
    },
    gugunSelected: async function (value) {
      if (value) {
        await this.getAvgDeal(this.gugunSelected);
      } else {
        await this.initAvgDeal();
      }
    },
  },
};
</script>

<style scoped>
.no-scroll-board::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}
</style>
