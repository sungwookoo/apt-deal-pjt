<template>
  <div>
    <b-container class="text-center justify-content-center">
      <house-map></house-map>
      <house-deal></house-deal>
      <div style="max-height: 30px; height: 30px"></div>
    </b-container>
  </div>
</template>

<script>
export default {
  name: "HouseDealView",
  components: {
    "house-map": () => import("@/components/house/HouseMap.vue"),
    "house-deal": () => import("@/components/house/HouseDeal.vue"),
  },
};
</script>

<style></style>
