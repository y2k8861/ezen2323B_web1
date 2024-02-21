output();   //JS 열릴때
//1. 저장메소드 : 실행조건 : 등록버튼 클릭시 매개변수x, 리턴x
function input(){
    let name=document.querySelector('#name').value;     console.log(name);
    let count=document.querySelector('#count').value;     console.log(count);
    let price=document.querySelector('#price').value;     console.log(price);

    let info={name:name, count:count, price:price};
    console.log(info);

    $.ajax({
        url : '/item/input',
        method : 'POST',
        data : info,
        success : function (result){
            console.log(result);
            if(result){
                alert('등록 성공');
                output();
            }
            else{
                alert('등록 실패');
            }
        }
    })
};

//2. 전체 호출 메소드 : 실행조건 : 페이지 열릴때, 변화(저장, 수정, 삭제) 가 있을때(새로고침) 매개변수x, 리턴x
function output(){
    $.ajax({
        url : '/item',
        method : 'Get',
        success : function (result){
            console.log(result);
            let tbody=document.querySelector('#tbody');
            let html="";
            for(let i=0; i<result.length; i++){
                let no=result[i].no
                html+=`<tr>
                        <td>${no}</td>
                        <td>${result[i].name}</td>
                        <td>${result[i].count}</td>
                        <td>${result[i].price}</td>
                            <td><input name="input" id="input${no}" /></td>
                        <td>
                            <button type="button" onclick="countUpdate(${no});">수량 수정</button>
                            <button type="button" onclick="priceUpdate(${no});">가격 수정</button>
                        </td>
                        <td><button type="button" onclick="itemDelete(${no});">삭제</button></td>
                    </tr>`
            }

            tbody.innerHTML=html;
        }
    })
};

//4. 삭제메소드 : 실행조건 : 삭제버튼 클릭시
function itemDelete(e){
    let info={no:e};
    console.log(info);

    $.ajax({
        url : '/item/delete',
        method : 'POST',
        data : info,
        success : function (result){
            console.log(result);
            if(result){
                alert('삭제 성공');
                output();
            }
            else{
                alert('삭제 실패');
            }
        }
    })

}

//3. 수정메소드 : 실행조건 : 수정버튼 클릭시
function countUpdate(no){
let input=document.querySelector('#input'+no).value;

let info={no:no, count:input};
    console.log(info);

    $.ajax({
        url : '/item/itemUpdate',
        method : 'POST',
        data : info,
        success : function (result){
            console.log(result);
            if(result){
                alert('개수 수정 성공');
                output();
            }
            else{
                alert('개수 수정 실패');
            }
        }
    })
}

function priceUpdate(no){
let input=document.querySelector('#input'+no).value;

let info={no:no, price:input,count:-1};
    console.log(info);

    $.ajax({
        url : '/item/itemUpdate',
        method : 'POST',
        data : info,
        success : function (result){
            console.log(result);
            if(result){
                alert('가격 수정 성공');
                output();
            }
            else{
                alert('가격 수정 실패');
            }
        }
    })
}