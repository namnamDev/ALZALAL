<template>
	<div class="container">
    <LoginForm/>
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
  height: 90vh;
}

</style>
