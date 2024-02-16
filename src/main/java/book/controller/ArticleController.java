package book.controller;

import book.dao.ArticleDao;
import book.dto.ArticleForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
// 1. 스프링 컨테이너(메모리 저장소)에 빈(객체/힙) 등록
// 2. 스프링이 해당 클래스 사용할 수 있다.
// 3. 모든 클라이언트 요청은 컨트롤러로 들어온다.
public class ArticleController {

    // 1. 글쓰기 페이지 반환
    @GetMapping("/articles/new") // HTTP 요청 경로 : GET 방식 : localhost:80/articles/new
    public String newArticleForm(){
        return "articles/new"; //.확장자 빼고 resources/templates 부터 경로 작성
    }

    // 2. 글쓰기 처리
        // 1. <form action="/articles/create" method="post">
        // 2. 입력태그 속성의 name과 DTO필드의 필드명 일치하면 자동 연결/대입한다.
        // 3. public 생성자 필요하다.

    @Autowired
    ArticleDao articleDao;  // 스프링 컨테이너에 등록된 빈 주입한다.
    @PostMapping("/articles/create") // HTTP 요청 경로 : GET 방식 : localhost:80/articles/create
    public String createArticle(ArticleForm form){
        // soutm : 메소드명 출력
        System.out.println("ArticleController.createArticle");

        // soutp : 매게변수 출력\
        System.out.println("form = " + form);

        // (디버그) 로그
        log.debug(form.toString());

        // (경고) 로그
        log.warn(form.toString());

        // (에러) 로그
        log.error(form.toString());

        // (정보) 로그
        log.info(form.toString());

        ArticleForm result = articleDao.createArticle(form);
        return "redirect:/articles/"+result;
    }

    //p.156 조회
        // 1. 클라이언트가 서버(spring) 요청시 id 전달/식별키/pk전달
        // 2. HTTP URL 이용한 요청 : /article/1, /article/2, /article/3
        //    정해진 값이 아닌 매개변수일경우에는 : /artice/{매개변수명}/{매개변수명}/{매개변수명}  
        //    요청 : /article/1또는 2또는 3    
        // 3. 서버(spring) Controller 요청 URL 매핑/연결받기
        // 4. @GetMapping("/article/{매개변수}")
        // 5. 함수 매개변수에서 URL상의 매개변수와 이름 일치
        // 6. 함수 매개변수 앞에 @PathVariable 어노테이션 주입
                // @PathVariable : url 요청으로 들어온 전달값을 컨트롤러함수의 매개변수로 가져오는어노테이션
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        System.out.println("id = " + id);
        // p.159 1. 요청된 id를 가지고 DAO에게 데이터 요청 [JPA 대신 DAO]
        ArticleForm form = articleDao.show(id);
        System.out.println("form = " + form);
        // p.160 2. DAO에게 전달받은 값을 뷰템플릿에게 전달하기// model.addAttribute("키","값");
            // model : 머스테치(뷰 템플릿)에서 사용할 데이터 전달 객체
        model.addAttribute("article",form);
        model.addAttribute("name","유재석");
            // {{model 속성명}}
            // {{>파일경로}}
        // p.161 3. 해당 함수가 종료될떄 리턴 1.화면뷰(머스테치,HTML) 2.값(JSON)
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        // 1. DAO에게 요청해서데이터 가져오기
        List<ArticleForm> list = articleDao.index();
        // 2. 뷰템플릿(머스테치)에게 전달할 값을 model에 담아준다.
        model.addAttribute("articleList",list);
        // 3. 뷰 페이지 설정하기
        return "articles/index";
    }

    // p.202 수정 1단계 : 기존데이터 불러오기
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable long id, Model model){
        model.addAttribute("article",articleDao.findById(id));
        return "articles/edit";
    }
    // @PathVariable : 1.요청한 HTTP URL 경로상의 매개변수 대입 2.타입변환
        // URL : /articles/{매개변수명}/edit
        // JAVA함수(@PathVariable("URL매개변수명") 타입매개변수명)
        //  URL 매개변수명 생략시 함수의 매개변수 명과 일치할 경우 자동 대입

    // p.214 수정 2단계 : 수정데이터 받기
    @PostMapping("/articles/update")
    public String update(ArticleForm form){
        // * form 입력 데이터를 DTO 매개변수로 받을떄
            // 1. form 입력상자의 name과 DTO의 필드명 통일
            // 2. DTO의 필드 닶을 저장할 생성자 필요
        System.out.println("form = " + form);
        boolean result = articleDao.update(form);
        return "redirect:/articles/"+form.getId();
    }

    // p.234 : 삭제요청
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable long id){
        // 1. 삭제할 대상
        ArticleForm target = articleDao.findById(id);

        // 2. Dao 삭제 요청하고 응답받기
        boolean result = articleDao.delete(id);;

        // 3. 결과페이지로 리다이렉트하기.
        return "redirect:/articles";
    }
}

/*
    @어노테이션
        1. 표준 어노테이션 : 자바에서 기본적으로 제공
            @Override : 메소드 재정의
            등등
        2. 메타 어노테이션 : p.64
            - 소스코드에 추가해서 사용하는 메타 데이터
            - 메타 데이터 : 구조화된 데이터
            - 컴파일 또는 실행 했을 때 코드를 어떻게 처리 해야 할지 알려주는 정보
            @SpringBootApplcation
                - 1. 내장 서버(톰캣) 지원
                - 2. 스프링 MVC 내장
                    view : resources
                    controller : @Controller,@RestController
                        Service : @Service
                    model(dao) : @Repository
                        entity(db table) : @Entrity
                        그 외 별도 : @Component
                        설정 클래스 : @Configuration
                - 3. 컴포넌트(modul) 스캔 : MVC 클래스 스캔
                    @Controller @RestController @Service @
            @Controller
            @GetMapping

            - 다른 클래스의 함수를 호출하는 방법
            [MVC패턴은 클래스들이 분업하기 떄문에 서로 다른 클래스들끼리 데이터 주고(매개변수)받는다(리턴값. *상호작용]
            1. 해당 함수가 인스턴스(static없으면)멤버이면
                클래스명 변수명 = new 클래스명()
                변수명.함수()
            2. 해당 함수가 인스턴스(static없으면)멤버이면
                new 클래스명().함수()
            3. 해당 함수가 정적(static) 멤버이면
                클래스명.함수()
            4. 해당 클래스가 싱글톤(프로그램내 무조건 하나의 객체만 갖는 패턴)
                클래스명.getInstance().함수()
            5. @Autowired
            ArticleDao articleDao;
*/
