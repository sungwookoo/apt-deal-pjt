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
              v-model="user.userid"
              @keyup.enter="validate"></b-form-input>
          </b-form-group>
          <b-form-group label="비밀번호:" label-for="userpwd">
            <b-form-input
              type="password"
              ref="userLoginPwd"
              required
              v-model="user.userpwd"
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
export default {
  data() {
    return {
      user: {
        userid: "",
        userpwd: "",
      },
    };
  },
  methods: {
    validate() {
      let isValid = true;
      let errMsg = "";

      !this.user.userid.trim()
        ? ((isValid = false),
          (errMsg = "아이디를 입력해주세요."),
          this.$refs.userLoginId.focus())
        : !this.user.userpwd.trim()
        ? ((isValid = false),
          (errMsg = "비밀번호를 입력해주세요."),
          this.$refs.userLoginPwd.focus())
        : (isValid = true);

      if (!isValid) {
        alert(errMsg);
      } else {
        // const payload = {
        //   type: this.type,
        //   searchParam: this.userLoginId.trim(),
        //   selected: this.selected,
        // };
        // this.selected = "subject";
        // this.searchParam = "";
        // this.getBoardSearch(payload);
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
