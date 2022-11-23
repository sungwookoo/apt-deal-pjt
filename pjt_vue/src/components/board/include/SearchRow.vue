<template>
  <b-card
    :title="board.subject"
    align="left"
    style="height: 100px; max-height: 100px"
    @click="moveBoardDetail">
    <b-card-text
      >{{
        board.content.slice(0, 100) + (board.content.length >= 100 ? "..." : "")
      }}
      <span style="float: right; color: gray"
        >{{ board.userId }} <span style="color: lightgray">|</span>
        {{ $moment(this.board.createDate).format("YYYY.MM.DD.") }}</span
      ></b-card-text
    >
  </b-card>
</template>

<script>
export default {
  props: {
    board: Object,
  },
  methods: {
    moveBoardDetail() {
      if (this.board.type == "0") {
        this.$router.push({
          name: "NoticeDetail",
          query: { boardNo: this.board.boardNo },
        });
      } else {
        this.$router.push({
          name: "QnaDetail",
          query: { boardNo: this.board.boardNo },
        });
      }
    },
  },
};
</script>

<style></style>
