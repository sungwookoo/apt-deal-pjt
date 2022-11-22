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
        <b-icon-question-circle></b-icon-question-circle> QnA
      </b-col>
      <b-col
        cols="3"
        align-self="center"
        id="if-admin"
        style="text-align: right">
        <button
          v-if="
            !(
              type == '0' &&
              (!isLogin || (isLogin && checkUserInfo.userType == '0'))
            )
          "
          class="btn btn-dark"
          @click="moveBoardCreate">
          등록
        </button>
        <button
          v-else
          class="btn btn-dark"
          style="visibility: hidden"
          @click="moveBoardCreate">
          등록
        </button>
      </b-col>
    </b-row>
    <br />
    <b-row style="height: 520px">
      <b-col v-if="boardList.length">
        <b-table-simple class="no-scroll-board" hover sticky-header="500px">
          <b-thead head-variant="dark">
            <b-tr>
              <b-th style="width: 100px">번호</b-th>
              <b-th style="width: 600px">제목</b-th>
              <b-th style="width: 200px">작성자</b-th>
              <b-th style="width: 200px">작성일시</b-th>
            </b-tr>
          </b-thead>
          <b-tbody>
            <!-- <tr
              class="no-scroll-board"
              style="height: 530px; overflow-y: scroll"> -->
            <board-row
              v-for="board in boardList"
              :key="board.boardNo"
              :board="board">
            </board-row>
            <!-- </tr> -->
          </b-tbody>
        </b-table-simple>
      </b-col>
      <!-- <b-col v-else class="text-center">도서 목록이 없습니다.</b-col> -->
    </b-row>

    <!-- <b-row
      class="border-dark mt-3 p-2"
      style="
        border-bottom-style: solid;
        border-width: 3px;
        font-size: 17px;
        font-weight: bold;
      ">
      <b-col cols="6">제목</b-col>
      <b-col
        cols="3"
        style="
          border-right-style: dotted;
          border-left-style: dotted;
          border-width: 2px;
        "
        >작성자</b-col
      >
      <b-col cols="3">날짜</b-col>
    </b-row>

    <div class="no-scroll-board" style="height: 530px; overflow-y: scroll">
      <board-row v-for="board in boardList" :key="board.boardNo" :board="board">
      </board-row>
    </div> -->
    <div class="d-flex justify-content-center mt-0">
      <button class="btn btn-dark me-3">◀</button>
      <span
        class="text-black mx-2 align-self-center text-decoration-none"
        href="">
        1
      </span>
      <a class="text-black mx-2 align-self-center text-decoration-none" href="">
        2
      </a>
      <a class="text-primary mx-2 align-self-center" href=""> 3 </a>
      <a class="text-black mx-2 align-self-center text-decoration-none" href="">
        4
      </a>
      <a class="text-black mx-2 align-self-center text-decoration-none" href="">
        5
      </a>
      <button class="btn btn-dark ms-3">▶</button>
    </div>
    <br />
    <b-row align-v="center" align-h="center">
      <b-col cols="1.2">
        <b-form-select
          size="sm"
          v-model="selected"
          :options="options"></b-form-select>
      </b-col>
      <b-col cols="3">
        <b-input-group>
          <b-form-input
            size="sm"
            class=""
            placeholder="Search..."
            v-model="searchParam"
            @keyup.enter="validate"
            ref="searchParam"></b-form-input>
          <b-input-group-append>
            <b-button size="sm" class="" variant="" @click="validate"
              >Search</b-button
            >
          </b-input-group-append>
        </b-input-group>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import { mapActions, mapGetters, mapState } from "vuex";
const memberStore = "memberStore";
export default {
  components: {
    "board-row": () => import("@/components/board/include/BoardRow.vue"),
  },
  props: {
    type: String,
  },
  data() {
    return {
      selected: "subject",
      options: [
        { value: "subject", text: "제목" },
        { value: "user_id", text: "작성자" },
      ],
      searchParam: "",
    };
  },
  methods: {
    ...mapActions(["getBoardList", "getBoardSearch"]),
    moveBoardCreate() {
      switch (this.type) {
        case "0":
          this.$router.push({ name: "NoticeCreate" });
          break;
        case "1":
          this.$router.push({ name: "QnaCreate" });
          break;
      }
    },
    validate() {
      let isValid = true;
      let errMsg = "";

      !this.searchParam.trim()
        ? ((isValid = false),
          (errMsg = "검색어를 입력해주세요."),
          this.$refs.searchParam.focus())
        : (isValid = true);

      if (!isValid) {
        alert(errMsg);
      } else {
        const payload = {
          type: this.type,
          searchParam: this.searchParam.trim(),
          selected: this.selected,
        };
        this.selected = "subject";
        this.searchParam = "";
        this.getBoardSearch(payload);
      }
    },
  },
  computed: {
    ...mapGetters(["boardList"]),
    ...mapGetters(memberStore, ["checkUserInfo"]),
    ...mapState(memberStore, ["isLogin"]),
  },
  created() {
    this.getBoardList(this.type);
  },
};
</script>

<style scoped>
.no-scroll-board::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}
</style>
