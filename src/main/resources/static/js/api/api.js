console.log("api()");

let pageObject = {
    page : 1,           // 현재 페이지
    pageBoardSize : 10,   // 한재 페이지당 게시물 수
    bcno : 0,           // 현재 카테고리
    key : 'b.title',    // 현재 검색 key
    keyword : ''        // 현재 검색 keyword
}

getApiList(pageObject.page,pageObject.pageBoardSize)
function getApiList(page,pageBoardSize){
    $.ajax({
        url : "https://api.odcloud.kr/api/15109599/v1/uddi:5959c05a-f2b4-41f7-90b7-c282d3ea67dd?page="+page+"&perPage="+pageBoardSize+"&serviceKey=9bdIeF071iZoRUgvPAmF7maL1odUbT4XmZRVqvxguHcnevkJAySMb5%2B3fn5sp63lWboPN9IHeN350Uq6EvX0ug%3D%3D",
        method : "Get",
        success : (r2)=>{
            console.log(r2.data);
            let boardTableBody = document.querySelector("#boardTable1 tbody");
            let html = ``;
            r2.data.forEach(data => {
                html += `
                    <tr onclick="getMapView(${data.식당경도} ,${data.식당위도})">
                        <td>${data.사업장명}</td>
                        <td>${data.대표메뉴1} </td>
                        <td>${data.도로명전체주소}</td>
                        <td>${data.영업시간}</td>
                        <td>${data["홈페이지(URL)"]}</td>
                        <td>${data.주차가능}</td>
                    </tr>
                `;
            });

            boardTableBody.innerHTML = html;
        }
    });
}

getApiList2(pageObject.page,pageObject.pageBoardSize)
function getApiList2(page,pageBoardSize){
    $.ajax({
        url : "https://api.odcloud.kr/api/15111852/v1/uddi:71ee8321-fea5-4818-ade4-9425e0439096?page="+page+"&perPage="+pageBoardSize+"&serviceKey=9bdIeF071iZoRUgvPAmF7maL1odUbT4XmZRVqvxguHcnevkJAySMb5%2B3fn5sp63lWboPN9IHeN350Uq6EvX0ug%3D%3D",
        method : "Get",
        success : (r)=>{
            console.log(r.data);
            let boardTableBody = document.querySelector("#boardTable tbody");
            let html = ``;
            r.data.forEach(data => {
                html += `
                    <tr>
                        <td>${data.관리기관명}</td>
                        <td>${data.날짜}</td>
                        <td>${data.관리기관명.split(" ")[0]} ${data.시도명} ${data.시군구명} ${data.읍면동}</td>
                        <td>${data["우량(mm)"]}</td>
                    </tr>
                `;
            });

            boardTableBody.innerHTML = html;
        }
    });
}

function getMapView(no1,no2){
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new kakao.maps.LatLng(no2, no1), // 지도의 중심좌표
            level: 3 // 지도의 확대 레벨
        };

    var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다

    // 마커가 표시될 위치입니다
    var markerPosition  = new kakao.maps.LatLng(no2, no1);

    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
        position: markerPosition
    });

    // 마커가 지도 위에 표시되도록 설정합니다
    marker.setMap(map);
}

var map = new kakao.maps.Map(document.getElementById('map'), { // 지도를 표시할 div
    center : new kakao.maps.LatLng(37.3218778, 126.8308848), // 지도의 중심좌표
    level : 14 // 지도의 확대 레벨
});

// 마커 클러스터러를 생성합니다
var clusterer = new kakao.maps.MarkerClusterer({
    map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
    averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
    minLevel: 10 // 클러스터 할 최소 지도 레벨
});

// 데이터를 가져오기 위해 jQuery를 사용합니다
// 데이터를 가져와 마커를 생성하고 클러스터러 객체에 넘겨줍니다

$.ajax({
    url : "https://api.odcloud.kr/api/15109599/v1/uddi:5959c05a-f2b4-41f7-90b7-c282d3ea67dd?page="+pageObject.page+"&perPage="+pageObject.pageBoardSize+"&serviceKey=9bdIeF071iZoRUgvPAmF7maL1odUbT4XmZRVqvxguHcnevkJAySMb5%2B3fn5sp63lWboPN9IHeN350Uq6EvX0ug%3D%3D",
    method : "get",
    success : (respnse)=>{
        console.log(respnse);
         // 데이터에서 좌표 값을 가지고 마커를 표시합니다
        // 마커 클러스터러로 관리할 마커 객체는 생성할 때 지도 객체를 설정하지 않습니다
        console.log(respnse.data);
        var markers = respnse.data.map((object)=>{
            console.log(object.식당위도)
            console.log(object.식당경도)
            return new kakao.maps.Marker({
                position : new kakao.maps.LatLng(object.식당위도 ,object.식당경도)
            });
        });

        // 클러스터러에 마커들을 추가합니다
        clusterer.addMarkers(markers);
    }
});