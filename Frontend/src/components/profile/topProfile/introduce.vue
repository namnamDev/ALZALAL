<template>
  <div class="contents">
		<div class="form-wrapper form-wrapper-sm">
			<form @submit.prevent="submitForm" class="form">
				<div>
					<label align="left" for="introduce">한 줄 소개를 입력해주세요</label>
					<input id="introduce" type="text" v-model="introduce" />
          <p class="validation-text">
              <span class='warning' v-if="!introLength">
                한줄소개는 40자 이하로 입력해주세요
              </span>
            </p>     
				</div>
			    <button :disabled="!introLength" type="submit" class="btn" align="right">
					                                    확인
				  </button>
			</form>
		</div> 
	</div>
</template>

<script>
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  data() {
		return {
      introduce: '',
		};
	},
  created: function() {
    const token = sessionStorage.getItem('jwt')
    if(!token){
      this.$router.push({name:'login'})
    }
  },
  computed: {
    getToken(){
      const token = sessionStorage.getItem('jwt')
      const config = {
        Authorization: `Bearer ${token}`
      }
      return config
    },
    introLength(){
      if(this.introduce.length<41){
        return true;
      }
      return false
    },
  },
  methods: {
    // getToken(){
    //   const token = localStorage.getItem('jwt')
    //   const config = {
    //     Authroization: `JWT ${token}`
    //   }
    //   return config
    // }
    
    submitForm: function() {
      // console.log(this.getToken)
      axios({
            method: 'post',
            url: `${SERVER_URL}/profile/introduce`,
            data: {
              "introduce" : this.introduce
              },
            headers: this.getToken,
          })
          .then(res => {
            console.log(res);
            this.$router.push({ name: 'profilePage' })
        
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
  top:180px;
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
  background-color: rgb(62, 171, 111);
  color: white;
  width: 120px;
}
.btn.disabled {
  background-color: white;
  color: black;
  border: 1px solid rgb(62, 171, 111);
  width: 120px;
}
.btn-user{
    opacity: 0.8;
}

</style>