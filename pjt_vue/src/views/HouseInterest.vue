<template>
  <div>
    <b-container
      class="mt-4 bg-light bg-opacity-75 pt-4"
      style="max-height: 700px; height: 700px">
      <interest-com></interest-com>
    </b-container>
  </div>
</template>

<script>
export default {
  components: {
    "interest-com": () => import("@/components/interest/InterestCom.vue"),
  },
};
</script>

<style></style>
