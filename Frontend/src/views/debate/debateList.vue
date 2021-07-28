<template>
  <div class="container feed">
    <div class="row">
      <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
      <div class="box col-lg-6 col-md-10 col-sm-9 col-10 ">
        <div class="debatePage">
          토론게시판
        </div>
        
        <div class="boardList" id="boardList">
            <table>
              <tr>
                <th>
                  대회이름
                </th>
                <th>
                  소분류
                </th>
                <th>
                  날짜
                </th>
              </tr>
              <tr>
                <td>
                 프로그래머스 대회 
                </td>
                <td>
                 몇번 문제
                </td>
                <td>
                 2017/10/10
                </td>
               
              </tr>
              <tr>
                <td>
                 프로그래머스 대회 
                </td>
                <td>
                 몇번 문제
                </td>
                <td>
                 2017/10/10
                </td>
               
              </tr>
            </table>

    <!---->
    <!---->
        </div>
        <div>
          <button>
            토론방 만들기
          </button>
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
  margin-top: 13vw;
}
.box {
  border: 1px solid black;
  height: 300px;
}
.debatePage {
    font-size: 2rem;
    font-weight: 600;
    line-height: 2;
    
}
</style>