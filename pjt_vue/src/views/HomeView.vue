<template>
  <div>
    <b-container>
      <b-row>
        <b-col>
          <b-row class="justify-content-center home-logo" align-v="center">
            <b-col>
              <img
                src="@/assets/home.png"
                class="d-inline-block align-middle home-img"
                alt="home-img"
            /></b-col>
          </b-row>
          <b-row>
            <b-col cols="12" class="home-login">
              <login-com></login-com>
            </b-col>
          </b-row>
        </b-col>
        <b-col>
          <b-row class="justify-content-center">
            <b-col cols="11" offset-md="1" class="home-avg">
              <avg-deal></avg-deal>
            </b-col>
          </b-row>
        </b-col>
      </b-row>

      <b-row class="mt-5">
        <b-col cols="4">
          <p>
            <router-link :to="{ name: 'Deal' }" class="home-icon">
              <b-icon icon="geo-alt-fill" color="skyblue"></b-icon>
            </router-link>
          </p>
        </b-col>
        <b-col cols="4">
          <p>
            <router-link :to="{ name: 'Notice' }" class="home-icon">
              <b-icon icon="bell-fill" color="orange"></b-icon>
            </router-link>
          </p>
        </b-col>
        <b-col cols="4">
          <p>
            <router-link :to="{ name: 'Qna' }" class="home-icon">
              <b-icon icon="question-circle" color="purple"></b-icon>
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
  font-size: 4rem;
  color: black;
}

.home-login {
  height: 250px;
  border: 1px solid black;
}

.home-avg {
  height: 550px;
  border: 1px solid black;
}

.home-logo {
  height: 300px;
  font-size: 45px;
  font-weight: bold;
}

.home-img {
  height: 280px;
}

.test {
  border: 2px solid black;
  height: 200px;
}
</style>
