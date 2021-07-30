<template>
    <div>
        <TopProfile></TopProfile>
        <DownProfile></DownProfile>
    </div>

</template>

<script>
import TopProfile from '@/components/profile/topProfile.vue'
import DownProfile from '@/components/profile/downProfile.vue'
export default {
 components: {
     TopProfile,
     DownProfile
 }

}
</script>
