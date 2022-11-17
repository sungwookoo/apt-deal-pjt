let date = new Date();
let apts;
window.onload = function () {
  // 시도 정보 얻기.
  fetch(`${root}/api/house/sido`)
    .then(response => response.json())
    .then(data => {
      let options = `<option value="">시도선택</option>`;
      Object.keys(data).forEach(function (key) {
        options += `<option value = "${key}">${data[key]}</option>`;
      });

      document.querySelector('#big').innerHTML = options;
    });
};

// 시도가 바뀌면 구군정보 얻기.
document.querySelector('#big').addEventListener('change', function () {
  let sidoCode = this[this.selectedIndex].value;

  fetch(`${root}/api/house/gugun/${sidoCode}`)
    .then(response => response.json())
    .then(data => {
      console.log(data);
      let options = `<option value="">구군선택</option>`;

      Object.keys(data).forEach(function (key) {
        options += `<option value = "${data[key]['dongCode']}">${data[key]['gugunName']}</option>`;
      });

      document.querySelector('#middle').innerHTML = options;
    });
});

// 구군이 바뀌면 동별 아파트 정보 갱신.
document.querySelector('#middle').addEventListener('change', function () {
  let gugunCode = this[this.selectedIndex].value;

  // 동별 아파트 정보 획득
  fetch(`${root}/api/house/dongDealInfo/${gugunCode}`, {
    method: 'GET',
  })
    .then(response => response.json())
    .then(data => {
      console.log(data);
      let options = `<div
          class="row ms-5 mt-4 text-dark text-center fw-bold fs-5 border-dark border-3 border-bottom"
          style="width: 92%"
        >
          <div class="col-5 border-dark border-1">동명</div>
          <div class="col-6 border-dark border-1">평균 매매가</div>
        </div>`;
      for (const deal of data) {
        for (const [key, value] of Object.entries(deal)) {
          console.log(`${key} : ${value}`);
          options += `<div class="row ms-5 pt-4 text-dark text-center fs-6" style="width: 92%">
				<div class="col-5">${key}</div>
				<div class="col-6">${value}</div>
			</div>`;
        }
      }
      document.querySelector('#dealInfo').innerHTML = options;
    });
});
