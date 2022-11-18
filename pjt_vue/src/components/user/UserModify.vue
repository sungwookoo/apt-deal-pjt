<template>
  <write-form type="modify" :user="user"></write-form>
</template>

<script>
export default {
  data() {
    return {
      user: null,
    };
  },
  created() {
    this.user = this.$route.params.user;
  },
  components: {
    "write-form": () => import("@/components/user/include/WriteForm.vue"),
  },
};
</script>
<style scoped></style>
