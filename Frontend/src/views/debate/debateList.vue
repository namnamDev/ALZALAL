<template>
  <div class="container feed">
    <div class="row">
      <div class="col-lg-3 col-md-2 col-sm-3 col-0"></div>
      <div class="debate col-lg-6 col-md-10 col-sm-9 col-12 ">
        <div class="debate-request" align="right">
          <button class="btn btn-request">토론방 요청하기</button>
        </div>
        <div class="col-md-12">
          <div class="table-wrap">
          <table class="table table-striped">
          <thead>
            <tr>
              <th>토론방 번호</th>
              <th>개최 기관</th>
              <th>대회 이름</th>
              <th>문제번호</th>
              <th>일시</th>
              <th>상태</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <th scope="row">1001</th>
              <td>프로그래머스</td>
              <td>2020상반기대회</td>
              <td>1번 문제</td>
              <td>2020/03/01</td>
              <td><a href="#" class="btn btn-success">Progress</a></td>
            </tr>
            <tr>
              <th scope="row">1001</th>
              <td>코드포스</td>
              <td>무슨무슨대회</td>
              <td>몇번문제</td>
              <td>2021/04/25</td>
              <td><a href="#" class="btn btn-warning">Open</a></td>
            </tr>
            <tr>
              <th scope="row">1001</th>
              <td>엣코더</td>
              <td>무슨무슨대회</td>
              <td>몇번문제</td>
              <td>2023/01/01</td>
              <td><a href="#" class="btn btn-danger">On hold</a></td>
            </tr>
          </tbody>
          </table>
          </div>
          </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
.feed{
  position: absolute;
  margin-top: 100px;
  left: 50%;
  transform: translateX(-50%);
  margin-left:0;
}
.debate {
  height: 300px;
  font-size: 1vw;
  line-height: 2;
}
.btn{
  width:100%
}
.btn-request {
  float: left;
  padding: 0.5rem 1.5rem;
  font-weight: 700;
  border-radius: .1rem;
}
.table-wrap{
  /* margin-top: 10%; */
}
@media (max-width:577px) {
  .feed{
    left: 0;
    transform: translateX(0);
  }
}
</style>