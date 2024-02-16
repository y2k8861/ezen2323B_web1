package example.day01.consoleMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainView {

    private TodoController todoController = new TodoController();
    Scanner scanner = new Scanner(System.in);
    public void home(){
        while (true){
            doGet();    // 할일목록 출력
            System.out.println("1. 할일등록 : ");
            int ch = scanner.nextInt();
            if(ch == 1){doPost();}   // 할일등록
        }
    }

    // 2. 할일 등록 함수
    public void doPost(){
        // 1. 입력받기
        System.out.print("할일 내용 : "); String content = scanner.next();
        System.out.print("마가임[yyyy-mm-dd] : "); String deadline = scanner.next();

        // 2. 객체
        TodoDto todoDto = new TodoDto();
        todoDto.setContent(content);
        todoDto.setDeadline(deadline);

        // 3. 컨트롤러에게 보내기
        boolean result = todoController.doPost(todoDto);

        System.out.println(result);
    }

    // 3. 할일 목록 출력 함수
    public void doGet(){
        // 1. 입력받기 - 전체 출력이라서 조건이 없음
        // 2. 객체화X
        // 3. 컨트롤러에게 요청 응답 받기
        ArrayList<TodoDto> result = todoController.doGet();
        for(int i = 0; i< result.size(); i++){
            // i번째 DTO 호출
            TodoDto todoDto = result.get(i);
            System.out.printf("%-2s %-10s %-5s %-30s \n",todoDto.getId(),todoDto.getDeadline(),todoDto.isState(), todoDto.getContent());
        }
    }
}
