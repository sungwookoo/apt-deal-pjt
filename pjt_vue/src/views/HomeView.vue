<template>
  <div>
    <b-container>
      <b-row class="justify-content-center home-logo" align-v="center">
        <b-col> 구해줘 홈즈 </b-col>
      </b-row>
      <b-row class="justify-content-center">
        <b-col cols="5" class="home-com">
          <login-com></login-com>
        </b-col>
        <b-col cols="5" offset-md="1" class="home-com">
          <avg-deal></avg-deal>
        </b-col>
      </b-row>
      <b-row class="mt-5">
        <b-col cols="4">
          <p>
            <router-link :to="{ name: 'Home' }" class="home-icon">
              <b-icon icon="geo-alt-fill"></b-icon>
            </router-link>
          </p>
        </b-col>
        <b-col cols="4">
          <p>
            <router-link :to="{ name: 'Notice' }" class="home-icon">
              <b-icon icon="bell-fill"></b-icon>
            </router-link>
          </p>
        </b-col>
        <b-col cols="4">
          <p>
            <router-link :to="{ name: 'Qna' }" class="home-icon">
              <b-icon icon="question-circle"></b-icon>
            </router-link>
          </p>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
export default {
  components: {
    "login-com": () => import("@/components/home/LoginCom.vue"),
    "avg-deal": () => import("@/components/home/AvgDeal.vue"),
  },
};
</script>

<style scoped>
.home-icon {
  font-size: 5rem;
  color: black;
}

.home-com {
  height: 400px;
  border: 1px solid black;
}

.home-logo {
  height: 150px;
  font-size: 45px;
  font-weight: bold;
}
.test {
  border: 2px solid black;
  height: 400px;
}
</style>
