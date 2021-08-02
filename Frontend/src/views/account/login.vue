<template>
	<div class="container">
    <LoginForm></LoginForm>
	</div>
</template>

<script>
import LoginForm from '@/components/account/LoginForm.vue';
export default {
  components: {
    LoginForm,
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
