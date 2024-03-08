console.log("board/write.js 실행");

// 섬머노트 실행
$(document).ready(function() {
    $('#bcontent').summernote({
        placeholder: 'Hello Bootstrap 4',
        tabsize: 2,
        height: 100,
        lang: "ko-KR"					// 한글 설정
    });
});

// 1. 글쓰기
function onWrite(){
    console.log("onWrite()");
    // 1. form DOM 가져온다.
    let boardWriteForm = document.querySelector(".boardWriteForm");
    // 2. 폼 바이트(바이너리) 객체 변환[첨부파일 보낼때는 필수]
    let boardWriteFormData = new FormData(boardWriteForm);
    console.log(boardWriteFormData);
    // 3. ajax 첨부파일 폼 전송
    $.ajax({
        url : "/board/write.do",
        method : "post",
        data : boardWriteFormData,
        contentType :false,
        processData : false,
        success : (result)=>{
            console.log(result);
            if(result == 0){
                alert("글쓰기 실패:관리자에게 문의(DB문의)");
            } else if(result == -1){
                alert("글쓰기 실패:관리자에게 문의(첨부파일오류)");
            } else if(result == -2){
                alert("로그인 세션이 존재하지 않습니다.(잘못된 접근)");
                location.href="/member/login";
            } else {
                alert("글쓰기 성공")
                location.href="/board/view?bno="+result;
            }
        }
    });
}