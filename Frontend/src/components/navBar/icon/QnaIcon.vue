<template>
<i class="fas fa-question-circle" @click="clickQnA"></i>  
</template>

<script>
export default {

  methods: {
    clickQnA: function() {
      this.$router.push({'name':'qna'})      
    }
  },
}
</script>

<style>

</style>