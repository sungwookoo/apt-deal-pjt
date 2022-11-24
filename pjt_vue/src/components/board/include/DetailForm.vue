<template>
  <div>
    <b-row class="mb-1">
      <b-col>
        <b-card class="mb-2" border-variant="dark" no-body>
          <b-card-header class="text-left" style="height: 80px">
            <h5>[{{ board.boardNo }}] {{ board.subject }}</h5>
            <div>
              <p>
                {{ board.userName }} ({{ board.userId }})<span
                  style="color: lightgray">
                  &nbsp;&nbsp;|&nbsp;&nbsp;</span
                >
                {{ $moment(this.board.createDate).format("YYYY.MM.DD HH:mm:ss")
                }}<span style="color: lightgray">&nbsp;&nbsp;|&nbsp;</span> 조회
                {{ board.hit }}
              </p>
            </div>
          </b-card-header>
          <b-card-body
            class="text-left"
            style="height: 400px; overflow-y: scroll">
            <div :inner-html.prop="board.content | filterEnterToBr"></div>
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
