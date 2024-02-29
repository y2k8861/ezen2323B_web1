console.log("member.js연결");

 /*
    정규표현식
        - 주로 문자열 데이터 검사할때 사용 - 유효성 검사
        - 메소드
            정규표현식.test(검사할 대상)

        - 형식 규칙
            /^ : 정규표현식 시작
            $/ : 정규표현식 끝
            {최소길이,최대길이} : 문자 길이 규착
            [허용할문자]        : 
 */

let checkArray = [false,false,false,false,false]

function idCheck(e){
    console.log("idCheck(e)");
    let id = e.value;
    console.log(id);
    let idj = /^[a-z0-9]{5,30}$/
    console.log(idj.test(id))
    if(idj.test(id)){
        $.ajax({
            url: "/member/find/idCheck",
            method: "get",
            data: {'id':id},
            success: function (result) {
                if(result){
                    document.querySelector("#idCheckBox").innerHTML = "사용중인 아이디";
                    checkArray[0] = false;
                } else {
                    document.querySelector("#idCheckBox").innerHTML = "통과";
                    checkArray[0] = true;
                }     
            }
        });
        
    } else {
        document.querySelector("#idCheckBox").innerHTML = "영소문자+숫자 조합의 5~30 글자로  입력해주세요.";
        checkArray[0] = false;
    }
}

function pwCheck(){
    let pw = document.querySelector("#pw").value;
    let pwconfirm = document.querySelector("#pwCheckInput").value;

    let pwCheckMsg = "";
    checkArray[1] = false;
    let 비밀번호규칙 =  /^(?=.*[A-Za-z])(?=.*[0-9])[A-Za-z0-9]{5,30}$/;

    if(비밀번호규칙.test(pw)){
        if(비밀번호규칙.test(pwconfirm)){
            if(pw == pwconfirm){
                console.log("통과");
                pwCheckMsg = "통과";
                checkArray[1] = true;
            } else {
                console.log("패스워드 불일치");
                pwCheckMsg = "패스워드 불일치";
            }
        } else {
            console.log("영대소문자 1개 필수 와 숫자 1개 필수의 조합 5~30글자 1");
            pwCheckMsg = "영대소문자 1개 필수 와 숫자 1개 필수의 조합 5~30글자";
        }

    } else {
        console.log("영대소문자 1개 필수 와 숫자 1개 필수의 조합 5~30글자 2");
        pwCheckMsg = "영대소문자 1개 필수 와 숫자 1개 필수의 조합 5~30글자";
    }


    document.querySelector("#pwCheckBox").innerHTML = pwCheckMsg;
}

function nameCheck(){
    let name = document.querySelector("#name").value;
    let 이름규칙 = /^[가-힣]{5,20}$/;
    let msg = "";
    checkArray[2] = false;
    if(이름규칙.test(name)){
        msg = "통과";
        checkArray[2] = true;
    } else {
        msg="한글 5~20글자";
    }

    document.querySelector("#nameCheckBox").innerHTML = msg;
}

function phoneCheck(){
    let phone = document.querySelector("#phone").value;
    let 번호규칙 = /^([0-9]{2,3})+[-]+([0-9]{3,4})+[-]+([0-9]{4})$/

    let msg="000-0000-0000 또는 00-000-0000 입력해주세요.";
    checkArray[3] = false;

    if(번호규칙.test(phone)){
        msg = "통과";
        checkArray[3] = true;
    }
    document.querySelector("#phoneCheckBox").innerHTML = msg;
}

function emailCheck(){
    let email = document.querySelector("#email").value;
    let 이메일규칙 = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+\.[a-zA-Z\.]+$/

    let msg="이메일 양식이 아닙니다.";
    checkArray[4] = false;
    document.querySelector(".authreqBtn").disabled = true;
    if(이메일규칙.test(email)){
        msg = "인증요청가능";
        document.querySelector(".authreqBtn").disabled = false;
        
    }
    document.querySelector("#emailCheckBox").innerHTML = msg;
}

// 1. 회원가입
function doPostSignup(){
    for(let i = 0; i<checkArray.length; i++){
        if(!checkArray[i]){
            alert("입력사항들을 모두 입력해주세요.");
            return;
        }
    }
    console.log("doPostSignup() 실행");
    // 1. HTML 입력값 호출
    //  1. 하나씩 요청
        // let id = document.querySelector("#id").value;       console.log(id);
        // let pw = document.querySelector("#pw").value;       console.log(pw);
        // let name = document.querySelector("#name").value;   console.log(name);
        // let email = document.querySelector("#email").value; console.log(email);
        // let phone = document.querySelector("#phone").value; console.log(phone);
        // let img = document.querySelector("#img").value;     console.log(img);
    // 2. 객체화
        //    let info = {
        //        id:id,pw:pw,name:name,email:email,phone:phone,img:img
        //    }; console.log(info)
    //  2. 폼가져오기
    let signUpForm = document.querySelectorAll("#signUpForm");
    console.log(signUpForm);
    let signUpFormDate = new FormData(signUpForm[0]);    //  new FormData : 문자 처리가아닌 바이트 데이터로 변환(첨부파일 필수)
    console.log(signUpFormDate)




    // -- 유효성 검사

    // 3. 객체를 배열에 저장 --> SPRING CONTROLLER 서버와 통신[JQUERY AJAX]
     $.ajax({
        url : "/member/signup",
        method : "post",
        //data : info,
        data : signUpFormDate,
        contentType : false,
        processData : false,
        success : function(result){
            console.log(result);
            if(result == true){
                alert('회원가입 성공');
                location.href="/member/login";
            } else {
                alert('회원가입 실패')
            }
        }
    });
}

let timer = 2;

// 테스트
    // 정의 : setInterval(함수,밀리초)  : 특정 밀리초 마다 함수 실행
    // 종료 : clearInterval(종료할 함수명)     : setInterval 종료
    // let timerInter = setInterval(()=>{
    //     // 1. 초 변수를 분/초 변환
    //     let m = parseInt(timer/60); // 분
    //     let s = parseInt(timer%60); // 분제외한 초

    //     // 2. 시간을 두 자릿수로 표현
    //     m = m<10? "0"+m : m;
    //     s = s<10? "0"+s : s;

    //     // 3. 시간 출력
    //     document.querySelector(".authbox").innerHTML = `${m}분${s}초`;

    //     // 4. 초 감소
    //     timer --;

    //     // 5. 만약에 초가 0보다 작아지면
    //     if(timer<0){
    //         clearInterval(timerInter);
    //     }
    // },1000);

let authbox = document.querySelector(".authbox");
let authreqBtn = document.querySelector(".authreqBtn");
let timerInter = null;

function authreq(){

    let html = `
        <span class="timebox">03:00</span>
        <input type="text" class="ecodeinput" />
        <button type="button" onclick="auth()">인증</button>
    `;

    authbox.innerHTML = html;

    // 자바에게 인증 요청
    $.ajax({
        url : "/auth/email/req",
        method : "get",
        data : {"email":document.querySelector("#email").value},
        success : (r)=>{
            if(r){
                timer = 30;
                ontimer();
                authreqBtn.disabled = true;
            } else {
                alert('관리자에게 문의하세요.');
            }
        }
    });
}

function auth(){
    let ecodeinput = document.querySelector(".ecodeinput").value;

    $.ajax({
        method: "get",
        url: "/auth/email/check",
        data: {"ecodeinput":ecodeinput},
        success: (result)=>{
            if(result){
                checkArray[4] = true;
                document.querySelector("#emailCheckBox").innerHTML = "통과"
                clearInterval(timerInter);
                authreqBtn.disabled = false;
                authbox.innerHTML = "";
            } else {
                alert("인증번호가 다릅니다.");
            }
        }
    });
}

function ontimer(){
    timerInter = setInterval(()=>{
        // 1. 초 변수를 분/초 변환
        let m = parseInt(timer/60); // 분
        let s = parseInt(timer%60); // 분제외한 초

        // 2. 시간을 두 자릿수로 표현
        m = m<10? "0"+m : m;
        s = s<10? "0"+s : s;

        // 3. 시간 출력
        document.querySelector(".timebox").innerHTML = `${m}:${s}`;

        // 4. 초 감소
        timer --;

        // 5. 만약에 초가 0보다 작아지면
        if(timer<0){
            clearInterval(timerInter);
            authreqBtn.disabled = false;
            authbox.innerHTML = "다시 인증 요청 해주세요.";
        }
    },1000);
}

function doPostLogin(){
    console.log("doPostLogin() 실행");

    // 1. HTML 입력값 호출
    let id = document.querySelector("#id").value; console.log(id);
    let pw = document.querySelector("#pw").value; console.log(pw);

    // -- 유효성 검사

    // 2. 객체화
    let info = {id:id,pw:pw}; console.log(info)

     // 3. 객체를 배열에 저장 --> SPRING CONTROLLER 서버와 통신[JQUERY AJAX]
         $.ajax({
            url : "/member/login",
            method : "post",
            data : info,
            success : function(result){
                console.log(result);
                if(result == true){
                    alert('로그인 성공');
                    location.href="/"; //로그인 성공시 메인페이지로이동
                } else {
                    alert('로그인 실패')
                }
            }
        });
}