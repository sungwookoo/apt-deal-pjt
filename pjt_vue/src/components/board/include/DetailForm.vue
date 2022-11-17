<template>
  <div>
    <b-row class="mb-1">
      <b-col>
        <b-card class="mb-2" border-variant="dark" no-body>
          <b-card-header class="text-center">
            <h3>제목 : {{ board.subject }}</h3>
            <h5>번호 : {{ board.boardNo }}</h5>
            <div>
              <h6>작성자 : {{ board.userName }}</h6>
            </div>
            <div>
              <h6>조회 : {{ board.hit }}</h6>
            </div>
          </b-card-header>
          <b-card-body class="text-left">
            <div :inner-html.prop="board.content"></div>
          </b-card-body>
        </b-card>
      </b-col>
    </b-row>
  </div>
</template>

<script>
export default {
  props: {
    board: Object,
  },
};
</script>

<style></style>
