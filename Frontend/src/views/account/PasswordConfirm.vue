<template>
  <div class="container">
    <PasswordComfirmInput></PasswordComfirmInput>
  </div>
</template>

<script>
import PasswordComfirmInput from '@/components/account/PasswordConfirmInput.vue'

export default {
  components: {
    PasswordComfirmInput,
  },
};
</script>

<style scoped>
.container{
  margin-top: 150px;
}
@media (max-width:576px){
  .container{
    margin-top: 90px;
  }
}
</style>
