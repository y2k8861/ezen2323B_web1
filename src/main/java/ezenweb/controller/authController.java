package ezenweb.controller;

import ezenweb.Service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@RequestMapping("/auth")
public class authController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    EmailService emailService;

    // 1. email 인증요청
    @GetMapping("/email/req")
    public boolean getEmailReq(@RequestParam String email){
        System.out.println("authController.getEmailReq");

        // 1. 난수 이용한 6자리 인증코드 발급
            // 1. 난수 객체 생성
        Random random = new Random();
            // 2. 6번
        String ecode = "";
        for(int i = 0; i<6; i++){
            // 3. 난수생성해서 변수에 누적 문자연결하기
            ecode += random.nextInt(10);    // 10미만 : 0~9
        }

        System.out.println("ecode = " + ecode);

        // 2. HTTP 세션에 특정시간 만큼 발급된 인증코드 보관
            // 1. 세션에 속성 추가 해서 발급된 인증코드 대입하기.
        request.getSession().setAttribute("ecode",ecode);
            // 2. 세션에 생명주기
        request.getSession().setMaxInactiveInterval(30);

        // 3. 발급된 인증코드를 유저의 이메일로 전송
        emailService.send(email,"000페이지 인증 메일입니다.","인증번호 : "+ ecode);

        return true;
    }

    // 2. email 인증확인
    @GetMapping("/email/check")
    public boolean getEmailCheck(@RequestParam String ecodeinput){
        System.out.println("authController.getEmailCheck");
        System.out.println("ecodeinput = " + ecodeinput);

        // 1. HTTP세션에 보관했던 인증코드를 꺼내서
        Object object = request.getSession().getAttribute("ecode");
        if(object != null) {
            String ecode = (String)object;
            // 2. 입력받은 인증코드와 생선된 인증코드 일치여부
            System.out.println(ecode);
            if(ecode.equals(ecodeinput)){
                return true;
            }
        }

        return false;
    }

}
