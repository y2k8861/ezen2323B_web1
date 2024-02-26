package example.day10._3Servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// 자바회사에서 웹개발 위한 HTTP 통신 클래스 : HttpServlet
    // : MVC패턴에서는 주로 controller 역할
    // 서블릿 선언하는 방법
    // 1. 해당 클래스에 HttpServlet 상속받는다.
    // 2. 해당 클래스에 @WebServlet 어노테이션 주입한다.
    // 3. HttpServlet 가 제공하는 메소드를 오버라이딩 : init(), service(), doXXX(), destory()

    /*
        서블릿 싱행 구동 순서

        1. 클라이언트(브러우저) HTTP 요청이(AWS(톰캣서버)) 들어온다.
        2. 서블릿컨테이너에 요청 받은 서블릿이 있는지 없는지 판단
        3. 없으면 init() 메소드 실행한 서블릿 생성
        4. 있으면 또는 생성했으면 Thread (작업스레드) 할당
        5. service() 실행하고 HTTP요청 Method에 따른 메소드로 이동
        6. doXXX() 메소드 실행될때 요청(HttpServletRequest) 객체 생성
            - HTTP 관련된 정보를 요청할 수 있는 기능을 가지고 있다.
        7. doXXX() 메소드 종료될때 응답(HttpServletResponse) 객체 생성
            - HTTP 관련된 정보를 응답할 수 있는 기능을 가지고 있다.
        ------- 다음 요청이 올때 까지
     */


// 자바회사에서 웹개발 위한 HTTP 통신 클래스 : HttpServlet
@WebServlet("/hello-servlet")   // web.xml 해당 서블릿 등록
public class HelloServlet extends HttpServlet {
    // HttpServlet 클래스로부터 상속 받으면 다양한 HTTP 관련 메소드 사용

    @Override   // 1. [최초 요청 1번 실행]해당 서블릿 객체(1개)가 생성되었을때 실행되는 메소드
    public void init() throws ServletException {
        System.out.println("HelloServlet.init");
        super.init();
    }

    @Override   // 2. [무조건 실행]해당 서블릿으로부터 HTTP 서비스가 실행 되었을때 실행되는 메소드
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HelloServlet.service");
        super.service(req, resp);
    }

    @Override   // 3. HTTP 서비스 요청중에 HTTP method 방식이 get이면 실행되는 메소드
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HelloServlet.doGet");
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("HelloServlet.doPost");
        super.doPost(req, resp);
    }

    @Override   // 4. 해당 서블릿 객체가 삭제 되었을때 실행되는 메소드
    public void destroy() {
        System.out.println("HelloServlet.destroy");
        super.destroy();
    }
}
