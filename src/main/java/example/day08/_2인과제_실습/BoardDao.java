package example.day08._2인과제_실습;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


@Component
public class BoardDao {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public BoardDao(){
        try {
            //1. mysql JDBC 호출 단 라이브러리 다운로드 > runtimeOnly 'com.mysql:mysql-connector-j'	//mysql 라이브러리
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2. 해당 db서버의 주소와 db연동
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springtest", "root", "1234");
            System.out.println("db연동 성공");
        } catch (Exception e) {
            System.out.println("e = " + e);
        }
    }

    // 1. 저장
    public boolean create(BoardDto boardDto){
        System.out.println("BoardDao.create");
        System.out.println("boardDto = " + boardDto);
        try {
            String sql = "insert into board(content, writer, pw) values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1,boardDto.getContent());
            ps.setString(2,boardDto.getWriter());
            ps.setString(3,boardDto.getPw());
            int count = ps.executeUpdate();
            if(count == 1) return true;
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    // 2. 전체 호출
    public ArrayList<BoardDto> read(){
        System.out.println("BoardDao.read");
        ArrayList<BoardDto> result = new ArrayList<>();
        try {
            String sql = "select * from board";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                result.add(new BoardDto(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4)
                ));
            }
        } catch (Exception e){
            System.out.println("e = " + e);
        }

        return result;
    }

    // 3. 수정
    public boolean update(BoardDto boardDto){
        System.out.println("BoardDao.update");
        System.out.println("boardDto = " + boardDto);
        try {
            String sql = "update board set content = ? where no = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, boardDto.getContent());
            ps.setInt(2, boardDto.getNo());
            int count = ps.executeUpdate();
            if(count == 1) return true;
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    // 4. 삭제
    public boolean delete(int no){
        System.out.println("BoardDao.delete");
        System.out.println("no = " + no);
        try {
            String sql = "delete from board where no = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, no);
            int count = ps.executeUpdate();
            if(count == 1) return true;
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }


    public boolean doPw(int no, String pw){
        System.out.println("BoardDao.doPw");
        try {
            String sql = "select * from board where no = ? and pw = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,no);
            ps.setString(2,pw);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (Exception e){
            System.out.println("e = " + e);
        }

        return false;
    }
}
