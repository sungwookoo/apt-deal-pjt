<template>
  <div>
    <b-row class="p-2" style="border-bottom-style: solid; border-width: 2px">
      <b-col cols="6">
        <router-link
          v-if="board.type == '0'"
          :to="{ name: 'NoticeDetail', query: { boardNo: board.boardNo } }"
          class="link">
          {{ this.board.subject }}
        </router-link>
        <router-link
          v-else
          :to="{ name: 'QnaDetail', query: { boardNo: board.boardNo } }"
          class="link">
          {{ this.board.subject }}
        </router-link>
      </b-col>
      <b-col
        cols="3"
        style="
          border-right-style: dotted;
          border-left-style: dotted;
          border-width: 2px;
        "
        >{{ this.board.userName }}</b-col
      >
      <b-col cols="3">{{ this.board.createDate }}</b-col>
    </b-row>
  </div>
</template>

<script>
export default {
  props: {
    board: Object,
  },
  created() {
    console.log(this.board);
  },
};
</script>

<style scoped>
.link {
  text-decoration: none;
  color: black;
}
</style>
