<template>
  <div>
    <div
      v-if="type == '0'"
      class="justify-content-center search-box no-scroll-board"
      style="overflow-y: scroll">
      <search-row
        v-for="board in searchNotice"
        :key="board.boardNo"
        :board="board"></search-row>
    </div>
    <div
      v-else
      class="justify-content-center search-box no-scroll-board"
      style="overflow-y: scroll">
      <search-row
        v-for="board in searchQna"
        :key="board.boardNo"
        :board="board"></search-row>
    </div>
  </div>
</template>

<script>
import { mapGetters } from "vuex";
export default {
  props: {
    type: String,
  },
  components: {
    "search-row": () => import("@/components/board/include/SearchRow.vue"),
  },
  computed: {
    ...mapGetters(["searchNotice", "searchQna"]),
  },
};
</script>

<style scoped>
.no-scroll-board::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}

.search-box {
  position: relative;
  height: 300px;
  width: 90%;
  border: 3px solid #000;
  margin: auto;
}
.search-box .legend {
  height: 1px;
  position: absolute;
  top: -8px;
  padding: 0 20px;
  color: #000;
  overflow: visible;
}

.search-box .legend span {
  padding: 2px;
  background: #f8f9fa;
  border: 1px solid #000;
  top: -0.5em;
  position: relative;
  vertical-align: middle;
  display: inline-block;
  overflow: visible;
}
</style>
