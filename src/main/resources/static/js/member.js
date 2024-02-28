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

    if(이메일규칙.test(email)){
        msg = "통과";
        checkArray[4] = true;
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