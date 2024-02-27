package ezenweb.controller;

import ezenweb.Service.FileService;
import ezenweb.Service.MemberService;
import ezenweb.model.dao.MemberDao;
import ezenweb.model.dto.LoginDto;
import ezenweb.model.dto.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class MemberController {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MemberService memberService;

    // 1단계. V <----> C 사이의 HTTP 통신방식 설계
    // 2단계. Controller Mapping 함수 선언하고 통신체크 (API Tester)
    // 3단계. Controller request 매개변수,매핑
        // -------------- Dto, Survice -------------- //
    // 4단계 응답 : 1.text/html VS 2. Appplication/JSON

    // 1. 회원가입 처리 요청
    @PostMapping("/member/signup")
    @ResponseBody // 응답방식
    public boolean doPostSignup(MemberDto memberDto){
        return memberService.doPostSignUp(memberDto);
    }

    // 2. 로그인 처리요청
    @PostMapping("/member/login")
    @ResponseBody // 응답방식
    public boolean doPostLogin(LoginDto loginDto){
        /*
            params
            {id="아이디",pw="비밀번호"}
        */
        System.out.println("MemberController.login");
        System.out.println("loginDto = " + loginDto);
        // --
        boolean result = memberDao.doPostLogin(loginDto); // Dao처리
//        boolean result = true; // Dao처리
        // 로그인 성공시(세션)
            // 세션 저장소 : 톰캣서버에 브라우저 마다의 메모리 할당
            // 세션 객체 타입 : Object(여러가지의 타입들을 저장할려고)
            // 1. Http 요청 객체 호출.    HttpServlertRequest
            // 2. Http 세션 객체 호출     .getSesstion()
            // 3. Http 세션 데이터 저장    .setAttribute("세션명",데이터);     -- 자동형 변환(자식 -> 부모)
            // -  Http 세션 데이터 호출    .getAttribute("세션명");           -- 강제형 변환(부모 -> 자식) / 캐스팅
            // -  Http 세션 데이터 초기화   .invalidate
        if(result){
            request.getSession().setAttribute("loginDto",loginDto.getId());    // loginDto : 3
        }
        return result; // Dao 요청후 응답 결과를 보내기
    }

    // 2-2 로그인 여부 확인 요청
    @GetMapping("/member/login/check")
    @ResponseBody
    public String doGetLoginCheck(){
        // * 로그인 여부 확인 = 세션이 있다 없다 확인
            // 1. http 요청 객체 호출, 2.http세션 객체 호출 3.http세션 데이터 호출
        String loginDto = "";
        Object sesstionObj = request.getSession().getAttribute("loginDto");
        if(sesstionObj != null){
            loginDto = (String)sesstionObj;
        }
        return loginDto;
    }


    // 2-3 로그아웃 / 세션 초기화
    @GetMapping("/member/logout")
    @ResponseBody
    public boolean doGetLogout(){
        // 로그인 관련 세션 초기화
            // 1. 모든 세션 초기화
            request.getSession().invalidate();  // 현재 요청 보낸 브라우저의 모든 세션 초기화
            // 2. 특정 세션 초기화 => 동일한 세션속성명에 null 대입한다.
            // request.getSession().setAttribute("loginDto",null);
        int loginDto = 0;
        return true;
        // 로그아웃 성공시 => 메인페이지 또는 로그인 페이지 이동
    }

    // 3. 회원가입 페이지 요청
    @GetMapping("/member/signup")
    public String viewSignup(){
        System.out.println("MemberController.viewSignup");
        return "ezenweb/signup";
    }

    // 4. 로그인 페이지 요청
    @GetMapping("/member/login")
    public String viewLogin(){
        System.out.println("MemberController.viewLogin");
        return "ezenweb/login";
    }

    // 5. 회원수정 페이지 요청
    @PostMapping("/member/update")
    @ResponseBody // 응답방식
    public boolean doPostUpdate(MemberDto memberDto){
        System.out.println("MemberController.doPostUpdate");
        System.out.println("memberDto = " + memberDto);
        // --
        boolean result = true; // Dao처리
        return result; // Dao 요청후 응답 결과를 보내기
    }

    // 6. 회원수정 페이지 요청
    @GetMapping("/member/update")
    public String viewUpdae(){
        System.out.println("MemberController.viewUpdae");
        return "ezenweb/update";
    }

    // 7. 회원삭제 요청
    @PostMapping("/member/delete")
    @ResponseBody // 응답방식
    public boolean doPostDelete(MemberDto memberDto){
        System.out.println("MemberController.doPostDelete");
        System.out.println("memberDto = " + memberDto);
        // --
        boolean result = true; // Dao처리
        return result; // Dao 요청후 응답 결과를 보내기
    }
}
