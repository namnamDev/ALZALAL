<template>
  <i class="fas fa-compass" @click="clickTimeline"></i>
</template>

<script>
export default {
    methods:{
      clickTimeline: function() {
      this.$router.push({'name':'timeline'})
      },
    }
}
</script>

<style>

</style>