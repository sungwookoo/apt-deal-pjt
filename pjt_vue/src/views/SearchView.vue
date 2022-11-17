<template>
  <div>
    <b-container
      class="mt-4 bg-light bg-opacity-75 pt-4"
      style="max-height: 700px; height: 700px">
      <b-row class="text-left mb-1">
        <div class="search-keyword">'{{ keyword }}' 검색 결과</div>
      </b-row>
      <board-search type="0"></board-search>
      <div class="m-2"></div>
      <board-search type="1"></board-search>
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
