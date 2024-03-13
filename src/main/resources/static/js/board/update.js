console.log("board/update.js 실행");

// 섬머노트 실행
$(document).ready(function() {
    $('#bcontent').summernote({
        placeholder: 'Hello Bootstrap 4',
        tabsize: 2,
        height: 100,
        lang: "ko-KR"					// 한글 설정
    });
});
let bno = new URL(location.href).searchParams.get("bno");
onView(bno);
function onView(bno){
    console.log("onView()");
    $.ajax({
        url :"/board/view.do",
        method:"get",
        data : {"bno":bno},
        success : (r)=>{
            console.log(r);
            document.querySelector("#bcno").value = r.bcno;
            document.querySelector("#btitle").value = r.btitle;
            $('#bcontent').summernote('insertText', r.bcontent);
        }
    });
}

// 1. 글쓰기
function onUpdate(){
    console.log("onUpdate()");
    // 1. form DOM 가져온다.
    let boardWriteForm = document.querySelector(".boardWriteForm");
    // 2. 폼 바이트(바이너리) 객체 변환[첨부파일 보낼때는 필수]
    let boardWriteFormData = new FormData(boardWriteForm);
    boardWriteFormData.set('bno',bno)
    console.log(boardWriteFormData);
    // 3. ajax 첨부파일 폼 전송
    $.ajax({
        url : "/board/update.do",
        method : "put",
        data : boardWriteFormData,
        contentType :false,
        processData : false,
        success : (result)=>{
            console.log(result);
            if(!result){
                alert("글쓰기 실패:관리자에게 문의(DB문의)");
            } else {
                alert("글쓰기 성공")
                location.href="/board/view?bno="+bno;
            }
        }
    });
}