package example.day01.consoleMvc;

import java.util.ArrayList;
import java.util.List;

public class TodoController {
    private  TodoDao todoDao = new TodoDao();

    // 2. 할일 등록 함수
    public boolean doPost(TodoDto todoDto){
        return todoDao.doPost(todoDto);
    }

    // 3. 할일 목록 출력 함수
    public ArrayList<TodoDto> doGet(){
        return todoDao.doGet();
    }
}
