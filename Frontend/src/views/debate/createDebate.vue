<template>
  <div class="container feed">
    <div class="row">
      <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
      <div class="box col-lg-6 col-md-10 col-sm-9 col-10 ">
          <div>
              토론방 요청
          </div>
          <div class="selectOption">
              <p>개최 기관 
                <select name="" id="" v-model="form.agency">
                    <option value="codeforce">코드포스</option>
                    <option value="atcoder">엣코더</option>
                    <option value="programmers">프로그래머스</option>
                </select>
              </p>
              <p>대회 이름 <input type="text" v-model="form.conferenceName"></p>
              <p>문제 번호 <input type="number" v-model="form.questionNumber"></p>
              <p>토론시작일시 <input type="date" v-model="form.debateDate"></p>
          </div>
          <div class="submit">
              <button class="btn btn-submit">요청</button>
              <button class="btn btn-cancel">취소</button>
          </div>
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

<style>
.feed{
  margin-top: 13vw;
   margin-left: 50px;
}
.box {
  border: 1px solid black;
  height: 300px;
}
</style>