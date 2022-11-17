<template>
  <b-col class="m-4 user-box" style="border: solid" offset="2">
    <b-row>
      <b-col><h2>로그인</h2></b-col>
    </b-row>
    <b-row>
      <b-col>
        <b-form class="text-left">
          <!-- <b-alert show variant="danger" v-if="isLoginError"
            >아이디 또는 비밀번호를 확인하세요.</b-alert
          > -->
          <b-form-group label="아이디:" label-for="userid">
            <b-form-input
              ref="userLoginId"
              required
              placeholder="아이디 입력...."
              v-model="user.userId"
              @keyup.enter="validate"></b-form-input>
          </b-form-group>
          <b-form-group label="비밀번호:" label-for="userpwd">
            <b-form-input
              type="password"
              ref="userLoginPwd"
              required
              v-model="user.userPassword"
              placeholder="비밀번호 입력...."
              @keyup.enter="validate"></b-form-input>
          </b-form-group>
          <!-- <b-button type="button" variant="primary" class="m-1" @click="validate"
            >로그인</b-button
          >
          <b-button
            type="button"
            variant="success"
            class="m-1"
            @click="movePage"
            >회원가입</b-button
          > -->
        </b-form>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <div @click="validate">로그인</div>
      </b-col>
      <b-col>
        <router-link :to="{ name: 'UserCreate' }"> 회원가입 </router-link>
      </b-col>
    </b-row>
    <b-row>
      <b-col>
        <router-link :to="{ name: 'UserFind' }">
          비밀번호 찾기 버튼
        </router-link>
      </b-col>
    </b-row>
  </b-col>
</template>

<script>
import { mapState, mapActions } from "vuex";
const memberStore = "memberStore";
export default {
  data() {
    return {
      user: {
        userId: "",
        userPassword: "",
      },
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError", "userInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    validate() {
      let isValid = true;
      let errMsg = "";

      !this.user.userId.trim()
        ? ((isValid = false),
          (errMsg = "아이디를 입력해주세요."),
          this.$refs.userLoginId.focus())
        : !this.user.userPassword.trim()
        ? ((isValid = false),
          (errMsg = "비밀번호를 입력해주세요."),
          this.$refs.userLoginPwd.focus())
        : (isValid = true);

      if (!isValid) {
        alert(errMsg);
      } else {
        console.log(this.user);
        this.confirm();
      }
    },
    async confirm() {
      console.log(this.user);
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      // console.log("1. confirm() token >> " + token);
      if (this.isLogin) {
        await this.getUserInfo(token);
        // console.log("4. confirm() userInfo :: ", this.userInfo);
        console.log(this.userInfo + "======= 로그인 성공");
        this.$router.push({ name: "Home" });
      }
    },
  },
};
</script>
<style scoped>
.user-box {
  height: 400px;
}
</style>
