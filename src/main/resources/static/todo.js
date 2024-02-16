console.log("todo.js 실행");
// JS함수 정의 : function 함수명(매개변수){실행문;}
// {key : value,key : value} : 객체 vs : 해쉬맵
doGet();
// 1.
function doPost(){
    console.log('doPost()');
    // 1. HTML입력받은 값 가져오기
    let content = document.querySelector("#content").value;
    let deadline = document.querySelector("#deadline").value;

    // 2. 객체화
    let info = {
        content : content,
        deadline : deadline
    };
    console.log(info)

    // 3. 컨트롤에게 요청/응답
        // HTTP 통신 : 어디에(action/url), 어떻게(method/method), 보낼데이터(name/data), 응답데이터(x/success)
    $.ajax({
        url : "/todo/post.do",
        method : "post",
        data : info,
        success : function(result){
            console.log(result);
            if(result == true){
                // 화면 갱신
                doGet();
            }
        }
    });
    // 4. 출룍
}

// 2.
function doGet(){
    // - 스프링(자바)와 통신 (주고받기)
    // - JQUERY AJAX
        // $.ajax(JSON 형식의 통신정보)
        //$.ajax({})
        /*

            HTTP method : post,get,put,delete 등등

            $.ajax({
                url : spring controller url / 통신 대상 식별
                method : 'HTTP method / 통신 방법',
                data : 'HTTP request value / 통신 요청으로 보낼 데이터'
                success : HTTP response function / 통신 응답 함수
            })
        */
    $.ajax({
        url : "/todo/get.do",
        method : "get",
        success : function result(resultValue){
            console.log(resultValue);
            // 통신 응답 결과를 HTML형식으로 출력해주기.
            // 1. 어디에
            let tbody = document.querySelector('table tbody');
            // 2. 무엇을
            let html = '';
            for(let i=0; i<resultValue.length;i++){
                html += `
                    <tr>
                        <td>${resultValue[i].id}</td>
                        <td><a href="/show.html?id=${resultValue[i].id}">${resultValue[i].content}</a></td>
                        <td>${resultValue[i].deadline}</td>
                        <td>${resultValue[i].state}</td>
                    </tr>
                `;
            }
            // 3. 대입
            tbody.innerHTML = html;
        }
    });
}



// 3.
function doPut(){}

// 4.
function doDelete(){}