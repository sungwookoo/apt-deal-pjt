<template>
  <div>
    <b-row align-v="center" align-h="between">
      <b-col
        v-if="type == '0'"
        cols="3"
        align-self="center"
        style="font-size: 20px; font-weight: bold; text-align: left">
        <b-icon-bell-fill></b-icon-bell-fill> 공지사항
      </b-col>
      <b-col
        v-else
        cols="3"
        align-self="center"
        style="font-size: 20px; font-weight: bold; text-align: left">
        <b-icon-question-circle></b-icon-question-circle>&nbsp;&nbsp;QnA
      </b-col>
      <!-- <b-col cols="2" align-self="center" id="if-admin">
        <button class="btn btn-dark" @click="moveBoardCreate">공지작성</button>
      </b-col> -->
    </b-row>
    <br />
    <b-row class="mb-1">
      <b-col>
        <b-card class="mb-2" border-variant="dark" no-body>
          <b-card-header class="text-left" style="height: 100px">
            <label for="subject" class="form-label">제목</label>
            <h5>
              <input
                type="text"
                class="form-control"
                name="subject"
                ref="subject"
                placeholder="제목..."
                v-model="input.subject" />
            </h5>
            <div></div>
          </b-card-header>
          <b-card-body
            class="text-left"
            style="height: 400px; overflow-y: scroll">
            <label for="content" class="form-label">내용</label>
            <textarea
              style="resize: none"
              class="form-control"
              name="content"
              rows="13"
              ref="content"
              v-model="input.content"></textarea>
          </b-card-body>
        </b-card>
        <b-row class="mb-1" style="margin-top: 10px">
          <b-col class="text-left">
            <b-button
              type="button"
              size="sm"
              class="mr-2 btn btn-dark"
              @click="moveList(type)">
              목록
            </b-button>
          </b-col>
          <b-col class="text-right">
            <b-button
              v-if="action == 'create'"
              size="sm"
              @click="validate"
              class="mr-2 btn"
              >등록</b-button
            >
            <b-button v-else size="sm" @click="validate" class="mr-2 btn"
              >수정</b-button
            >
          </b-col>
        </b-row>
      </b-col>
    </b-row>
  </div>
</template>
<script>
import { mapActions, mapGetters } from "vuex";
export default {
  props: {
    type: String,
    action: String,
  },
  methods: {
    ...mapActions(["createBoard", "modifyBoard"]),
    moveList(move) {
      switch (move) {
        case "0":
          this.$router.push({ name: "NoticeList" });
          break;
        case "1":
          this.$router.push({ name: "QnaList" });
          break;
      }
    },
    validate() {
      let isValid = true;
      let errMsg = "";

      !this.input.subject
        ? ((isValid = false),
          (errMsg = "제목을 입력해주세요."),
          this.$refs.subject.focus())
        : !this.input.content
        ? ((isValid = false),
          (errMsg = "내용을 입력해주세요."),
          this.$refs.content.focus())
        : (isValid = true);

      if (!isValid) {
        alert(errMsg);
      } else {
        const payload = {
          board: this.input,
          callback: () => {
            this.moveList(this.type);
          },
        };
        if (this.action == "create") {
          this.createBoard(payload);
        } else {
          this.modifyBoard(payload);
        }
      }
    },
  },

  created() {},

  computed: {
    ...mapGetters(["board", "userInfo2"]),
    input() {
      if (this.action == "modify") {
        return { ...this.board };
      } else {
        return {
          userId: this.userInfo2.userId,
          userName: this.userInfo2.userName,
          subject: "",
          content: "",
          type: this.type,
        };
      }
    },
  },
};
</script>

<style></style>
