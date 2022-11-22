<template>
  <div>
    <b-container
      class="mt-4 bg-light bg-opacity-75 pt-4"
      style="max-height: 730px; height: 730px">
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
