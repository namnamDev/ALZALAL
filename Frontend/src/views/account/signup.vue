<template>
 	<div class="container">
     <SignupForm/>
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
  height: 90vh;
}
</style>
