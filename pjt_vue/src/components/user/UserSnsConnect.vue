<template>
  <b-container
    class="mt-4 bg-light bg-opacity-75 pt-4"
    style="max-height: 700px; height: 700px">
    네이버 연동하기
  </b-container>
</template>

<script>
export default {
  mounted() {
    const naverLogin = new window.naver.LoginWithNaverId({
      clientId: process.env.VUE_APP_NAVER_LOGIN_CLIENT_ID,
    });
    naverLogin.init();
    console.log(naverLogin);

    naverLogin.getLoginStatus(async (status) => {
      if (status) {
        const payload = {
          info: {
            userId: this.$store.state.memberStore.userInfo.userId,
            naverId: naverLogin.user.id,
          },
          failCallback: () => {
            setTimeout(() => {
              this.$bvToast.toast(`이미 연동된 아이디 입니다.`, {
                title: "SNS 연동 실패",
                variant: "danger",
                toaster: "b-toaster-bottom-center",
                autoHideDelay: 3000,
                solid: true,
                // eslint-disable-next-line prettier/prettier
              });
            }, 100);
            this.$router.push({ name: "UserDetail" });
          },
        };
        await this.$store.dispatch("memberStore/userSnsConnect", payload);
      } else {
        console.log("AccessToken이 올바르지 않습니다.");
        setTimeout(() => {
          this.$bvToast.toast(`SNS 연동 실패하였습니다.`, {
            title: "SNS 연동 실패",
            variant: "danger",
            toaster: "b-toaster-bottom-center",
            autoHideDelay: 3000,
            solid: true,
            // eslint-disable-next-line prettier/prettier
          });
        }, 100);
        await this.$store.dispatch(
          "memberStore/setDetailUser",
          // eslint-disable-next-line prettier/prettier
          this.$store.state.memberStore.userInfo.userId
        );
        this.$router.push({ name: "UserDetail" });
      }
    });
  },
};
</script>

<style></style>
