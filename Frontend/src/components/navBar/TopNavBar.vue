<template>
  <div id="nav">
    <nav class="navbar navbar-expand-lg navbar-light">
      <div class="container-fluid">
        <a class="navbar-brand" href="/">6 To 4</a>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto">
            <li class="nav-item">
              <a class="nav-link active" aria-current="page" href="#">Home</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Link</a>
            </li>
          </ul>
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
                <li><a class="dropdown-item" href="#">정보수정</a></li>
                <li><a class="dropdown-item" href="#">프로필페이지</a></li>
              </ul>
            </li>
          </ul>
          <a href="" class="me-3">로그아웃</a>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>
export default {

}
</script>

<style scoped>
#nav {
  position:fixed;
  top: 0;
  height:60px;
  width: 100%;
  background-color: rgba(161, 212, 226, 1);  
  z-index:2;
}
</style>