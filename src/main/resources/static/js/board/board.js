
// 1. 전체 출력용 : 함수 : 매개변수 x, 반환 x , 언제 실행 할껀지 : 페이지 열릴때 (js)
doViewList(1);
function doViewList(page){
    console.log("doViewList");

    $.ajax({
        url : "/board/do",
        method : "get",
        data : {"page" : page },
        success : (r)=>{
            console.log(r)
            console.log(r.totalPage)
            console.log(r.startBtn)
            console.log(r.endtBtn)
            let boardTableBody = document.querySelector("#boardTable tbody");

            let html = ``;
            r.list.forEach(board => {
                html += `
                    <tr>
                        <th scope="row">${board.bno}</th>
                        <td>${board.btitle}</td>
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
                <li class="page-item page-item ${r.page == r.totalPage ? 'disabled': ''}">
                    <a class="page-link" href="javascript:void(0)" onclick="doViewList(${r.page + 1})" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            `;
            pagenation.innerHTML = html;
        }
    });
}