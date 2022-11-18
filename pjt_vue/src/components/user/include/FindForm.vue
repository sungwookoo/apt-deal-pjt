<template>
  <b-col class="m-4 user-box" offset="2">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show>
          <h2>비밀번호 찾기</h2>
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
              v-model="user.userId"
              required
              placeholder="아이디 입력..." />
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
              v-model="user.userName"
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
                v-model="user.emailId"
                required
                placeholder="이메일 입력..." />
              <b-input-group-text>@</b-input-group-text>
              <b-form-select
                v-model="user.emailDomain"
                ref="emaildomain"
                :options="options"></b-form-select>
            </b-input-group>
          </b-form-group>
          <b-button class="m-1" variant="outline-primary" @click="validate">
            찾기
          </b-button>
          <b-button variant="outline-danger" @click="movePage">취소</b-button>
        </b-form>
      </b-col>
    </b-row>
  </b-col>
</template>

<script>
import { mapActions } from "vuex";
const memberStore = "memberStore";
export default {
  data() {
    return {
      user: {
        userId: "",
        userName: "",
        emailId: "",
        emailDomain: "",
      },
      options: [
        { value: "", text: "도메인" },
        { value: "ssafy.com", text: "싸피" },
        { value: "google.com", text: "구글" },
        { value: "naver.com", text: "네이버" },
      ],
    };
  },
  methods: {
    ...mapActions(memberStore, ["pwdFind"]),
    movePage() {
      this.$router.push({ name: "UserLogin" });
    },
    validate() {
      let isValid = true;
      let errMsg = "";

      !this.user.userId.trim()
        ? ((isValid = false),
          (errMsg = "아이디를 입력해주세요."),
          this.$refs.id.focus())
        : !this.user.userName.trim()
        ? ((isValid = false),
          (errMsg = "이름을 입력해주세요."),
          this.$refs.name.focus())
        : !this.user.emailId.trim()
        ? ((isValid = false),
          (errMsg = "이메일 아이디를 입력해주세요."),
          this.$refs.emailid.focus())
        : !this.user.emailDomain.trim()
        ? ((isValid = false),
          (errMsg = "이메일 도메인 선택해주세요."),
          this.$refs.emaildomain.focus())
        : (isValid = true);

      if (!isValid) {
        alert(errMsg);
      } else {
        const payload = {
          user: this.user,
          successCallback: (data) => {
            setTimeout(() => {
              this.$bvToast.toast(
                `비밀번호 찾기에 성공하였습니다. 비밀번호 : ${data} `,
                {
                  title: "비밀번호 찾기",
                  variant: "success",
                  toaster: "b-toaster-bottom-center",
                  autoHideDelay: 1500,
                  solid: true,
                  // eslint-disable-next-line prettier/prettier
                }
              );
            }, 100);
            this.movePage();
          },
          failCallback: () => {
            this.$bvToast.toast(
              `비밀번호 찾기에 실패하였습니다. 다시 시도해 주세요`,
              {
                title: "비밀번호 찾기",
                variant: "danger",
                toaster: "b-toaster-bottom-center",
                autoHideDelay: 1500,
                solid: true,
                // eslint-disable-next-line prettier/prettier
              }
            );
          },
        };
        this.findPwd(payload);
      }
    },
    async findPwd(payload) {
      await this.pwdFind(payload);
    },
  },
};
</script>

<style></style>
