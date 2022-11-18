<template>
  <detail-form :user="user"></detail-form>
</template>

<script>
export default {
  components: {
    "detail-form": () => import("@/components/user/include/DetailForm.vue"),
  },
  created() {
    console.log(this.user);
  },
  computed: {
    user() {
      return {
        userId: "1",
        userPassword: "2",
        userName: "3",
        emailId: "4",
        emailDomain: "naver.com",
        userPhone: "5",
      };
    },
  },
};
</script>

<style scoped></style>
