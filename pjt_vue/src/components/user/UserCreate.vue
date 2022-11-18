<template>
  <write-form type="create"></write-form>
</template>

<script>
export default {
  components: {
    "write-form": () => import("@/components/user/include/WriteForm.vue"),
  },
};
</script>
<style scoped></style>
