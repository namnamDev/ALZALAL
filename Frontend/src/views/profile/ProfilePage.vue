<template>
  <div class="contents">
      <div class="profile-wrapper profile-wrapper-sm"></div>
      <span>
          <div>
              <div class="profileImageDiv">
                  <!-- <img class='profileImage' src="@/assets/logo.png" alt="사진"> -->
              </div>
          </div>
          <div>
              <div>
                  <div>
                      profilename
                  </div>
                  <div>
                    <ul>
                        <li>게시글</li>
                        <li>팔로워</li>
                        <li>팔로우</li>
                    </ul>
                  </div>
                  <div>
                      <button>팔로우</button>
                  </div>
              </div>
          </div>
      </span>
      <div>
          한줄소개
      </div>
     
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.contents {
  max-width: 1020px;
  margin: 0 auto;
  padding: 0 5px;
  width: 100%;  
}
.profile-wrapper {
    background: white;
  /* -webkit-box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08); */
  /* box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08); */
  border-radius: 3px;
  padding: 15px 15px;
}
.profile-wrapper-sm {
     max-width: 500px;
  margin: 40px auto;
  padding: 0px 0 20px;
}
.profileImageDiv {
    width:100px;
    height:100px;
}
.profileImage {
    height: 100%;
    width: 100%;
}
</style>