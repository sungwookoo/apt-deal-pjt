<template>
  <find-form></find-form>
</template>

<script>
export default {
  components: {
    "find-form": () => import("@/components/user/include/FindForm.vue"),
  },
};
</script>

<style scoped>
.user-box {
  height: 400px;
}
</style>
