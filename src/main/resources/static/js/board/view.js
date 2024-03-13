// * 경로(URL)상의 쿼리스트링(매개변수)호출하기.
    // 1. new URL(location.href) : 현재 페이지 경로호출
console.log(new URL(location.href));
    // 2. [.searchParams] 경로상의 (쿼리스트링)매개변수들
console.log(new URL(location.href).searchParams);
    // 3. [.get('queryStringKey')] (쿼리스트링)매개변수들 에서 특정 매개변수 호출
console.log(new URL(location.href).searchParams.get("bno"));

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
            document.querySelector(".bcno").innerHTML = r.bcname;
            document.querySelector(".btitle").innerHTML = r.btitle;
            document.querySelector(".bcontent").innerHTML = r.bcontent;
            document.querySelector(".mno span").innerHTML = r.mid;
            document.querySelector(".bdate span").innerHTML = r.bdate;
            document.querySelector(".bview span").innerHTML = r.bview;
            onGetReply();
            if(r.bfile != null){
                document.querySelector(".bfile").innerHTML = `<a href="/board/file/download?bfile=${r.bfile}">${r.bfile}</a>`;
            } else {
                document.querySelector(".bfile").style.display = "none";
            }
            $.ajax({
                url : '/member/login/check',
                method : 'get',
                async : false,
                success: (r2)=>{
                    if(r2 == r.mid){
                        document.querySelector(".writeBtnWrap").innerHTML = `
                            <a class="btn" href="/board/update?bno=${r.bno}">수정</a>
                            <button class='btn' onclick='onDeleteBoard(${r.bno})'>삭제</button>
                        `;
                    }
                }
            });

            document.querySelector(".writeBtnWrap").innerHTML += `<a class="btn" href="/board/">목록</a>`;
        }
    });
}

// 2. 게시물 삭제 함수
function onDeleteBoard(bno){
    $.ajax({
        url : "/board/delete.do",
        method : "delete",
        data : {"bno":bno},
        success : (r)=>{
            if(r){
                alert('삭제성공');
                location.href= '/board/'
            } else {
                alert('삭제실패');
            }
        }
    });
}

// 댓글쓰기
function onReplyWrite(brindex){
    $.ajax({
        url : "/board/reply/write.do",
        method : "post",
        data : {
            "bno":bno,  // 현재 보고 있는 게시물 번호
            "brcontent" : document.querySelector(".brcontent"+brindex).value,   // 작성된 댓글 내용
            "brindex" : brindex   // 댓글 인덱스 번호[0:상위, 1~:하위]
        },
        success : (r)=>{
            console.log(r);
            if(r){
                alert('댓글 작성 성공');
                onGetReply()
            } else {
                alert('댓글 작성 실패')
            }
        }
    });
}

// 댓글출력
function onGetReply(){
    $.ajax({
        url : "/board/reply/do",
        method : "get",
        data : {"bno":bno},
        async: false,
        success : (r)=>{
            console.log(r);
            let replyListBox = document.querySelector(".replyListBox")
            let html = ``;
            r.forEach(reply=>{
                console.log(reply);
                html += `
                    <div class="replyView">
                        <p class="brcontent">${reply.brcontent}</p>
                        <p class="mid">${reply.id}</p>
                        <p class="brdate">${reply.brdate}</p>
                        <button onclick="subReplyView(${reply.brno})" type="button">답변작성</button>
                        <div class="subReplyBox subReplyBox${reply.brno}">

                        </div>
                        ${onSubReplyList(reply.subreply)}
                    </div>
                `;
            });
            replyListBox.innerHTML = html;
        }
    });
}

// 5. 대댓글 작성칸 생성함수
function subReplyView(brno){
    document.querySelector(".subReplyBox"+brno).innerHTML=`
        <textarea class="brcontent brcontent${brno}"></textarea>
        <button onclick="onReplyWrite(${brno})" type="button">답변작성</button>
    `;
}

function onSubReplyList(subReply){
    let subHtml  = ``;
    subReply.forEach(subreply=>{
        subHtml += `
            <div class="replyView">
                <p class="subReply">┖</p>
                <p class="brcontent">${subreply.brcontent}</p>
                <p class="mid">${subreply.id}</p>
                <p class="brdate">${subreply.brdate}</p>
            </div>
        `;
    })
    return subHtml;
}