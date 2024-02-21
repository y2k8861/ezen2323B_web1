package example.day08._2인과제_2;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Component
public class Dao {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public Dao() {
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

    public Boolean input(Dto dto) {
        try {
            String sql = "insert into item(name, count, price) values(?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, dto.getName());
            ps.setInt(2, dto.getCount());
            ps.setInt(3, dto.getPrice());

            int count = ps.executeUpdate();
            if (count == 1) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("e = " + e);
        }

        return false;
    }

    public ArrayList<Dto> pageView() {
        ArrayList<Dto> result = new ArrayList<>();
        try {
            String sql = "select * from item order by no";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add(new Dto(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getInt(4)
                ));
            }

        } catch (Exception e) {
            System.out.println("e = " + e);
        }

        return result;
    }

    public boolean itemDelete(Dto dto) {
        try {
            String sql = "delete from item where no = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, dto.getNo());
            int count = ps.executeUpdate();
            if (count == 1) {
                return true;
            }

        } catch (Exception e) {
            System.out.println("e = " + e);
        }
        return false;
    }

    public boolean itemUpdate(Dto dto){
        try{
            String sql="";
            if(dto.getCount() == -1){
                sql="update item set price=? where no=?";

                ps=conn.prepareStatement(sql);
                ps.setInt(1,dto.getPrice());
                ps.setInt(2, dto.getNo());
            }
            else{
                sql="update item set count=? where no=?";

                ps=conn.prepareStatement(sql);
                ps.setInt(1,dto.getCount());
                ps.setInt(2, dto.getNo());
            }
            int count=ps.executeUpdate();

            if(count==1){
                return true;
            }
        }
        catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

}//c end
