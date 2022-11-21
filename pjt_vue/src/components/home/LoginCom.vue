<template>
  <div v-if="!isLogin">
    <div>
      <b-card title="" header-tag="header" footer-tag="footer">
        <template #header>
          <h6 class="mb-0">
            <strong>구해줘! 홈즈</strong>에 오신것을 환영합니다
          </h6>
        </template>
        <b-button
          style="width: 30%"
          href="#"
          variant="primary"
          :to="{ name: 'UserLogin' }"
          >로그인</b-button
        >
        <template #footer>
          <span>
            구해줘! 홈즈가 처음이신가요?
            <router-link :to="{ name: 'UserCreate' }">회원가입</router-link>
          </span>
        </template>
      </b-card>
    </div>
  </div>
  <div v-else>
    <!-- {{ userInfo }} 환영합니다. -->
    <div>
      <b-card
        :title="userInfo.userName + ' (' + userInfo.userId + ')'"
        header-tag="header"
        footer-tag="footer">
        <template #header>
          <h6 class="mb-0" style="text-align: left">
            [{{ userInfo.userType == "1" ? "관리자" : "회원" }}]
          </h6>
        </template>

        <b-card-text> </b-card-text>

        <b-button
          style="margin-right: 10px"
          href="#"
          variant="primary"
          :to="{ name: 'UserDetail' }"
          >정보변경</b-button
        >
        <b-button
          style="margin-left: 10px"
          href="#"
          variant="primary"
          @click.prevent="onClickLogout"
          >로그아웃</b-button
        >
      </b-card>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from "vuex";
const memberStore = "memberStore";
export default {
  computed: {
    ...mapState(memberStore, ["userInfo", "isLogin"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout"]),
    onClickLogout() {
      console.log(this.userInfo.userId);
      //vuex actions에서 userLogout 실행(Backend에 저장 된 리프레시 토큰 없애기
      //+ satate에 isLogin, userInfo 정보 변경)
      // this.$store.dispatch("userLogout", this.userInfo.userid);
      this.userLogout(this.userInfo.userId);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      alert("로그아웃 완료.");
      console.log("로그아웃===================");
      if (this.$route.path != "/") this.$router.push({ name: "Home" });
    },
  },
};
</script>

<style scoped>
.link {
  text-decoration: none;
  color: black;
}
</style>
