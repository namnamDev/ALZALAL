<template>
  <div>
    <div class="logo">
      LOGO
    </div>
    <div class="user">
      <ul class="navbar-nav me-4">
        <li class="nav-item dropdown">
          <a
            class="nav-link"
            href="#"
            id="navbarDropdown"
            role="button"
            data-bs-toggle="dropdown"
            aria-expanded="false"
          >
            User1
          </a>
          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
            <li><a class="dropdown-item" href="/passwordConfirm">정보수정</a></li>
            <li><a class="dropdown-item" href="/profilePage">프로필페이지</a></li>
            <li><a class="dropdown-item" href="/logout">로그아웃</a></li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>

.box{
  background-color: bisque;
}
.top-nav-bar{
  position: fixed;
  top: 0;
}


.logo{
  border: 10px solid blanchedalmond;
  font-size:4vw;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
}
.user{
  border: 10px solid blanchedalmond;
  font-size:2vw;
  position: absolute;
  right: 10%;
}

</style>