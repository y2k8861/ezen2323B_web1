console.log("js열림");

// 1. 저장 메소드 : 실행 조건 : 등록 버튼 클릭시 매개변수X,리턴X
function doCreate(){
    console.log("doCreate() 실행");
    let content = document.querySelector("#content").value;    console.log(content);
    let writer = document.querySelector("#writer").value;     console.log(writer);
    let pw = document.querySelector("#pw").value;             console.log(pw);

    let info = {content:content, writer:writer, pw:pw};

    $.ajax({
        url : "/board/create",
        method :"POST",
        data : info,
        success : function(result){
            console.log(result);
            if(result){
                alert("등록성공");
                doRead();
            } else {
                alert("등록실패")
            }
        }
    });
};

// 2.전체 호출 메소드 : 실행 조건 : 페이지 열림, 변화(저장,수정,삭제) 매개변수X,리턴 객체 리스트
doRead(); // JS열릴떄 최초 실행
function doRead(){
    console.log("doRead()");
    $.ajax({
        url : "/board/read",
        method : "GET",
        success : function(result){
            console.log(result);
            let tbody = document.querySelector("table tbody");
            let html =``;
            for(let i = 0; i<result.length; i++){
                html +=`
                    <tr>
                        <td>${result[i].no}</td>
                        <td>${result[i].content}</td>
                        <td>${result[i].writer}</td>
                        <td>
                            <button onclick="doUpdate(${result[i].no})">수정</button>
                            <button onclick="doDelete(${result[i].no})">삭제</button>
                        </td>
                    </tr>
                `;
            }
            tbody.innerHTML = html;
        }
    });
};

// 3. 수정 메소드 : 실행 조건 : 수정 버튼 클릭시 수정할 번호,리턴X
function doUpdate(no){
    console.log("doUpdate()"+no);
//    if(!doPw(no)){
//        return;
//    }

    let content = prompt('수정할 내용'); console.log(content);
    let pw = prompt('비밀번호'); console.log(pw);

    let info = {no:no,content:content,pw:pw};

    $.ajax({
        url : "/board/update",
        method :"POST",
        data : info,
        success : function(result){
            console.log(result);
            if(result){
                alert("등록성공");
                doRead();
            } else {
                alert("등록실패")
            }
        }
    });
};

// 4. 삭제 메소드 : 실행 조건 : 삭제 버튼 클릭시 삭제할 번호, 리턴X
function doDelete(no){
    console.log("doDelete()"+no);
    let pw = prompt('비밀번호'); console.log(pw);
//    if(!doPw(no)){
//        return;
//    }

    $.ajax({
            url : "/board/delete/"+no+"/"+pw,
            method :"get",
            success : function(result){
                console.log(result);
                if(result){
                    alert("삭제성공");
                    doRead();
                } else {
                    alert("삭제실패")
                }
            }
        });
};

// 5. 비밀번호 유효성 검사
function doPw(no){
    let pw = prompt('비밀번호를 입력해주세요.'); console.log(pw);
    let info = {no:no,pw:pw}; console.log(info);
    let returnResult = 0;

    $.ajax({
        url : "/board/doPw",
        method : "Post",
        async: false, //동기식 추가
        data : info,
        success : function(result){
            console.log(result);
            if(result){
                returnResult = true;
            } else {
                alert('비밀번호를 다시 입력해주세요.');
                returnResult = false;
            }
        }
    });

    return returnResult
}