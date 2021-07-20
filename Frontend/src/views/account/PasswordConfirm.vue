<template>
  <div>
      <div class="contents">
		<div class="form-wrapper form-wrapper-sm">
			<form @submit.prevent="submitForm" class="form">
				<div>
					<label align="left" for="password">비밀번호 확인</label>
					<input id="password" type="password" v-model="password" />
                    <p class="validation-text">
						<span class="warning" v-if="!password">
							비밀번호를 입력해주세요
						</span>
					</p>
				</div>
				<button
                :disabled="!password"
					type="submit"
					class="btn"
                >
					확인
				</button>
			</form>
		
		</div>
       
	</div>
  </div>
</template>

<script>
export default {
      data() {
		return {
           password: '',
		};
	},
}
</script>

<style scoped>
/*--- LAYOUT ---*/
.contents {
  max-width: 1020px;
  margin: 0 auto;
  padding: 0 5px;
  width: 100%;  
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
  width: 100%;
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

</style>