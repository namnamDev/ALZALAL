<template>
  <div class="container">
    <QnAItem />
    <QnAItem />
    <QnAItem />
    <QnAItem />
    
  </div>
</template>

<script>
import QnAItem from '@/components/article/QnAItem.vue'

export default {
  components: { QnAItem },

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