// 지도 부분
var mapContainer = document.getElementById('map'), // 지도를 표시할 div
  mapOption = {
    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
    level: 4, // 지도의 확대 레벨
  };

// 지도를 표시할 div와 지도 옵션으로 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);
let customOverlay;
let marker;

function toHome(lat, lng, aptName) {
  var coords = new kakao.maps.LatLng(lat, lng);

  // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
  map.setCenter(coords);

  document.querySelector('#map-aptName').innerText = aptName;
}

let markers = [];

function setMarker(lat, lng, aptName) {
  var coords = new kakao.maps.LatLng(lat, lng);

  // 결과값으로 받은 위치를 마커로 표시합니다
  marker = new kakao.maps.Marker({
    map: map,
    position: coords,
  });

  kakao.maps.event.addListener(marker, 'click', markerClick(map, marker, aptName));

  marker.setMap(map);

  markers.push(marker);
}

function move(adress) {
  var geocoder = new kakao.maps.services.Geocoder();

  geocoder.addressSearch(adress, function (result, status) {
    // 정상적으로 검색이 완료됐으면
    if (status === kakao.maps.services.Status.OK) {
      var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

      map.setCenter(coords);
    }
  });
}

function markerClick(map, marker, aptName) {
  return function () {
    findApts(aptName);
    map.setCenter(marker.getPosition());
  };
}

let date = new Date();
let apts;

window.onload = function () {
  let yearEl = document.querySelector('#year');
  let yearOpt = `<option value="">매매년도선택</option>`;
  let year = date.getFullYear();
  for (let i = year; i > year - 20; i--) {
    yearOpt += `<option value="${i}">${i}년</option>`;
  }
  yearEl.innerHTML = yearOpt;

  // 시도 정보 얻기.
  fetch(`${root}/api/house/sido`)
    .then(response => response.json())
    .then(data => {
      let options = `<option value="">시도선택</option>`;
      Object.keys(data).forEach(function (key) {
        options += `<option value = "${data[key]['dongCode']}">${data[key]['sidoName']}</option>`;
      });

      document.querySelector('#big').innerHTML = options;
    });
};

document.querySelector('#year').addEventListener('change', function () {
  let month = date.getMonth() + 1;
  let monthEl = document.querySelector('#month');
  let monthOpt = `<option value="">매매월선택</option>`;
  let yearSel = document.querySelector('#year');
  let m = yearSel[yearSel.selectedIndex].value == date.getFullYear() ? month : 13;
  for (let i = 1; i < m; i++) {
    monthOpt += `<option value="${i < 10 ? '0' + i : i}">${i}월</option>`;
  }
  monthEl.innerHTML = monthOpt;
});

// 시도가 바뀌면 구군정보 얻기.
document.querySelector('#big').addEventListener('change', function () {
  let sidoCode = this[this.selectedIndex].value;

  // 구군 정보 얻기
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

// 구군이 바뀌면 동정보 얻기.
document.querySelector('#middle').addEventListener('change', function () {
  let gugunCode = this[this.selectedIndex].value;

  // 동 정보 얻기
  fetch(`${root}/api/house/dong/${gugunCode}`)
    .then(response => response.json())
    .then(data => {
      let options = `<option value="">동선택</option>`;
      Object.keys(data).forEach(function (key) {
        options += `<option value = "${data[key]['dongCode']}">${data[key]['dongName']}</option>`;
      });

      document.querySelector('#small').innerHTML = options;
    });
});

function searchHomes() {
  let small = document.querySelector('#small');
  let smallCode = small[small.selectedIndex].value;
  let yearSel = document.querySelector('#year');
  let year = yearSel[yearSel.selectedIndex].value;
  let monthSel = document.querySelector('#month');
  let month = monthSel[monthSel.selectedIndex].value;

  initMarkers();

  let big = document.querySelector('#big');
  let bigName = big[big.selectedIndex].innerText;
  let middle = document.querySelector('#middle');
  let middleName = middle[middle.selectedIndex].innerText;
  let smallName = small[small.selectedIndex].innerText;

  let address = bigName + ' ' + middleName;

  fetch(`${root}/api/house/searchDeal/${smallCode}/${year}/${month}`)
    .then(response => response.json())
    .then(data => {
      console.log(data);
      makeList(data, address, smallName);
    });
}

function makeList(data, address, dong) {
  document.querySelector('#dong-list').style.display = 'block';
  document.querySelector('#apt-list').style.display = 'none';
  document.querySelector('#list-dong-name').innerText = address + ' ' + dong;

  move(address + ' ' + dong);
  apts = data;
  initTable();
  apts.forEach(apt => {
    let infoDiv = document.createElement('div');

    infoDiv.setAttribute(
      'onclick',
      `toHome('${parseFloat(apt.lat)}', '${parseFloat(apt.lng)}', '${apt.apartmentName}')`
    );
    infoDiv.setAttribute('class', 'text-center text-light');
    document.querySelector('#dong-list').appendChild(infoDiv);
    setMarker(parseFloat(apt.lat), parseFloat(apt.lng), apt.apartmentName);
    infoDiv.innerHTML = `              
      <div class="text-center text-light list-dong-info">
        <div>아파트 : ${apt.apartmentName}</div>
        <div>거래금액 : ${apt.dealAmount}</div>
        <div>${apt.dealYear} / ${apt.dealMonth} / ${apt.dealDay}</div>
        <hr />
      </div>`;
  });
}

function findApts(name) {
  document.querySelector('#dong-list').style.display = 'none';
  document.querySelector('#apt-list').style.display = 'block';
  document.querySelector('#list-apt-name').innerText = name;
  document.querySelector('#map-aptName').innerText = name;

  initList();
  apts.forEach(apt => {
    if (name == apt.apartmentName) {
      let infoDiv = document.createElement('div');

      infoDiv.innerHTML = `
      <div class="text-center text-light list-apt-info">
      <form method="post" action="">
      <input type="hidden" name="no" value="${apt.no}"/>
    	<div>금액 : <input type="text" id = "deal${apt.no}" name="dealAmount" value="${apt.dealAmount}"/></div>
    	<div>면적 : <input type="text" id = "area${apt.no}" name="area" value="${apt.area}"/></div>
        <div>년 : <input type="text" id = "year${apt.no}" name="year" value="${apt.dealYear}"/></div>
        <div>월 : <input type="text" id = "month${apt.no}" name="month" value="${apt.dealMonth}"/></div>
    	<div>일 : <input type="text" id = "day${apt.no}" name="day" value="${apt.dealDay}"/></div>
    	<div><button type="button" onclick="updateApt('${apt.no}')">수정</button> <button type="button" onclick="deleteApt('${apt.no}')">삭제</button></div>
        <hr />
       </form>
      </div>`;

      infoDiv.setAttribute('class', 'text-center text-light');
      document.querySelector('#apt-list').appendChild(infoDiv);
    }
  });
}

function updateApt(no) {
  let info = {
    dealAmount: document.querySelector(`#deal${no}`).value,
    area: document.querySelector(`#area${no}`).value,
    dealYear: document.querySelector(`#year${no}`).value,
    dealMonth: document.querySelector(`#month${no}`).value,
    dealDay: document.querySelector(`#day${no}`).value,
    no : no
  }

  let config = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(info),
  }

  fetch(`${root}/api/house/update`, config)
    .then(response => {
      if (response.status == 200) location.href =`${root}/assets/findHome.html`
    })
}

function deleteApt(no) {
  let config = {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json"
    }
  }
  fetch(`${root}/api/house/${no}`, config)
    .then(response => {
      if (response.status == 200) location.href =`${root}/assets/findHome.html`
    })
}

function initTable() {
  let infos = document.querySelectorAll('.list-dong-info');
  infos.forEach(info => {
    info.style.display = 'none';
  });
}

function initList() {
  let infos = document.querySelectorAll('.list-apt-info');
  infos.forEach(info => {
    info.style.display = 'none';
  });
}

function initMarkers() {
  markers.forEach(marker => {
    marker.setMap(null);
  });

  markers = [];

  console.log(markers);
}
