console.log("member.js연결")

// 1. 회원가입
function doPostSignup(){
    console.log("doPostSignup() 실행")
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