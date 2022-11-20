<template>
  <div>
    <b-container
      class="mt-4 bg-light bg-opacity-75 pt-4"
      style="max-height: 800px; height: 800px">
      <b-row class="text-left mb-1">
        <div class="search-keyword">'{{ keyword }}' 검색 결과</div>
      </b-row>
      <b-tabs
        content-class="mt-3"
        align="center"
        active-nav-item-class="font-weight-bold">
        <b-tab title="통합 검색">
          <board-search type="0" count="total"></board-search>
          <div class="m-2"></div>
          <board-search type="1" count="total"></board-search>
        </b-tab>
        <b-tab title="공지사항">
          <board-search type="0" count="all"></board-search>
        </b-tab>
        <b-tab title="QnA">
          <board-search type="1" count="all"></board-search>
        </b-tab>
      </b-tabs>
      <div style="max-height: 120px; height: 120px"></div>
    </b-container>
  </div>
</template>

<script>
import { mapActions } from "vuex";
export default {
  components: {
    "board-search": () => import("@/components/board/BoardSearch.vue"),
  },
  methods: {
    ...mapActions(["getSearchBoard"]),
  },
  computed: {
    keyword() {
      const key = this.$route.query.keyword;

      this.getSearchBoard(key);
      return key;
    },
  },
};
</script>

<style scoped>
.search-keyword {
  border-style: outset;
  font-size: 20px;
  font-weight: bold;
  margin-left: 6.2%;
  padding: 7px;
}
</style>
