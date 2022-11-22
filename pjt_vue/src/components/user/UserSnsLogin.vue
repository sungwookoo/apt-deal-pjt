<template>
  <div></div>
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
          userId: naverLogin.user.id,
          failCallback: () => {
            setTimeout(() => {
              this.$bvToast.toast(`로그인 후 연동이 필요합니다.`, {
                title: "SNS 로그인 실패",
                variant: "danger",
                toaster: "b-toaster-bottom-center",
                autoHideDelay: 3000,
                solid: true,
                // eslint-disable-next-line prettier/prettier
              });
            }, 100);
            this.$router.push({ name: "UserLogin" });
          },
        };
        await this.$store.dispatch("memberStore/userSnsConfirm", payload);
        let token = sessionStorage.getItem("access-token");
        if (this.$store.state.memberStore.isLogin) {
          await this.$store.dispatch("memberStore/getUserInfo", token);
          // console.log("4. confirm() userInfo :: ", this.userInfo);
          console.log(this.$store.state.memberStore.userInfo);
          console.log(
            this.$store.state.memberStore.userInfo.userId +
              // eslint-disable-next-line prettier/prettier
              "======= 로그인 성공"
          );
          this.$router.push({ name: "Home" });
        }
      } else {
        console.log("AccessToken이 올바르지 않습니다.");
        setTimeout(() => {
          this.$bvToast.toast(`로그인 후 연동이 필요합니다.`, {
            title: "SNS 로그인 실패",
            variant: "danger",
            toaster: "b-toaster-bottom-center",
            autoHideDelay: 3000,
            solid: true,
            // eslint-disable-next-line prettier/prettier
          });
        }, 100);
        this.$router.push({ name: "UserLogin" });
      }
    });
  },
};
</script>

<style></style>
