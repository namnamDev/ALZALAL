<template>
  <div class="container feed">
    <div class="row">
      <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
      <div class="profile box col-lg-6 col-md-10 col-sm-9 col-10 ">

			  <div class="profile-image">

				  <img src="@/assets/images/logo.png" alt="">

			  </div>

			  <div class="profile-user-settings">

				  <h1 class="profile-user-name">다른유저잉름</h1>

				  
			  </div>

			  <div class="profile-stats">

				  <ul>
					  <li><span class="profile-stat-count">164</span> 게시글</li>
					  <li><span class="profile-stat-count">188</span> 팔로워</li>
					  <li><span class="profile-stat-count">206</span> 팔로잉</li>
				  </ul>

			  </div>

			  <div class="profile-bio">

				  <p>총 n개의 게시글에 답변했습니다.</p>

			  </div>

		  </div>
		<!-- End of profile section -->
    
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.row {
    margin-top: 120px;
}

.profile-image {
    float: left;
    width: 160px;
    height: auto;
    margin-right: 3rem;
    
}

.profileImg {
    width: 160px;
    height: 160px;
    border-radius: 75%;
    
}
.btn-modify{
    width: 120%;
    height: 100%;
    font-size: 10px;
    line-height: 1.3;
    border-radius: 0.3rem;
    padding: 0 2.4rem;
    font-weight: 700;
    margin-top:30px
}
i {
    font-size: 30px;
    line-height: 1;
    border-radius: 0.3rem;
    padding: 0 2.4rem;
    margin-left: 2rem;
}

.profile-user-settings,
.profile-stats,
.profile-bio {
    width: calc(66.666% - 2rem);
    vertical-align: 10px;
}

.profile-user-settings {

    margin-top: 1.1rem;
}

.profile-user-name {
    font-size: 30px;
    font-weight: 600;
    margin-left: 5vw;
}



.profile-settings-btn {
    font-size: 10px;
    margin-left: 1rem;
}

.profile-stats {
    margin-top: 1rem;
}

.profile-stats li {
    display: inline-block;
    font-size: 20px;
    line-height: 1.5;
    margin-right: 1rem;
    cursor: pointer;
}

.profile-stats li:last-of-type {
    margin-right: 0;
}

.profile-bio {
    font-size: 20px;
    font-weight: 400;
    line-height: 1.5;
    margin-top: 1rem;
}
.downInfo {
    font-size: 20px;
    font-weight: 600;
    line-height: 1.0;
}
.downInfo1 {
    font-size: 20px;
    font-weight: 600;
    line-height: 1.0;
}
.profile-real-name,
.profile-stat-count,
.profile-edit-btn {
    font-weight: 600;
} 
.profile-user-settings{
    align-items: center;
}
.introduceline{
    font-size: 10px;
    font-weight: 400;
    line-height: 1.3;
    margin-top: 1rem;
}
.btn-intro{
    font-weight: 600;
    box-shadow: 0 2px 4px 0 rgba(0,0,0,.1);
    font-size: 10px;
}
.introtext{
    font-size: 20px;
    font-weight: 400;
}

.clickIntro{
    width: 1%;
    margin-left: -10%;
}
.clickSetting {
    width: 1%;
    margin-left: -15%;
}
</style>