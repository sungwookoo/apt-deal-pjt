<template>
  <div>
    <write-form :type="type" action="create"></write-form>
  </div>
</template>

<script>
export default {
  props: {
    type: String,
  },
  components: {
    "write-form": () => import("@/components/board/include/WriteForm.vue"),
  },
};
</script>

<style></style>
