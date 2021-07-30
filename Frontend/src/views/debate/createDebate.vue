<template>
  <div class="container feed">
    <div class="row">
      <div class="col-lg-4 col-md-2 col-sm-3 col-1"></div>
      <div class="debate col-lg-6 col-md-10 col-sm-9 col-10 ">
          <div class="requestText">
              토론방 요청
          </div>
          <form action="">
            <div class="selectOption">
                <label>개최 기관 </label>
                  <select name="" id="" v-model="form.agency">
                      <option value="" selected disabled hidden>선택해주세요</option>
                      <option value="codeforce">코드포스</option>
                      <option value="atcoder">엣코더</option>
                      <option value="programmers">프로그래머스</option>
                  </select>
                
                <div>
                  <label align="left" for="agency">대회 이름</label>
                  <input type="text" v-model="form.conferenceName">
                </div>
                <div>
                  <label align="left" for="testNum">문제 번호</label>
                  <input type="number" v-model="form.questionNumber">
                </div>
                <div>
                  <label align="left" for="date">토론시작일시</label>
                  <input type="date" v-model="form.debateDate">
                </div>
            </div>
            <div align="right" class="submit">
                <button class="btn btn-submit">요청</button>
                <button class="btn btn-cancel">취소</button>
            </div>
          </form>
      </div>
    </div>
  </div>

</template>

<script>
export default {
    data(){
        return{
            form:{
                agency: '',
                conferenceName: '',
                questionNumber: '',
                debateDate: '',
            }
        }
    }

}
</script>

<style scoped>
.feed{
  margin-top: 13vw;
}
.debate {
  margin-top: 5vw;
  height: 300px;
 
}
.requestText {
    display: inline-block;
    font-size: 2.3vw;
    font-weight: 540;
    line-height: 10px;
    margin-bottom: 50px;
}
p{
    font-size: 1.3vw;
    font-weight: 540;
    margin-bottom: 15px;
}
select{
    font-family: inherit;
  font-size: 100%;
  width: 50%;
  border: 1px solid #dae1e7;
  box-shadow: 0 2px 4px 0 rgba(0,0,0,.1);
  padding: 0.5rem 0.75rem;
  margin-bottom: 1rem;
}
label{
  width: 100%;
  display: block;
  margin-bottom: 0.5rem;
  font-size: 90%;
}
input{
  font-family: inherit;
  font-size: 100%;
  width: 70%;
  border: 1px solid #dae1e7;
  box-shadow: 0 2px 4px 0 rgba(0,0,0,.1);
  padding: 0.5rem 0.75rem;
  margin-bottom: 1rem;
  
}
.submit{
  margin-right: 27%;
}
</style>