<template>
  <div>
    <b-navbar toggleable="lg" type="light" variant="light" fixed="top">
      <b-navbar-brand href="#">
        <router-link to="/">
          <img
            src="@/assets/logo.png"
            class="d-inline-block align-middle"
            width="70px"
            alt="kitten" />
        </router-link>
      </b-navbar-brand>

      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item
            ><router-link :to="{ name: 'Deal' }" class="link"
              >매매검색</router-link
            ></b-nav-item
          >
          <b-nav-item
            ><router-link :to="{ name: 'Notice' }" class="link"
              >공지사항</router-link
            ></b-nav-item
          >
          <b-nav-item
            ><router-link :to="{ name: 'Qna' }" class="link"
              >QnA</router-link
            ></b-nav-item
          >
          <b-nav-item v-if="isLogin"
            ><router-link :to="{ name: 'Interest' }" class="link"
              >관심지역</router-link
            ></b-nav-item
          >
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto">
          <b-nav-form>
            <b-form-input
              size="sm"
              class="mr-sm-2"
              placeholder="Search..."
              v-model="fullSearchParam"
              @keyup.enter="validate"
              ref="fullSearch"></b-form-input>
            <input type="text" style="display: none" />

            <b-button
              size="sm"
              class="my-2 my-sm-0"
              variant="outline-success"
              @click="validate"
              >Search</b-button
            >
          </b-nav-form>

          <b-nav-item-dropdown right>
            <div v-if="!isLogin">
              <b-dropdown-item
                ><router-link :to="{ name: 'UserCreate' }" class="link"
                  >회원가입</router-link
                ></b-dropdown-item
              >
              <b-dropdown-item
                ><router-link :to="{ name: 'UserLogin' }" class="link"
                  >로그인</router-link
                ></b-dropdown-item
              >
            </div>
            <div v-else>
              <b-dropdown-item
                ><div class="link" @click="onClickLogout">
                  로그아웃
                </div></b-dropdown-item
              >
              <b-dropdown-item
                ><div class="link" @click="moveMyDetail">회원 정보</div>
              </b-dropdown-item>
            </div>
            <b-dropdown-item v-if="isLogin && userInfo.userType == '1'"
              ><router-link :to="{ name: 'UserList' }" class="link"
                >회원 목록</router-link
              ></b-dropdown-item
            >
          </b-nav-item-dropdown>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>
import { mapState, mapGetters, mapActions } from "vuex";
const memberStore = "memberStore";
export default {
  data() {
    return {
      fullSearchParam: "",
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
    ...mapGetters(["checkUserInfo"]),
  },
  methods: {
    ...mapActions(memberStore, ["userLogout", "setDetailUser"]),
    validate() {
      let isValid = true;
      let errMsg = "";

      !this.fullSearchParam.trim()
        ? ((isValid = false),
          (errMsg = "검색어를 입력해주세요."),
          this.$refs.fullSearch.focus())
        : (isValid = true);

      if (!isValid) {
        alert(errMsg);
      } else {
        const fullSearch = this.fullSearchParam.trim();
        this.fullSearchParam = "";
        this.$router.push({
          name: "Search",
          query: { keyword: fullSearch },
        });
      }
    },
    onClickLogout() {
      console.log(this.userInfo.userId);
      //vuex actions에서 userLogout 실행(Backend에 저장 된 리프레시 토큰 없애기
      //+ satate에 isLogin, userInfo 정보 변경)
      // this.$store.dispatch("userLogout", this.userInfo.userid);
      this.userLogout(this.userInfo.userId);
      sessionStorage.removeItem("access-token"); //저장된 토큰 없애기
      sessionStorage.removeItem("refresh-token"); //저장된 토큰 없애기
      alert("로그아웃 완료.");
      console.log("로그아웃===================");
      if (this.$route.path != "/") this.$router.push({ name: "Home" });
    },
    async moveMyDetail() {
      console.log(this.userInfo.userId);
      await this.setDetailUser(this.userInfo.userId);
      this.$router.push({ name: "UserDetail" });
    },
  },
};
</script>

<style scoped>
.link {
  text-decoration: none;
  font-weight: bold;
}
.link:hover {
  cursor: pointer;
  color: #007bff;
}
</style>
