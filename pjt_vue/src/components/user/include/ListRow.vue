<template>
  <b-tr class="text-center">
    <b-td>{{ no }}</b-td>
    <b-th>
      <router-link
        :to="{
          path: '#',
        }"
        class="link">
        {{ userid }}
      </router-link>
    </b-th>
    <b-td>
      {{ username }}
    </b-td>
    <b-td>{{ userphone }}</b-td>
    <b-td>{{ useremail }}</b-td>
    <b-td>{{ userregtime }}</b-td>
    <b-td>{{ usertype == "1" ? "관리자" : "일반" }}</b-td>
  </b-tr>
</template>

<script>
export default {
  // 부모 component로 부터 전달받은 도서정보
  props: {
    no: String,
    userid: String,
    username: String,
    userphone: String,
    useremail: String,
    userregtime: String,
    usertype: String,
  },
};
</script>

<style scope>
.link {
  text-decoration: none;
  color: black;
}
</style>
