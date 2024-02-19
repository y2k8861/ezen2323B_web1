package ezenweb.controller;

import ezenweb.model.dao.MemberDao;
import ezenweb.model.dto.LoginDto;
import ezenweb.model.dto.MemberDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {

    @Autowired
    MemberDao memberDao;

    // 1단계. V <----> C 사이의 HTTP 통신방식 설계
    // 2단계. Controller Mapping 함수 선언하고 통신체크 (API Tester)
    // 3단계. Controller request 매개변수,매핑
        // -------------- Dto, Survice -------------- //
    // 4단계 응답 : 1.text/html VS 2. Appplication/JSON

    // 1. 회원가입 처리 요청
    @PostMapping("/member/signup")
    @ResponseBody // 응답방식
    public boolean doPostSignup(MemberDto memberDto){
        /*
            params
            {id="아이디",pw="비밀번호",name="이름",email="이메일",phone="전화번호",img="프로필사진"}
        */
        System.out.println("MemberController.signup");
        System.out.println("memberDto = " + memberDto);
        // --
        boolean result = memberDao.doPostSignup(memberDto); // Dao처리
        return result; // Dao 요청후 응답 결과를 보내기
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
        return result; // Dao 요청후 응답 결과를 보내기
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
