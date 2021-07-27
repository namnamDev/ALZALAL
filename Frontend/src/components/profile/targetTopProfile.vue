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
.feed{
  margin-top: 13vw;
}
.profile box{
  border: 1px solid black;
  height: 300px;
  padding: 5rem 0;
}
.profile::after {
    content: "";
    display: block;
    clear: both;
}
.profile-image {
    float: left;
    width: calc(33.333% - 1rem);
    display: flex;
    justify-content: center;
    align-items: center;
    margin-right: 3rem;
}

.profile-image img {
    border-radius: 50%;
}

.profile-user-settings,
.profile-stats,
.profile-bio {
    float: left;
    width: calc(66.666% - 2rem);
}

.profile-user-settings {
    margin-top: 1.1rem;
}

.profile-user-name {
    display: inline-block;
    font-size: 3.2rem;
    font-weight: 300;
}

.profile-edit-btn {
    font-size: 1.4rem;
    line-height: 1.8;
    border: 0.1rem solid #dbdbdb;
    border-radius: 0.3rem;
    padding: 0 2.4rem;
    margin-left: 2rem;
}

.profile-settings-btn {
    font-size: 2rem;
    margin-left: 1rem;
}

.profile-stats {
    margin-top: 2.3rem;
}

.profile-stats li {
    display: inline-block;
    font-size: 1.6rem;
    line-height: 1.5;
    margin-right: 4rem;
    cursor: pointer;
}

.profile-stats li:last-of-type {
    margin-right: 0;
}

.profile-bio {
    font-size: 1.2rem;
    font-weight: 400;
    line-height: 1.5;
    margin-top: 2.3rem;
}

.profile-real-name,
.profile-stat-count,
.profile-edit-btn {
    font-weight: 600;
} 
</style>