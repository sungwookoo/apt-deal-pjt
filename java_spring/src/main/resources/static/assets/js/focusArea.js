// 지도 부분
var mapContainer = document.getElementById("map"), // 지도를 표시할 div
  mapOption = {
    center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
    level: 3, // 지도의 확대 레벨
  };

// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

function toArea(adress, code) {
  // 주소-좌표 변환 객체를 생성합니다
  var geocoder = new kakao.maps.services.Geocoder();

  // 주소로 좌표를 검색합니다
  geocoder.addressSearch(adress, function (result, status) {
    // 정상적으로 검색이 완료됐으면
    if (status === kakao.maps.services.Status.OK) {
      var coords = new kakao.maps.LatLng(result[0].y, result[0].x);

      // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
      map.setCenter(coords);
    }
  });
  
  searchHomes(code);
}

let markers = [];
function searchHomes(code) {
	  initMarkers();
	  
	  fetch(`${root}/api/house/searchDeal/${code}`)
	    .then((response) => response.json())
	    .then((data) => {
	    	 apts = data;
	    	 apts.forEach((apt) => setMarker(parseFloat(apt.lat), parseFloat(apt.lng), apt.apartmentName));
		});
}

function setMarker(lat, lng, aptName) {
	  var coords = new kakao.maps.LatLng(lat, lng);

	  // 결과값으로 받은 위치를 마커로 표시합니다
	  marker = new kakao.maps.Marker({
	    map: map,
	    position: coords,
	  });

	  kakao.maps.event.addListener(marker, "click", markerClick(map, marker, aptName));

	  marker.setMap(map);

	  markers.push(marker);
}

function markerClick(map, marker, aptName) {
	  return function () {
	    map.setCenter(marker.getPosition());
	  };
	}

window.onload = function () {
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
  
  // 관심 지역 정보 얻기.
  fetch(`${root}/api/house/interest`)
    .then(response => response.json())
    .then(data => {
      let el = document.querySelector("#focus-list");
      let options = `<div class="fs-4 text-center my-3">관심지역 목록</div>`;
      Object.keys(data).forEach(function (key) {
        options += `<hr style="height: 2px" />
                    <div class="row my-3 w-100 mt-3">
                    <div class="col-9 fs-4 text-center">
                      <a href="#" onclick="toArea('${data[key]['gugunName']} ${data[key]['dongName']}', '${data[key]['dongCode']}')" class="text-decoration-none text-light"
                        >${data[key]['gugunName']} / ${data[key]['dongName']}</a
                      >
                    </div>
                    <div class="col-2">
                      <button type="button" class="btn text-light" onclick="deleteInter('${data[key]['dongCode']}', '${data[key]['userId']}')">
                        <i class="fa-solid fa-trash"></i>
                      </button>
                    </div>
                  </div>`;
      });

      el.innerHTML = options;
    })

};

function deleteInter(dongCode, userId) {
  fetch(`${root}/api/house/interest/${userId}/${dongCode}`, config = { method: "DELETE", headers: { 'Content-Type': 'application/json', } })
    .then(response => {
      if (response.status == 200) location.href = `${root}/assets/findInterest.html`;
    })
}

//시도가 바뀌면 구군정보 얻기.
document.querySelector("#big").addEventListener("change", function () {
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
document.querySelector("#middle").addEventListener("change", function () {
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

function interestRegister() {
  let small = document.querySelector("#small");
  let info = {
    userId: JSON.parse(sessionStorage.getItem("user")).userId,
    code : small[small.selectedIndex].value
  }

  let config = {
    method: "POST",
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(info),
  }

  fetch(`${root}/api/house/interest`, config)
    .then(response => {
      if (response.status == 200) location.href = `${root}/assets/findInterest.html`;
    });
}

function showMarkers(code) {
  let url = "https://apis.data.go.kr/B553077/api/open/sdsc2/storeListInDong";
  let gugunSel = document.querySelector("#middle");
  let queryParams =
    encodeURIComponent("ServiceKey") +
    "=" +
    "6CukAXbFje92O8pvkJ1DzpmFVBk7zhDGKeYPVOrs1Qxd9OF9Jr9tOTnM%2FE5nq0YDk6IKzv%2FhXaQZxA926RKOhw%3D%3D"; /*Service Key*/
  /*시도는 시도코드값, 시군구는 시군구코드값, 행정동은 행정동코드값을 사용*/
  queryParams += "&" + encodeURIComponent("key") + "=" + encodeURIComponent(code);
  /*시도는 ctprvnCd, 시군구는 signguCd, 행정동은 adongCd를 사용 */
  queryParams += "&" + encodeURIComponent("divId") + "=" + encodeURIComponent("ctprvnCd");
  /*입력된 대분류 업종에 해당하는 것만 조회*/
  queryParams += "&" + encodeURIComponent("indsLclsCd") + "=" + encodeURIComponent("Q");
  /*입력된 중분류 업종에 해당하는 것만 조회*/
  queryParams += "&" + encodeURIComponent("indsMclsCd") + "=" + encodeURIComponent("Q12");
  /*입력된 소분류 업종에 해당하는 것만 조회*/
  queryParams += "&" + encodeURIComponent("indsSclsCd") + "=" + encodeURIComponent("Q12A01");
  queryParams += "&" + encodeURIComponent("pageNo") + "=" + encodeURIComponent("1"); /*페이지번호*/
  queryParams +=
    "&" + encodeURIComponent("numOfRows") + "=" + encodeURIComponent("1000"); /*페이지당건수*/
  queryParams += "&" + encodeURIComponent("type") + "=" + encodeURIComponent("xml"); /*xml / json*/

  let bigSel = document.querySelector("#big");
  let bigName = bigSel[bigSel.selectedIndex].innerText;

  let middleSel = document.querySelector("#middle");
  let middleName = middleSel[middleSel.selectedIndex].innerText;

  let dongSel = document.querySelector("#small");
  let dongName = dongSel[dongSel.selectedIndex].innerText;

  let address = bigName + " " + middleName;

  initMarkers();

  fetch(`${url}?${queryParams}`)
    .then((response) => response.text())
    .then((data) => makeList(data, address, dongName));
}

function addBigs() {
  const url =
    "https://apis.data.go.kr/B553077/api/open/sdsc2/largeUpjongList?serviceKey=6CukAXbFje92O8pvkJ1DzpmFVBk7zhDGKeYPVOrs1Qxd9OF9Jr9tOTnM%2FE5nq0YDk6IKzv%2FhXaQZxA926RKOhw%3D%3D&type=json";
  fetch(`${url}`)
    .then((response) => response.json())
    .then((data) => addBigService(data));
}

function addBigService(data) {
  let opt = ``;

  let items = data.body.items;

  opt += `<option value="">대분류 카테고리 선택</option>`;
  items.forEach(function (item) {
    opt += `
        <option value="${item.indsLclsCd}">${item.indsLclsNm}</option>
        `;
  });

  document.querySelector("#bigServiceSel").innerHTML = opt;
}

function addMiddles(code) {
  const url = "https://apis.data.go.kr/B553077/api/open/sdsc2/middleUpjongList";
  let params =
    "serviceKey=6CukAXbFje92O8pvkJ1DzpmFVBk7zhDGKeYPVOrs1Qxd9OF9Jr9tOTnM%2FE5nq0YDk6IKzv%2FhXaQZxA926RKOhw%3D%3D" +
    "&indsLclsCd=" +
    code +
    "&type=json";

  fetch(`${url}?${params}`)
    .then((response) => response.json())
    .then((data) => addMiddleService(data));
}

function addMiddleService(data) {
  let opt = ``;

  let items = data.body.items;

  opt += `<option value="">중분류 카테고리 선택</option>`;
  items.forEach(function (item) {
    opt += `
        <option value="${item.indsMclsCd}">${item.indsMclsNm}</option>
        `;
  });

  document.querySelector("#middleServiceSel").innerHTML = opt;
}

function addSmalls(code) {
  const url = "https://apis.data.go.kr/B553077/api/open/sdsc2/smallUpjongList";
  let params =
    "serviceKey=6CukAXbFje92O8pvkJ1DzpmFVBk7zhDGKeYPVOrs1Qxd9OF9Jr9tOTnM%2FE5nq0YDk6IKzv%2FhXaQZxA926RKOhw%3D%3D" +
    "&indsLclsCd=" +
    code.charAt(0) +
    "&indsMclsCd=" +
    code +
    "&type=json";

  fetch(`${url}?${params}`)
    .then((response) => response.json())
    .then((data) => addSmallService(data));
}

function addSmallService(data) {
  let opt = ``;

  let items = data.body.items;

  opt += `<option value="">소분류 카테고리 선택</option>`;
  items.forEach(function (item) {
    opt += `
        <option value="${item.indsSclsCd}">${item.indsSclsNm}</option>
        `;
  });

  document.querySelector("#smallServiceSel").innerHTML = opt;
}

function initService(selid) {
  let options = document.querySelector(`#${selid}`);
  options.length = 0;
}

// 대분류 카테고리가 변경되면 중분류 얻기
document.querySelector("#bigServiceSel").addEventListener("change", function () {
  if (this[this.selectedIndex].value) {
    initService("middleServiceSel");
    initService("smallServiceSel");
    let code = this[this.selectedIndex].value;
    addMiddles(code);
  } else {
  }
});

// 중분류 카테고리가 변경되면 소분류 얻기
document.querySelector("#middleServiceSel").addEventListener("change", function () {
  if (this[this.selectedIndex].value) {
    initService("smallServiceSel");
    let code = this[this.selectedIndex].value;
    addSmalls(code);
  } else {
  }
});

// 소분류 카테고리가 변경되면 마크 찍기
document.querySelector("#smallServiceSel").addEventListener("change", function () {
  if (this[this.selectedIndex].value) {
    let code = this[this.selectedIndex].value;
    setMarkers(code);
  }
});


function initMarkers() {
  markers.forEach((marker) => {
    marker.setMap(null);
  });

  markers = [];

  console.log(markers);
}

function setMarkers(code) {
  let center = map.getCenter();
  let cy = center.getLat();
  let cx = center.getLng();

  const url = "https://apis.data.go.kr/B553077/api/open/sdsc2/storeListInRadius";
  let params =
    "serviceKey=6CukAXbFje92O8pvkJ1DzpmFVBk7zhDGKeYPVOrs1Qxd9OF9Jr9tOTnM%2FE5nq0YDk6IKzv%2FhXaQZxA926RKOhw%3D%3D" +
    "&cx=" +
    cx +
    "&cy=" +
    cy +
    "&radius=1300" +
    "&pageNo=1" +
    "&numOfRows=500" +
    "&indsLclsCd=" +
    code.charAt(0) +
    "&indsMclsCd=" +
    code.substring(0, 3) +
    "&indsSclsCd=" +
    code +
    "&type=json";

  fetch(`${url}?${params}`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      let items = data.body.items;
      items.forEach((item) => {
        var position = new kakao.maps.LatLng(item.lat, item.lon);
        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
          position: position,
          clickable: true, // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
        });

        markers.push(marker);

        // 마커를 지도에 표시합니다.
        marker.setMap(map);

        // 마커를 클릭했을 때 마커 위에 표시할 인포윈도우를 생성합니다
        var iwContent = `<div style="padding:5px;">${item.bizesNm}</div>`, // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
          iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

        // 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
          content: iwContent,
          removable: iwRemoveable,
        });

        // 마커에 클릭이벤트를 등록합니다
        kakao.maps.event.addListener(marker, "click", function () {
          // 마커 위에 인포윈도우를 표시합니다
          infowindow.open(map, marker);
        });
      });
    });
}
