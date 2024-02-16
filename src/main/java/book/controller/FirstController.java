package book.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // * 이 클래스가 컨트롤러임을 선언
public class FirstController {
    @GetMapping("/hi")
    public String niceToMeetYou(Model model){
        // return "머스테치 파일명";
        model.addAttribute("username","홍팍");
        return "greetings";
        // 서버가 알아서 templates 폴더에서 파일을 찾아 브라우저에게 전송
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){
        model.addAttribute("nickname","홍팍");
        return "goodbye";
    }
}

/*
    브라우저 [클라이언트]                                          스프링[서버] localhost:80
    강호동                                                       신동엽
                    강호동이 신동엽에게 도서지급대장문서를 줘.
                    localhost:80/hi
                    ----------------------------------->        서랍 = hi[도서지급대장문서]
    브라우저         신동엽이 강호동에게 도서지급대장문서를 응답
    html렌더링가능    greetings.html                             강호동은 템플릿을 모르니까. 템플릿을 HTML 렌더링하고 보낸다
                    <-----------------------------------





*/
