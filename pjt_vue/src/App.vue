<template>
  <div id="app">
    <header-nav></header-nav>
    <router-view style="margin-top: 120px; margin-bottom: 50px" />
    <footer-nav></footer-nav>
  </div>
</template>

<script>
export default {
  components: {
    "header-nav": () => import("@/components/common/HeaderNav.vue"),
    "footer-nav": () => import("@/components/common/FooterNav.vue"),
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #000000;
}

.link {
  text-decoration: none;
}

a {
  text-decoration: none;
  color: black;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #000000;
}

nav a.router-link-exact-active {
  color: #242eb1;
}

.underline {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(196, 146, 146, 0) 80%,
    rgb(101, 204, 147) 30%
  );
}
</style>
