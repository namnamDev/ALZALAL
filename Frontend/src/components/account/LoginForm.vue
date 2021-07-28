<template>
  <div class="contents">
    <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
		<div class="form-wrapper form-wrapper-sm box col-lg-6 col-md-10 col-sm-9 col-10">
			<form @submit.prevent="submitForm" class="form">
				<div>
					<label align="left" for="email">E-mail</label>
					<input id="email" type="text" v-model="form.email" />
					<p class="validation-text">
						<span class="warning" v-if="!isEmailValid || !form.email">
							Please enter an email address
						</span>
					</p>
				</div>
				<div>
					<label align="left" for="password">Password</label>
					<input id="password" type="password" v-model="form.password" />
				</div>
				<button
                :disabled="!isEmailValid && form.password"
					type="submit"
					class="btn"
                >
					Login
				</button>
        <div>
          <span><button class="btn btn-user">비밀번호찾기</button>
                <router-link to="/signup"><button class="btn btn-user">회원가입</button></router-link>
          </span>      
        </div>
        <div class='snsLogin'>
          <button class="btn btn-sns"><img src="" alt="kakao login" style=""></button>
          <button class="btn btn-sns"><img src="" alt="naver login" style=""></button>
          <button class="btn btn-sns"><img src="" alt="google login" style=""></button>
        </div>
			</form>
		
		</div>
       
	</div>
</template>

<script>
import { validateEmail } from '@/utils/validation.js';
import axios from 'axios';
//import jwt_decode from 'jwt-decode'

const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default {
    data() {
		return {
      form: {
        email : '',
			  password: '',
		  }
    };
	},
	computed: {
		isEmailValid() {
			return validateEmail(this.form.email);
		},
	},
  created: function() {
    const token = localStorage.getItem('jwt')
    if(token){
      this.$router.push({name:'timeline'})
    }
  },
  methods: {
    submitForm: function(){
      console.log(this.form)
      axios({
        method: 'post',
        url: `${SERVER_URL}/member/login`,
        data: this.form
      })   // back 에 로그인 요청
      .then(res =>{
        console.log(res.data.token);                    
        localStorage.setItem('jwt', res.data.token.accessToken); // vuex actions로 보냄
        localStorage.setItem('refresh', res.data.token.refreshToken);
        const payload = {
          email: this.form.email,
          isLogin: true
        }
        this.$store.dispatch("login",payload)
        //this.$store.dispatch("login",this.form.email)
        this.$router.push({name: 'timeline'})
        
      })
      .catch(err =>{  // 실패하면 error
        console.log(err)
        alert("ID 또는 비밀번호가 틀렸습니다.")
      })
    }
  }
	
};
</script>

<style scoped>
/*--- LAYOUT ---*/
.contents {
  position: absolute;
  margin: 0 auto;
  padding: 0 5px;
  width: 100%;  
  top:90px;
}
.form-wrapper {
  background: white;
  -webkit-box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
  box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
  border-radius: 3px;
  padding: 15px 15px;
}
.form-wrapper.form-wrapper-sm {
  max-width: 500px;
  margin: 40px auto;
}
.form-wrapper-sm .page-header {
  padding: 0px 0 20px;
}
.form {
	width: 460px;
	height: 100%;
}
.form .validation-text {
	margin-top: -0.5rem;
	margin-bottom: 0.5rem;
	font-size: 1rem;
	display: flex;
	flex-direction: row-reverse;
	justify-content: space-between;
}
.form label {
  width: 100%;
  display: block;
  margin-bottom: 0.5rem;
  font-size: 90%;
}
.form input,
.form textarea {
  font-family: inherit;
  font-size: 100%;
  width: 80%;
  border: 1px solid #dae1e7;
  box-shadow: 0 2px 4px 0 rgba(0,0,0,.1);
  padding: 0.5rem 0.75rem;
  margin-bottom: 1rem;
}
.form div:nth-last-child(2) {
  margin-bottom: 0.5rem;
}
.btn {
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  border-radius: .25rem;
}
.btn.disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
.btn-user{
    opacity: 0.8;
}
.btn-sns {
  display: block;
  margin-left: 0 auto;
}
</style>