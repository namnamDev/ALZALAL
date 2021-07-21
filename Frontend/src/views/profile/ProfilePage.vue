<template>
    <div class='contents'>
    <topProfile></topProfile>
     <downProfile></downProfile>
    </div>
</template>

<script>
import topProfile from '@/components/profile/topProfile.vue'
import downProfile from '@/components/profile/downProfile.vue'
export default {
    components: {
        topProfile,
        downProfile
    },
}
</script>

<style>
body {
    text-align: center;
}
</style>