<template>
  <b-container class="mt-4" style="max-height: 700px; height: 700px">
    <b-row
      class="justify-content-center"
      style="max-height: 700px; height: 700px"
      align-v="center">
      <b-row style="width: 90%; height: 500px; border: solid" align-v="center">
        <b-col class="m-1">
          <img
            src="@/assets/logo.png"
            class="d-inline-block align-middle"
            width="90%"
            alt="kitten" />
        </b-col>
        <router-view></router-view>
      </b-row>
    </b-row>
  </b-container>
</template>

<script>
export default {};
</script>

<style></style>
