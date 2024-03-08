// =========== 페이지 관련 객체 =========== //
let pageObject = {
    page : 1,           // 현재 페이지
    pageBoardSize : 5,   // 한재 페이지당 게시물 수
    // bcno : 0,           // 현재 카테고리
    key : 'b.title',    // 현재 검색 key
    keyword : ''        // 현재 검색 keyword
}
console.log(pageObject);

// 1. 전체 출력용 : 함수 : 매개변수 x, 반환 x , 언제 실행 할껀지 : 페이지 열릴때 (js)
doViewList(1);
function doViewList(page){
    console.log("doViewList");

    pageObject.page = page;

    $.ajax({
        url : "/board/do",
        method : "get",
        data : pageObject,
        // data : {"page" : pageObject.page, "pageBoardSize" : pageObject.pageBoardSize },
        success : (r)=>{
            console.log(r)
            console.log(r.totalPage)
            console.log(r.startBtn)
            console.log(r.endtBtn)
            let boardTableBody = document.querySelector("#boardTable tbody");

            let html = ``;
            console.log(r.list);
            r.list.forEach(board => {
                html += `
                    <tr>
                        <th scope="row">${board.bno}</th>
                        <td><a href="javascript:onBoardView(${board.bno})">${board.btitle}</a></td>
                        <td><img src="/img/upload/${board.mimg}" style="width:20px;border-radius:50%" /> ${board.mid}</td>
                        <td>${board.bdate}</td>
                        <td>${board.bview}</td>
                    </tr>
                `;
            });

            boardTableBody.innerHTML = html;

            let pagenation = document.querySelector(".pagination");

            html = ``;
            html += `
                <li class="page-item ${r.page == 1 ? 'disabled': ''}">
                    <a class="page-link" href="javascript:void(0)" onclick="doViewList(${r.page - 1})" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            `;

            for(let i = r.startBtn; i<=r.endtBtn; i++){

                html += `<li class="page-item ${r.page == i ? 'active':''}"><a class="page-link" href="javascript:void(0)" onclick="javascript:doViewList(${i})">${i}</a></li>`;
            }

            html += `
                <li class="page-item page-item ${r.page == r.totalBoardSize ? 'disabled': ''}">
                    <a class="page-link" href="javascript:void(0)" onclick="doViewList(${r.page + 1})" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            `;
            pagenation.innerHTML = html;

            // ----- 부가 출력 -------
            document.querySelector('.totalPage').innerHTML = r.totalPage;
            document.querySelector('.totalBoardSize').innerHTML = r.totalBoardSize;
        }
    });
}

function onPageBoardSize(object){
    console.log(object);
    console.log(object.value);
    pageObject.pageBoardSize = object.value;
    doViewList(pageObject.page);
}

function onBcno(object,bcno){
    console.log("onBcno(bcno)");
    console.log(bcno);
    console.log(object)
    pageObject.bcno = bcno;
    document.querySelector(".categoryWrap .active").className = "bcBtn";
    object.classList += " active";
    pageObject.key = 'b.title';
    pageObject.keyword = '';
    document.querySelector(".searchKey option:first-child").selected = true;
    document.querySelector(".searchKeyword").value = '';
    doViewList(1)
}

function onSearch(){
    console.log("onSearch()");
    let searchKey = document.querySelector(".searchKey").value;
    let searchKeyword = document.querySelector(".searchKeyword").value;
    console.log(searchKey);
    console.log(searchKeyword);
    pageObject.key = searchKey;
    pageObject.keyword = searchKeyword;
    doViewList(1);
}

function onBoardView(bno){
    $.ajax({
        url : "/board/viewPlus",
        method : "get",
        data : {"bno":bno},
        // data : {"page" : pageObject.page, "pageBoardSize" : pageObject.pageBoardSize },
        success : (r)=>{
            if(r){
                location.href='/board/view?bno='+bno;
            } else {
                alert('오류:관리자에게 문의하시기 바랍니다.')
            }
        }
    });
}