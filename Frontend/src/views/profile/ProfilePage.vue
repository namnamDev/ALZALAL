<template>
  <div class="contents">
      <div class="profile-wrapper profile-wrapper-sm"></div>
      <span>
          <div>
              <div class="profileImageDiv">
                  <img class='profileImage' src="@/assets/images/logo.png" alt="사진">
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
// import topProfile from '@/components/profile/topProfile.vue'
// import downProfile from '@/components/profile/downProfile.vue'
export default {
    components: {
        // topProfile,
        // downProfile
    },
}
</script>

<style scoped>
body {
    text-align: center;
}
</style>