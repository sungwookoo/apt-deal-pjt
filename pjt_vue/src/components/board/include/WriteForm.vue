<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-lg-8 col-md-10 col-sm-12">
        <h2 class="my-3 py-3 shadow-sm bg-white border text-center">
          <div v-if="boardAction == 'create'" class="underline">글 작성</div>
          <div v-else class="underline">글 수정</div>
        </h2>
      </div>
      <div class="col-lg-8 col-md-10 col-sm-12">
        <form id="form-register" action="">
          <div class="mb-3">
            <label for="subject" class="form-label">제목</label>
            <input
              type="text"
              class="form-control"
              name="subject"
              ref="subject"
              placeholder="제목..."
              v-model="input.subject" />
          </div>
          <div class="mb-3">
            <label for="content" class="form-label">내용</label>
            <textarea
              class="form-control"
              name="content"
              rows="15"
              ref="content"
              v-model="input.content"></textarea>
          </div>
          <div class="col-auto text-center">
            <button
              v-if="this.boardAction == 'create'"
              type="button"
              class="btn btn-primary m-1"
              @click="validate">
              등록
            </button>
            <button
              v-else
              type="button"
              class="btn btn-primary m-1"
              @click="validate">
              수정
            </button>
            <button
              type="button"
              class="btn btn-danger m-1"
              @click="moveList(boardType)">
              목록
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapGetters } from "vuex";
export default {
  props: {
    type: String,
  },
  data() {
    return {
      boardType: "",
      boardAction: "",
    };
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
            console.log("callback!! 호출", this);
            this.moveList(this.boardType);
          },
        };
        if (this.boardAction == "create") {
          this.createBoard(payload);
        } else {
          this.modifyBoard(payload);
        }
      }
    },
  },

  created() {
    this.boardType = this.type.split(" ")[0];
    this.boardAction = this.type.split(" ")[1];
  },

  computed: {
    ...mapGetters(["board", "userInfo2"]),
    input() {
      if (this.boardAction == "modify") {
        return { ...this.board };
      } else {
        return {
          userId: this.userInfo2.userId,
          userName: this.userInfo2.userName,
          subject: "",
          content: "",
          type: this.boardType,
        };
      }
    },
  },
};
</script>

<style></style>
