<template>
  <tr>
    <td>
      {{ deal.dongName }}
    </td>
    <td>
      {{ parseFloat(deal.deal).toFixed(2) }}
    </td>
  </tr>
</template>

<script>
export default {
  props: {
    deal: Object,
  },
};
</script>

<style scoped>
td {
  text-align: center;
}
</style>
