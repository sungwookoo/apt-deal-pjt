<template>
  <div>
    <div v-if="type == '0' && count == 'total'">
      <div class="search-menu-title mb-2">
        <b-icon-bell-fill></b-icon-bell-fill> 공지사항 ({{
          searchNotice.length
        }})
      </div>
      <div
        class="justify-content-center search-total-box no-scroll-board"
        style="overflow-y: scroll">
        <search-row
          v-for="board in searchNotice.slice(0, 3)"
          :key="board.boardNo"
          :board="board"></search-row>
      </div>
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

    <div v-if="type == '1' && count == 'total'">
      <div class="search-menu-title mb-2 mt-3">
        <b-icon-question-circle></b-icon-question-circle> QnA ({{
          searchQna.length
        }})
      </div>
      <div
        class="justify-content-center search-total-box no-scroll-board"
        style="overflow-y: scroll">
        <search-row
          v-for="board in searchQna.slice(0, 3)"
          :key="board.boardNo"
          :board="board"></search-row>
      </div>
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
  height: 255px;
  width: 90%;
  /* border: 3px solid #000; */
  margin: auto;
}

.search-all-box {
  position: relative;
  height: 600px;
  width: 90%;
  /* border: 3px solid #000; */
  margin: auto;
}

.search-menu-title {
  font-weight: bold;
  text-align: left;
}
</style>
