<template>
  <div>
    <router-link class="link" :to="{ name: 'UserLogin' }"> 로그인 </router-link>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.link {
  text-decoration: none;
  color: black;
}
</style>
