<template>
    <div class="container feed">
        <div class="row">
            <div class="col-lg-3 col-md-2 col-sm-3 col-1"></div>
            <div class="box col-lg-6 col-md-10 col-sm-9 col-10 ">
                <div>
                    <p>대회이름</p>
                    <p>문제번호</p>
                    <p>날짜</p>
                </div>
                <div>
                    <div id="form-commentInfo"> 
                        <div id="comment-count">댓글 <span id="count">0</span></div> 
                        <input id="comment-input" placeholder="댓글을 입력해 주세요."> 
                        <button id="submit">등록</button>
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