<template>
  <div>
      <TargetTopProfile></TargetTopProfile>
      <TargetDownProfile></TargetDownProfile>
  </div>
</template>

<script>
import TargetDownProfile from '@/components/profile/targetDownProfile.vue'
import TargetTopProfile from '@/components/profile/targetTopProfile.vue'
export default {
    components: {
        TargetDownProfile,
        TargetTopProfile,
    }
}
</script>

<style>

</style>