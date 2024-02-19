package ezenweb.model.dao;

import ezenweb.model.dto.LoginDto;
import ezenweb.model.dto.MemberDto;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class MemberDao {
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    public MemberDao(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/ezenweb", "root", "1234");
            System.out.println("DB연동 성공");
        } catch (Exception e){
            System.out.println(e);
            System.out.println("DB연동 실패");
        }
    }

    public boolean doPostSignup(MemberDto memberDto){
        boolean result = false;
        try {
            String sql = "insert into member(id,pw,name,email,phone,img) values(?,?,?,?,?,?)";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, memberDto.getId());
            ps.setString(2, memberDto.getPw());
            ps.setString(3, memberDto.getName());
            ps.setString(4, memberDto.getEmail());
            ps.setString(5, memberDto.getPhone());
            ps.setString(6, memberDto.getImg());
            int count = ps.executeUpdate();
            if(count == 1){
//                rs = ps.getResultSet();
//                if(rs.next()){
//                    result = true;
//                }
                result = true;
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return result;
    }

    public boolean doPostLogin(LoginDto loginDto){
        boolean result = false;
        try {
            String sql = "Select * from member where id = ? and pw = ?";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, loginDto.getId());
            ps.setString(2, loginDto.getPw());
            rs = ps.executeQuery();
            if(rs.next()){
                result = true;
            }
        } catch (Exception e){
            System.out.println(e);
        }

        return result;
    }
}
