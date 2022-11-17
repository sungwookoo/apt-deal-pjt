<template>
  <div>
    <b-row align-v="center" align-h="between">
      <b-col
        v-if="type == '0'"
        cols="2"
        align-self="center"
        style="font-size: 20px; font-weight: bold">
        <b-icon-bell-fill></b-icon-bell-fill>&nbsp;&nbsp;공지사항
      </b-col>
      <b-col
        v-else
        cols="2"
        align-self="center"
        style="font-size: 20px; font-weight: bold">
        <b-icon-question-circle></b-icon-question-circle>&nbsp;&nbsp;QnA
      </b-col>
      <!-- <b-col cols="2" align-self="center" id="if-admin">
        <button class="btn btn-dark" @click="moveBoardCreate">공지작성</button>
      </b-col> -->
    </b-row>

    <detail-form :board="board"></detail-form>

    <b-row class="mb-1" style="margin-top: 10px">
      <b-col class="text-left">
        <b-button
          variant="outline-info"
          size="sm"
          @click="moveList"
          class="mr-2"
          >목록</b-button
        >
      </b-col>
      <b-col class="text-right">
        <b-button
          variant="outline-info"
          size="sm"
          @click="moveModifyBoard"
          class="mr-2"
          >글수정</b-button
        >
        <b-button variant="outline-danger" size="sm" @click="deleteBtn"
          >글삭제</b-button
        >
      </b-col>
    </b-row>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  name: "BoardDetail",
  components: {
    "detail-form": () => import("@/components/board/include/DetailForm.vue"),
  },
  props: {
    type: String,
  },
  computed: {
    ...mapGetters(["board"]),
    message() {
      if (this.board.content)
        return this.board.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    console.log(this.$route.query);
    this.getBoard(this.$route.query.boardNo);
  },
  methods: {
    ...mapActions(["getBoard", "deleteBoard"]),
    moveModifyBoard() {
      switch (this.type) {
        case "0":
          this.$router.push({
            name: "NoticeModify",
          });
          break;
        case "1":
          this.$router.push({
            name: "QnaModify",
          });
          break;
      }
    },
    deleteBtn() {
      const payload = {
        boardNo: this.board.boardNo,
        callback: () => {
          this.moveList();
        },
      };
      this.deleteBoard(payload);
    },
    moveList() {
      switch (this.type) {
        case "0":
          this.$router.push({ name: "NoticeList" });
          break;
        case "1":
          this.$router.push({ name: "QnaList" });
          break;
      }
    },
  },
};
</script>

<style></style>
