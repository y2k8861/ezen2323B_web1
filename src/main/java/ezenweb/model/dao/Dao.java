package ezenweb.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class Dao {
    Connection conn;
    PreparedStatement ps;
    ResultSet rs;
    public Dao(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/springweb", "root", "1234");
            System.out.println("DB연동 성공");
        } catch (Exception e){
            System.out.println(e);
            System.out.println("DB연동 실패");
        }
    }
}
