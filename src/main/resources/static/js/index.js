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
            $.ajax({
                url : '/member/login/info',
                method : 'get',
                data : {id : r},
                async : false,
                success : (r2)=> {
                    console.log(r2);
                    console.log(r2.uuidFile);
                    html += `
                        <li class="nav-item"><a class="nav-link" href="#" onclick="logout()">로그아웃</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">내정보</a></li>
                        <li class="nav-item"><div class="profilimg"><img src="/img/upload/${r2.uuidFile}" /></div>${r}님 환영합니다.</li>
                    `;
                }
            });
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

function preimg(e){
    console.log("preimg() 실행");
    console.log(e)
    console.log(e.files);
    console.log(e.files[0]);
    // 1. input에 업로드 된 파일을 바이트로 가져오기
    let fileReader = new FileReader();
    // 2. 파일 읽기 메소드
    fileReader.readAsDataURL(e.files[0]);
    console.log(fileReader);
    console.log(fileReader.result);
    // 3. 파일 onload 정의
    fileReader.onload = e => {
        console.log(e);
        console.log(e.target);
        console.log(e.target.result);

        document.querySelector(".imgView").src=e.target.result;
    }
}