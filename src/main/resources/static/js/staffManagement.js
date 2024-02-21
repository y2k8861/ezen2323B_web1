staffList();

//=============== 사원등록 ================
function inputStaff(){
    console.log("inputStaff()실행");

    let sname=document.querySelector('#sname').value;     console.log(sname);
    let sphone=document.querySelector('#sphone').value;     console.log(sphone);

    let info={sname:sname, sphone:sphone};
    console.log(info);

    $.ajax({
        url : '/staff/create',
        method : 'POST',
        data : info,
        success : function (result){
            console.log(result);
            if(result){
                alert('등록 성공');
                staffList();
            }
            else{
                alert('등록 실패');
            }
        }
    })
};
//=============== 사원등록 end ================

//=============== 사원출력 ================
function staffList() {
    console.log("staffList()실행");

    $.ajax({
        url : '/staff/read',
        method : 'Get',
        success : function (result){
            console.log(result);

            let html="";
            let staffList=document.querySelector("#staffList");

            for(let i=0; i<result.length; i++){
                html+=` <tr>
                            <td>${result[i].sno}</td>
                            <td>${result[i].sname}</td>
                            <td>${result[i].sphone}</td>
                            <td>
                                <button type="button" onclick="updateStaff(${result[i].sno})">수정</button>
                                <button type="button" onclick="deleteStaff(${result[i].sno})">삭제</button>
                                <button type="button" onclick="scoreView(${result[i].sno})">점수보기</button>
                            </td>
                        </tr>`
            }//for end

            staffList.innerHTML=html;
        }
    })
};

//=============== 사원출력 end ================

//=============== 사원수정 ================
function updateStaff(sno) {  
    console.log("updateStaff()실행");
    
    let sphone=prompt("수정할 전화번호를 입력 해 주십시오.");

    let info={
        sno : sno,
        sphone : sphone
    }

    $.ajax({
        url : '/staff/update',
        method : 'Post',
        data : info,
        success : function (result){
            if(result){
                alert("수정 성공했습니다.");
                staffList();
            }
            else{
                alert("수정 실패했습니다.");
            }
            
        }
    })
}
//=============== 사원수정 end ================

//=============== 사원삭제 ================
function deleteStaff(sno){
    console.log("deleteStaff()실행");
    
    $.ajax({
        url : `/staff/delete/${sno}`,
        method : 'Get',
        success : function (result){
            if(result){
                alert("삭제 성공했습니다.");
                $("#scoreView").css({
                    "margin-left":"10px",
                    "display":"none"
                });
                staffList();
            }
            else{
                alert("삭제 실패했습니다.");
            }
            
        }
    })
}
//=============== 사원삭제 end ================

//=============== 사원 점수 등록 ================
function scoreInput(sno){
    console.log("scoreInput()실행");

    let sccontent=document.querySelector('#sccontent').value;   console.log(sccontent);
    let scscore=document.querySelector('#scscore').value;       console.log(scscore);

    let info={sno:sno, sccontent:sccontent, scscore:scscore};
    console.log(info);
    $.ajax({
        url : `/score/create`,
        method : 'Post',
        data : info,
        success : function (result){
            if(result){
                alert("등록 성공했습니다.");
                scoreList(sno);
            }
            else{
                alert("등록 실패했습니다.");
            }
            
        }
    })
}
//=============== 사원 점수 등록 end ================

//=============== 사원 점수 출력 ================
function scoreList(sno){
    $.ajax({
        url : `/score/read/${sno}`,
        method : 'Get',
        success : function (result){
            let html="";
            let scoreList=document.querySelector("#scoreList");
            
            for(let i=0; i<result.length; i++){
                html+=`<tr>
                            <td>${result[i].scno}</td>
                            <td>${result[i].sno}</td>
                            <td>${result[i].sccontent}</td>
                            <td>${result[i].scscore}</td>
                            <td>${result[i].scdate}</td>
                        </tr>`;
            }

            scoreList.innerHTML=html;
            
        }
    })
}
//=============== 사원 점수 출력 end ================

function scoreView(sno){
    console.log("scoreView()실행");
    $("#scoreView").css({
        "margin-left":"10px",
        "display":"block"
    });

    $("#scoreBtn").attr("onclick",`scoreInput(${sno})`);
    document.querySelector("#scoreH1").innerHTML = `${sno}번 사원 점수 페이지`;
    scoreList(sno);
}