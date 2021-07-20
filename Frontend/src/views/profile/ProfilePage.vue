<template>
  <div class="contents">
		    <div class="profile">

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
					    <li><span class="profile-stat-count">000</span> 게시글</li>
					    <li><span class="profile-stat-count">000</span> 팔로워</li>
					    <li><span class="profile-stat-count">000</span> 팔로잉</li>
				    </ul>

			    </div>

			    <div class="profile-bio">

				    <p><span class="profile-real-name">ProfileName</span> 한줄소개를 입력해주세요</p>

			    </div>

		    </div>
 
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.contents {
  position: absolute;
  top: 100px;
  left: 380px;
  margin: 0 auto;
  padding: 0 5px;
  width: 50%;  
  border: 1px solid;
}

img {
    display: block;
}

.btn {
    display: inline-block;
    font: inherit;
    background: none;
    border: none;
    color: inherit;
    padding: 0;
    cursor: pointer;
}

.btn:focus {
    outline: 0.5rem auto #4d90fe;
}

.visually-hidden {
    position: absolute !important;
    height: 1px;
    width: 1px;
    overflow: hidden;
    clip: rect(1px, 1px, 1px, 1px);
}

/* Profile Section */

.profile {
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
    font-size: 1rem;
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
    font-size: 1.6rem;
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