<template>
  <div class="container feed">
    <div class="row">
      <div class="col-lg-3 col-md-2 col-sm-3 col-0"></div>
      <div class="profile box5 col-lg-6 col-md-10 col-sm-9 col-12 ">
                <!-- 프로필이미지 -->
			  <div class="profile-image">
				  <img class="profileImg" v-if="imgsrc" :src="imgsrc" alt="프로필사진">
                  <img class="profileImg" v-if="!imgsrc" src="@/assets/images/profileImg.png" alt="">
                  <div class="modifyProfile">
                      <button class="btn clickImg" @click="clickImg">
                          Select Image
                      </button>
                  </div>
			  </div>

                <!-- 프로필 이름, 수정버튼 -->
			  <div class="profile-user-settings">
				  <h1 class="profile-user-name">{{name}}</h1>
                  <span>
				    <button class="btn clickSetting" @click="clickSetting"><i class="fa">&#xf013;</i></button>
                  </span>
			  </div>
                    <!-- 게시글 팔로워 팔로잉 -->
			  <div class="profile-stats">
				  <ul>
					  <li><span class="profile-stat-count">{{articleCount}}</span> 게시글</li>
					  <li><span href="/profilePage/followPage" class="profile-stat-count">{{follower}}</span> 팔로워</li>
					  <li><span href="/profilePage/followPage" class="profile-stat-count">{{following}}</span> 팔로잉</li>
				  </ul>
			  </div>

			  <div class="profile-bio">
                  <p align="left" class="downInfo">Main :
                      <span v-for="item,index in language" :key="index">{{item}}</span>
                  </p>
                  <p align="left" class="downInfo">Site : 
                      <span v-for="item,index in problemsite" :key="index">{{item}}</span>
                  </p>
				  <p align="left" class="downInfo1">{{helpmeSuccessCount}}개의 게시글에 답변완료.</p>
			  </div>
              <div class="introduceline">
                  <p class="introtext" align="left">{{introduce}}
                      <button @click="clickIntro" class="btn clickIntro"><i class="fad fa-pencil"></i></button>
                  </p>
              </div>

		  </div>
		<!-- End of profile section -->
    </div>
  </div>
</template>

<script>
import jwt_decode from 'jwt-decode'
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL
const token = localStorage.getItem('jwt')
let userpk = '';
if (token) {
  const decoded = jwt_decode(token)
  userpk = decoded.sub
}
export default {
    data(){
        return{
           imgsrc: `${SERVER_URL}/profile/img/${userpk}`,
           no: '',
           follower: '',
           language: [],
           problemsite: [],
           helpmeSuccessCount: '',
           following: '',
           name: '',
           articleCount:'',
           helpmeCount:'',
           introduce: '',

        }
    },
    created: function() {
        console.log(userpk)
        axios({
            method: 'get',
            url: `${SERVER_URL}/profile/${userpk}`,

        })
        .then(res =>{
            console.log(res)         
            this.no = res.data.no
            this.follower = res.data.follower
            this.following = res.data.following
            this.language = res.data.language
            this.problemsite = res.data.problemsite
            this.helpmeSuccessCount = res.data.helpmeSuccessCount
            this.helpmeCount = res.data.helpmeCount
            this.name = res.data.name
            this.articleCount = res.data.articleCount
            this.introduce = res.data.introduce
            console.log(this.language)
        })
        .catch(err => {
            console.log(err);
        })
       
    },
    methods: {
        clickIntro: function() {
            this.$router.push({'name':'introduce'})
        },
        clickImg: function() {
            this.$router.push({'name':'uploadImg'})
        },
        clickSetting: function() {
            this.$router.push({'name':'passwordConfirm'})
        },
    },
    computed: {
        userPk: function(){
            return userpk
        },
        // outputLanguage: function(language){
        //      var reg = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
        //     //특수문자 검증
        //     if(reg.test(language)){
        //         //특수문자 제거후 리턴
        //         return language.replace(reg, "");    
        //     } else {
        //         //특수문자가 없으므로 본래 문자 리턴
        //         return language;
        //     }  
        // }
    }
}
</script>

<style scoped>
.feed{
  margin-top: 15vw;
   margin-left: 50px;
}
.profile box5{
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
    height: auto;
    justify-content: center;
    align-items: center;
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
    font-size: 1vw;
    line-height: 1.3;
    border-radius: 0.3rem;
    padding: 0 2.4rem;
    font-weight: 700;
    margin-top:30px
}
i {
    font-size: 1.9vw;
    line-height: 1;
    border-radius: 0.3rem;
    padding: 0 2.4rem;
    margin-left: 2rem;
}

.profile-user-settings,
.profile-stats,
.profile-bio {
    float: left;
    width: calc(66.666% - 2rem);
    vertical-align: 10px;
}

.profile-user-settings {

    margin-top: 1.1rem;
}

.profile-user-name {
    display: inline-block;
    font-size: 4vw;
    font-weight: 600;
    margin-left: 5vw;
}



.profile-settings-btn {
    font-size: 2rem;
    margin-left: 1rem;
}

.profile-stats {
    margin-top: 1rem;
}

.profile-stats li {
    display: inline-block;
    font-size: 1.6vw;
    line-height: 1.5;
    margin-right: 1rem;
    cursor: pointer;
}

.profile-stats li:last-of-type {
    margin-right: 0;
}

.profile-bio {
    font-size: 1vw;
    font-weight: 400;
    line-height: 1.5;
    margin-top: 1rem;
}
.downInfo {
    font-size: 2.5vw;
    font-weight: 600;
    line-height: 1.0;
}
.downInfo1 {
    font-size: 1.7vw;
    font-weight: 500;
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
    font-size: 2vw;
    font-weight: 400;
    line-height: 1.3;
    margin-top: 1rem;
}
.btn-intro{
    font-weight: 600;
    box-shadow: 0 2px 4px 0 rgba(0,0,0,.1);
    font-size: 2vw;
}
.introtext{
    font-size: 2.5vw;
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
@media (max-width:577px) {
  .feed{
    margin-left:0;
  }
}

</style>