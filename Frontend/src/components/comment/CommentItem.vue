<template>
  <div class="comment mb-5">
    <div class="user">
      <div class="row">
        <div class="col">
          <div class="inline">
            작성자
          </div >
          <div class="inline">
            작성날짜
          </div>
          <div class="inline">
            <i class="fal fa-thumbs-up">
              <span class="ms-2">34</span>
            </i>
          </div>
        </div>
        <div class="col text-end pe-3">
          좋아요
        </div>
      </div>
    </div>
    <div class="content">
      Lorem ipsum dolor sit amet consectetur adipisicing elit. Est corrupti minus, inventore ab voluptate numquam nihil dolore illum harum suscipit veniam itaque aliquam velit porro cumque consectetur repellat ducimus sint minima ex. Nesciunt temporibus saepe impedit sed provident suscipit, dignissimos, delectus ab quasi eius iusto quisquam, non quos quaerat soluta!
    </div>

  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.content{
  /* border: 1px solid black; */
  padding: 10px;
  border-radius: 6px;
  box-shadow: 0 10px 10px rgba(0, 0, 0, 0.08);
}
.inline{
  display:inline;
}
</style>