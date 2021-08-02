<template>
 	<div class="container">
     <SignupForm></SignupForm>
   </div>
</template>

<script>
import SignupForm from '@/components/account/SignupForm.vue';
export default{
  components: {
    SignupForm,
  },
}
</script>

<style scoped>
.container{
  margin-top: 110px;
}
@media (max-width:576px){
  .container{
    margin-top: 60px;
  }
}
</style>
