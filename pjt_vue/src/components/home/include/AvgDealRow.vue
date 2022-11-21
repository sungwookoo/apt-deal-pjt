<template>
  <tr v-if="deal.deal != 0">
    <td>{{ index + 1 }}</td>
    <td>
      {{ deal.dongName }}
    </td>
    <td style="text-align: right">
      {{ parseFloat(deal.deal).toFixed(2) }}
    </td>
  </tr>
</template>

<script>
export default {
  props: {
    deal: Object,
    index: Number,
  },
};
</script>

<style scoped>
td {
  text-align: center;
}
</style>
