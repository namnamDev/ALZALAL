<template>
  <div class="container">
      <ModifyForm></ModifyForm>
  </div>
</template>

<script>
import ModifyForm from '@/components/account/ModifyForm.vue'
export default {
    components: {
        ModifyForm,
    }
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