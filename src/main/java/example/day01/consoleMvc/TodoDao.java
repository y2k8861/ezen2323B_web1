package example.day01.consoleMvc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// 데이터 접근 객체 : db에 접근과 SQL(비지니스 로직)역할
public class TodoDao {
    // 1. 필드
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    // 2. 생성자 : db연동 코드
    public TodoDao(){
        try {
            // 1. jdbc 라이브러리 호출
            Class.forName("com.mysql.cj.jdbc.Driver");

            //2. 연동
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/springweb",
                    "root","1234"
            );
            System.out.println("DB success");
        } catch (Exception e){
            System.out.println("DB fali" + e);
        }
    }

    // 3. 메소드

    // 2. 할일 등록 함수
    public boolean doPost(TodoDto todoDto){
        try {
            // 1. SQL 작성
            String sql = "insert into todo(content,deadline) values(?,?)";

            // 2. SQL 기재
            ps = conn.prepareStatement(sql);

            // 3. SQL 매개변수 정의
            ps.setString(1,todoDto.getContent());
            ps.setString(2, todoDto.getDeadline());

            // 4. SQL 실행
            int count = ps.executeUpdate();

            // 5. SQL 실행 결과
            if(count == 1){return true;}

            // 6. 함수 리턴
        } catch (Exception e){
            System.out.println(e);
        }
        return false;
    }

    // 3. 할일 목록 출력 함수
    public ArrayList<TodoDto> doGet(){
        try{
            // 1. SQL 작성
            String sql = "select * from todo";

            // 2. SQL 기재
            ps = conn.prepareStatement(sql);

            // 3. SQL 매개변수 정의

            // 4. SQL 실행
            rs = ps.executeQuery();

            // 5. SQL 실행 결과
            ArrayList<TodoDto> result = new ArrayList<>();
            while (rs.next()){
                TodoDto todoDto = new TodoDto(
                        rs.getInt("id"),
                        rs.getString("content"),
                        rs.getString("deadline"),
                        rs.getBoolean("state")
                );
                result.add(todoDto);
            }

            // 6. 함수 리턴
            return result;
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
}
