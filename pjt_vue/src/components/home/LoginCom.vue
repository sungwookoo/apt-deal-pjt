<template>
  <div>
    <router-link class="link" :to="{ name: 'UserManagement' }">
      로그인
    </router-link>
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
