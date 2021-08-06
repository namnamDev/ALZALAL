<template>
  <div>
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">검색</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body py-4">
            <SearchDetail />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SearchDetail from '@/components/search/SearchDetail.vue'
export default {
  components: {
    SearchDetail
  },

  methods: {
  }
}
</script>

<style scoped>
  .modal-content{
    /* height: 600px; */
    position:relative;
  }
  #submit{
    background-color: rgba(161,212,226,1);
  }
  #submit:hover{
    background-color: rgb(133, 191, 207);
  }
  .btn{
    border-radius: 4px;
  }
</style>