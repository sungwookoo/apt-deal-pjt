<template>
  <div>
    <div
      v-if="type == '0' && count == 'total'"
      class="justify-content-center search-total-box no-scroll-board"
      style="overflow-y: scroll">
      <search-row
        v-for="board in searchNotice.slice(0, 3)"
        :key="board.boardNo"
        :board="board"></search-row>
    </div>
    <div
      v-else-if="type == '0' && count == 'all'"
      class="justify-content-center search-all-box no-scroll-board"
      style="overflow-y: scroll">
      <search-row
        v-for="board in searchNotice"
        :key="board.boardNo"
        :board="board"></search-row>
    </div>
    <div></div>
    <div
      v-if="type == '1' && count == 'total'"
      class="justify-content-center search-total-box no-scroll-board"
      style="overflow-y: scroll">
      <search-row
        v-for="board in searchQna.slice(0, 3)"
        :key="board.boardNo"
        :board="board"></search-row>
    </div>
    <div
      v-else-if="type == '1' && count == 'all'"
      class="justify-content-center search-all-box no-scroll-board"
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
    count: String,
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

.search-total-box {
  position: relative;
  height: 310px;
  width: 90%;
  border: 3px solid #000;
  margin: auto;
}

.search-all-box {
  position: relative;
  height: 650px;
  width: 90%;
  border: 3px solid #000;
  margin: auto;
}
</style>
