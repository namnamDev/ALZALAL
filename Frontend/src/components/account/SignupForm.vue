<template>
  <div class="row main">
      <div class="row top mt-2 form-wrapper form-wrapper-sm content">
        <span class="header">회원가입</span>
        <form @submit.prevent="submitForm" class="form">
          <div>
            <label for="email" align="left">E-mail </label>
            <input id="email" type="text" v-model="form.email" />
              <p class="validation-text">
              <span class="warning" v-if="!isEmailValid && !form.email">
                Please enter an email address
              </span>
            </p>
          </div>
          <div>
            <label for="password" align="left">비밀번호 </label>
            <input id="password" type="password" v-model="form.password" />
            <p class="validation-text">
              <span class="warning" v-if="!passwordLength">
                비밀번호는 10자 이상 20자 이하로 입력해주세요.
              </span>
            </p>
          </div>
                  <div>
            <label for="passwordConfirm" align="left">비밀번호 확인 </label>
            <input id="passwordConfirm" type="password" v-model="passwordConfirm" />
            <p class="validation-text">
              <span class='warning' v-if="!isPasswordValid">
                Please check the password
              </span>
            </p>
          </div>
          <div>
            <label for="name" align="left">닉네임</label>
            <input id="name" type="text" v-model="form.name" />
            <p class="validation-text">
              <span class='warning' v-if="!nameLength">
                닉네임은 2자이상 10자 이하로 입력해주세요
              </span>
            </p>            
          </div>
          <div class='checkbox' align="left">
            <details>
              <summary for="use_language">선호 언어 <i class="fas fa-chevron-down"></i></summary>
                <ul>
                    <li><input class="checkbox-check" type="checkbox" value="Java" id="flexCheckDefault" v-model="form.use_language">Java</li>
                    <li><input class="checkbox-check" type="checkbox" value="Python" id="flexCheckDefault" v-model="form.use_language">Python</li>
                    <li><input class="checkbox-check" type="checkbox" value="C" id="flexCheckDefault" v-model="form.use_language">C</li>
                    <li><input class="checkbox-check" type="checkbox" value="C++" id="flexCheckDefault" v-model="form.use_language">C++</li>
                    <li><input class="checkbox-check" type="checkbox" value="JavaScript" id="flexCheckDefault" v-model="form.use_language">JavaScript</li>
                    <li><input class="checkbox-check" type="checkbox" value="Ruby" id="flexCheckDefault" v-model="form.use_language">Ruby</li>
                    <li><input class="checkbox-check" type="checkbox" value="Go" id="flexCheckDefault" v-model="form.use_language">Go</li>
                </ul>
            </details>
          </div>
          <div class='checkbox' align="left">
            <details>
              <summary for="problem_site">선호 알고리즘사이트 <i class="fas fa-chevron-down"></i> </summary>
                  <ul>
                      <li><input class="checkbox-check" type="checkbox" value="boj" id="flexCheckDefault" v-model="form.problem_site">백준</li>
                      <li><input class="checkbox-check" type="checkbox" value="jungol" id="flexCheckDefault" v-model="form.problem_site">정보올림피아드</li>
                      <li><input class="checkbox-check" type="checkbox" value="programmers" id="flexCheckDefault" v-model="form.problem_site">프로그래머스</li>
                      <li><input class="checkbox-check" type="checkbox" value="swea" id="flexCheckDefault" v-model="form.problem_site">SWEA</li>
                      <li><input class="checkbox-check" type="checkbox" value="algospot" id="flexCheckDefault" v-model="form.problem_site">알고스팟</li>
                      <li><input class="checkbox-check" type="checkbox" value="codeforce" id="flexCheckDefault" v-model="form.problem_site">코드포스</li>
                      <li><input class="checkbox-check" type="checkbox" value="atcoder" id="flexCheckDefault" v-model="form.problem_site">엣코더</li>
                  </ul>
            </details>
          </div>
                  
          <button
            :disabled="!isEmailValid || !passwordLength || !passwordConfirm || !nameLength  || !form.email"
            type="submit"
            class="btn"
                  >회원 가입</button>
        </form>
      </div>
	</div>
</template>

<script>
import { validateEmail } from '@/utils/validation.js';
import { validatePassword } from '@/utils/passwordConfirm.js'
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL
export default{
	data() {
		return {
      form: {
      email : '',
			name : '',
			password: '',
      
      use_language: [],
      problem_site: [],
			//log Message
			},
      passwordConfirm: '',
      
		};
	},
	computed: {
		isEmailValid() {
			return validateEmail(this.form.email);
		},
    isPasswordValid(){
      return validatePassword(this.form.password, this.passwordConfirm);
    },
    passwordLength(){
      if(this.form.password.length > 9 && this.form.password.length<21){
        return true;
      }
      return false
    },
    nameLength(){
      if(this.form.name.length >= 2 && this.form.name.length<11){
        return true;
      }
      return false
    },
	},
  created: function() {
    const token = sessionStorage.getItem('jwt')
    if(token){
      this.$router.push({name:'login'})
    }
  },
  methods: {
        submitForm: function () {
          console.log(this.form)
          axios({
            method: 'post',
            url: `${SERVER_URL}/member/signup`,
            data: this.form

          })
          .then(res => {
            this.$swal(res.data.msg)
            this.$router.push({ name: 'login' })
          })
          .catch(err => {
            console.log(err)
          })
        }
  }
  
}
</script>

<style scoped>
/*--- LAYOUT ---*/

.header{
  margin-top: 15px;
  margin-right: 25px;
  font-size: 30px;
  font-weight: 550;
  text-align: right;
}
.form-wrapper {
  background: white;
  -webkit-box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
  box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
  border-radius: 3%;
  padding: 15px 15px;
}
.form-wrapper.form-wrapper-sm {
  max-width: 700px;
}
.form-wrapper-sm .page-header {
  padding: 0px 0 20px;
}
.form {
	width: 100%;
	height: 100%;
}
.form>div:not(:first-child){
  margin-top: 8px;
}
.form .validation-text {
	margin-top: -0.5rem;
	margin-bottom: 0.5rem;
	font-size: 16px;
	display: flex;
	flex-direction: row-reverse;
	justify-content: space-between;
}
.form label {
  width: 100%;
  display: block;
  margin-bottom: 0.5rem;
  font-size: 20px;
  font-weight: 600;
}
.form input,
.form textarea {
  font-family: inherit;
  font-size: 20px;
  width: 100%;
  border: 1px solid #dae1e7;
  box-shadow: 0 2px 4px 0 rgba(0,0,0,.1);
  padding: 0.5rem 0.75rem;
  margin-bottom: 1rem;
}
@media (min-width: 1440px){
.form input,
.form textarea {
  font-family: inherit;
  font-size: 20px;
  width: 100%;
  border: 1px solid #dae1e7;
  box-shadow: 0 2px 4px 0 rgba(0,0,0,.1);
  padding: 0.5rem 0.75rem;
  margin-bottom: 1rem;
}
.form .validation-text {
  width: 100%;
	margin-top: -0.5rem;
	margin-bottom: 0.5rem;
  margin-right: -4.5rem;
	font-size: 16px;
	display: flex;
	flex-direction: row-reverse;
	justify-content: space-between;
}
}
.form div:nth-last-child(2) {
  margin-bottom: 0.5rem;
}
.btn {
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  border-radius: .25rem;
  font-size: 20px;
}
.btn-duplicate{
    opacity: 0.8;
}
.btn.disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
.form li input{
    width:100%
}
ul{
    list-style: none;
}
summary {
  display: block; /* works in firefox */
  list-style: none; /* works in firefox */
  font-size: 20px;
  font-weight: 550;
}
li {
  margin-right: 10px;
  font-size: 15px;
  font-weight: 550;
}
.form li input{
  margin-left: 10px;
  width:5%;
}
button{
  float: right;
}
.main{
  margin-top: 5%;
  display:flex;
  justify-content:center;
  align-items:flex-start;
  height: 95%;
}
.main .content{
  display:flex;
  justify-content:center;
}
.button-area{
  width: 100%;;
  display: flex;
  justify-content: space-around;
  align-items: center;
}
</style>