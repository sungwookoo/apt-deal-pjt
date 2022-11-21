<template>
  <div v-if="!isLogin">
    <div>
      <router-link class="link" :to="{ name: 'UserLogin' }">
        로그인
      </router-link>
    </div>
  </div>
  <div v-else>{{ userInfo }} 환영합니다.</div>
</template>

<script>
import { mapState } from "vuex";
const memberStore = "memberStore";
export default {
  computed: {
    ...mapState(memberStore, ["userInfo", "isLogin"]),
  },
};
</script>

<style scoped>
.link {
  text-decoration: none;
  color: black;
}
</style>
