
// 클라이언트(브라우저) 위치 가져오기
navigator.geolocation.getCurrentPosition((data)=>{
    console.log(data);
});


// 1. 지도 객체
var map = new kakao.maps.Map(document.getElementById('map'), { // 지도를 표시할 div
    center : new kakao.maps.LatLng(37.3218778, 126.8308848), // 지도의 중심좌표
    level : 6 // 지도의 확대 레벨
});

var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png', // 마커이미지의 주소입니다
    imageSize = new kakao.maps.Size(64, 69), // 마커이미지의 크기입니다
    imageOption = {offset: new kakao.maps.Point(27, 69)}

// 2. 클러스터 객체(클러스터란 마커가 여러개일때 효과)
// 마커 클러스터러를 생성합니다
var clusterer = new kakao.maps.MarkerClusterer({
    map: map, // 마커들을 클러스터로 관리하고 표시할 지도 객체
    averageCenter: true, // 클러스터에 포함된 마커들의 평균 위치를 클러스터 마커 위치로 설정
    minLevel: 8 // 클러스터 할 최소 지도 레벨
});

// 데이터를 가져오기 위해 jQuery를 사용합니다
// 데이터를 가져와 마커를 생성하고 클러스터러 객체에 넘겨줍니다
$.get("/product/list.do", (response) => {
    // 데이터에서 좌표 값을 가지고 마커를 표시합니다
    // 마커 클러스터러로 관리할 마커 객체는 생성할 때 지도 객체를 설정하지 않습니다
    console.log(response);
    let markers = response.map((data)=>{
        // 1. 마커 생성
        let marker = new kakao.maps.Marker({
            position : new kakao.maps.LatLng(data.plat,data.plng),
            image: new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption) // 마커이미지 설정
        });
        kakao.maps.event.addListener(marker, 'click', function() {
            // 사이드바 열기
            document.querySelector(".sidebarBtn").click();

            // 사이드바 내용물
                // 1. 제품 제목
            document.querySelector(".offcanvas-title").innerHTML = "제품명 : " + data.pname;
                // 2. 제품 이미지
                let carouselHTML = ``;
                let index = 0;
                data.pimg.forEach((pimg)=>{
                    carouselHTML += `
                        <div class="carousel-item ${index == 0 ? 'active':''}">
                            <img src="/img/upload/${pimg}" class="d-block w-100" alt="...">
                        </div>
                    `;
                    index++;
                });
                document.querySelector("#carouselExample .carousel-inner").innerHTML = carouselHTML;


                // 3. 제품 설명,가격,작성자
            document.querySelector(".offcanvas-body .pcontent").innerHTML = "<p>제품설명 : " + data.pcontent + "</p>";
            document.querySelector(".offcanvas-body .pcontent").innerHTML += "<p>제품가격 : " + data.pprice + "</p>";
            document.querySelector(".offcanvas-body .pcontent").innerHTML += "<p>작성자 : " + data.mid + "</p>";
                // 4,
            plikeView(data.pno)
        });
        return marker
    });

    // 클러스터러에 마커들을 추가합니다
    clusterer.addMarkers(markers);
});

function plikeBtn(pno,method){
    let result = false;
    console.log(pno);
    $.ajax({
        url : '/product/plike.do',
        method : method,
        data : {"pno":pno},
        async : false,
        success : (r)=>{
            console.log(r);
            result = r ;
            console.log(result)
        }
    });
    if(method != "get"){plikeView(pno)}
    return result;
}

function plikeView(pno){
    let result = plikeBtn(pno,"get");
    console.log(result);
    if(result){
        document.querySelector(".offcanvas-body .sideBarBtnBox").innerHTML =`
            <button onclick="plikeBtn(${pno},'delete')" type= "button">찜하기 ♥</button>
            <button onclick="" type= "button">채팅</button>
        `;
    } else {
        document.querySelector(".offcanvas-body .sideBarBtnBox").innerHTML =`
            <button onclick="plikeBtn(${pno},'post')" type= "button">찜하기 ♡</button>
            <button onclick="" type= "button">채팅</button>
        `;
    }
};


//var markers = $(data.positions).map(function(i, position) {
//    return new kakao.maps.Marker({
//        position : new kakao.maps.LatLng(position.lat, position.lng)
//    });
//});