// 시작점과 도착점 위도&경도 간 거리 계산(피타고라스의 정리)
function calculateDistance(lat1, lon1, lat2, lon2) {
   var latDifference = lat2 - lat1;
   var lonDifference = lon2 - lon1;

   // 1도에 대한 거리 (단위: km)
   var kmPerDegree = 111; // 위도, 경도 1도의 차이는 약 111km

   // 피타고라스의 정리 사용
   var distance = Math.sqrt(Math.pow(latDifference * kmPerDegree, 2) + Math.pow(lonDifference * kmPerDegree, 2));

   return distance;
}

// 지도 초기화 및 출발지 마커 표시
function initializeMap() {
   var mapContainer = document.getElementById('map');
   var mapOption = {
      center: new kakao.maps.LatLng(altitude, longitude),
      level: 6
   };

   map = new kakao.maps.Map(mapContainer, mapOption);

   // 출발지 마커 생성
   var startMarkerPosition = new kakao.maps.LatLng(altitude, longitude);
   var startMarker = new kakao.maps.Marker({
      position: startMarkerPosition,
      title: "출발지"
   });
   startMarker.setMap(map);
   markers.push(startMarker);
}
var startMarker, destinationMarker;

// 최종 선택한 두 장소 마커 표시
function destination(currentTable) {

   //var currentTable = button.closest('table');

   var destinationInputs = currentTable.querySelectorAll('.destination-input');
   //    const destinationInputs = document.getElementsByClassName("destination-input");            
   const lastInput = destinationInputs[destinationInputs.length - 1];

   var lat = lastInput.getAttribute('data-lat');
   var lon = lastInput.getAttribute('data-lon');
   console.log(lat)
   console.log(lon)
   var previousTable22 = currentTable.previousElementSibling;
   if (!previousTable22) {
      const class01Table = document.getElementById('class01');
      var destinationInputs22 = class01Table.querySelectorAll('.destination-input');
   } else {
      var destinationInputs22 = previousTable22.querySelectorAll('.destination-input');
   }
   var lastInput22 = destinationInputs22[destinationInputs22.length - 1];
   var latValue22 = lastInput22.getAttribute('data-lat');
   var lonValue22 = lastInput22.getAttribute('data-lon');

   console.log(latValue22)
   console.log(lonValue22)

   const markerPosition = new kakao.maps.LatLng(lat, lon);
   const marker = new kakao.maps.Marker({
      position: markerPosition,
      title: "도착지"
   });

   marker.setMap(map);
   markers.push(marker);
   const markerPosition22 = new kakao.maps.LatLng(latValue22, lonValue22);
   const marker22 = new kakao.maps.Marker({
      position: markerPosition22,
      title: "출발지"
   });
   marker22.setMap(map);
   markers.push(marker22);

   var bounds = new kakao.maps.LatLngBounds();
   bounds.extend(markerPosition);   // 도착지 마커의 위치 추가
   bounds.extend(markerPosition22); // 출발지 마커의 위치 추가

   // 지도에 바운더리 반영
   map.setBounds(bounds);

}


// 여행 플래닝 본화면에 이전 세션 사용자 입력 정보(1일차 출발지&일정) 띄우기
document.addEventListener("DOMContentLoaded", function() {

   var table = document.getElementById('class01');
   if (table) {
      table.querySelector('#destination').innerText = departureValue;
      table.querySelector('#destination').value = departureValue;
      table.querySelector('#id3_plan').innerText = planValue;
      table.querySelector('#destination').setAttribute('data-lat', altitude);
      table.querySelector('#destination').setAttribute('data-lon', longitude);

      console.log("innerText: ", table.querySelector('#destination').innerText);
   } else {
      console.log("테이블을 찾을 수 없음");
   }
   initializeMap(); // 지도 초기화
});


// O박 O일 일정 선택에 따른 N일차 표기
generateDays(planValue);

function generateDays(planValue) {

   // "박 일" 형식에서 박과 일 수를 추출
   const daysMatch = planValue.match(/(\d+)박\s+(\d+)일/);
   if (!daysMatch) {
      alert("올바른 형식이 아닙니다.");
      return;
   }

   const nights = parseInt(daysMatch[1], 10);
   const days = parseInt(daysMatch[2], 10);

   const itineraryDiv = document.getElementById('itinerary');
   itineraryDiv.innerHTML = ''; // 이전 내용 초기화

   for (let i = 1; i <= days; i++) {
      if (i === 1) {
         const dayRow = document.createElement('table');
         //dayRow.classList.add('day-box'); // 스타일을 위한 클래스 추가

         dayRow.innerHTML = `
       <tr>
          <th>${i}일차</th>
       </tr>
       <tr>                
          <th class="destination-header">도착지
          <input type="text" class="destination-input" id="destination" data-lat="0" data-lon="0" data-spot="" placeholder="도착지를 입력하세요"></th>
          <th><button onclick="getCoordinates(this);" id="finishButton">완료</button></th>
          <th><button class="add-destination-btn" onclick="requestPay(this)" id="finishButton">+</button></th>
       </tr>
       <tr>
       <th>소요시간</th>
       <th id="result"></th>
       </tr>
       <tr>
       <th>
      <button id="nearbyButton" data-action="one"  onclick="go(this)">관광지</button>   
      <button id="nearbyButton" data-action="three"  onclick="go(this)">식당/카페</button>  
       </th>
       </tr>
       
       <tr>          
           <td colspan="2">
               <!-- 이곳에 장소 목록이 표시됩니다. -->
               <ul id="placesList" style="display: inline-block; margin-right: 20px;">

          </ul>
           </td>
           </tr>
          `;

         document.getElementById('itinerary').appendChild(dayRow);


      } else {
         const dayRow = document.createElement('table');
         //dayRow.classList.add('day-box'); // 스타일을 위한 클래스 추가
         dayRow.setAttribute('id', 'day')
         dayRow.innerHTML = `
             <tr>
                <th>${i}일차</th>
             </tr>
             <tr>                
                <th class="destination-header">출발지 
                <input type="text" class="destination-input" id="destination" data-lat="0" data-lon="0" data-spot="" placeholder="출발지를 입력하세요"></th>
                <th><button onclick="Save(this);getCoordinates(this);initializeMap();" id="finishButton">저장</button></th>
                <th><button class="add-destination-btn" onclick="requestPay(this);" id="finishButton">+</button></th>
             </tr>
                  `;

         document.getElementById('itinerary').appendChild(dayRow);
      }
   }
}


// 2일차 이후 출발지 [저장] 버튼 함수
function Save(button) {
   const currentTable2 = button.closest('table');
   const jjValue = currentTable2.querySelector('#destination').value;
   if (jjValue == null) {
      alert("출발지를 입력해주세요.")
      return
   };

   currentTable2.querySelector('#destination').innerText = jjValue; // '출발지'에 값 추가
   currentTable2.querySelector('#destination').value = jjValue;

}

// 목적지 추가하기(+ 버튼)
function requestPay(button) {
   const currentTable = button.closest('table');
   const destinationInputs = currentTable.querySelectorAll('.destination-input');

   // 첫 번째 목적지 입력 필드가 비어있는지 확인
   const firstDestinationInput = currentTable.querySelector('input#destination');
   if (!firstDestinationInput.value) {
      alert("도착지를 선택해주세요");
      return;
   }

   const newRow = document.createElement('table');
   newRow.innerHTML = `
        <tr>
            <th class="destination-header">도착지
            <input type="text" class="destination-input" id="destination" data-lat="0" data-lon="0" data-spot="" placeholder="도착지를 입력하세요"></th>
            <th><button onclick="getCoordinates(this);" id="finishButton">완료</button></th>
            <th><button onclick="requestPay(this);" id="finishButton">+</button></th>
            
        </tr>
        <tr>
            <th>소요시간</th>
             <th id="result"></th>
          <th><button onclick="removeLastField(this)" id="finishButton">-</button></th>
       </tr>
                <tr>
                <th>
                    <button id="nearbyButton" data-action="two"  onclick="go2(this)">관광지</button>   
               <button id="nearbyButton" data-action="four"  onclick="go2(this)">식당/카페</button>  
               
                    </th>
                </tr>
            <tr>    
                    
                    <td colspan="2">
                        <!-- 이곳에 장소 목록이 표시됩니다. -->
                        <ul id="placesList" style="display: inline-block; margin-right: 20px;">
                   </ul>
                    </td>             
                    </tr>
                   `;

   // 새 행을 현재 테이블 뒤에 추가
   currentTable.insertAdjacentElement('afterend', newRow);

}
var infoWindows = [];

// 목적지 지우기(- 버튼)
function removeLastField(button) {
   var row = button.closest('table');
   row.remove();
}

// 마커 초기화(5개 장소 마커 보여준 후, 개별 장소 마커 찍을 때 기존 5개 마커 초기화)
function clearMarkers() {
   markers.forEach(function(marker, index) {
      marker.setMap(null);  // 마커 지우기
      if (infoWindows[index]) {
         infoWindows[index].close(); // 해당 마커에 연결된 InfoWindow 닫기
      }
   });
   markers = [];
   infoWindows = [];
}

// 소요시간 계산
let destination1Output = '';
let destinationOutput = '';

// Google Geocoder를 사용하여 출발지의 위도, 경도 가져오기
function getCoordinates(button) {
   const currentTable = button.closest('table');

   const currentDestination = currentTable.querySelector('#destination').value;
   if (!currentDestination) {
      alert("장소를 입력해주세요.")
      return
   }
   destination(currentTable);
   const itineraryTable = document.getElementById('itinerary');
   const firstTableInItinerary = itineraryTable.querySelector('table');
   const previousTable = currentTable.previousElementSibling;

   if (currentTable.id === 'day') {
      const destination1location = currentTable.querySelector('#destination').value;
      alert("출발지 정보(" + destination1location + ")가 저장되었습니다.")

      const geocoder = new google.maps.Geocoder();

      // 출발지 좌표를 비동기적으로 처리
      geocoder.geocode({ address: destination1location }, (results, status) => {
         if (status === 'OK') {
            const lat = results[0].geometry.location.lat();
            const lng = results[0].geometry.location.lng();

            destination1Output = `${lng},${lat}`;  // 출발지 좌표를 전역 변수에 할당

            const destinationInputs = currentTable.querySelectorAll('.destination-input');
            const lastInput = destinationInputs[destinationInputs.length - 1];
            lastInput.setAttribute('data-lat', lat);
            lastInput.setAttribute('data-lon', lng);
            // 출발지 좌표가 설정된 후에 도착지 좌표 계산
            clearMarkers();
            var startMarkerPosition = new kakao.maps.LatLng(lat, lng);
            var startMarker = new kakao.maps.Marker({
               position: startMarkerPosition,
               title: "출발지"
            });
            startMarker.setMap(map);
            markers.push(startMarker)
            var bounds = new kakao.maps.LatLngBounds();

            // 바운더리에 출발지 마커의 위치 추가
            bounds.extend(startMarkerPosition);

            // 지도에 마커들이 다 보이도록 바운더리 설정
            map.setBounds(bounds);


            //return getClosestTouristSpots2(currentTable,lat,lng);
            // 도착지 좌표를 설정하고 API 호출
         } else {
            console.error('출발지 주소 변환 실패');
         }
      });

   } else {

      if (currentTable !== firstTableInItinerary) {
         console.log("This is not the first table inside #itinerary.");
         // #itinerary의 첫 번째 테이블이 아닌 경우, previousTable에서 #destination 값을 가져옴
         const previousTable = currentTable.previousElementSibling;
         const destination1location = previousTable.querySelector('#destination').value;
         const into = previousTable.querySelector('#destination')
         const lat = parseFloat(into.dataset.lat);
         const lng = parseFloat(into.dataset.lon);

         destination1Output = `${lng},${lat}`;
         setDestinationCoordinates(currentTable);
      } else {

         const class01Table = document.getElementById('class01');
         console.log("class01Table" + class01Table);

         destination1location = class01Table.querySelector('#destination').value;
         console.log("destination1location" + destination1location);
         const geocoder = new google.maps.Geocoder();

         // 출발지 좌표를 비동기적으로 처리
         geocoder.geocode({ address: destination1location }, (results, status) => {
            if (status === 'OK') {
               const lat = results[0].geometry.location.lat();
               const lng = results[0].geometry.location.lng();


               destination1Output = `${lng},${lat}`;  // 출발지 좌표를 전역 변수에 할당

               // 출발지 좌표가 설정된 후에 도착지 좌표 계산

               setDestinationCoordinates(currentTable);  // 도착지 좌표를 설정하고 API 호출
            } else {
               console.error('출발지 주소 변환 실패');
            }
         });
      }
   };
}

// 도착지 위도/경도 추출(태그 내 data-lat, data-lon에 저장된 값 추출)
function setDestinationCoordinates(currentTable) {

   const selectedDestination = currentTable.querySelector('.destination-input');

   // input 필드의 data-lat 및 data-lon 속성에서 좌표를 가져옴
   const destinationLat = parseFloat(selectedDestination.getAttribute('data-lat'));
   const destinationLng = parseFloat(selectedDestination.getAttribute('data-lon'));

   console.log('destinationLat:', destinationLat);
   console.log('destinationLng:', destinationLng);

   destinationOutput = `${destinationLng},${destinationLat}`;  // 도착지 좌표를 전역 변수에 할당
   console.log('도착지 좌표:', destinationOutput);

   // 출발지와 도착지 좌표가 모두 설정된 후에 API 호출
   if (destination1Output && destinationOutput) {
      callApi(destination1Output, destinationOutput, currentTable);
   }

}

// 카카오 모빌리티 API 호출(소요시간 계산)
function callApi(origin, destination, currentTable) {
   const apiUrl = `https://apis-navi.kakaomobility.com/v1/directions?origin=${origin}&destination=${destination}`;
   console.log("api주소: " + apiUrl);

   const headers = new Headers();
   headers.append("Authorization", "KakaoAK 32740405e6eb37b382f673ffd9a72e94"); // 카카오 API 키

   fetch(apiUrl, { method: 'GET', headers: headers })
      .then(response => {
         if (!response.ok) {
            throw new Error('API 호출 실패');
         }
         return response.json();
      })
      .then(data => {
         const duration = data.routes[0].summary.duration;
         const durationInMinutes = Math.floor(duration / 60);
         currentTable.querySelector('#result').textContent = `${durationInMinutes}분`;
      })
      .catch(error => {
         console.error('API 호출 실패:', error);
         currentTable.querySelector('#result').textContent = '소요시간 계산 실패';
      });
}

function initMap() {
   // Google Maps API callback으로 사용하기 위해 빈 initMap 함수 추가
}

window.onload = function() {
   initializeMap();
};

/////////////////////////////////////////////////////////////////


// 최종 선택 경로 모두 출력하기
function getDestinations() {
   let currentSpot = []; // 현재 일차별 경로를 담는 배열
   let spotIndex = 1; // 현재 일차 인덱스

   // 첫 번째 table (1일차)에서부터 시작해서 이후 ID가 'day'인 테이블을 기준으로 나눕니다.
   const tables = document.querySelectorAll('#itinerary > table');

   // 각 일차별 spot 배열을 담을 객체
   const spotData = {};

   tables.forEach((table, index) => {
      // 각 테이블 내의 .destination-input 값 추출하여 currentSpot에 추가
      table.querySelectorAll('#destination').forEach(input => {
         currentSpot.push(input.value);
      });

      // 다음 table이 'day' ID를 가지고 있다면, 새로운 일차가 시작됨을 의미하므로 spotData에 저장
      if (index < tables.length - 1 && tables[index + 1].id === 'day') {
         // spotData 객체에 'spot1', 'spot2'와 같이 일차별로 저장
         spotData[`spot${spotIndex}`] = currentSpot;
         currentSpot = []; // 다음 일차를 위한 새로운 배열 초기화
         spotIndex++; // 일차 인덱스 증가
      }
   });

   // 마지막 일차의 데이터 저장 (반복문 종료 후 남은 currentSpot)
   if (currentSpot.length > 0) {
      spotData[`spot${spotIndex}`] = currentSpot;
   }

   // 각 spot을 동적으로 할당 (최대 spotIndex까지)
   let spots = {};
   for (let i = 1; i <= spotIndex; i++) {
      spots[`spot${i}`] = spotData[`spot${i}`] || [];
   }

   // 전체 일차 개수 반환
   return spots;
}

// modal 창에 대한 함수
function showModal() {
   // getDestinations()를 호출하여 여행지 목록을 가져오기
   const spots = getDestinations();

   // 여행지 목록을 모달에 추가
   const destinationList = document.getElementById('destinationList');
   destinationList.innerHTML = ''; // 기존 목록을 비움

   // 각 일차별로 여행지 목록을 출력
   for (let i = 1; i <= Object.keys(spots).length; i++) {
      let spotArray = spots[`spot${i}`];  // spot1, spot2, spot3 등의 배열을 가져옴



      if (spotArray && spotArray.length > 0) {

         // 1일차의 첫 번째 여행지는 departureValue로 설정
		 if (i === 1 && departureValue) {
            spotArray.unshift(departureValue);  // spot1의 첫 번째 위치에 departureValue 추가
         }

         // 1일차, 2일차, 3일차 제목을 추가
         const dayTitle = document.createElement('h3');
         dayTitle.setAttribute('class', 'day-title');
         dayTitle.textContent = `${i}일차`;
         destinationList.appendChild(dayTitle);

         // 해당 일차의 여행지 목록을 <ul>로 생성
         const spotList = document.createElement('ul');
         spotArray.forEach(function(spot) {
            const li = document.createElement('li');
            li.textContent = spot; // 여행지 이름을 목록에 추가
            spotList.appendChild(li);
         });

         // 여행지 목록을 모달에 추가
         destinationList.appendChild(spotList);
      }
   }

   // 모달 창 보이기
   document.getElementById('destinationModal').style.display = "block";
}


// 모달 닫기 기능
document.getElementById('closeModal').onclick = function() {
   document.getElementById('destinationModal').style.display = "none";
}

// [홈으로 가기] 버튼 클릭 시 동작하는 함수
function goHome() {
   window.location.href = '/'; // 홈 페이지로 이동 (필요한 URL로 수정 가능)
}

////////////////////////////////////////////////////////    
// 관광지와 식당 마커 별도로 제작  
function go(button) {

   clearMarkers();

   if (button.dataset.action === 'one') {

      numberOne(button);

   } else if (button.dataset.action === 'three') {

      numberThree(button);
   }
}

function go2(button) {

   clearMarkers();

   if (button.dataset.action === 'two') {

      numberTwo(button);

   } else if (button.dataset.action === 'four') {

      numberFour(button);
   }
}

// 관광지 
// 1일차 출발지-목적지를 위한 함수 묶음
function numberOne(button) {
   getClosestTouristSpots(button);
   loadTouristSpots(button);
   selectPlace(button);
}

// 목적지-목적지를 위한 함수 묶음
function numberTwo(button) {
   getClosestTouristSpots2(button);
   loadTouristSpots(button);
   selectPlace(button);
}

// 출발지 기준 가까운 5개 장소 배열 출력
function getClosestTouristSpots(button) {
   // 각 관광지의 정보 객체로 배열화 
   currentTable = button.closest('table');

   var places = [];

   for (var i = 0; i < touristSpots.length; i++) {

      var distance = calculateDistance(altitude, longitude, touristAl[i], touristLo[i]);

      places.push({
         name: touristSpots[i],
         overview: touristOverviews[i],
         altitude: touristAl[i],
         longitude: touristLo[i],
         distance: distance
      });
   }

   // 거리 기준으로 오름차순 정렬 (가장 가까운 5개)
   places.sort(function(a, b) {
      return a.distance - b.distance;
   });

   // 가장 가까운 5개 관광지 정보 업데이트
   var touristSpots2 = places.slice(0, 5).map(function(place) { return place.name; });
   var touristOverviews2 = places.slice(0, 5).map(function(place) { return place.overview; });
   var touristAl2 = places.slice(0, 5).map(function(place) { return place.altitude; });
   var touristLo2 = places.slice(0, 5).map(function(place) { return place.longitude; });

   console.log("Fsdfsd" + touristOverviews2);

   select_touristSpots = touristSpots2;
   select_touristOverviews = touristOverviews2;
   select_touristAl = touristAl2;
   select_touristLo = touristLo2;

   loadTouristSpots(currentTable, select_touristSpots, select_touristOverviews, select_touristAl, select_touristLo);
};

function getClosestTouristSpots2(button) {

   currentTable = button.closest('table');
   const previousTable = currentTable.previousElementSibling;

   const into = previousTable.querySelector('#destination')
   const previousLat = parseFloat(into.dataset.lat);
   const previousLon = parseFloat(into.dataset.lon);

   var places = [];
   for (var i = 0; i < touristSpots.length; i++) {
      var distance = calculateDistance(previousLat, previousLon, touristAl[i], touristLo[i]);
      places.push({
         name: touristSpots[i],
         overview: touristOverviews[i],
         altitude: touristAl[i],
         longitude: touristLo[i],
         distance: distance

      });
   }

   // 거리 기준으로 오름차순 정렬 (가장 가까운 5개)
   places.sort(function(a, b) {
      return a.distance - b.distance;
   });

   // 가장 가까운 5개 관광지 정보 업데이트
   touristSpots30 = places.slice(0, 5).map(function(place) { return place.name; });
   touristOverviews30 = places.slice(0, 5).map(function(place) { return place.overview; });
   touristAl30 = places.slice(0, 5).map(function(place) { return place.altitude; });
   touristLo30 = places.slice(0, 5).map(function(place) { return place.longitude; });

   select_touristSpots = touristSpots30;
   select_touristOverviews = touristOverviews30;
   select_touristAl = touristAl30;
   select_touristLo = touristLo30;

   //  console.log(touristOverviews30);
   loadTouristSpots(currentTable, select_touristSpots, select_touristOverviews, select_touristAl, select_touristLo);

}

var map;
var markers = []; // 현재 지도에 표시된 마커를 저장하는 배열

// [내 주변 볼거리] 버튼 클릭 시 위 출력한 5개 장소 리스트와 마커 생성
function loadTouristSpots(currentTable, select_touristSpots, select_touristOverviews, select_touristAl, select_touristLo) {

   const placesList = currentTable.querySelector('#placesList');

   placesList.innerHTML = "";

   var bounds = new kakao.maps.LatLngBounds();
   for (let i = 0; i < 5; i++) {
      const placeName = select_touristSpots[i];
      const overview = select_touristOverviews[i];
      const lat = select_touristAl[i];
      const lon = select_touristLo[i];

      const listItem = document.createElement("li");
      listItem.textContent = placeName;
      listItem.onclick = () => selectPlace(i, currentTable);
      placesList.appendChild(listItem);

      // marker 시작
      var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
      var imageSize = new kakao.maps.Size(24, 35);
      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

      const markerPosition = new kakao.maps.LatLng(lat, lon);
      const marker = new kakao.maps.Marker({
         position: markerPosition,
         title: placeName,
         image: markerImage
      });

      marker.setMap(map);
      markers.push(marker);

      var infoWindow = new kakao.maps.InfoWindow({
         content: "<div style='font-family: \"Orbit\", sans-serif; width: 150px; height: 40px; font-size: 13px; display: flex; justify-content: center; align-items: center; text-align: center; padding: 0;'>" + placeName + "</div>"
      });

      infoWindow.open(map, marker);
      infoWindows.push(infoWindow);

      var infowindow12 = new kakao.maps.InfoWindow({
         content: "" // 초기 상태는 빈 문자열 (숨겨짐)
      });

      kakao.maps.event.addListener(marker, 'click', function() {
         if (infowindow12.getMap()) {
            // 이미 열려 있으면 닫기
            infowindow12.close();
         } else {
            // 열려 있지 않으면 overview를 설정하고 열기
            infowindow12.setContent("<div style='font-family: \"Orbit\", sans-serif; width: 150px; height: 50px; font-size: 12px; padding: 10px; display: flex; justify-content: center; align-items: center; text-align: center;'>" + overview + "</div>"); // 해당 마커의 overview로 내용 설정
            infowindow12.open(map, marker); // infowindow12 표시
         }

      });


      bounds.extend(markerPosition);
   }

   map.setBounds(bounds);

};

// 장소 선택 시 도착지 입력창에 추가 및 지도에 해당 장소 마커 표시
function selectPlace(index, currentTable) {

   const destinationInputs = currentTable.querySelectorAll('.destination-input');
          
   const lastInput = destinationInputs[destinationInputs.length - 1];
   lastInput.value = select_touristSpots[index];

   const lat = select_touristAl[index];
   const lon = select_touristLo[index];

   lastInput.setAttribute('data-lat', lat);
   lastInput.setAttribute('data-lon', lon);

   clearMarkers();

   const markerPosition = new kakao.maps.LatLng(lat, lon);
   const marker = new kakao.maps.Marker({
      position: markerPosition,
      title: select_touristSpots[index]
   });
   marker.setMap(map);
   markers.push(marker);
   map.panTo(markerPosition);

}

////////////////////////////////////////////////////////      

// 식당&카페
// 1일차 출발지-목적지를 위한 함수 묶음
function numberThree(button) {
   getClosesttouristSpots3(button);
   loadtouristSpots2(button);
   selectPlace2(button);
}

// 목적지-목적지를 위한 함수 묶음
function numberFour(button) {
   getClosesttouristSpots4(button);
   loadtouristSpots2(button);
   selectPlace2(button);
}

// 출발지 기준 가까운 5개 장소 배열 출력
function getClosesttouristSpots3(button) {
   currentTable = button.closest('table');

   var places = [];

   for (var i = 0; i < touristSpots2.length; i++) {
      var distance2 = calculateDistance(altitude, longitude, touristAl2[i], touristLo2[i]);

      places.push({
         name: touristSpots2[i],
         overview: touristOverviews2[i],
         altitude: touristAl2[i],
         longitude: touristLo2[i],
         distance2: distance2
      });
   }

   // 거리 기준으로 오름차순 정렬 (가장 가까운 5개)
   places.sort(function(a, b) {
      return a.distance2 - b.distance2;
   });

   // 가장 가까운 5개 관광지 정보 업데이트

   var touristSpots10 = places.slice(0, 5).map(function(place) { return place.name; });
   var touristOverviews10 = places.slice(0, 5).map(function(place) { return place.overview; });
   var touristAl10 = places.slice(0, 5).map(function(place) { return place.altitude; });
   var touristLo10 = places.slice(0, 5).map(function(place) { return place.longitude; });

   select_touristSpots2 = touristSpots10;
   select_touristOverviews2 = touristOverviews10;
   select_touristAl2 = touristAl10;
   select_touristLo2 = touristLo10;

   loadtouristSpots2(currentTable, select_touristSpots2, select_touristOverviews2, select_touristAl2, select_touristLo2);
};

function getClosesttouristSpots4(button) {

   currentTable = button.closest('table');
   const previousTable = currentTable.previousElementSibling;

   const into = previousTable.querySelector('#destination')
   const previousLat = parseFloat(into.dataset.lat);
   const previousLon = parseFloat(into.dataset.lon);

   var places = [];
   for (var i = 0; i < touristSpots2.length; i++) {
      var distance2 = calculateDistance(previousLat, previousLon, touristAl2[i], touristLo2[i]);
      places.push({
         name: touristSpots2[i],
         overview: touristOverviews2[i],
         altitude: touristAl2[i],
         longitude: touristLo2[i],
         distance2: distance2

      });

   }

   // 거리 기준으로 오름차순 정렬 (가장 가까운 5개)
   places.sort(function(a, b) {
      return a.distance2 - b.distance2;
   });

   // 가장 가까운 5개 관광지 정보 업데이트
   touristSpots50 = places.slice(0, 5).map(function(place) { return place.name; });
   touristOverviews50 = places.slice(0, 5).map(function(place) { return place.overview; });
   touristAl50 = places.slice(0, 5).map(function(place) { return place.altitude; });
   touristLo50 = places.slice(0, 5).map(function(place) { return place.longitude; });

   select_touristSpots2 = touristSpots50;
   select_touristOverviews2 = touristOverviews50;
   select_touristAl2 = touristAl50;
   select_touristLo2 = touristLo50

   loadtouristSpots2(currentTable, select_touristSpots2, select_touristOverviews2, select_touristAl2, select_touristLo2);

}

var map;
var markers = []; // 현재 지도에 표시된 마커를 저장하는 배열


// [내 주변 볼거리] 버튼 클릭 시 위 출력한 5개 장소 리스트와 마커 생성
function loadtouristSpots2(currentTable, select_touristSpots2, select_touristOverviews2, select_touristAl2, select_touristLo2) {


   const placesList2 = currentTable.querySelector('#placesList');

   placesList2.innerHTML = "";

   var bounds = new kakao.maps.LatLngBounds();
   for (let i = 0; i < 5; i++) {
      const placeName = select_touristSpots2[i];
      const overview = select_touristOverviews2[i];
      const lat = select_touristAl2[i];
      const lon = select_touristLo2[i];

      const listItem = document.createElement("li");
      listItem.textContent = placeName;
      listItem.onclick = () => selectPlace2(i, currentTable);
      placesList2.appendChild(listItem);

      // marker 시작
      var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
      var imageSize = new kakao.maps.Size(24, 35);
      var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

      const markerPosition = new kakao.maps.LatLng(lat, lon);
      const marker = new kakao.maps.Marker({
         position: markerPosition,
         title: placeName,
         image: markerImage
      });
      marker.setMap(map);
      markers.push(marker);

      var infoWindow = new kakao.maps.InfoWindow({
         content: "<div style='font-family: \"Orbit\", sans-serif; width: 150px; height: 40px; font-size: 13px; display: flex; justify-content: center; align-items: center; text-align: center; padding: 0;'>" + placeName + "</div>"
      });
      infoWindow.open(map, marker);
      infoWindows.push(infoWindow);

      var infowindow12 = new kakao.maps.InfoWindow({
         content: "" // 초기 상태는 빈 문자열 (숨겨짐)
      });

      kakao.maps.event.addListener(marker, 'click', function() {
         if (infowindow12.getMap()) {
            // 이미 열려 있으면 닫기
            infowindow12.close();
         } else {
            // 열려 있지 않으면 overview를 설정하고 열기
            infowindow12.setContent("<div style='font-family: \"Orbit\", sans-serif; width: 150px; height: 50px; font-size: 12px; padding: 10px; display: flex; justify-content: center; align-items: center; text-align: center;'>" + overview + "</div>"); // 해당 마커의 overview로 내용 설정
            infowindow12.open(map, marker); // infowindow12 표시
         }

      });
      bounds.extend(markerPosition);
   }

   map.setBounds(bounds);

};

// 장소 선택 시 도착지 입력창에 추가 및 지도에 해당 장소 마커 표시
function selectPlace2(index, currentTable) {

   const destinationInputs = currentTable.querySelectorAll('.destination-input');
          
   const lastInput = destinationInputs[destinationInputs.length - 1];
   lastInput.value = select_touristSpots2[index];

   const lat = select_touristAl2[index];
   const lon = select_touristLo2[index];

   lastInput.setAttribute('data-lat', lat);
   lastInput.setAttribute('data-lon', lon);

   clearMarkers();

   const markerPosition = new kakao.maps.LatLng(lat, lon);
   const marker = new kakao.maps.Marker({
      position: markerPosition,
      title: select_touristSpots2[index]
   });
   marker.setMap(map);
   markers.push(marker);
   map.panTo(markerPosition);

}

