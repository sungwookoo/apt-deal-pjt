<template>
  <b-col class="m-4 user-box" offset="2">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show>
          <h2>회원 정보</h2>
        </b-alert>
      </b-col>
    </b-row>
    <b-row class="mb-1">
      <b-col class="text-left">
        <b-form>
          <b-form-group
            label-cols="2"
            id="id-group"
            label="아이디"
            label-for="id">
            <b-form-input
              id="id"
              ref="id"
              type="text"
              v-model="userDetailInfo.userId"
              required
              placeholder="아이디 입력..."
              readonly />
          </b-form-group>
          <b-form-group
            label-cols="2"
            id="pwd-group"
            label="패스워드"
            label-for="pwd">
            <b-input-group v-if="!isPwd">
              <b-form-input
                id="pwd"
                ref="pwd"
                type="password"
                v-model="userDetailInfo.userPassword"
                required
                readonly
                placeholder="비밀번호 입력..." />
              <b-button variant="outline-dark" @click="isPwdChange"
                ><b-icon-eye-fill></b-icon-eye-fill
              ></b-button>
            </b-input-group>
            <b-input-group v-else>
              <b-form-input
                id="pwd"
                ref="pwd"
                type="text"
                v-model="userDetailInfo.userPassword"
                required
                readonly
                placeholder="비밀번호 입력..." />
              <b-button variant="outline-dark" @click="isPwdChange"
                ><b-icon-eye-slash-fill></b-icon-eye-slash-fill
              ></b-button>
            </b-input-group>
          </b-form-group>
          <b-form-group
            label-cols="2"
            id="name-group"
            label="이름"
            label-for="name">
            <b-form-input
              id="name"
              ref="name"
              type="text"
              v-model="userDetailInfo.userName"
              required
              readonly
              placeholder="이름 입력..." />
          </b-form-group>
          <b-form-group
            label-cols="2"
            id="email-group"
            label="이메일"
            label-for="emailid">
            <b-input-group>
              <b-form-input
                id="emailid"
                ref="emailid"
                type="text"
                :value="userDetailInfo.emailId"
                required
                readonly
                placeholder="이메일 입력..." />
              <b-input-group-text>@</b-input-group-text>
              <b-form-select
                ref="emaildomain"
                v-model="userDetailInfo.emailDomain"
                disabled
                :options="options"></b-form-select>
            </b-input-group>
          </b-form-group>
          <b-form-group
            label-cols="2"
            id="phone-group"
            label="휴대폰"
            label-for="phone">
            <b-form-input
              id="phone"
              ref="phone"
              type="text"
              v-model="userDetailInfo.userPhone"
              readonly
              required
              placeholder="휴대폰 입력..." />
          </b-form-group>
          <b-row>
            <b-button class="m-1" variant="outline-success" @click="movePage">
              홈
            </b-button>
            <b-button
              class="m-1"
              variant="outline-primary"
              @click="moveUserModify">
              수정
            </b-button>
            <b-button class="m-1" variant="outline-danger" @click="remove"
              >삭제</b-button
            >
            <b-row v-if="!isNaver" align-v="center">
              <b-col cols="1" id="naverIdLogin"></b-col>
              <b-col
                cols="7"
                style="
                  height: 40px;
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  font-weight: bold;
                  color: white;
                  margin-left: 26px;
                  background-color: #1ec800;
                ">
                연동 하기
              </b-col>
            </b-row>
            <b-row v-else align-v="center">
              <b-col cols="1" id="naverIdLogin" class="no-pointer"></b-col>
              <b-col
                cols="7"
                style="
                  height: 40px;
                  display: flex;
                  justify-content: center;
                  align-items: center;
                  font-weight: bold;
                  color: white;
                  margin-left: 26px;
                  background-color: #1ec800;
                ">
                연동 완료
              </b-col>
            </b-row>
          </b-row>
        </b-form>
      </b-col>
    </b-row>
  </b-col>
</template>

<script>
import { mapActions, mapState } from "vuex";
const memberStore = "memberStore";
export default {
  mounted() {
    const naverLogin = new window.naver.LoginWithNaverId({
      clientId: process.env.VUE_APP_NAVER_LOGIN_CLIENT_ID,
      callbackUrl: process.env.VUE_APP_NAVER_CONNECT_CALLBACK_URL,
      loginButton: { color: "green", type: 1, height: 40 },
    });
    naverLogin.init();
  },

  props: {
    user: Object,
  },
  created() {},
  data() {
    return {
      isPwd: false,
      options: [
        { value: "", text: "도메인" },
        { value: "ssafy.com", text: "싸피" },
        { value: "google.com", text: "구글" },
        { value: "naver.com", text: "네이버" },
      ],
    };
  },
  methods: {
    ...mapActions(memberStore, ["removeUser"]),
    moveUserModify() {
      this.$router.push({
        name: "UserModify",
        params: { user: this.userDetailInfo },
      });
    },
    movePage() {
      this.$router.push({ name: "Home" });
    },
    isPwdChange() {
      this.isPwd = !this.isPwd;
    },

    async remove() {
      await this.removeUser(this.userDetailInfo.userId);
      this.movePage();
    },
  },
  computed: {
    ...mapState(memberStore, ["userDetailInfo"]),
    isNaver() {
      return this.userDetailInfo.naverId == null ? false : true;
    },
  },
};
</script>

<style scoped>
.user-box {
  height: 400px;
}
.no-pointer {
  pointer-events: none;
}
</style>
