package ezenweb.model.dao;

import example.day04.리스트컬렉션1.Board;
import ezenweb.model.dto.BoardDto;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class BoardDao extends Dao {
    // 1. 글쓰기 처리
    public long doPostBoardWrite(BoardDto boardDto){
        System.out.println("BoardDao.doPostBoardWrite");
        System.out.println("boardDto = " + boardDto);

        try {
            String sql = "insert into board(btitle,bcontent,bfile,mno,bcno) values(?,?,?,?,?);";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, boardDto.getBtitle());
            ps.setString(2, boardDto.getBcontent());
            ps.setString(3, boardDto.getBfile());
            ps.setLong(4, boardDto.getMno());
            ps.setLong(5, boardDto.getBcno());

            int count = ps.executeUpdate();
            if(count==1){
                rs = ps.getGeneratedKeys();
                if(rs.next()){
                    return rs.getLong(1);
                }
            }

        } catch (Exception e){
            System.out.println("e = " + e);
        }

        return 0;
    }

    // 2. 전체 글 출력 호출
    public List<Object> doGetBoardViewList(int pageBoardSize, int startRow,int bcno, String key, String keyword){
        System.out.println("BoardDao.doGetBoardViewList");
        List<Object> list = new ArrayList<>();
        try {
            String sql = "select * from board b inner join member m on b.mno = m.no inner join bcategory bc on b.bcno = bc.bcno ";
            if(bcno > 0) sql += " where b.bcno = "+ bcno;
            if(!keyword.isEmpty()) sql += " and " + key + " like '%"+ keyword + "%'";
            sql += " order by b.bno desc limit ?,?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,startRow);
            ps.setInt(2,pageBoardSize);
            rs = ps.executeQuery();
            while (rs.next()){
                BoardDto boardDto = new BoardDto(
                        rs.getLong("bno"),
                        rs.getString("btitle"),
                        rs.getString("bcontent"),
                        rs.getString("bfile"),
                        rs.getLong("bview"),
                        rs.getString("bdate"),
                        rs.getLong("mno"),
                        rs.getLong("bcno"),
                        null,
                        rs.getString("id"),
                        rs.getString("img"),
                        rs.getString("bcname")
                );
                int compare  = boardDto.getBdate().split(" ")[0].compareTo( new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis())));
                if (compare == 0) boardDto.setBdate(boardDto.getBdate().split(" ")[1]);
                list.add(boardDto);
            }
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return list;
    }

    // 2-2 전체 게시물 수 호출
    public int getBoardSize(int bcno, String key, String keyword){
        try {
            String sql = "select count(*) from board b inner join member m on b.mno = m.no";
            if(bcno > 0) sql += " where b.bcno = "+ bcno;
            if(!keyword.isEmpty())sql += " and " + key + " like '%"+ keyword + "%'";
                    ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()){return rs.getInt(1);}
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return 0;
    }

    // 3. 개별 글 출력 호출
    public BoardDto doGetBoardView(int bno){
        System.out.println("BoardDao.doGetBoardView");
        System.out.println("bno = " + bno);

        BoardDto boardDto = null;

        try {
            String sql = "select * from board b inner join member m on b.mno = m.no inner join bcategory bc on b.bcno = bc.bcno where bno = ? order by b.bno desc;";
            ps = conn.prepareStatement(sql);
            ps.setLong(1,bno);
            rs = ps.executeQuery();
            if(rs.next()){
                boardDto = new BoardDto(
                    rs.getLong("bno"),
                    rs.getString("btitle"),
                    rs.getString("bcontent"),
                    rs.getString("bfile"),
                    rs.getLong("bview"),
                    rs.getString("bdate"),
                    rs.getLong("mno"),
                    rs.getLong("bcno"),
                    null,
                    rs.getString("id"),
                    rs.getString("img"),
                    rs.getString("bcname")
                );
            }
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        System.out.println(boardDto);
        return boardDto;
    }

    public boolean onBoardPlus(int bno){
        try {
            String sql = "select bview from board where bno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,bno);
            rs = ps.executeQuery();
            int bview = 0;
            if(rs.next()){
                bview = rs.getInt("bview");
            }
            sql = "update board set bview = ? where bno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,bview+1);
            ps.setInt(2,bno);
            int count = ps.executeUpdate();
            if(count == 1) return true;
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    // 4. 글 수정 처리
    public boolean doUpdateBoard(BoardDto boardDto){
        System.out.println("BoardDao.doUpdateBoard");
        System.out.println("boardDto = " + boardDto);
        try {
            String sql = "update board set btitle = ?,bcontent=?,bcno=?,bfile=? where bno = ?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,boardDto.getBtitle());
            ps.setString(2,boardDto.getBcontent());
            ps.setLong(3,boardDto.getBcno());
            ps.setString(4,boardDto.getBfile());
            ps.setLong(5,boardDto.getBno());
            int count = ps.executeUpdate();
            if(count == 1) return true;
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    // 5. 글 삭제 처리
    public boolean doDeleteBoard(int bno){
        System.out.println("BoardDao.doDeleteBoard");
        System.out.println("bno = " + bno);
        try {
            String sql = "delete from board where bno = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,bno);
            int count = ps.executeUpdate();
            if(count == 1) return true;
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return false;
    }

    // 6. 작성자 인증
    public boolean boardWriteAuth(long bno, String mid){
        try {
            String sql = "select * from board b inner join member m on b.mno = m.no where b.bno = ? and m.id = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1,bno);
            ps.setString(2,mid);
            rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (Exception e){
            System.out.println("e = " + e);
        }

        return false;
    }

    // 7. 댓글 등록
    public boolean postReplyWrite(Map<String,String> map){
        System.out.println("BoardDao.postReplyWrite");
        System.out.println("map = " + map);

        try {
            String sql = "insert into breply(brcontent,brindex,mno,bno) values(?,?,?,?)";
            ps = conn.prepareStatement(sql);
            ps.setString(1, map.get("brcontent"));
            ps.setString(2, map.get("brindex"));
            ps.setString(3, map.get("mno"));
            ps.setString(4, map.get("bno"));
            int count = ps.executeUpdate();
            if(count == 1) return true;
        } catch (Exception e){
            System.out.println("e = " + e);
        }

        return false;
    }

    // 8. 댓글 출력
    public List<Map<String,Object>> getReplyDo(int bno){
        System.out.println("BoardDao.getReplyDo");
        List<Map<String,Object>> list = new ArrayList<>();
        try {
            String sql = "select * from breply br inner join member m on br.mno = m.no where brindex=0 and bno = "+bno;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()){
                Map<String,Object> map = new HashMap<>();
                map.put("brno",rs.getString("brno"));
                map.put("brcontent",rs.getString("brcontent"));
                map.put("brindex",rs.getString("brindex"));
                map.put("mno",rs.getString("mno"));
                map.put("id",rs.getString("id"));
                map.put("brdate",rs.getString("brdate"));

                    String subSql = "select * from breply br inner join member m on br.mno = m.no where brindex = ? and bno = "+bno;
                    ps = conn.prepareStatement(subSql);
                    ps.setInt(1, rs.getInt("brno"));
                    ResultSet rs2 = ps.executeQuery();
                    List<Map<String,Object>> sublist = new ArrayList<>();
                    while (rs2.next()){
                        Map<String,Object> subMap = new HashMap<>();
                        subMap.put("brno",rs2.getString("brno"));
                        subMap.put("brcontent",rs2.getString("brcontent"));
                        subMap.put("brindex",rs2.getString("brindex"));
                        subMap.put("mno",rs2.getString("mno"));
                        subMap.put("id",rs2.getString("id"));
                        subMap.put("brdate",rs2.getString("brdate"));
                        sublist.add(subMap);
                    }
                map.put("subreply",sublist);
                list.add(map);
            }
        } catch (Exception e){
            System.out.println("e = " + e);
        }
        return list;
    }
}

