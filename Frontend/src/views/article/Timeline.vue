<template>
  <div class="container">
    <TimelineItem />
    <TimelineItem />
    <TimelineItem />
    <TimelineItem />
    
  </div>
</template>

<script>
import TimelineItem from '@/components/article/TimelineItem.vue'

export default {
  components: { TimelineItem },

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