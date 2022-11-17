// 초기화
function initHeader() {
  const iflogin = document.querySelector("#iflogin");
  const ifnotlogin = document.querySelector("#ifnotlogin");
  const ifadmin = document.querySelector("#ifadmin");

  const user = JSON.parse(sessionStorage.getItem("user"));
  console.log(user);
  if (user) {
    // loginUser.querySelector("span").innerHTML = `${user.name}님 반갑습니다.`;
    iflogin.style.display = "flex";
    ifnotlogin.style.display = "none";
    if (user.userType == "1") ifadmin.style.display = "block";
    else ifadmin.style.display = "none";
  } else {
    iflogin.style.display = "none";
    ifnotlogin.style.display = "flex";
    ifadmin.style.display = "none";
  }
}

// 로그아웃
function logout() {
  fetch(`${root}/api/user/logout`).then(response => {
    if (response.status == 200) {
      const iflogin = document.querySelector("#iflogin");
      const ifnotlogin = document.querySelector("#ifnotlogin");
      const ifadmin = document.querySelector("#ifadmin");

      iflogin.style.display = "none";
      ifnotlogin.style.display = "flex";
      ifadmin.style.display = "none";

      fetch(`${root}/api/user/logout`).then(response => {
        if (response.status == 200) {
          sessionStorage.removeItem("user");
          location.href = `${root}/`;
        }
      });
    }
  });
}

function noticeSerach() {
  let param = document.querySelector("#noticeSearchParam").value;
  if (param == "") {
    alert("검색어를 입력해주세요.");
  } else {
    sessionStorage.setItem("searchParam", param);
    location.href = `${root}/assets/noticeSearch.html`;
  }
}
