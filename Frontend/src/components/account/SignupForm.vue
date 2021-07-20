<template>
  <div class="contents">
		<div class="form-wrapper form-wrapper-sm">
			<form @submit.prevent="submitForm" class="form">
				<div>
					<label for="member_email" align="left">E-mail </label>
					<input id="member_email" type="text" v-model="member_email" />
                    <p class="validation-text">
						<span class="warning" v-if="!isEmailValid && !member_email">
							Please enter an email address
						</span>
					</p>
				</div>
				<div>
					<label for="member_password" align="left">비밀번호 </label>
					<input id="member_password" type="password" v-model="member_password" />
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
					<label for="member_name" align="left">닉네임 <button class='btn btn-duplicate'>중복검사</button></label>
					<input id="member_name" type="text" v-model="member_name" />
                    
				</div>
                <div class='checkbox' align="left">
                    <details>
					<summary for="algorithm_like">선호 언어 <i class="fas fa-chevron-down"></i></summary>
					<ul>
                    <li>Java<input class="checkbox-check" type="checkbox" value="" id="flexCheckDefault"></li>
                    <li>Python<input class="checkbox-check" type="checkbox" value="" id="flexCheckDefault"></li>
                    <li>C<input class="checkbox-check" type="checkbox" value="" id="flexCheckDefault"></li>
                    </ul>
                    </details>
				</div>
                <div class='checkbox' align="left">
                    <details>
					<summary for="problem_site_like">선호 알고리즘사이트 <i class="fas fa-chevron-down"></i> </summary>
                        <ul>
                            <li>백준<input class="checkbox-check" type="checkbox" value="" id="flexCheckDefault"></li>
                            <li>정보올림피아드<input class="checkbox-check" type="checkbox" value="" id="flexCheckDefault"></li>
                            <li>프로그래머스<input class="checkbox-check" type="checkbox" value="" id="flexCheckDefault"></li>
                        </ul>
                    </details>
				</div>
                
				<button
                 :disabled="!isEmailValid || !member_password || !passwordConfirm || !member_name  || !member_email"
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
export default{
	data() {
		return {
      member_email : '',
			member_name : '',
			member_password: '',
      passwordConfirm: '',
			//log Message
			
		};
	},
	computed: {
		isEmailValid() {
			return validateEmail(this.member_email);
		},
    isPasswordValid(){
      return validatePassword(this.member_password, this.passwordConfirm);
    }
	},
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
/* didn't work in any browser */

</style>