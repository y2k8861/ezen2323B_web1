console.log("index.js 연결");

// 1. 로그인 여부 확인 요청
$.ajax({
    url : "/member/login/check",
    method : "get",
    success : (r) => {
        console.log(r);
        // 1. 어디에
        let login_menu = document.querySelector("#login_menu");
        // 2. 무엇을
        let html = ``;

        if(r != ""){ // 로그인 했을떄
            html += `
                <li class="nav-item"><a class="nav-link" href="#" onclick="logout()">로그아웃</a></li>
                <li class="nav-item"><a class="nav-link" href="#">내정보</a></li>
                <li class="nav-item"><img src="#" /> ${r} 님 환영합니다.</li>
            `;
        } else {    // 로그인 안했을떄
            html += `
                <li class="nav-item"><a class="nav-link" href="/member/login">로그인</a></li>
                <li class="nav-item"><a class="nav-link" href="/member/signup">회원가입</a></li>
            `;
        }
        login_menu.innerHTML = html;
    }
});

function logout(){
    $.ajax({
        url : "/member/logout",
        method : "get",
        success : (r) => {
            console.log(r);
            if(r){
                alert('로그아웃 했습니다.')
                location.href="/";
            } else {
                alert('로그아웃 실패[관리자에게 문의]');
            }
        }
    });
}