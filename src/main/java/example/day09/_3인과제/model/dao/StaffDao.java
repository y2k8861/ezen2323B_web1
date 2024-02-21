package example.day09._3인과제.model.dao;

import example.day09._3인과제.model.dto.ScoreDto;
import example.day09._3인과제.model.dto.StaffDto;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Component
public class StaffDao {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public StaffDao(){
        try {
            // 1. jdbc 라이브러리 호출
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 2. 연동
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springtest","root","1234");
            System.out.println(" ★ DB 연동성공 ★ ");
        }catch (Exception e){
            System.out.println(" ★ DB 연동실패 ★ " + e);
        }
    }

    public boolean create(StaffDto staffDto){
        System.out.println("1. ☆ StaffDao.create ☆");
        System.out.println("1. ☆ [TEST] staffDto ☆ = " + staffDto);
        try {
            String sql = "insert into staff(sname, sphone) values(?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, staffDto.getSname());
            ps.setString(2, staffDto.getSphone());
            int count = ps.executeUpdate();
            if(count == 1){
                return true;
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    public List<StaffDto> read(){
        System.out.println("2. ☆ StaffDao.read ☆");
        List<StaffDto> list = new ArrayList<>();
        try {
            String sql = "select * from staff";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                StaffDto staffDto = new StaffDto(rs.getInt(1),rs.getString(2),rs.getString(3));
                list.add(staffDto);
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return list;
    }

    public boolean update(StaffDto staffDto){
        System.out.println("3. ☆ StaffDao.update ☆");
        System.out.println("3. staffDto = " + staffDto);
        try {
            String sql = "update staff set sphone = ? where sno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, staffDto.getSphone());
            ps.setInt(2, staffDto.getSno());
            int count = ps.executeUpdate();
            if(count == 1){
                return true;
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    public boolean delete(int sno){
        System.out.println("4. ☆ StaffDao.delete ☆");
        System.out.println("4. ☆ sno ☆ = " + sno);
        try {
            String sql = "delete from staff where sno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, sno);
            int count = ps.executeUpdate();
            if(count == 1){
                return true;
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    public boolean scoreCreate(ScoreDto scoreDto){
        System.out.println("5. ☆ StaffDao.scoreCreate ☆");
        System.out.println("5. ☆ scoreDto ☆ = " + scoreDto);
        try {
            String sql = "insert into score(sno, sccontent, scscore) values (?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,scoreDto.getSno());
            ps.setString(2,scoreDto.getSccontent());
            ps.setInt(3,scoreDto.getScscore());
            int count = ps.executeUpdate();
            if(count == 1){
                return true;
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    public List<ScoreDto> scoreRead(int sno){
        System.out.println("6. ☆ StaffDao.scoreRead ☆");
        System.out.println("6. ☆ sno = ☆ " + sno);
        List<ScoreDto> list = new ArrayList<>();
        try {
            String sql = "select * from score where sno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, sno);
            rs = ps.executeQuery();
            while (rs.next()){
                ScoreDto scoreDto = new ScoreDto(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getString(5)
                );
                list.add(scoreDto);
            }
        }catch (Exception e){
            System.out.println("e = " + e);
        }
        return list;
    }
}
