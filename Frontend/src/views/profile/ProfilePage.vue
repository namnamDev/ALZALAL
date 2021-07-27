<template>
<<<<<<< HEAD
<div>
    <TopProfile></TopProfile>
    <DownProfile></DownProfile>
 
</div>
  <!-- <div class="container feed">
    <div class="row">
      <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
      <div class="profile box col-lg-6 col-md-10 col-sm-9 col-10 ">

			<div class="profile-image">

				<img src="@/assets/logo.png" alt="">

			</div>

			<div class="profile-user-settings">

				<h1 class="profile-user-name">janedoe_</h1>

				<button class="btn profile-edit-btn">Edit Profile</button>

				<button class="btn profile-settings-btn" aria-label="profile settings"><i class="fas fa-cog" aria-hidden="true"></i></button>

			</div>

			<div class="profile-stats">

				<ul>
					<li><span class="profile-stat-count">164</span> 게시글</li>
					<li><span class="profile-stat-count">188</span> 팔로워</li>
					<li><span class="profile-stat-count">206</span> 팔로잉</li>
				</ul>

			</div>

			<div class="profile-bio">

				<p> Lorem ipsum dolor sit, amet consectetur adipisicing elit</p>

			</div>

		</div>
		
        <div>
            게시글,댓글
        </div>
    </div>
    
  </div> -->
</template>

<script>
import TopProfile from '@/components/profile/topProfile.vue'
import DownProfile from '@/components/profile/downProfile.vue'
export default {
 components: {
     TopProfile,
     DownProfile
 }

=======
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
>>>>>>> 30b0cb8c4f62403cd4bf5bbad51a9e189108fa79
}
</script>
