package book.dao;

import book.dto.ArticleForm;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class ArticleDao {
    // ------------ JDBC DB연동 ------------ //
    // 1. DB연동 필요한 인터페이스(구현객체 => 각 회사(MYSQL com.mysql.cj.jdbc패키지내.Driver 클래스)) 필드 선언
    private Connection connection;  // DB연동 결과 객체를 연결. 기재된 SQL Statement 객체 반환
    private PreparedStatement ps;   // 기재된 SQL에 매개변수 할당 , SQL 실행
    private ResultSet rs;           // select 결과 여러개 레코드를 호출

    public ArticleDao(){
        try {
            // 1.MYSQL 회사의 JDBC관련된 객체를 JVM에 로딩한다
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.연동된 결과의 객체를 connetction 인터페이스에 대입한다
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/springweb", "root", "1234");
                    }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public ArticleForm createArticle(ArticleForm form){
        System.out.println("ArticleDao.createArticle");
        System.out.println("form = " + form);
        ArticleForm saved = new ArticleForm();
        try { // 0. try{}catch(){}
            // 1.
            String sql = "insert into article(title,content) Values(?,?)";

            // 2.
            ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // 3.
            ps.setString(1, form.getTitle());
            ps.setString(2, form.getContent());

            // 4.
            int count = ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if(rs.next()){
                System.out.println("방금 자동으로 생선된 pk(id) : " + rs.getLong(1));
                saved.setId(rs.getLong(1));
                saved.setTitle(form.getTitle());
                saved.setContent(form.getContent());
                return saved;
            }

            // 5.
            // if(count == 1) return true;
        } catch (Exception e){
            System.out.println(e);
        }

        return saved;
    }

    // ---------------------------------- //
    // 2. 개별 글 조회 : 매개변수 : 조회할게시번호(ID) 반환:조회한게시물정보 1개(DTO)
    public ArticleForm show (Long id){
        try {
            String sql="select * from article where id = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                ArticleForm form = new ArticleForm(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3)
                );
                return form;
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return new ArticleForm();
    }

    // ---------------------------------- //
    // 2. 개별 글 조회 : 매개변수X, 리턴타입 : Arraylist
    public List<ArticleForm> index(){
        List<ArticleForm> list = new ArrayList<>();
        try {
            String sql = "select * from article order by id desc";
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                // 1. 객체만들기
                ArticleForm articleForm = new ArticleForm(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3)
                );

                // 2. 객체를 리스트에 넣기
                list.add(articleForm);
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return list;
    }

    public ArticleForm findById(long id){
        ArticleForm articleForm = new ArticleForm();
        try {
            String sql = "select * from article where id = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1,id);
            rs = ps.executeQuery();
            if(rs.next()){
                articleForm.setId(rs.getLong(1));
                articleForm.setTitle(rs.getString(2));
                articleForm.setContent(rs.getString(3));
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return articleForm;
    }

    public boolean update(ArticleForm form){
        boolean result = false;
        try {
            String sql = "update article set title=?,content=? where id=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1,form.getTitle());
            ps.setString(2,form.getContent());
            ps.setLong(3,form.getId());
            int count = ps.executeUpdate();
            if(count == 1) result = true;
        } catch (Exception e){
            System.out.println(e);
        }
        return result;
    }

    public boolean delete(long id){
        try {
            String sql = "delete from article where id = ?";
            ps = connection.prepareStatement(sql);
            ps.setLong(1,id);
            int count = ps.executeUpdate();
            if (count == 1) return true;
        } catch (Exception e){

        }
        return false;
    }
}
