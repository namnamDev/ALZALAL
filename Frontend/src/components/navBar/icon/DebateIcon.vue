<template>
  <i class="fas fa-users" @click="clickDebate"></i>
</template>

<script>
export default {
    methods: {
        clickDebate: function() {
        this.$router.push({'name':'debate'})
        }
    },
}
</script>

<style>
</style>