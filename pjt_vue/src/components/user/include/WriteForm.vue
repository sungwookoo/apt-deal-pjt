<template>
  <b-col class="m-4 user-box" offset="2">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show>
          <h2 v-if="type == 'create'">회원 등록</h2>
          <h2 v-else>회원 수정</h2>
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
              v-if="type == 'create'"
              id="id"
              ref="id"
              type="text"
              v-model="input.userId"
              required
              placeholder="아이디 입력..." />
            <b-form-input
              v-else
              id="id"
              ref="id"
              type="text"
              v-model="input.userId"
              required
              placeholder="아이디 입력..."
              readonly />
          </b-form-group>
          <b-form-group
            label-cols="2"
            id="pwd-group"
            label="패스워드"
            label-for="pwd">
            <b-form-input
              id="pwd"
              ref="pwd"
              type="password"
              v-model="input.userPassword"
              required
              placeholder="비밀번호 입력..." />
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
              v-model="input.userName"
              required
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
                v-model="input.emailId"
                required
                placeholder="이메일 입력..." />
              <b-input-group-text>@</b-input-group-text>
              <b-form-select
                ref="emaildomain"
                v-model="input.emailDomain"
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
              v-model="input.userPhone"
              required
              placeholder="휴대폰 입력..." />
          </b-form-group>
          <b-button
            v-if="type == 'create'"
            class="m-1"
            variant="outline-primary"
            @click="validate">
            회원 등록
          </b-button>
          <b-button
            v-else
            class="m-1"
            variant="outline-primary"
            @click="validate">
            회원 수정
          </b-button>
          <b-button
            v-if="type == 'create'"
            class="m-1"
            variant="outline-danger"
            @click="moveLogin"
            >취소</b-button
          >
          <b-button
            v-else
            class="m-1"
            variant="outline-danger"
            @click="moveUserDetail"
            >취소</b-button
          >
        </b-form>
      </b-col>
    </b-row>
  </b-col>
</template>

<script>
import { mapActions, mapState } from "vuex";
const memberStore = "memberStore";
export default {
  props: {
    type: String,
  },
  data() {
    return {
      options: [
        { value: "", text: "도메인" },
        { value: "ssafy.com", text: "싸피" },
        { value: "google.com", text: "구글" },
        { value: "naver.com", text: "네이버" },
      ],
    };
  },
  computed: {
    ...mapState(memberStore, ["userDetailInfo"]),
    input() {
      if (this.type == "create") {
        return {
          userId: "",
          userPassword: "",
          userName: "",
          emailId: "",
          emailDomain: "",
          userPhone: "",
          userType: "0",
        };
      } else {
        return { ...this.userDetailInfo };
      }
    },
  },
  methods: {
    ...mapActions(memberStore, ["userJoin", "userModify", "userInfo"]),
    validate() {
      let isValid = true;
      let errMsg = "";

      !this.input.userId.trim()
        ? ((isValid = false),
          (errMsg = "아이디를 입력해주세요."),
          this.$refs.id.focus())
        : !this.input.userPassword.trim()
        ? ((isValid = false),
          (errMsg = "비밀번호를 입력해주세요."),
          this.$refs.pwd.focus())
        : !this.input.userName.trim()
        ? ((isValid = false),
          (errMsg = "이름을 입력해주세요."),
          this.$refs.name.focus())
        : !this.input.emailId.trim()
        ? ((isValid = false),
          (errMsg = "이메일 아이디를 입력해주세요."),
          this.$refs.emailid.focus())
        : !this.input.emailDomain.trim()
        ? ((isValid = false),
          (errMsg = "이메일 도메인 선택해주세요."),
          this.$refs.emaildomain.focus())
        : !this.input.userPhone.trim()
        ? ((isValid = false),
          (errMsg = "휴대폰을 입력해주세요."),
          this.$refs.phone.focus())
        : (isValid = true);

      if (!isValid) {
        alert(errMsg);
      } else {
        switch (this.type) {
          case "create":
            console.log(this.input);
            this.join();
            break;
          case "modify":
            this.modify();
            break;
        }
      }
    },
    async join() {
      const payload = {
        user: this.input,
        callback: () => {
          if (
            this.userInfo == null ||
            this.userInfo == {} ||
            this.userInfo == undefined
          )
            this.moveLogin();
          else this.$router.push({ name: "Home" });
        },
      };
      await this.userJoin(payload);
    },
    async modify() {
      const user = { ...this.input };
      await this.userModify(user);
      this.moveUserDetail();
    },
    moveLogin() {
      this.$router.push({ name: "UserLogin" });
    },
    moveUserDetail() {
      this.$router.push({
        name: "UserDetail",
        query: { userId: this.input.userId },
      });
    },
  },
};
</script>

<style scoped>
.user-box {
  height: 400px;
}
</style>
