window.onload = function () {
  fetch(`${root}/api/notice/`)
    .then(response => response.json())
    .then(data => makeList(data));
};

function makeList(notices) {
    let html = ``;
    notices.forEach(function (notice) {
      html += `
				<div class="row ms-5 pt-4 text-dark text-center fs-6" style="width: 92%">
					<div class="col-6" onclick="goView('${notice.noticeNo}');" style="cursor:pointer">${notice.subject}</div>
					<div class="col-3">${notice.userName}</div>
					<div class="col-3">${notice.createDate}</div>
				</div>
				`;
    });
    document.querySelector("#noticelist").innerHTML = html;
}

function goView(noticeNo) {
	sessionStorage.setItem('noticeNo', noticeNo);
	location.href='/assets/noticeView.html';
}