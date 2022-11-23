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
    this.userDetail(this.detailUserId);
  },
  methods: {
    ...mapActions(memberStore, ["getUserDetailInfo"]),
    async userDetail(userid) {
      await this.getUserDetailInfo(userid);
    },
  },
  computed: {
    ...mapState(memberStore, ["detailUserId"]),
  },
};
</script>

<style scoped></style>
