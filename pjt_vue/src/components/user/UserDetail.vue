<template>
  <detail-form :user="userDetailInfo"></detail-form>
</template>

<script>
import { mapActions, mapState } from "vuex";
const memberStore = "memberStore";
export default {
  data() {
    return {
      userId: "",
    };
  },
  components: {
    "detail-form": () => import("@/components/user/include/DetailForm.vue"),
  },
  created() {
    const payload = {
      userid: this.$route.query.userId,
      callback: () => {
        this.movePage();
      },
    };
    this.userDetail(payload);
  },
  methods: {
    ...mapActions(memberStore, ["getUserDetailInfo"]),
    async userDetail(payload) {
      await this.getUserDetailInfo(payload);
    },
    movePage() {
      this.$router.push({ name: "Home" });
    },
  },
  computed: {
    ...mapState(memberStore, ["userDetailInfo"]),
  },
};
</script>

<style scoped></style>
