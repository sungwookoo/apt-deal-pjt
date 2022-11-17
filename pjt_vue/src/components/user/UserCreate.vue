<template>
  <b-col class="m-4 user-box" style="border: solid" offset="2">
    <b-row>
      <b-col><h2>유저 등록</h2></b-col>
    </b-row>
    <b-row>
      <b-col>아이디</b-col>
    </b-row>
    <b-row>
      <b-col>비밀번호</b-col>
    </b-row>
    <b-row>
      <b-col>이름</b-col>
    </b-row>
    <b-row>
      <b-col>이메일(아이디 + 도메인)</b-col>
    </b-row>
    <b-row>
      <b-col>휴대폰 번호</b-col>
    </b-row>
    <b-row>
      <b-col>등록 버튼</b-col>
      <b-col>
        <router-link :to="{ name: 'UserLogin' }"> 취소 버튼 </router-link>
      </b-col>
    </b-row>
  </b-col>
</template>

<script>
export default {};
</script>
<style scoped>
.user-box {
  height: 400px;
}
</style>
