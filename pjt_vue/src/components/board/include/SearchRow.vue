<template>
  <b-row
    class="justify-content-center p-2 m-2"
    style="height: 90px; max-height: 90px">
    <div>{{ board }}</div>
  </b-row>
</template>

<script>
export default {
  props: {
    board: Object,
  },
};
</script>

<style></style>
