<template>
  <div>
    <write-form :type="type" :action="modify"></write-form>
  </div>
</template>

<script>
export default {
  props: {
    type: String,
  },
  created() {
    console.log(this.type);
  },
  components: {
    "write-form": () => import("@/components/board/include/WriteForm.vue"),
  },
};
</script>

<style></style>
