<template>
    <div class="row">
        <div class="col-lg-3 col-md-2 col-sm-3 col-3"></div>
        <div class="col-lg-6 col-md-10 col-sm-9 col-6 ">
            <div class="row-lg-8">
                    <!-- 프로필이미지 -->
                <div class="col-lg-3">
                    <div class="profile-image">
                        <img class="profileImg" v-if="imgsrc" :src="imgsrc" @error="replaceByDefault" alt="프로필사진">
                        <div class="modifyProfile">
                            <button class="btn clickImg" @click="clickImg">
                                Select Image
                            </button>
                        </div>
                    </div>
                </div>
                    <!-- 프로필 이름, 수정버튼 -->
                <div class="col-lg-9">
                    <div class="row-lg-8">
                        <div class="profile-user-settings row-lg-8">
                            <h1 class="profile-user-name">{{name}}<button class="btn clickSetting" @click="clickSetting"><i class="fa">&#xf013;</i></button></h1>
                        </div>
                                <!-- 게시글 팔로워 팔로잉 -->
                        <div class="profile-stats">
                            <ul>
                                <li>{{articleCount}}<span class="profile-stat-count"> 게시글</span></li>
                                <li>{{follower}}<span @click="clickFollower" class="profile-stat-count"> 팔로워</span></li>
                                <li>{{following}}<span @click="clickFollow" class="profile-stat-count"> 팔로잉</span></li>
                            </ul>
                        </div>
                    </div>
                    
                    <div class="profile-bio row-lg-4">
                        <p align="left" class="downInfo">Main :
                            <span v-for="item,index in language" :key="index">{{item}}</span>
                        </p>
                        <p align="left" class="downInfo">Site : 
                            <span v-for="item,index in problemsite" :key="index">{{item}}</span>
                        </p>                           
                    </div>
                  
                </div>
            </div>
            <div class="row-lg-4">
                <div>
                    <p align="left" class="downInfo1">{{helpmeSuccessCount}}개의 게시글에 답변완료.</p>
                </div>
                <div class="introduceline">
                    <p class="introtext" align="left">{{introduce}}
                        <button @click="clickIntro" class="btn clickIntro"><i class="fad fa-pencil"></i></button>
                    </p>
                </div>
            </div>
	    </div>
		<!-- End of profile section -->
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
           defaultImg: '@/assets/images/profileImg.png',
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
            console.log(res.data.follower)
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
        clickFollow: function() {
            this.$router.push({'name':'followPage'})
        },
        clickFollower: function() {
            this.$router.push({'name':'followPage'})
        },
        replaceByDefault(e){
            e.target.src = "@/assets/images/profileImg.png";
        }
    },
    computed: {
        userPk: function(){
            return userpk
        },
    }
}
</script>

<style scoped>
.row {
    margin-top: 120px;
}

.profile-image {
    float: left;
    width: 170px;
    height: auto;
    margin-right: 20px;
    
}

.profileImg {
    width: 170px;
    height: 170px;
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
.profile-user-name {
    font-size: 50px;
    font-weight: 600;
    margin-left: 5vw;
}
.profile-settings-btn {
    font-size: 10px;
}

.profile-stats {
    margin-top: 1rem;
    display: inline-block;
}
.profile-stats li {
    display: inline-block;
    font-size: 20px;
    line-height: 1.5;
    margin-right: 1rem;
    cursor: pointer;
}
.profile-bio {
    font-size: 20px;
    font-weight: 550;
    line-height: 1.5;
    margin-top: 20px;
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
    line-height: 1.3;
    margin-top: 1rem;
}
.btn-intro{
    font-weight: 600;
    box-shadow: 0 2px 4px 0 rgba(0,0,0,.1);
    font-size: 10px;
}
.introtext{
    font-size: 25px;
    font-weight: 400;
}
.clickIntro{
    width: 1%;
    margin-left: -70px;
}
.clickSetting {
    width: 1%;
    margin-left: -70px;
}
.clickImg{
    font-size: 20px;
    font-weight: 550;
}
@media (max-width:576px) {
.profile-image {
    float: left;
    width: 150px;
    height: auto;
    margin-right: 20px;
    
}

.profileImg {
    width: 180px;
    height: 180px;
    border-radius: 75%;
    
}
}
</style>