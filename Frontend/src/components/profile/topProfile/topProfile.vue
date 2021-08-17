<template>
    <div class="row top">
        <div class="col-lg-3 col-md-2 col-sm-3 col-3 col-xl-3"></div>
        <div class="feed-item col-lg-6 col-md-10 col-sm-9 col-6 col-xl-6">
            <div class="row">
                    <!-- 프로필이미지 -->
                <div class="offset-1 col-lg-3">
                    <div class="profile-image">
                        <img class="profileImg"  :src="imgsrc">
                        <!-- <img class="profileImg"  :src="require(`@/assets/images/${imgsrc}.png`)" alt="Img" v-else> -->
                        <div class="modifyProfile" v-if="this.myPage">
                            <button class="btn clickImg" @click="clickImg">
                                Select Image
                            </button>
                        </div>
                    </div>
                </div>
                    <!-- 프로필 이름, 수정버튼 -->
                <div class="col-lg-8">
                    <div class="row">
                        <div class="profile-user-settings row-lg-8">
                            <h1 class="profile-user-name">{{name}}
                                <button v-if="this.myPage" class="btn clickSetting" @click="clickSetting"><i class="fa">&#xf013;</i></button>
                            </h1>
                            <button class="btn-follow" v-if="!this.myPage && !this.followState" @click="clickFollowBtn($event)">follow</button>
                            <button class="btn-unfollow" v-if="!this.myPage && this.followState" @click="clickFollowBtn($event)">Unfollow</button>
                        </div>
                                <!-- 게시글 팔로워 팔로잉 -->
                        <div class="profile-stats">
                            <ul>
                                <li class="count">{{articleCount}}<span class="profile-stat-count"> 게시글</span></li>
                                <li class="countc">{{follower}}<span @click="clickFollower" class="profile-stat-count"> 팔로워</span></li>
                                <li class="countc">{{following}}<span @click="clickFollow" class="profile-stat-count"> 팔로잉</span></li>
                            </ul>
                        </div>
                    </div>
                    
                    <div class="profile-bio row-lg-4">
                        <details>
                            <summary>Main Language</summary>
                            <p class="summaryList" v-for="item,index in language" :key="index">- {{item}}</p>    
                        </details>
                        <details>
                            <summary>Problem Site</summary>
                            <p class="summaryList" v-for="item,index in problemsite" :key="index">- {{item}}</p> 
                        </details>
                        <!-- <p align="left" class="downInfo">Main :
                            <span v-for="item,index in language" :key="index">{{item}}</span>
                        </p> -->
                        <!-- <p align="left" class="downInfo">Site : 
                            <span v-for="item,index in problemsite" :key="index">{{item}}</span>
                        </p>                            -->
                    </div>
                  
                </div>
            </div>
            <div class="row status-intro">
                <div>
                    <p align="left" class="downInfo1">{{helpmeSuccessCount}}개의 게시글에 답변완료.</p>
                </div>
                <div class="introduceline col-lg-2 col-md-2 col-sm-2 col-2 col-xl-2">
                    <button v-if="this.myPage" @click="clickIntro" class="btn clickIntro"><i class="fad fa-pencil"></i></button>
                </div>
                <div class="introduceline col-lg-7 col-md-7 col-sm-7 col-7 col-xl-7">
                    <p class="introtext" align="left">{{introduce}}</p>
                </div>
                <div class="introduceline col-lg-3 col-md-3 col-sm-3 col-3 col-xl-3">

                </div>
                <div v-if="!myPage">
                    <button @click="clickRequest"  class="btn btn-request">문제풀이 요청하기</button>
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
const token = sessionStorage.getItem('jwt')
let userpk = '';
if (token) {
  const decoded = jwt_decode(token)
  userpk = decoded.sub
}
export default {
    props:{
        userPk: Number
    },
    data(){
        return{
            //imgsrc: `${SERVER_URL}/profile/img/1004`,
            imgsrc: '',
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
            isLogin: '',
            myPage: '',
            followState: '',
        }
    },
    created: function() {
        // console.log("target",this.userPk)
        const userPk = localStorage.getItem("userPk")
         
        let pk = ''
        if(userPk){
            pk = userPk
            this.myPage = false
            this.imgsrc=`${SERVER_URL}/profile/img/${pk}`
            if(pk==userpk){
                this.myPage = true
            }
        }else{
            pk = userpk
            this.myPage = true
            this.imgsrc=`${SERVER_URL}/profile/img/${pk}`
        }
        
        axios({
            method: 'get',
            url: `${SERVER_URL}/profile/${pk}`,
            headers: this.getToken
        })
        .then(res =>{   
            // console.log(res.data)   
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
            this.followState = res.data.followState
        })
        .catch(err => {
            console.log(err);
        })
        // axios({
        //     method: 'get',
        //     url: `${SERVER_URL}/profile/img/${this.pk}`
        // })
        // .then(res => {
        //     this.flag = true
        //     this.imgsrc = res.data
        // })
        // .catch(() => {
        //     this.flag= false
        //     this.imgsrc = 'profile'
        //     // console.log(err)
        // })
       
    },
    computed:{
        getToken(){
        const token = sessionStorage.getItem('jwt')
        const config = {
            Authorization: `Bearer ${token}`
        }
        return config
        },

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
            this.$router.push({'name':'followPage', params:{compName:'followList'}})
        },
        clickFollower: function() {
            this.$router.push({'name':'followPage', params:{compName:'followerList'}})
        },
        clickRequest: function(){
            const token = sessionStorage.getItem('jwt')
                if(!token){
                    alert("로그인이 필요합니다.")
                this.$router.push({name:'login'})
                }
            this.$router.push({name:'createHelpme', params:{ targetPK:this.userPk }})
        },
        clickFollowBtn: function(event){
                const token = sessionStorage.getItem('jwt')
                if(!token){
                    alert("로그인이 필요합니다.")
                this.$router.push({name:'login'})
                }
            axios({
                    method: 'post',
                    url: `${SERVER_URL}/follow/member`,
                    data: {"memberNo": this.no},
                    headers: this.getToken,
                })
                .then(()=>{
                    // console.log(res)
                })
                .catch({
                    // console.log(this.no)
                    // console.log(this.getToken)
                    // console.log(err);
                })
            if(event.target.innerText == 'follow' ){
              event.target.innerText = 'Unfollow'
              event.target.style.backgroundColor='#FFFFFF'
              event.target.style.color="black"
              
            }else{
              event.target.innerText = 'follow'
              
              event.target.style.backgroundColor='rgb(62, 171, 111)'
              event.target.style.color="white"
            }

        },
    },
    

}
</script>

<style scoped>
.feed-item{
  /* box-shadow: 0 0 0px 5px rgba(62 ,171 ,111 , 1);     */
}
.fa{
    color: rgb(62, 171, 111) ;
}

.top {
    margin-top: 20px;
}
.profile-image {
    float: left;
    width: 170px;
    height: auto;
    
margin-left: -40px;
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
    font-size: 45px;
    font-weight: 600;
    margin-left: 5vw;
}
.profile-settings-btn {
    font-size: 10px;
}
.btn-unfollow {
  background-color: white;
  color: black;
  border: 1px solid rgb(62, 171, 111);
  font-weight: 550;
  width: 120px;
  margin-left: 70px;
}
.btn-follow{
  background-color: rgb(62, 171, 111);
  color: white;
  width: 120px;
  font-weight: 550;
  margin-left: 70px;
}
.profile-stats {
    margin-top: 1rem;
    display: inline-block;
}
.count{
    display: inline-block;
    font-size: 20px;
    line-height: 1.5;
    margin-right: 1rem;
}
.countc {
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
.btn-request{
  background-color: rgb(62, 171, 111);
  color: white;
  width: 160px;    
  margin-top: -20px;
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
    font-size: 20px;
    font-weight: 400;
    word-wrap: break-word;
    
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
    margin-top: 10px;
    margin-left: 30px;
    font-size: 13px;
    font-weight: 550;
    background-color: rgb(62, 171, 111);
  color: white;
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
@media (max-width:1200px),(min-width: 992px){
    .profile-stats{
        margin-left: 20px;
    }
}
@media (min-width:992px){
    .profile-bio{
        margin-left: 40px;
    }
}

.status-intro{
    margin-top: 20px;
}
.summaryList{
    margin-left: 20px;
    font-size: 18px;
}
</style>