<template>
  <tr>
    <td>
      {{ this.board.boardNo }}
    </td>
    <td style="text-align: left">
      <router-link
        v-if="board.type == '0'"
        :to="{ name: 'NoticeDetail', query: { boardNo: board.boardNo } }"
        class="link">
        {{ this.board.subject }}
      </router-link>
      <router-link
        v-else
        :to="{ name: 'QnaDetail', query: { boardNo: board.boardNo } }"
        class="link">
        {{ this.board.subject }}
      </router-link>
    </td>
    <td>
      {{ this.board.userName }}
    </td>
    <td>
      <!-- {{ this.board.createDate }} -->
      {{ $moment(this.board.createDate).format("YY/MM/DD HH:mm") }}
    </td>
  </tr>
</template>

<script>
export default {
  props: {
    board: Object,
  },
  created() {
    // console.log(this.board);
  },
};
</script>

<style scoped>
.link {
  text-decoration: none;
  color: black;
}
</style>
