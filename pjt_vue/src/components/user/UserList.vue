<template>
  <b-container
    class="mt-4 bg-light bg-opacity-75 pt-4"
    style="max-height: 700px; height: 700px">
    <b-row>
      <b-col class="text-center">
        <h3 class="underline">회원 목록</h3>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col cols="3">
        <b-input-group>
          <b-form-input
            size="sm"
            class="mr-sm-2"
            placeholder="ID 검색..."
            v-model="idSearchParam"
            ref="idSearchParam"></b-form-input>
          <b-input-group-append>
            <b-button
              size="sm"
              class="my-2 my-sm-0"
              variant="outline-success"
              @click="validate"
              >Search</b-button
            >
          </b-input-group-append>
        </b-input-group>
      </b-col>
      <b-col class="text-right">
        <b-button variant="outline-primary" @click="moveUserRegister"
          >등록</b-button
        >
      </b-col>
    </b-row>
    <b-row>
      <b-col v-if="userList">
        <b-table-simple hover responsive>
          <b-thead head-variant="dark">
            <b-tr class="text-center">
              <b-th>번호</b-th>
              <b-th>아이디</b-th>
              <b-th>이름</b-th>
              <b-th>휴대폰</b-th>
              <b-th>이메일</b-th>
              <b-th>가입 일자</b-th>
              <b-th>타입</b-th>
            </b-tr>
          </b-thead>
          <tbody>
            <list-row
              v-for="(user, index) in userList"
              :key="index"
              :no="`${index + 1}`"
              :userid="user.userId"
              :username="user.userName"
              :userphone="user.userPhone"
              :useremail="user.emailId + '@' + user.emailDomain"
              :userregtime="user.joinDate"
              :usertype="user.userType"></list-row>
          </tbody>
        </b-table-simple>
      </b-col>
      <b-col v-else class="text-center">회원 목록이 없습니다.</b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapActions, mapState } from "vuex";
const memberStore = "memberStore";
export default {
  data() {
    return {
      idSearchParam: "",
    };
  },
  components: {
    "list-row": () => import("@/components/user/include/ListRow.vue"),
  },
  methods: {
    ...mapActions(memberStore, ["getUserList"]),
    validate() {
      let isValid = true;
      let errMsg = "";

      !this.idSearchParam.trim()
        ? ((isValid = false),
          (errMsg = "검색어를 입력해주세요."),
          this.$refs.idSearchParam.focus())
        : (isValid = true);

      if (!isValid) {
        alert(errMsg);
      } else {
        const payload = this.idSearchParam.trim();
        this.idSearchParam = "";
        this.getUserList(payload);
      }
    },
    moveUserRegister() {
      this.$router.push({ name: "Home" });
    },
  },
  created() {
    const userid = "";
    this.getUserList(userid);
  },
  computed: {
    ...mapState(memberStore, ["userList"]),
  },
};
</script>

<style></style>
