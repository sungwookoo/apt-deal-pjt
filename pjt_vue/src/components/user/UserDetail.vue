<template>
  <detail-form></detail-form>
</template>

<script>
import { mapActions, mapState } from "vuex";
const memberStore = "memberStore";
export default {
  components: {
    "detail-form": () => import("@/components/user/include/DetailForm.vue"),
  },
  created() {
    const payload = {
      userid: this.detailUserId,
      callback: () => {
        this.movePage();
      },
    };
    this.userDetail(payload);
  },
  methods: {
    ...mapActions(memberStore, ["getUserDetailInfo", "initDetailUser"]),
    async userDetail(payload) {
      await this.getUserDetailInfo(payload);
    },
    async movePage() {
      await this.initDetailUser();
      this.$router.push({ name: "Home" });
    },
  },
  computed: {
    ...mapState(memberStore, ["detailUserId"]),
  },
};
</script>

<style scoped></style>
