goShow()
// 2-2
function goShow(){
    let id = Request("id");
    $.ajax({
            url : "/todo/get1.do",

            method : "get",
            data : {id:id},
            success : function result(resultValue){
                console.log(resultValue);
                // 통신 응답 결과를 HTML형식으로 출력해주기.
                // 1. 어디에
                let tbody = document.querySelector('table tbody');
                // 2. 무엇을
                let html = '';
                html += `
                    <tr>
                        <td>${resultValue.id}</td>
                        <td>${resultValue.content}</td>
                        <td>${resultValue.deadline}</td>
                        <td>${resultValue.state}</td>
                    </tr>
                `;
                // 3. 대입
                tbody.innerHTML = html;
            }
        });
}

function Request(id){
    let result = {};
    let url = unescape(location.href);
    var paramArr = (url.substring(url.indexOf("?")+1,url.length)).split("&");
    for(var i = 0 ; i < paramArr.length ; i++){
        var temp = paramArr[i].split("=");
        let key = temp[0]
        result[key] = temp[1];
    }
    return result[id];
 }