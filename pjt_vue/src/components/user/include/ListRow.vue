<template>
  <b-tr class="text-center">
    <b-td>{{ no }}</b-td>
    <b-th class="link" @click="moveUserDetail">
      {{ userid }}
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
import { mapActions } from "vuex";
const memberStore = "memberStore";
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
  methods: {
    ...mapActions(memberStore, ["setDetailUser"]),
    async moveUserDetail() {
      await this.setDetailUser(this.userid);
      this.$router.push({ name: "UserDetail" });
    },
  },
};
</script>

<style scope>
.link {
  text-decoration: none;
  color: black;
}
.link:hover {
  cursor: pointer;
}
</style>
