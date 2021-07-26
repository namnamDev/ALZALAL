<template>
  <div class="contents">
		<div class="form-wrapper form-wrapper-sm">
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
					<label for="name" align="left">닉네임 <button class='btn btn-duplicate'>중복검사</button></label>
					<input id="name" type="text" v-model="form.name" />
                    
				</div>
                <div class='checkbox' align="left">
                    <details>
					<summary for="use_language">선호 언어 <i class="fas fa-chevron-down"></i></summary>
					<ul>
                    <li>Java<input class="checkbox-check" type="checkbox" value="Java" id="flexCheckDefault" v-model="form.use_language"></li>
                    <li>Python<input class="checkbox-check" type="checkbox" value="Python" id="flexCheckDefault" v-model="form.use_language"></li>
                    <li>C<input class="checkbox-check" type="checkbox" value="C" id="flexCheckDefault" v-model="form.use_language"></li>
                    <li>C++<input class="checkbox-check" type="checkbox" value="C++" id="flexCheckDefault" v-model="form.use_language"></li>
                    <li>JavaScript<input class="checkbox-check" type="checkbox" value="JavaScript" id="flexCheckDefault" v-model="form.use_language"></li>
                    <li>Ruby<input class="checkbox-check" type="checkbox" value="Ruby" id="flexCheckDefault" v-model="form.use_language"></li>
                    <li>Go<input class="checkbox-check" type="checkbox" value="Go" id="flexCheckDefault" v-model="form.use_language"></li>
                    </ul>
                    </details>
				</div>
                <div class='checkbox' align="left">
                    <details>
					<summary for="problem_site">선호 알고리즘사이트 <i class="fas fa-chevron-down"></i> </summary>
                        <ul>
                            <li>백준<input class="checkbox-check" type="checkbox" value="boj" id="flexCheckDefault" v-model="form.problem_site"></li>
                            <li>정보올림피아드<input class="checkbox-check" type="checkbox" value="jungol" id="flexCheckDefault" v-model="form.problem_site"></li>
                            <li>프로그래머스<input class="checkbox-check" type="checkbox" value="programmers" id="flexCheckDefault" v-model="form.problem_site"></li>
                            <li>SWEA<input class="checkbox-check" type="checkbox" value="swea" id="flexCheckDefault" v-model="form.problem_site"></li>
                            <li>알고스팟<input class="checkbox-check" type="checkbox" value="algospot" id="flexCheckDefault" v-model="form.problem_site"></li>
                            <li>코드포스<input class="checkbox-check" type="checkbox" value="codeforce" id="flexCheckDefault" v-model="form.problem_site"></li>
                            <li>엣코더<input class="checkbox-check" type="checkbox" value="atcoder" id="flexCheckDefault" v-model="form.problem_site"></li>
                        </ul>
                    </details>
				</div>
                
				<button
          
					type="submit"
					class="btn"
                 >회원 가입</button>
			</form>
			<!-- <p class="log">{{ logMessage }}</p> -->
		</div>
	</div>
  <!-- :disabled="!isEmailValid || member_password || !passwordConfirm || !member_name  || !member_email" -->
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
    }
	},
  created: function() {
   // console.log(SERVER_URL)
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
            console.log(res);
            this.$router.push({ name: 'login' })
        
          })
          .catch(err => {
            alert(err)
            console.log(err)
          })
        }
  }
  
}
</script>

<style scoped>
/*--- LAYOUT ---*/
.contents {
  position: absolute;
  top: 90px;
  margin: 0 auto;
  padding: 0 5px;
  width: 100%;  
}
.form-wrapper {
  background: white;
  -webkit-box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
  box-shadow: 0 20px 20px rgba(0, 0, 0, 0.08);
  border-radius: 1px;
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
  width: 100%;
  border: 1px solid #dae1e7;
  box-shadow: 0 2px 4px 0 rgba(0,0,0,.1);
  padding: 0.5rem 0.75rem;
  margin-bottom: 1rem;
}
.form input.valid {

}
.form input.invalid {

}
.form div:nth-last-child(2) {
  margin-bottom: 0.5rem;
}
.btn {
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  border-radius: .1rem;
}
.btn-duplicate{
    opacity: 0.8;
}
.btn.disabled {
  opacity: 0.5;
  cursor: not-allowed;
}
.form li input{
    width:5%
}
ul{
    list-style: none;
}
summary {
  display: block; /* works in firefox */
  list-style: none; /* works in firefox */
}
li {
  display: inline;
  
  margin-right: 10px;
}

</style>